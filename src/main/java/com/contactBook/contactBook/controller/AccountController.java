package com.contactBook.contactBook.controller;

import com.contactBook.contactBook.dto.UserCreateAccountRequestDTO;
import com.contactBook.contactBook.exception.AuthenticationException;
import com.contactBook.contactBook.exception.InputValidationException;
import com.contactBook.contactBook.service.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/account")
public class AccountController {

	@Autowired
	private UserAccount userAccount;

	@RequestMapping(value = "/createAccount", method = RequestMethod.POST)
	public ResponseEntity<String> createAccount(@RequestBody UserCreateAccountRequestDTO userCreateAccountRequestDTO) {
		ResponseEntity<String> responseEntity = null;
		try {
			userAccount.addAccount(userCreateAccountRequestDTO);
			responseEntity = new ResponseEntity("sucess", HttpStatus.OK);
		} catch (InputValidationException er) {
			responseEntity = new ResponseEntity(er.getMessage(), HttpStatus.BAD_REQUEST);
			return responseEntity;
		} catch (Exception er) {
			throw new RuntimeException();
		}
		return responseEntity;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<String> login(@RequestBody Map<String, String> request) {
		ResponseEntity<String> responseEntity = null;
		try {
			userAccount.login(request.get("username"), request.get("password"));
			responseEntity = new ResponseEntity("sucess", HttpStatus.OK);
		} catch (InputValidationException er) {
			responseEntity = new ResponseEntity(er.getMessage(), HttpStatus.BAD_REQUEST);
			return responseEntity;
		} catch (AuthenticationException er) {
			responseEntity = new ResponseEntity(er.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception er) {
			responseEntity = new ResponseEntity(er.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

	@RequestMapping(value = "/user/updateEmail", method = RequestMethod.POST)
	public ResponseEntity<String> updateEmail(@RequestBody Map<String, String> request,
			HttpServletRequest httpServletRequest) {
		ResponseEntity<String> responseEntity = null;
		try {
			userAccount.updateEmailAddress(request.get("oldEmail"), request.get("newEmail"),
					Long.parseLong(httpServletRequest.getAttribute("userId").toString()));
			responseEntity = new ResponseEntity("sucess", HttpStatus.OK);
		} catch (InputValidationException er) {
			responseEntity = new ResponseEntity(er.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (AuthenticationException er) {
			responseEntity = new ResponseEntity(er.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception er) {
			responseEntity = new ResponseEntity(er.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

	@RequestMapping(value = "/user/updateName", method = RequestMethod.POST)
	public ResponseEntity<String> updateName(@RequestBody Map<String, String> request,
			HttpServletRequest httpServletRequest) {
		ResponseEntity<String> responseEntity = null;
		try {
			userAccount.updateName(
					Long.parseLong(httpServletRequest.getAttribute("userId").toString()), request.get("name"));
			responseEntity = new ResponseEntity("sucess", HttpStatus.OK);
		} catch (InputValidationException er) {
			responseEntity = new ResponseEntity(er.getMessage(), HttpStatus.BAD_REQUEST);
			return responseEntity;
		} catch (AuthenticationException er) {
			responseEntity = new ResponseEntity(er.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception er) {
			responseEntity = new ResponseEntity(er.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

	@RequestMapping(value = "/user/addEmail", method = RequestMethod.POST)
	public ResponseEntity<String> addEmail(@RequestBody List<String> request,
			HttpServletRequest httpServletRequest) {
		ResponseEntity<String> responseEntity = null;
		try {
			userAccount.addEmail(request, Long.parseLong(httpServletRequest.getAttribute("userId").toString()));
			responseEntity = new ResponseEntity("sucess", HttpStatus.OK);
		} catch (InputValidationException er) {
			responseEntity = new ResponseEntity(er.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (AuthenticationException er) {
			responseEntity = new ResponseEntity(er.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception er) {
			responseEntity = new ResponseEntity(er.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return responseEntity;
	}

	@RequestMapping(value = "/user/deleteEmail", method = RequestMethod.POST)
	public ResponseEntity<String> deleteEmail(@RequestBody List<String> request,
			HttpServletRequest httpServletRequest) {
		ResponseEntity<String> responseEntity = null;
		try {
			userAccount.deleteEmail(request, Long.parseLong(httpServletRequest.getAttribute("userId").toString()));
			responseEntity = new ResponseEntity("sucess", HttpStatus.OK);
		} catch (InputValidationException er) {
			responseEntity = new ResponseEntity(er.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (AuthenticationException er) {
			responseEntity = new ResponseEntity(er.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception er) {
			responseEntity = new ResponseEntity(er.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}
}
