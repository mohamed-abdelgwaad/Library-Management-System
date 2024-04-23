package com.maids.Library_Management_System.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maids.Library_Management_System.Models.BorrowingRecord;

@Repository
public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long> {

	Optional<BorrowingRecord> findByBookIdAndPatronId(Long bookId, Long patronId);

}
