package com.maids.Library_Management_System.Models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "books")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "Title cannot be empty")
	private String title;

	@NotNull(message = "Author cannot be empty")
	private String author;

	@NotNull(message = "Publication year is required")
	@Min(value = 1450, message = "Publication year must be greater than 1450")
	@Max(value = 2024, message = "Publication year must be less than or equal to 2024")
	private Integer publicationYear;

	@NotNull(message = "ISBN cannot be empty")
	@Pattern(regexp = "^[0-9-]+$", message = "ISBN must be a valid format")
	@Column(unique = true)
	private String isbn;
	
@JsonIgnore
	@OneToMany(mappedBy = "book")
	private List<BorrowingRecord> borrowingRecords;

	public Book(Long id, @NotNull(message = "Title cannot be empty") String title,
			@NotNull(message = "Author cannot be empty") String author,
			@NotNull(message = "Publication year is required") @Min(value = 1450, message = "Publication year must be greater than 1450") @Max(value = 2024, message = "Publication year must be less than or equal to 2024") Integer publicationYear,
			@NotNull(message = "ISBN cannot be empty") @Pattern(regexp = "^[0-9-]+$", message = "ISBN must be a valid format") String isbn) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.publicationYear = publicationYear;
		this.isbn = isbn;
	}

	public Book() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Integer getPublicationYear() {
		return publicationYear;
	}

	public void setPublicationYear(Integer publicationYear) {
		this.publicationYear = publicationYear;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public List<BorrowingRecord> getBorrowingRecords() {
		return borrowingRecords;
	}

	public void setBorrowingRecords(List<BorrowingRecord> borrowingRecords) {
		this.borrowingRecords = borrowingRecords;
	}

}
