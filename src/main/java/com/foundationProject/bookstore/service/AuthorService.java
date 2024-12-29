package com.foundationProject.bookstore.service;

import com.foundationProject.bookstore.model.dto.AuthorDto;
import com.foundationProject.bookstore.model.response.AuthorResponse;

import java.util.List;

public interface AuthorService {
    AuthorResponse createAuthor(AuthorDto authorDto);

    AuthorResponse getAuthor(Long authorId);

    String deleteAuthor(Long authorId);

    AuthorResponse updateAuthor(Long authorId, AuthorDto authorDto);

    List<AuthorResponse> getAllAuthors();
}
