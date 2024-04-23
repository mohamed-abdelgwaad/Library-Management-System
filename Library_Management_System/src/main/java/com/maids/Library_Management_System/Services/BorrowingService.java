package com.maids.Library_Management_System.Services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maids.Library_Management_System.Models.BorrowingRecord;
import com.maids.Library_Management_System.Repositories.BookRepository;
import com.maids.Library_Management_System.Repositories.BorrowingRecordRepository;
import com.maids.Library_Management_System.Repositories.PatronRepository;

import jakarta.transaction.Transactional;

@Service
public class BorrowingService {

	@Autowired
	private BorrowingRecordRepository borrowingRecordRepository;

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private PatronRepository patronRepository;
	@Transactional
	public Optional<BorrowingRecord> borrowBook(Long bookId, Long patronId) {
		return bookRepository.findById(bookId).flatMap(book -> patronRepository.findById(patronId).map(patron -> {
			BorrowingRecord record = new BorrowingRecord();
			record.setBook(book);
			record.setPatron(patron);
			record.setBorrowDate(new Date());
			return borrowingRecordRepository.save(record);
		}));
	}
	@Transactional
	public Optional<BorrowingRecord> returnBook(Long bookId, Long patronId) {
		return borrowingRecordRepository.findByBookIdAndPatronId(bookId, patronId).map(record -> {
			record.setReturnDate(new Date());
			return borrowingRecordRepository.save(record);
		});
	}
}
