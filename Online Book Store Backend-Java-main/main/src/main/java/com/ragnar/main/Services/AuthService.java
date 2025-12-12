package com.ragnar.main.Services;

import com.ragnar.main.API.DTOs.Auth.LoginDTO;
import com.ragnar.main.API.DTOs.Auth.LoginResponseDTO;
import com.ragnar.main.API.DTOs.Auth.SignupDTO;
import com.ragnar.main.Application.IRepositories.IRoleRepository;
import com.ragnar.main.Application.IRepositories.IUserRepository;
import com.ragnar.main.Application.IServices.IAuthService;
import com.ragnar.main.Domain.Entities.Users;
import com.ragnar.main.Domain.Enums.RoleType;
import com.ragnar.main.Util.CommonModels.ApiResponse;
import com.ragnar.main.Util.Constants.ResponseCode;
import com.ragnar.main.Util.Helpers.DbLogger;
import com.ragnar.main.Util.Helpers.JwtHelper;
import com.ragnar.main.Util.Mappers.AuthMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.logging.LogLevel;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {
    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final AuthMapper authMapper;
    private final DbLogger logger;

    // Auth
    private final AuthenticationManager authManager;
    private final JwtHelper jwtHelper;


    @Override
    public ApiResponse<LoginResponseDTO> LoginUser(LoginDTO dto) {
        try {
            Authentication authentication;

            try {
                authentication = authManager.authenticate(
                        new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
                );
            } catch (Exception ex) {
                return ApiResponse.Failed("Invalid user.", ResponseCode.IsUnauthorized);
            }

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtHelper.generateToken(userDetails);

            if (token.isBlank() || token.isEmpty()) {
                return ApiResponse.Failed("User auth failed.", ResponseCode.IsFailed);
            }

            LoginResponseDTO response = LoginResponseDTO.builder()
                    .token(token)
                    .build();

            return ApiResponse.Success(response, "User authenticated.", ResponseCode.IsSuccess);
        } catch (Exception ex) {
            logger.log(LogLevel.ERROR, ex.getMessage(), "AuthService_LoginUser", ex);
            return ApiResponse.Failed("Exception occured when authentcatinig.", ResponseCode.IsError);
        }
    }

    @Override
    public ApiResponse<?> SignupUser(SignupDTO dto) {
        return SignupUserWithRole(dto, RoleType.ROLE_USER);
    }

    @Override
    public ApiResponse<?> SignupAdminUser(SignupDTO dto) {
        return SignupUserWithRole(dto, RoleType.ROLE_ADMIN);
    }

    // Helper method
    public ApiResponse<?> SignupUserWithRole(SignupDTO dto, RoleType role) {
        try {
            Users userModel = authMapper.mapSignupToUsers(dto);

            // <editor-fold desc="Assign ROLE_USER to new user">
            var getUserRole = roleRepository.findByRoleName(role);
            if (getUserRole.isEmpty()) {
                return ApiResponse.Failed("Role " + role + " does not exist", ResponseCode.IsNotFound);
            }
            userModel.setRoles(List.of(getUserRole.get()));
            // </editor-fold>

            Users savedUser = userRepository.saveAndFlush(userModel);
            return ApiResponse.Success(savedUser, "User created successfully", ResponseCode.IsSuccess);
        } catch (DataIntegrityViolationException e) {
            return ApiResponse.Failed("Username or Email already exists", ResponseCode.IsFailed);
        } catch (Exception ex) {
            return ApiResponse.Failed("User creation failed: " + ex.getMessage(), ResponseCode.IsFailed);
        }
    }
}
