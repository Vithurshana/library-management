package com.example.library.management.dtos.responses.books;
import com.example.library.management.dtos.books.Book;
import com.example.library.management.entities.BookEntity;
import lombok.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookListResponse {
    private List<Book> books;
}
