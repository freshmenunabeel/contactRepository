package com.contactBook.contactBook.filter;

import com.contactBook.contactBook.dto.AuthenticationDTO;
import com.contactBook.contactBook.exception.AuthenticationException;
import com.contactBook.contactBook.model.Authentication;
import com.contactBook.contactBook.repository.AuthenticationRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Service
public class Filter implements javax.servlet.Filter {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Autowired
	private AuthenticationRepo authenticationRepo;

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {

		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		if (httpServletRequest.getContextPath().contains("/user")) {
			String token = httpServletRequest.getHeader("token");
			if (token == null)
				throw new AuthenticationException("login failed");
			else {
				Long userId = checkForAuthentication(token);
				if (userId == null) {
					throw new AuthenticationException("login failed");
				}
				httpServletRequest.setAttribute("userId", userId);
			}
		}
		filterChain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	private Long checkForAuthentication(String token) {
		try {
			String result = redisTemplate.opsForValue().get(token);
			if (result != null) {
				AuthenticationDTO authenticationDTO = objectMapper.readValue(result, AuthenticationDTO.class);
				return authenticationDTO.getUserId();
			} else {
				Authentication authentication = authenticationRepo.findByToken(token);
				if (authentication != null) {
					AuthenticationDTO authenticationDTO = new AuthenticationDTO();
					authenticationDTO.setId(authentication.getId());
					authenticationDTO.setUserId(authentication.getUserId());
					authenticationDTO.setToken(authentication.getToken());
					String object = objectMapper.writeValueAsString(authenticationDTO);
					redisTemplate.opsForValue().set(authenticationDTO.getToken(), object);
					return authentication.getUserId();
				}
			}
		} catch (Exception er) {
		}
		return null;
	}

	@Override
	public void destroy() {

	}
}
