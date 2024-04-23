package com.maids.Library_Management_System.Controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maids.Library_Management_System.Models.Book;
import com.maids.Library_Management_System.Services.BookService;
@CrossOrigin("*")
@RestController
@RequestMapping("/user/api/books")
@Validated
public class BookController {

	@Autowired
	private BookService bookService;

	@GetMapping
	public ResponseEntity<List<Book>> getAllBooks() {
		List<Book> books = new ArrayList<Book>();
		books = bookService.findAllBooks();
		return ResponseEntity.ok(books);

	}

	@GetMapping("/{id}")
	public ResponseEntity<Book> getBookById(@PathVariable Long id) {
		return bookService.findBookById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<Book> createBook(@RequestBody Book book) {
		Book savedBook = bookService.saveBook(book);
		return ResponseEntity.ok(savedBook);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book bookDetails) {
		return bookService.updateBook(id, bookDetails);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
		return bookService.deleteBook(id);
	}
}
