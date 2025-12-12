package com.ragnar.main.Services;

import com.ragnar.main.API.DTOs.Book.BookResponseDTO;
import com.ragnar.main.API.DTOs.Book.CreateBookDTO;
import com.ragnar.main.API.DTOs.Book.UpdateBookDTO;
import com.ragnar.main.Application.IRepositories.IAuthorRepository;
import com.ragnar.main.Application.IRepositories.IBookRepository;
import com.ragnar.main.Application.IServices.IBookService;
import com.ragnar.main.Domain.Entities.Books;
import com.ragnar.main.Util.CommonModels.ApiResponse;
import com.ragnar.main.Util.Constants.ResponseCode;
import com.ragnar.main.Util.Helpers.DbLogger;
import com.ragnar.main.Util.Mappers.BookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService implements IBookService {
    private final IBookRepository bookRepository;
    private final IAuthorRepository authorRepository;
    private final BookMapper bookMapper;
    private final DbLogger logger;

    @Override
    public ApiResponse<BookResponseDTO> AddBook(CreateBookDTO dto) {
        try {
            var author = authorRepository.findById(dto.getAuthorId()).orElse(null);
            if (author == null) {
                return ApiResponse.Failed("author id is incorrect", ResponseCode.IsNotFound);
            }

            var bookModel = bookMapper.mapCreateDtoToEntity(dto, author);
            var savedBook = bookRepository.saveAndFlush(bookModel);

            var mappedResModel = bookMapper.mapToResDTO(savedBook);
            return ApiResponse.Success(mappedResModel, "Book created successfullyy", ResponseCode.IsCreated);
        } catch (Exception ex) {
            logger.log(LogLevel.ERROR, ex.getMessage(), "BookService_AddBook", ex);
            return ApiResponse.Failed("Failed to create book: " + ex.getMessage(), ResponseCode.IsFailed);
        }
    }

    @Override
    public ApiResponse<List<BookResponseDTO>> GetAllBooks() {
        try {
            var books = bookRepository.findAll();
            if (!books.isEmpty()) {
                var mappedList = bookMapper.mapResListToResDTO(books);
                return ApiResponse.Success(mappedList, "Books fetched", ResponseCode.IsSuccess);
            }
            return ApiResponse.Failed("no author foung", ResponseCode.IsNotFound);
        } catch (Exception ex) {
            logger.log(LogLevel.ERROR, ex.getMessage(), "BookService_GetAllBooks", ex);
            return ApiResponse.Failed("Failed to get books: " + ex.getMessage(), ResponseCode.IsFailed);
        }
    }

    @Override
    public ApiResponse<BookResponseDTO> GetBookById(Long bookId) {
        try {
            Books book = bookRepository.findById(bookId).orElse(null);
            if (book != null) {
                var mappedModel = bookMapper.mapToResDTO(book);
                return ApiResponse.Success(mappedModel, "Author detail fetched", ResponseCode.IsSuccess);
            }
            return ApiResponse.Failed("no book found", ResponseCode.IsNotFound);
        } catch (Exception ex) {
            logger.log(LogLevel.ERROR, ex.getMessage(), "BookService_GetBookById", ex);
            return ApiResponse.Failed("Failed to get boko: " + ex.getMessage(), ResponseCode.IsFailed);
        }
    }

    @Override
    public ApiResponse<BookResponseDTO> UpdateBook(UpdateBookDTO dto) {
        try {
            var existingBook = bookRepository.findById(dto.getBookId()).orElse(null);

            if (existingBook == null ) {
                return ApiResponse.Failed("No book found", ResponseCode.IsNotFound);
            }

            if (dto.getBookName() != null && !dto.getBookName().isEmpty() && !dto.getBookName().isBlank()) {
                existingBook.setBookName(dto.getBookName());
            }
            if (dto.getPublicationYear() != null) {
                existingBook.setPublicationYear(dto.getPublicationYear());
            }
            if (dto.getGenre() != null) {
                existingBook.setGenre(dto.getGenre());
            }
            if (dto.getPrice() != null && !dto.getPrice().isEmpty() && !dto.getPrice().isBlank()) {
                try {
                    double parsedPrice = Double.parseDouble(dto.getPrice());
                    existingBook.setPrice(parsedPrice);
                } catch (NumberFormatException ex) {
                    return ApiResponse.Failed("Incorrect price: " + ex.getMessage(), ResponseCode.IsFailed);
                }
            }
            if (dto.getAuthorId() != null && !dto.getAuthorId().isEmpty() && !dto.getAuthorId().isBlank()) {
                try {
                    Long parsedAuthorId = Long.parseLong(dto.getAuthorId());
                    var author = authorRepository.findById(parsedAuthorId).orElse(null);

                    if (author == null) {
                        return ApiResponse.Failed("no author with provided author id found", ResponseCode.IsNotFound);
                    }
                    existingBook.setAuthor(author);
                } catch (NumberFormatException ex) {
                    return ApiResponse.Failed("failed to update book;s author: " + ex.getMessage(), ResponseCode.IsFailed);
                }
            }
            bookRepository.saveAndFlush(existingBook);
            var mappedModel = bookMapper.mapToResDTO(existingBook);
            return ApiResponse.Success(mappedModel, "Book updated", ResponseCode.IsSuccess);
        } catch (Exception ex) {
            logger.log(LogLevel.ERROR, ex.getMessage(), "BookService_UpdateBook", ex);
            return ApiResponse.Failed("Failed to update book: " + ex.getMessage(), ResponseCode.IsError);
        }
    }

    @Override
    public ApiResponse<?> DeleteBook(Long bookId) {
        try {
            Books existingBook = bookRepository.findById(bookId).orElse(null);
            if (existingBook == null) {
                return ApiResponse.Failed("No book found", ResponseCode.IsNotFound);
            }
            bookRepository.delete(existingBook);
            bookRepository.flush();

            return ApiResponse.Success(null, "Book deleted with book id: " + bookId, ResponseCode.IsSuccess);
        } catch (Exception ex) {
            logger.log(LogLevel.ERROR, ex.getMessage(), "BookService_DeleteBook", ex);
            return ApiResponse.Failed("Failed to delete book: " + ex.getMessage(), ResponseCode.IsFailed);
        }
    }

    @Override
    public ApiResponse<List<BookResponseDTO>> GetAllAvailableBooks() {
        try {
            var availableBooks = bookRepository.GetAllAvailableBooks();
            if (availableBooks == null) {
                return ApiResponse.Failed("No available book found", ResponseCode.IsNotFound);
            }

            var mappedResult = bookMapper.mapResListToResDTO(availableBooks);
            return ApiResponse.Success(mappedResult, "All available books listed", ResponseCode.IsSuccess);
        } catch (Exception ex) {
            logger.log(LogLevel.ERROR, ex.getMessage(), "BookService_GetAllAvailableBooks", ex);
            return ApiResponse.Failed("Failed to get all available books: " + ex.getMessage(), ResponseCode.IsFailed);
        }
    }

    @Override
    public ApiResponse<List<BookResponseDTO>> GetAllUnavailableBooks() {
        try {
            var availableBooks = bookRepository.GetAllUnavailableBooks();
            if (availableBooks == null) {
                return ApiResponse.Failed("No books found", ResponseCode.IsNotFound);
            }

            var mappedResult = bookMapper.mapResListToResDTO(availableBooks);
            return ApiResponse.Success(mappedResult, "All unavailable books listed", ResponseCode.IsSuccess);
        } catch (Exception ex) {
            logger.log(LogLevel.ERROR, ex.getMessage(), "BookService_GetAllUnavailableBooks", ex);
            return ApiResponse.Failed("Failed to get all unavailable books: " + ex.getMessage(), ResponseCode.IsFailed);
        }


    }

    @Override
    public ApiResponse<List<BookResponseDTO>> GetFilteredBooks(String name) {
        var filteredBooks = bookRepository.findByBookNameContainingIgnoreCase(name);
        if (filteredBooks.isEmpty()) {
            return ApiResponse.Failed("No books found", ResponseCode.IsNotFound);
        }

        var mappedRes = bookMapper.mapResListToResDTO(filteredBooks);
        return ApiResponse.Success(mappedRes, "All filtered books listed", ResponseCode.IsSuccess);
    }
}
