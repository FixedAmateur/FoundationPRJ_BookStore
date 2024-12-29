package com.foundationProject.bookstore.service;

import com.foundationProject.bookstore.model.dto.BookDto;
import com.foundationProject.bookstore.model.response.BookResponse;
import com.foundationProject.bookstore.model.response.PageCustom;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookResponse createBook(BookDto bookDto);

    BookResponse getBook(Long bookId);

    BookResponse updateBook(Long bookId, BookDto bookDto);


    String deleteBook(Long bookId);

    PageCustom<BookResponse> getBookByTitle(String bookTitle, Pageable pageable);

    PageCustom<BookResponse> getAllBook(Pageable pageable);

    PageCustom<BookResponse> getAllBookByAuthorId(String authorName, Pageable pageable);

    PageCustom<BookResponse> getAllBookByCategory(String categoryName, Pageable pageable);
}
