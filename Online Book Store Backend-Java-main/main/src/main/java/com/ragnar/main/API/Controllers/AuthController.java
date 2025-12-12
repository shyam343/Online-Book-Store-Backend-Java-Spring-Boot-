package com.ragnar.main.API.Controllers;

import com.ragnar.main.API.DTOs.Auth.LoginDTO;
import com.ragnar.main.API.DTOs.Auth.LoginResponseDTO;
import com.ragnar.main.API.DTOs.Auth.SignupDTO;
import com.ragnar.main.Application.IServices.IAuthService;
import com.ragnar.main.Util.CommonModels.ApiResponse;
import com.ragnar.main.Util.Constants.ResponseCode;
import com.ragnar.main.Util.Helpers.DbLogger;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/Auth/")
@RequiredArgsConstructor
public class AuthController {
    private final IAuthService authService;
    private final DbLogger logger;

    @PostMapping("register")
    public ResponseEntity<ApiResponse<?>> Signup(@Valid @RequestBody SignupDTO model) {
        try {
            var response = authService.SignupUser(model);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            logger.log(LogLevel.ERROR, ex.getMessage(), "AuthController_Signup", ex);
            return ResponseEntity.ok(ApiResponse.Failed("Exception occured.", ResponseCode.IsError));
        }
    }

    @PostMapping("login")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> Login(@RequestBody LoginDTO model) {
        var response = authService.LoginUser(model);
        return ResponseEntity.ok(response);
    }
}
