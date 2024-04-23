package com.maids.Library_Management_System.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.maids.Library_Management_System.Models.Book;
import com.maids.Library_Management_System.Repositories.BookRepository;

import jakarta.transaction.Transactional;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;
    @Cacheable(value = "allBooks") 
	public List<Book> findAllBooks() {
		return bookRepository.findAll();
	}
	
    @Cacheable(value = "books", key = "#id") 
	public Optional<Book> findBookById(Long id) {
		return bookRepository.findById(id);
	}
	
    @Transactional
    	public Book saveBook(Book book) {
		return bookRepository.save(book);
	}
    @Transactional
    @CacheEvict(value = {"books", "allBooks"}, allEntries = true, key = "#id")
	public ResponseEntity<Book> updateBook(Long id, Book bookDetails) {
		return bookRepository.findById(id).map(book -> {
			book.setTitle(bookDetails.getTitle());
			book.setAuthor(bookDetails.getAuthor());
			book.setPublicationYear(bookDetails.getPublicationYear());
			book.setIsbn(bookDetails.getIsbn());
			Book updatedBook = bookRepository.save(book);
			return ResponseEntity.ok(updatedBook);
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}
    @Transactional
    @CacheEvict(value = {"books", "allBooks"}, allEntries = true, key = "#id")
	public ResponseEntity<Void> deleteBook(Long id) {
		return bookRepository.findById(id).map(book -> {
			bookRepository.delete(book);
			return ResponseEntity.ok().<Void>build();
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}
}
