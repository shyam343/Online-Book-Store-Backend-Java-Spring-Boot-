package com.ragnar.main.API.Controllers;

import com.ragnar.main.API.DTOs.Auth.SignupDTO;
import com.ragnar.main.Application.IServices.IAuthService;
import com.ragnar.main.Util.CommonModels.ApiResponse;
import com.ragnar.main.Util.Constants.ResponseCode;
import com.ragnar.main.Util.Helpers.DbLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/Admin")
@PreAuthorize("hasRole('ROLE_SUPERADMIN')")
@RequiredArgsConstructor
public class AdminController {
    private final IAuthService authService;
    private final DbLogger logger;

    @PostMapping("create-admin")
    public ResponseEntity<ApiResponse<?>> CreateAdmin(@RequestBody SignupDTO model) {
        try {
            var response = authService.SignupAdminUser(model);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            logger.log(LogLevel.ERROR, ex.getMessage(), "AuthController_Signup", ex);
            return ResponseEntity.ok(ApiResponse.Failed("Exception occured.", ResponseCode.IsError));
        }
    }
}
