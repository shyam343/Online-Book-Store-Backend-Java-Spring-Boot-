package com.ragnar.main.API.Controllers;

import com.ragnar.main.API.DTOs.Book.BookResponseDTO;
import com.ragnar.main.API.DTOs.Book.CreateBookDTO;
import com.ragnar.main.API.DTOs.Book.UpdateBookDTO;
import com.ragnar.main.Application.IServices.IBookService;
import com.ragnar.main.Util.CommonModels.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/Book")
@RequiredArgsConstructor
public class BookController {
    private final IBookService bookService;

    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<?>> CreateBook(@RequestBody CreateBookDTO model) {
        var res = bookService.AddBook(model);
        return ResponseEntity.ok(res);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>> GetAllBooks() {
        var res = bookService.GetAllBooks();
        return ResponseEntity.ok(res);
    }

    @GetMapping("{id}")
    public ResponseEntity<ApiResponse<?>> GetBookById(@PathVariable Long id) {
        var res = bookService.GetBookById(id);
        return ResponseEntity.ok(res);
    }

    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_ADMIN')")
    @PutMapping
    public ResponseEntity<ApiResponse<?>> UpdateBook(@RequestBody UpdateBookDTO model) {
        var res = bookService.UpdateBook(model);
        return ResponseEntity.ok(res);
    }

    @PreAuthorize("hasRole('ROLE_SUPERADMIN') or hasRole('ROLE_ADMIN')")
    @DeleteMapping("{id}")
    public ResponseEntity<ApiResponse<?>> DeleteBook(@PathVariable Long id) {
        var res = bookService.DeleteBook(id);
        return ResponseEntity.ok(res);
    }

    @GetMapping("availableBooks")
    public ResponseEntity<ApiResponse<?>> GetAllAvailableBooks() {
        var res = bookService.GetAllAvailableBooks();
        return ResponseEntity.ok(res);
    }

    @GetMapping("unavailableBooks")
    public ResponseEntity<ApiResponse<?>> GetAllUnavailableBook() {
        var res = bookService.GetAllUnavailableBooks();
        return ResponseEntity.ok(res);
    }

    @GetMapping("search")
    public ResponseEntity<ApiResponse<List<BookResponseDTO>>> SearchBook(@RequestParam String name) {
        var res = bookService.GetFilteredBooks(name);
        return ResponseEntity.ok(res);
    }
}
