package com.contactBook.contactBook.controller;

import com.contactBook.contactBook.dto.SearchResultDTO;
import com.contactBook.contactBook.service.impl.SearchingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

	@Autowired
	private SearchingServiceImpl searchingService;

	@RequestMapping(value = "do", method = RequestMethod.GET)
	private List<SearchResultDTO> search(@RequestParam String query, @RequestParam Integer page) {
		return searchingService.getSearchResult(query, page);
	}
}
