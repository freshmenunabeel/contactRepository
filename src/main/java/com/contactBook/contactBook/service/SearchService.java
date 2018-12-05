package com.contactBook.contactBook.service;

import com.contactBook.contactBook.dto.SearchResultDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SearchService {
	List<SearchResultDTO> getSearchResult(String query, Integer page);
}
