package com.ragnar.main.API.Controllers;

import com.ragnar.main.API.DTOs.Authors.CreateAuthorDTO;
import com.ragnar.main.API.DTOs.Authors.UpdateAuthorDTO;
import com.ragnar.main.Application.IServices.IAuthorService;
import com.ragnar.main.Util.CommonModels.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/Author")
@RequiredArgsConstructor
public class AuthorController {
    private final IAuthorService authorService;

    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<?>> CreateAuthor(@RequestBody CreateAuthorDTO model) {
        var res = authorService.AddAuthor(model);
        return ResponseEntity.ok(res);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> GetAllAuthors() {
        var res = authorService.GetAllAuthors();
        return ResponseEntity.ok(res);
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<?>> GetAuthorById(@PathVariable Long id) {
        var res = authorService.GetAuthorById(id);
        return ResponseEntity.ok(res);
    }

    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_ADMIN')")
    @PutMapping
    public ResponseEntity<ApiResponse<?>> UpdateAuthor(@RequestBody UpdateAuthorDTO model) {
        var res = authorService.UpdateAuthor(model);
        return ResponseEntity.ok(res);
    }

    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<?>> DeleteAuthor(@PathVariable Long id) {
        var res = authorService.DeleteAuthor(id);
        return ResponseEntity.ok(res);
    }
}
