package com.andre.curso.library.model.repository;

import com.andre.curso.library.model.entity.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 17/05/2020
 */
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    TestEntityManager manager;

    @Autowired
    BookRepository repository;

    @Test
    @DisplayName("Deve retornar verdadeiro quando existir o livro na base com o ISBN informado")
    public void returnTrueWhenIsbnExists(){

        final String isbn = "123";
        final Book entity = Book.builder().title("Aventuras").author("Fulano").isbn(isbn).build();
        manager.persist(entity);

        final boolean exists = repository.existsByIsbn(isbn);

        assertThat(exists).isTrue();
    }
    @Test
    @DisplayName("Deve retornar verdadeiro quando existir o livro na base com o ISBN informado")
    public void returnFalseWhenIsbnExists(){

        final String isbn = "123";
        final Book entity = Book.builder().title("Aventuras").author("Fulano").isbn("12345").build();
        manager.persist(entity);

        final boolean exists = repository.existsByIsbn(isbn);

        assertThat(exists).isFalse();
    }

}
