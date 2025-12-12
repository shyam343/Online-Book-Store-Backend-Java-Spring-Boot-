package com.ragnar.main.Application.IServices;

import com.ragnar.main.API.DTOs.Book.BookResponseDTO;
import com.ragnar.main.API.DTOs.Book.CreateBookDTO;
import com.ragnar.main.API.DTOs.Book.UpdateBookDTO;
import com.ragnar.main.Util.CommonModels.ApiResponse;

import java.util.List;

public interface IBookService {
    ApiResponse<BookResponseDTO> AddBook(CreateBookDTO dto);
    ApiResponse<List<BookResponseDTO>> GetAllBooks();
    ApiResponse<BookResponseDTO> GetBookById(Long bookId);
    ApiResponse<BookResponseDTO> UpdateBook(UpdateBookDTO dto);
    ApiResponse<?> DeleteBook(Long bookId);
    ApiResponse<List<BookResponseDTO>> GetAllAvailableBooks();
    ApiResponse<List<BookResponseDTO>> GetAllUnavailableBooks();
    ApiResponse<List<BookResponseDTO>> GetFilteredBooks(String name);
}
