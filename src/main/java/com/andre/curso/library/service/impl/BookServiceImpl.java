package com.andre.curso.library.service.impl;

import com.andre.curso.library.exception.BusinessException;
import com.andre.curso.library.model.entity.Book;
import com.andre.curso.library.model.repository.BookRepository;
import com.andre.curso.library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 26/04/2020
 */
@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    final BookRepository repository;

    @Override
    public Book save(final Book book) {
        if (repository.existsByIsbn(book.getIsbn())){
            throw new BusinessException("Isbn já cadastrado");
        }
        return repository.save(book);
    }


}
