package com.ragnar.main.API.Common;

import com.ragnar.main.API.DTOs.Auth.LoginDTO;
import com.ragnar.main.Application.IServices.IAuthService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class TestHelper {
    private IAuthService authService;

    public String GetJwt() {
        var model = LoginDTO.builder()
                .username("superadmin")
                .password("superadmin@123")
                .build();
        var response = authService.LoginUser(model);
        return response.getData().getToken();
    }

    public String GetTestUserJwt() {
        var model = LoginDTO.builder()
                .username("actualtestuser")
                .password("user@123")
                .build();
        var response = authService.LoginUser(model);
        return response.getData().getToken();
    }
}
