package com.maids.Library_Management_System.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maids.Library_Management_System.Models.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
