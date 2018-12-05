package com.contactBook.contactBook.repository;

import com.contactBook.contactBook.model.Authentication;
import org.springframework.data.repository.CrudRepository;

public interface AuthenticationRepo extends CrudRepository<Authentication, Long> {
	Authentication findByToken(String token);
}
