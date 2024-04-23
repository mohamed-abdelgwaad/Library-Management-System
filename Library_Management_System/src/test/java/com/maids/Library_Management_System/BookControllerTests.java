package com.maids.Library_Management_System;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maids.Library_Management_System.Controllers.BookController;
import com.maids.Library_Management_System.Models.Book;
import com.maids.Library_Management_System.Repositories.RoleRepository;
import com.maids.Library_Management_System.Services.BookService;


@WithMockUser(username="admin", roles={"USER", "ADMIN"})
@WebMvcTest(BookController.class)
public class BookControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BookService bookService;
	@MockBean
	private RoleRepository roleRepository;

	@Test
	public void testGetAllBooks() throws Exception {
		List<Book> books = Arrays.asList(new Book(1L, "Book One", "Author One", 2001, "1234567890123"),
				new Book(2L, "Book Two", "Author Two", 2002, "1234567890124"));

		given(bookService.findAllBooks()).willReturn(books);

		mockMvc.perform(get("/user/api/books").contentType(MediaType.APPLICATION_JSON).with(csrf())).andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2))).andExpect(jsonPath("$[0].title", is("Book One")))
				.andExpect(jsonPath("$[1].title", is("Book Two")));
	}

	@Test
	public void testGetBookById_WhenFound() throws Exception {
		Long bookId = 1L;
		Book book = new Book(bookId, "Book One", "Author One", 2001, "1234567890123");

		given(bookService.findBookById(bookId)).willReturn(Optional.of(book));

		mockMvc.perform(get("/user/api/books/{id}", bookId).contentType(MediaType.APPLICATION_JSON).with(csrf()))
				.andExpect(status().isOk()).andExpect(jsonPath("$.title", is("Book One")));
	}

	@Test
	public void testGetBookById_WhenNotFound() throws Exception {
		Long bookId = 1L;
		given(bookService.findBookById(bookId)).willReturn(Optional.empty());

		mockMvc.perform(get("/user/api/books/{id}", bookId).contentType(MediaType.APPLICATION_JSON).with(csrf()))
				.andExpect(status().isNotFound());
	}

	@Test
	public void testCreateBook() throws Exception {
		Book newBook = new Book(null, "New Book", "New Author", 2023, "9876543210123");
		Book savedBook = new Book(3L, "New Book", "New Author", 2023, "9876543210123");

		given(bookService.saveBook(any(Book.class))).willReturn(savedBook);

		mockMvc.perform(post("/user/api/books").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(newBook)).with(csrf())).andExpect(status().isOk())
				.andExpect(jsonPath("$.title", is("New Book")));
	}


	@Test
	public void testUpdateBook_WhenFound() throws Exception {
		Long bookId = 1L;
		Book updatedDetails = new Book(bookId, "Updated Title", "Updated Author", 2001, "1234567890123");
		Book updatedBook = new Book(bookId, "Updated Title", "Updated Author", 2001, "1234567890123");

		given(bookService.updateBook(eq(bookId), any(Book.class))).willReturn(ResponseEntity.ok(updatedBook));

		mockMvc.perform(put("/user/api/books/{id}", bookId).contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(updatedDetails)).with(csrf())).andExpect(status().isOk())
				.andExpect(jsonPath("$.title", is("Updated Title")));
	}
	
	@Test
	public void testUpdateBook_WhenNotFound() throws Exception {
	    Long bookId = 1L;  
	    Book updatedDetails = new Book(bookId, "Updated Title", "Updated Author", 2001, "1234567890123");
 	    given(bookService.updateBook(eq(bookId), any(Book.class))).willReturn(ResponseEntity.notFound().build());
	    mockMvc.perform(put("/user/api/books/{id}", bookId)
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(new ObjectMapper().writeValueAsString(updatedDetails))
	            .with(csrf()))
	            .andExpect(status().isNotFound()); 
	}

	@Test
	public void testDeleteBook_Successful() throws Exception {
		Long bookId = 1L;
		when(bookService.deleteBook(bookId)).thenReturn(ResponseEntity.ok().build());
		mockMvc.perform(delete("/user/api/books/{id}", bookId).with(csrf())).andExpect(status().isOk());
		verify(bookService).deleteBook(bookId);
	}

	@Test
	public void testDeleteBook_NotFound() throws Exception {
		Long bookId = 1L;
		when(bookService.deleteBook(bookId)).thenReturn(ResponseEntity.notFound().build());
		mockMvc.perform(delete("/user/api/books/{id}", bookId).with(csrf())).andExpect(status().isNotFound());
		verify(bookService).deleteBook(bookId);
	}

}
