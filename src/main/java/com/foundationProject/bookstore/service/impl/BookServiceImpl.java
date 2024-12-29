package com.foundationProject.bookstore.service.impl;

import com.foundationProject.bookstore.model.response.PageCustom;
import com.foundationProject.bookstore.model.entity.Author;
import com.foundationProject.bookstore.model.entity.Book;
import com.foundationProject.bookstore.model.entity.Category;
import com.foundationProject.bookstore.exception.AppException;
import com.foundationProject.bookstore.exception.ErrorCode;
import com.foundationProject.bookstore.repository.AuthorRepository;
import com.foundationProject.bookstore.repository.CategoryRepository;
import com.foundationProject.bookstore.service.BookService;
import com.foundationProject.bookstore.model.dto.BookDto;
import com.foundationProject.bookstore.model.response.BookResponse;
import com.foundationProject.bookstore.exception.ResourceNotFoundException;
import com.foundationProject.bookstore.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public BookResponse createBook(BookDto bookDto) {
        if (bookRepository.existsByBookTitle(bookDto.getBookTitle())) {
            throw new AppException(ErrorCode.BOOK_EXISTED);
        }
        Book book = modelMapper.map(bookDto, Book.class);
        Optional<Author> author=authorRepository.findByAuthorName(bookDto.getAuthorName());
        if (author.isPresent()){
            book.setAuthor(author.get());
        }
        else {
            Author savingAuthor = new Author();
            savingAuthor.setAuthorName(bookDto.getAuthorName());
            authorRepository.save(savingAuthor);
            book.setAuthor(savingAuthor);
        }
        Set<Category> categories = new HashSet<>();
        for(String categoryName: bookDto.getCategoryNames()){
            Optional<Category> category = categoryRepository.findByCategoryName(categoryName);
            if (category.isPresent()){

                 categories.add(category.get());
            }
            else
            {
                Category savingCategory = new Category();
                savingCategory.setCategoryName(categoryName);
                categoryRepository.save(savingCategory);
                categories.add(savingCategory);
            }


        }
        book.setCategories(categories);
        return modelMapper.map(bookRepository.save(book), BookResponse.class);
    }

    @Override
    public BookResponse getBook(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(()->new ResourceNotFoundException("book","id",bookId));
        return modelMapper.map(book, BookResponse.class);
    }

    @Override
    public BookResponse updateBook(Long bookId, BookDto bookDto) {
        if (bookRepository.existsByBookTitle(bookDto.getBookTitle())) {
            throw new AppException(ErrorCode.BOOK_EXISTED);
        }
        Book book = bookRepository.findById(bookId).orElseThrow(()->new ResourceNotFoundException("book","id",bookId));
        modelMapper.map(bookDto, book);
        Optional<Author> author=authorRepository.findByAuthorName(bookDto.getAuthorName());
        if (author.isPresent()){
            book.setAuthor(author.get());
        }
        else {
            Author savingAuthor = new Author();
            savingAuthor.setAuthorName(bookDto.getAuthorName());
            authorRepository.save(savingAuthor);
            book.setAuthor(savingAuthor);
        }
        Set<Category> categories = new HashSet<>();
        for(String categoryName: bookDto.getCategoryNames()){
            Optional<Category> category = categoryRepository.findByCategoryName(categoryName);
            if (category.isPresent()){

                categories.add(category.get());
            }
            else
            {
                Category savingCategory = new Category();
                savingCategory.setCategoryName(categoryName);
                categoryRepository.save(savingCategory);
                categories.add(savingCategory);
            }


        }
        book.setCategories(categories);
        return modelMapper.map(bookRepository.save(book), BookResponse.class);
    }
    @Override
    public String deleteBook(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(()->new ResourceNotFoundException("book","id",bookId));
        bookRepository.delete(book);
        return "Book deleted successfully";
    }



    @Override
    public PageCustom<BookResponse> getBookByTitle(String bookTitle, Pageable pageable) {
        Page<Book> page = bookRepository.findAllByBookTitleContainingIgnoreCase(bookTitle, pageable);
        PageCustom<BookResponse> pageCustom = PageCustom.<BookResponse>builder()
                .pageNo(page.getNumber() + 1)
                .pageSize(page.getSize())
                .pageContent(page.getContent().stream().map(book->modelMapper.map(book, BookResponse.class)).toList())
                .totalPages(page.getTotalPages())
                .build();
        return pageCustom;
    }

    @Override
    public PageCustom<BookResponse> getAllBook(Pageable pageable) {
        Page<Book> page = bookRepository.findAll(pageable);
        PageCustom<BookResponse> pageCustom = PageCustom.<BookResponse>builder()
                .pageNo(page.getNumber() + 1)
                .pageSize(page.getSize())
                .pageContent(page.getContent().stream().map(book->modelMapper.map(book, BookResponse.class)).toList())
                .totalPages(page.getTotalPages())
                .build();
        return pageCustom;
    }

    @Override
    public PageCustom<BookResponse> getAllBookByAuthorId(String AuthorName, Pageable pageable) {
        Author author = authorRepository.findByAuthorName(AuthorName).orElseThrow(()->new ResourceNotFoundException("author","name",AuthorName));
        Page<Book> page = bookRepository.findAllByAuthor(author, pageable);
        PageCustom<BookResponse> pageCustom = PageCustom.<BookResponse>builder()
                .pageNo(page.getNumber() + 1)
                .pageSize(page.getSize())
                .pageContent(page.getContent().stream().map(book->modelMapper.map(book, BookResponse.class)).toList())
                .totalPages(page.getTotalPages())
                .build();
        return pageCustom;
    }

    @Override
    public PageCustom<BookResponse> getAllBookByCategory(String categoryName, Pageable pageable) {
        Category category = categoryRepository.findByCategoryName(categoryName).orElseThrow(()->new ResourceNotFoundException("category","name",categoryName));
        Page<Book> page = bookRepository.findAllByCategories(category, pageable);
        PageCustom<BookResponse> pageCustom = PageCustom.<BookResponse>builder()
                .pageNo(page.getNumber() + 1)
                .pageSize(page.getSize())
                .pageContent(page.getContent().stream().map(book->modelMapper.map(book, BookResponse.class)).toList())
                .totalPages(page.getTotalPages())
                .build();
        return pageCustom;
    }

}
