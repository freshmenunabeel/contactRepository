package com.contactBook.contactBook.service.impl;

import com.contactBook.contactBook.dto.SearchResultDTO;
import com.contactBook.contactBook.repository.UserInfoRepository;
import com.contactBook.contactBook.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchingServiceImpl implements SearchService {

	private final Integer SIZE_OF_PAGE = 1000;

	@Autowired
	private UserInfoRepository userInfoRepository;

	public List<SearchResultDTO> getSearchResult(String query, Integer page) {
		List<SearchResultDTO> searchResultDTOS = new ArrayList<>();
		Object[][] objects = userInfoRepository.search(query, page, SIZE_OF_PAGE);
		for (Object[] object : objects) {
			String name = object[0].toString();
			String email = object[1] == null ? null : object[1].toString();
			SearchResultDTO searchResultDTO = new SearchResultDTO();
			searchResultDTO.setEmail(email);
			searchResultDTO.setName(name);
			searchResultDTOS.add(searchResultDTO);
		}
		return searchResultDTOS;
	}
}
