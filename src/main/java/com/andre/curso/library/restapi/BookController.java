package com.andre.curso.library.restapi;

import com.andre.curso.library.exception.ApiErrors;
import com.andre.curso.library.exception.BusinessException;
import com.andre.curso.library.model.entity.Book;
import com.andre.curso.library.restapi.dto.BookDTO;
import com.andre.curso.library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.*;

/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 22/04/2020
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/books")
public class BookController {

    private static final Logger log = LoggerFactory.getLogger(BookController.class);

    private final BookService service;

    private final ModelMapper mapper;

    @PostMapping
    @ResponseStatus(CREATED)
    public BookDTO post(@RequestBody @Valid BookDTO bookDTO){

        log.info("Book: [ {} ] ", bookDTO);
        Book entity = mapper.map(bookDTO, Book.class);
        entity = service.save(entity);
        BookDTO dto = mapper.map(entity, BookDTO.class);
        log.info("DTO: [ {} ] ", dto);
        return dto;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public ApiErrors handleValidationException(MethodArgumentNotValidException ex){
        final BindingResult bindingResult = ex.getBindingResult();
        return new ApiErrors(bindingResult);
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(BAD_REQUEST)
    public ApiErrors handleValidationException(BusinessException ex){
        return new ApiErrors(ex);
    }
}
