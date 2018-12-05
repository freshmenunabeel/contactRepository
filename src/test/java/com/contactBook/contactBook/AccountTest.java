package com.contactBook.contactBook;

import com.contactBook.contactBook.config.ContactBookApplication;
import com.contactBook.contactBook.service.UserAccount;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
		ContactBookApplication.class }, initializers = ConfigFileApplicationContextInitializer.class)
@Profile("default")
public class AccountTest {

	@Autowired
	private UserAccount userAccount;

	@Test
	public void addEmail() {
		List<String> emails = new ArrayList<>();
		emails.add("nabeel@gmail.com");
		userAccount.addEmail(emails, 1L);
	}

	@Test
	public void deleteEmail() {
		List<String> emails = new ArrayList<>();
		emails.add("nabeel@gmail.com");
		userAccount.deleteEmail(emails, 1L);
	}

	@Test
	public void loginTest() {
		List<String> emails = new ArrayList<>();
		emails.add("nabeel@gmail.com");
		userAccount.login("userId", "123");
	}
}
