package com.ragnar.main.Util.Mappers;

import com.ragnar.main.API.DTOs.Book.BookResponseDTO;
import com.ragnar.main.API.DTOs.Book.CreateBookDTO;
import com.ragnar.main.Domain.Entities.Authors;
import com.ragnar.main.Domain.Entities.Books;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookMapper {
    public Books mapCreateDtoToEntity(CreateBookDTO dto, Authors authorModel) {
        return Books.builder()
                .bookName(dto.getBookName())
                .publicationYear(dto.getPublicationYear())
                .Genre(dto.getGenre())
                .Price(dto.getPrice())
                .author(authorModel)
                .build();
    }

    public BookResponseDTO mapToResDTO(Books model) {
        var authorDetail = new BookResponseDTO.AuthorDetail();
        authorDetail.setAuthorId(model.getAuthor().getAuthorId().toString());
        authorDetail.setAuthorName(model.getAuthor().getAuthorName());

        return BookResponseDTO.builder()
                .bookId(model.getBookId())
                .bookName(model.getBookName())
                .authorDetail(authorDetail)
                .genre(model.getGenre().name())
                .price(model.getPrice())
                .build();
    }

    public List<BookResponseDTO> mapResListToResDTO(List<Books> modelList){
        List<BookResponseDTO> bookList = new ArrayList<>();
        for (Books book : modelList) {
            bookList.add(mapToResDTO(book));
        }
        return bookList;
    }
}
