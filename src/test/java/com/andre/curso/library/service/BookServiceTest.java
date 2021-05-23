package com.andre.curso.library.service;

import com.andre.curso.library.exception.BusinessException;
import com.andre.curso.library.model.entity.Book;
import com.andre.curso.library.model.repository.BookRepository;
import com.andre.curso.library.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 26/04/2020
 */
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
class BookServiceTest {

    BookService service;
    @MockBean
    BookRepository repository;

    @BeforeEach
    public void setUp(){
        this.service = new BookServiceImpl(repository);
    }

    @Test
    @DisplayName("should return 200 success then Permission By Consumer")
    public void savedBook(){
        // cenário
        final Book book = createBook();
        final Book savedBookReturn = Book.builder().id(11L).isbn("123").author("Fulano").title("As aventuras").build();
        when(repository.save(book)).thenReturn(savedBookReturn);
        when(repository.existsByIsbn(anyString())).thenReturn(false);

        // execução
        Book savedBook = service.save(book);

        // verificação
        assertThat(savedBook.getId()).isNotNull();
        assertThat(savedBook.getAuthor()).isEqualTo("Fulano");
        assertThat(savedBook.getTitle()).isEqualTo("As aventuras");
        assertThat(savedBook.getIsbn()).isEqualTo("123");

    }

    @Test
    @DisplayName("deve lançar erro de negócio ao tenytar salvar um livro com isbn duplicado")
    public void shouldNotSavedABookWithDuplicatedISBN(){
        // cenario
        // cenário
        final Book book = createBook();
        when(repository.existsByIsbn(anyString())).thenReturn(true);

        final Throwable exeception = catchThrowable(() -> service.save(book));
        assertThat(exeception)
            .isInstanceOf(BusinessException.class)
            .hasMessage("Isbn já cadastrado")
            ;
        verify(repository, never()).save(book);
    }

    private Book createBook() {
        return Book.builder().isbn("123").author("Fulano").title("As aventuras").build();
    }
}
