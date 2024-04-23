package com.maids.Library_Management_System.Models;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "patrons")
public class Patron {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "Name cannot be empty")
	private String name;

	@NotNull(message = "Contact information cannot be empty")
	@Digits(message = "Contact information must be a valid phone number", fraction = 0, integer = 11)
	private String contactInfo;

	public Patron(Long id, @NotNull(message = "Name cannot be empty") String name,
			@NotNull(message = "Contact information cannot be empty") @Email(message = "Contact information must be a valid phone number") String contactInfo) {
		super();
		this.id = id;
		this.name = name;
		this.contactInfo = contactInfo;
	}

	public Patron() {
		super();
	}
	
	@OneToMany(mappedBy = "patron")
	private List<BorrowingRecord> borrowingRecords;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContactInfo() {
		return contactInfo;
	}

	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}

	public List<BorrowingRecord> getBorrowingRecords() {
		return borrowingRecords;
	}

	public void setBorrowingRecords(List<BorrowingRecord> borrowingRecords) {
		this.borrowingRecords = borrowingRecords;
	}

}
