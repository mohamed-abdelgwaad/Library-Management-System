package com.maids.Library_Management_System.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.maids.Library_Management_System.Services.BorrowingService;
@CrossOrigin("*")
@RestController
@RequestMapping("/user/api")
public class BorrowingController {

	@Autowired
	private BorrowingService borrowingService;

	@PostMapping("/borrow/{bookId}/patron/{patronId}")
	public ResponseEntity<String> borrowBook(@PathVariable Long bookId, @PathVariable Long patronId) {
		return borrowingService.borrowBook(bookId, patronId).map(b -> ResponseEntity.ok("Book borrowed successfully"))
				.orElse(ResponseEntity.badRequest().body("Error borrowing book"));
	}

	@PutMapping("/return/{bookId}/patron/{patronId}")
	public ResponseEntity<String> returnBook(@PathVariable Long bookId, @PathVariable Long patronId) {
		return borrowingService.returnBook(bookId, patronId).map(b -> ResponseEntity.ok("Book returned successfully"))
				.orElse(ResponseEntity.badRequest().body("Error returning book"));
	}
}
