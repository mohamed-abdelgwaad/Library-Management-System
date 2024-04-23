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

import com.maids.Library_Management_System.Models.Patron;
import com.maids.Library_Management_System.Services.PatronService;
@CrossOrigin("*")
@RestController
@RequestMapping("/user/api/patrons")
@Validated
public class PatronController {

	@Autowired
	private PatronService patronService;

	@GetMapping
	public ResponseEntity<List<Patron>> getAllPatrons() {
		List<Patron> patrons = new ArrayList<Patron>();
		patrons = patronService.findAllPatrons();
		return ResponseEntity.ok(patrons);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Patron> getPatronById(@PathVariable Long id) {
		return patronService.findPatronById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<Patron> createPatron(@RequestBody Patron patron) {
		Patron savedpatron = patronService.savePatron(patron);
		return ResponseEntity.ok(savedpatron);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Patron> updatePatron(@PathVariable Long id, @RequestBody Patron patronDetails) {
		return patronService.updatePatron(id, patronDetails);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePatron(@PathVariable Long id) {
		return patronService.deletePatron(id);
	}
}
