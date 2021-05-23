package com.andre.curso.library.api.resource;

import com.andre.curso.library.exception.BusinessException;
import com.andre.curso.library.model.entity.Book;
import com.andre.curso.library.restapi.dto.BookDTO;
import com.andre.curso.library.service.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Class comments go here...
 *
 * @author André Franco
 * @version 1.0 16/04/2020
 */
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@WebMvcTest
@AutoConfigureMockMvc
public class BookControllerTest {

    private static final String BOOK_API = "/api/books";

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    BookService bookService;

    @Test
    @DisplayName("Deve criar um livro com sucesso")
    public void createBookTest() throws Exception {

        final BookDTO bookDTO = createNewBook();
        final Book savedBook = Book
            .builder()
            .id(101L)
            .title("Meu Livro")
            .author("Autor")
            .isbn("1213212")
            .build();
        BDDMockito
            .given(bookService.save(Mockito.any(Book.class))).willReturn(savedBook);

        final MockHttpServletRequestBuilder requestBuilder = requestBookPost(bookDTO);
        mockMvc
            .perform(requestBuilder)
            .andExpect(status().isCreated())
            .andExpect(jsonPath("id").isNotEmpty())
            .andExpect(jsonPath("title").value("Meu Livro"))
            .andExpect(jsonPath("author").value("Autor"))
            .andExpect(jsonPath("isbn").value("1213212"))
            ;
    }

    @Test
    @DisplayName("Deve lançar erro de validação quando não houver dados suficientes de livro")
    public void createInvalidBookTest() throws Exception {

        final MockHttpServletRequestBuilder requestBuilder = requestBookPost(new BookDTO());

        mockMvc
            .perform(requestBuilder)
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("errors", hasSize(3)))
            ;
    }

    @Test
    @DisplayName("Deve lançar erro ao tentar cadastrar um livro com isbn já utilizado por outro")
    public void createBookWithDuplicatedIsbn() throws Exception {

        final BookDTO bookDTO = createNewBook();
        BDDMockito.given(bookService.save(Mockito.any(Book.class))).willThrow(new BusinessException(msgError()));

        final MockHttpServletRequestBuilder requestBuilder = requestBookPost(bookDTO);

        mockMvc
            .perform(requestBuilder)
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("errors", hasSize(1)))
            .andExpect(jsonPath("errors[0]").value(msgError()))

            ;
    }

    private String msgError() {
        return "Isbn já cadastrado";
    }

    private BookDTO createNewBook() {
        return BookDTO
            .builder()
            .title("Artur")
            .author("As aventuras")
            .isbn("001")
            .build();
    }

    private MockHttpServletRequestBuilder requestBookPost(final BookDTO bookDTO) throws JsonProcessingException {
        final String json = new ObjectMapper().writeValueAsString(bookDTO);
        return post(BOOK_API)
            .contentType(APPLICATION_JSON)
            .accept(APPLICATION_JSON)
            .content(json);
    }

}
