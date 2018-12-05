package com.contactBook.contactBook.service.impl;

import com.contactBook.contactBook.dto.AuthenticationDTO;
import com.contactBook.contactBook.dto.UserContactDTO;
import com.contactBook.contactBook.dto.UserCreateAccountRequestDTO;
import com.contactBook.contactBook.exception.AuthenticationException;
import com.contactBook.contactBook.exception.InputValidationException;
import com.contactBook.contactBook.model.Authentication;
import com.contactBook.contactBook.model.UserContact;
import com.contactBook.contactBook.model.UserInfo;
import com.contactBook.contactBook.repository.AuthenticationRepo;
import com.contactBook.contactBook.repository.UserContactRepository;
import com.contactBook.contactBook.repository.UserInfoRepository;
import com.contactBook.contactBook.service.UserAccount;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserAccountImpl implements UserAccount {

	final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Value("${timeZone}")
	private String timeZone;

	@Autowired
	private UserContactRepository userContactRepository;

	@Autowired
	private UserInfoRepository userInfoRepository;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Autowired
	private AuthenticationRepo authenticationRepo;

	@Autowired
	private ObjectMapper objectMapper;

	@Override
	@Transactional
	public void addAccount(UserCreateAccountRequestDTO userCreateAccountRequestDTO) {
		if (userCreateAccountRequestDTO != null) {
			if (userInfoRepository.findByUniqueUserId(userCreateAccountRequestDTO.getUniqueUserId()) != null) {
				LOGGER.info("User already exists");
				throw new InputValidationException("User already exist");
			}
			UserInfo userInfo = new UserInfo();
			userInfo.setUpdatedAt(new Timestamp(DateTime.now(DateTimeZone.forID(timeZone)).getMillis()));
			userInfo.setCreatedAt(new Timestamp(DateTime.now(DateTimeZone.forID(timeZone)).getMillis()));
			userInfo.setName(userCreateAccountRequestDTO.getName());
			userInfo.setPassword(userCreateAccountRequestDTO.getPassword());
			userInfo = userInfoRepository.save(userInfo);
			List<UserContactDTO> userContactDTOS = userCreateAccountRequestDTO.getContacts();
			if (!CollectionUtils.isEmpty(userContactDTOS)) {
				List<String> emails = new ArrayList<>(userContactDTOS.size());
				for (UserContactDTO userContactDTO : userContactDTOS) {
					emails.add(userContactDTO.getEmail());
				}
				addEmail(emails, userInfo.getId());
			}
		}
	}

	@Transactional
	public void updateName(Long userId, String name) {
		if (StringUtils.isEmpty(name)) {
			throw new InputValidationException("name shoud not null");
		}
		Optional<UserInfo> userInfoOptional = userInfoRepository.findById(userId);
		if (userInfoOptional.isPresent()) {
			UserInfo userInfo = userInfoOptional.get();
			userInfo.setName(name);
			userInfoRepository.save(userInfo);
		} else {
			throw new InputValidationException("User not present");
		}
	}

	@Transactional
	public void updateEmailAddress(String oldEmail, String newEmail, Long userId) {
		List<String> emails = new ArrayList<>();
		emails.add(oldEmail);
		List<UserContact> userContacts = userContactRepository.findByEmailInAndUserId(emails, userId);
		if (!CollectionUtils.isEmpty(userContacts)) {
			for (UserContact userContact : userContacts) {
				userContact.setEmail(newEmail);
			}
			userContactRepository.saveAll(userContacts);
		}
	}

	@Transactional
	public void deleteEmail(List<String> emails, Long userId) {
		List<UserContact> userContacts = userContactRepository.findByEmailInAndUserId(emails, userId);
		if (!CollectionUtils.isEmpty(userContacts)) {
			userContactRepository.deleteAll(userContacts);
		}
	}

	@Transactional
	public void addEmail(List<String> emails, Long userId) {
		if (CollectionUtils.isEmpty(userContactRepository.findByEmailIn(emails))) {
			List<UserContact> userContacts = new ArrayList<>();
			for (String email : emails) {
				UserContact userContact = new UserContact();
				userContact.setEmail(email);
				userContact.setUserId(userId);
				userContacts.add(userContact);
			}
			userContactRepository.saveAll(userContacts);
		} else {
			throw new InputValidationException("Email already exist");
		}
	}

	@Override
	@Transactional
	public void login(String username, String password) {
		UserInfo userInfo = userInfoRepository.findByUniqueUserId(username);
		if (userInfo != null && userInfo.getPassword().equals(password)) {
			Authentication authentication = new Authentication();
			authentication.setToken(UUID.randomUUID().toString());
			authentication.setUserId(userInfo.getId());
			authentication = authenticationRepo.save(authentication);
			if (authentication != null) {
				AuthenticationDTO authenticationDTO = new AuthenticationDTO();
				authenticationDTO.setId(authentication.getId());
				authenticationDTO.setUserId(authentication.getUserId());
				authenticationDTO.setToken(authentication.getToken());
				try {
					String object = objectMapper.writeValueAsString(authenticationDTO);
					redisTemplate.opsForValue().set(authenticationDTO.getToken(), object);
				} catch (JsonProcessingException er) {
					//it will not come
				} catch (Exception er) {
					throw new AuthenticationException("Login failed");
				}
			}
		} else {
			throw new AuthenticationException("Login failed");
		}
	}

	private boolean validateEmail(List<String> emails) {
		return true;
	}
}
