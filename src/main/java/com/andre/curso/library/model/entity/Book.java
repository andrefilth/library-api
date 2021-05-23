package com.andre.curso.library.model.entity;

import lombok.*;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 26/04/2020
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Book {

    @Id
    @Column
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column
    private String title;
    @Column
    private String author;
    @Column
    private String isbn;

}
