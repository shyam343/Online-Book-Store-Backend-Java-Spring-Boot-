package com.ragnar.main.Services;

import com.ragnar.main.API.DTOs.Users.UpdateUserDTO;
import com.ragnar.main.API.DTOs.Users.UsersResponseDTO;
import com.ragnar.main.Application.IRepositories.IUserRepository;
import com.ragnar.main.Application.IServices.IUserService;
import com.ragnar.main.Domain.Entities.Users;
import com.ragnar.main.Util.CommonModels.ApiResponse;
import com.ragnar.main.Util.Constants.ResponseCode;
import com.ragnar.main.Util.Mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final UserMapper userMapper;
    private final String superadmin = "superadmin";

    @Override
    public ApiResponse<List<UsersResponseDTO>> GetAllUsers() {
        try {
            List<Users> usersList = userRepository.findAll()
                    .stream()
                    .filter(x -> !x.isDeleted() && !x.getUsername().equalsIgnoreCase(superadmin))
                    .toList();

            if (usersList.isEmpty()) {
                return ApiResponse.Failed("User list is empty", ResponseCode.HasNoContent);
            }

            List<UsersResponseDTO> mappedResult = userMapper.mapListToUserResDTO(usersList);
            return ApiResponse.Success(mappedResult, "Users fetched", ResponseCode.IsSuccess);
        } catch (Exception ex) {
            return ApiResponse.Failed("Failed to gett all users: " + ex.getMessage(), ResponseCode.IsFailed);
        }
    }

    @Override
    public ApiResponse<UsersResponseDTO> GetUserByUsername(String username) {
        try {
            Users userDetail = userRepository.findByUsername(username);
            if (userDetail == null || userDetail.isDeleted() || userDetail.getUsername().equalsIgnoreCase(superadmin)) {
                return ApiResponse.Failed("no user found", ResponseCode.IsNotFound);
            }
            UsersResponseDTO mappedRes = userMapper.mapToUserResDTO(userDetail);
            return ApiResponse.Success(mappedRes, "User detail fetched.", ResponseCode.IsSuccess);
        } catch (Exception ex) {
            return ApiResponse.Failed("Failed to get user by username: " + ex.getMessage(), ResponseCode.IsFailed);
        }
    }

    @Override
    public ApiResponse<UsersResponseDTO> UpdateUserInformation(UpdateUserDTO dto) {
        try {
            var existingUser = userRepository.findById(dto.getUserId()).orElse(null);
            if (existingUser == null) {
                return ApiResponse.Failed("no user found", ResponseCode.IsNotFound);
            }

            if (existingUser.getUserId() == dto.getUserId() && !existingUser.isDeleted()) {
                if (!dto.getEmail().isEmpty()) {
                    existingUser.setEmail(dto.getEmail());
                }
                if (!dto.getFirstName().isEmpty()) {
                    existingUser.setFirstName(dto.getFirstName());
                }
                if (!dto.getLastName().isEmpty()) {
                    existingUser.setLastName(dto.getLastName());
                }
                if (!dto.getAddress().isEmpty()) {
                    existingUser.setAddress(dto.getAddress());
                }

                var updatedUser = userRepository.saveAndFlush(existingUser);
                var mappedRes = userMapper.mapToUserResDTO(updatedUser);
                return ApiResponse.Success(mappedRes, "User updated successfully", ResponseCode.IsSuccess);
            }
            return ApiResponse.Failed("Invalid user", ResponseCode.IsFailed);
        } catch (Exception ex) {
            return ApiResponse.Failed("Failed to update user: " + ex.getMessage(), ResponseCode.IsFailed);
        }
    }

    @Override
    public ApiResponse<String> DeleteUser(Long id) {
        try {
            var existingUser = userRepository.findById(id).orElse(null);
            if (existingUser == null || existingUser.isDeleted()) {
                return ApiResponse.Failed("no user found", ResponseCode.IsNotFound);
            }

            if (existingUser.getUserId() == 1 || !existingUser.getUsername().equalsIgnoreCase(superadmin)) {
                return ApiResponse.Failed("Cannot delete superadmin", ResponseCode.IsFailed);
            }

            existingUser.setDeleted(true);
            userRepository.saveAndFlush(existingUser);
            return ApiResponse.Success(null, "User deleted successfully", ResponseCode.IsSuccess);
        } catch (Exception ex) {
            return ApiResponse.Failed("Failed to delete user: " + ex.getMessage(), ResponseCode.IsFailed);
        }
    }
}
