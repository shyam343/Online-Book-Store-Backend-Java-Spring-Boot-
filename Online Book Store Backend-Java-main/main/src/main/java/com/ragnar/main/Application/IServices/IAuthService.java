package com.ragnar.main.Application.IServices;

import com.ragnar.main.API.DTOs.Auth.LoginDTO;
import com.ragnar.main.API.DTOs.Auth.LoginResponseDTO;
import com.ragnar.main.API.DTOs.Auth.SignupDTO;
import com.ragnar.main.Util.CommonModels.ApiResponse;

public interface IAuthService {
    ApiResponse<LoginResponseDTO> LoginUser(LoginDTO dto);
    ApiResponse<?> SignupUser(SignupDTO dto);
    ApiResponse<?> SignupAdminUser(SignupDTO dto);
}
