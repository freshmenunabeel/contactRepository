package com.contactBook.contactBook.repository;

import com.contactBook.contactBook.model.UserContact;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserContactRepository extends CrudRepository<UserContact, Long> {

	List<UserContact> findByUserId(Long userId);

	List<UserContact> findByEmailIn(List<String> emails);

	List<UserContact> findByEmailInAndUserId(List<String> emails, Long uerId);
}
