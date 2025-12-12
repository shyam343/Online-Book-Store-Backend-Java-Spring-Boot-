package com.ragnar.main.Application.IServices;

import com.ragnar.main.API.DTOs.Users.UpdateUserDTO;
import com.ragnar.main.API.DTOs.Users.UsersResponseDTO;
import com.ragnar.main.Util.CommonModels.ApiResponse;

import java.util.List;

public interface IUserService {
    ApiResponse<List<UsersResponseDTO>> GetAllUsers();
    ApiResponse<UsersResponseDTO> GetUserByUsername(String username);
    ApiResponse<UsersResponseDTO> UpdateUserInformation(UpdateUserDTO dto);
    ApiResponse<String> DeleteUser(Long id);

    // Admin user specific
}
