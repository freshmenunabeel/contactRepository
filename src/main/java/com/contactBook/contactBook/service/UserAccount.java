package com.contactBook.contactBook.service;

import com.contactBook.contactBook.dto.UserCreateAccountRequestDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserAccount {

	void addAccount(UserCreateAccountRequestDTO userCreateAccountRequestDTO);

	void login(String username, String password);

	void addEmail(List<String> emails, Long userId);

	void updateEmailAddress(String oldEmail, String newEmail, Long userId);

	void deleteEmail(List<String> emails, Long userId);

	void updateName(Long userId, String name);
}
