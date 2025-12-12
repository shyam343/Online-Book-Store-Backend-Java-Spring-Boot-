package com.ragnar.main.API.Controllers;

import com.ragnar.main.API.DTOs.Users.UpdateUserDTO;
import com.ragnar.main.API.DTOs.Users.UsersResponseDTO;
import com.ragnar.main.Application.IServices.IUserService;
import com.ragnar.main.Util.CommonModels.ApiResponse;
import com.ragnar.main.Util.Helpers.AuthUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/User")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;
    private final AuthUtil authUtil;

    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_ADMIN')")
    @GetMapping("get-all")
    public ResponseEntity<ApiResponse<List<UsersResponseDTO>>> GetAllUsers() {
        var response = userService.GetAllUsers();
        return ResponseEntity.ok(response);
    }

    @GetMapping("{username}")
    public ResponseEntity<ApiResponse<UsersResponseDTO>> GetUserDetail(@PathVariable String username) {
        var response = userService.GetUserByUsername(username);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<ApiResponse<UsersResponseDTO>> UpdateUser(@Valid @RequestBody UpdateUserDTO model, Principal principal) {
        model.setUserId(authUtil.getUserIdFromPrincipal(principal));
        var response = userService.UpdateUserInformation(model);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<String>> DeleteUser(@PathVariable Long id) {
        var response = userService.DeleteUser(id);
        return ResponseEntity.ok(response);
    }
}
