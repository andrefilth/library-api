package com.andre.curso.library.model.repository;

import com.andre.curso.library.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 26/04/2020
 */
public interface BookRepository extends JpaRepository<Book, Long> {

    boolean existsByIsbn(String isbn);

}
