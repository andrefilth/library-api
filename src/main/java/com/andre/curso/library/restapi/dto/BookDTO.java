package com.andre.curso.library.restapi.dto;

import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotEmpty;

import static org.apache.commons.lang3.builder.ToStringStyle.*;

/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 22/04/2020
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {

    private Long id;
    @NotEmpty
    private String title;
    @NotEmpty
    private String author;
    @NotEmpty
    private String isbn;

    @Override
    public String toString() {
        return new ToStringBuilder(this, JSON_STYLE)
            .append("id", id)
            .append("title", title)
            .append("author", author)
            .append("isbn", isbn)
            .build();
    }

}
