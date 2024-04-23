package com.maids.Library_Management_System.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.maids.Library_Management_System.Models.Patron;
import com.maids.Library_Management_System.Repositories.PatronRepository;

import jakarta.transaction.Transactional;

@Service
public class PatronService {

	@Autowired
	private PatronRepository patronRepository;
	@Cacheable(value = "allPatrons")
	public List<Patron> findAllPatrons() {
		return patronRepository.findAll();
	}
	@Cacheable(value = "patrons", key = "#id")
	public Optional<Patron> findPatronById(Long id) {
		return patronRepository.findById(id);
	}
	@Transactional
	public Patron savePatron(Patron patron) {
		return patronRepository.save(patron);
	}
	@Transactional
	@CacheEvict(value = {"patrons", "allPatrons"}, allEntries = true, key = "#id")
	public ResponseEntity<Patron> updatePatron(Long id, Patron patronDetails) {
		return patronRepository.findById(id).map(patron -> {
			patron.setName(patronDetails.getName());
			patron.setContactInfo(patronDetails.getContactInfo());
			Patron updatedPatron = patronRepository.save(patron);
			return ResponseEntity.ok(updatedPatron);
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}
	@Transactional
	@CacheEvict(value = {"patrons", "allPatrons"}, allEntries = true, key = "#id")
	public ResponseEntity<Void> deletePatron(Long id) {
		return patronRepository.findById(id).map(patron -> {
			patronRepository.delete(patron);
			return ResponseEntity.ok().<Void>build();
		}).orElseGet(() -> ResponseEntity.notFound().build());
	}
}
