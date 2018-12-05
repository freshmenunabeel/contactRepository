package com.contactBook.contactBook;

import com.contactBook.contactBook.config.ContactBookApplication;
import com.contactBook.contactBook.dto.SearchResultDTO;
import com.contactBook.contactBook.service.SearchService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
		ContactBookApplication.class }, initializers = ConfigFileApplicationContextInitializer.class)
@Profile("default")
public class SearchTest {

	@Autowired
	private SearchService searchService;

	@Test
	public void search() {
		List<SearchResultDTO> searchResultDTOS = searchService.getSearchResult("NABEEL", 1);
	}

}
