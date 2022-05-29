package com.mindtree.phoneBook.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mindtree.phoneBook.dto.CallDetailsDTO;
import com.mindtree.phoneBook.dto.CustomerDTO;
import com.mindtree.phoneBook.dto.FriendFamilyDTO;
import com.mindtree.phoneBook.dto.LoginDTO;
import com.mindtree.phoneBook.dto.PlanDTO;
import com.mindtree.phoneBook.dto.TokenDTO;
import com.mindtree.phoneBook.exception.PhoneBookException;

import com.mindtree.phoneBook.service.PhoneBookService;

@RestController
@RequestMapping(value = "/phonebook")
@Validated
public class PhoneBookController {

	@Autowired
	Environment environment;

	@Autowired
	PhoneBookService bookService;

	final Log LOGGER = LogFactory.getLog(PhoneBookController.class);

	@PostMapping(value = "/customers", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createCustomer(@RequestBody CustomerDTO custDTO) throws PhoneBookException {
		Long phoneNo = bookService.createCustomer(custDTO);
		String message = environment.getProperty("Service.PROFILE_CREATED") + phoneNo;
		LOGGER.info(message);
		return new ResponseEntity<String>(message, HttpStatus.CREATED);
	}

	// Login

	@PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TokenDTO> logIn(@RequestBody LoginDTO loginDTO) throws PhoneBookException {
		TokenDTO token = bookService.login(loginDTO);
		String message = environment.getProperty("Service.LOGIN");
		LOGGER.info(message);
		return new ResponseEntity<TokenDTO>(token, HttpStatus.ACCEPTED);
	}

	// Fetches full profile of a specific customer

	@GetMapping(value = "/customers/{phoneNo}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<CustomerDTO> getCustomerProfile(@PathVariable Long phoneNo) throws PhoneBookException {
		CustomerDTO customerDetails = bookService.getCustomerProfile(phoneNo);
		String message = environment.getProperty("");
		LOGGER.info(message);
		return new ResponseEntity<CustomerDTO>(customerDetails, HttpStatus.FOUND);
	}

	// Fetches call details of a specific customer

	@GetMapping(value = "/customers/{phoneNo}/calldetails", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CallDetailsDTO>> getCustomerCallDetails(@PathVariable long phoneNo)
			throws PhoneBookException {
		List<CallDetailsDTO> detailsList = bookService.getCustomerCallDetails(phoneNo);
		String message = environment.getProperty("");
		LOGGER.info(message);
		return new ResponseEntity<>(detailsList, HttpStatus.FOUND);
	}

	// Save the friend details of a specific customer

	@PostMapping(value = "/customers/{phoneNo}/friends", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> saveFriend(@PathVariable Long phoneNo, @RequestBody FriendFamilyDTO friendDTO)
			throws PhoneBookException {
		bookService.saveFriend(phoneNo, friendDTO);
		String message = environment.getProperty("");
		LOGGER.info(message);
		return new ResponseEntity<>(message, HttpStatus.OK);
	}
	@GetMapping(value = "/plans", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<PlanDTO>> getAllPlans() throws PhoneBookException {
		List<PlanDTO>allPlans=bookService.getAllPlans();
		String message = environment.getProperty("");
		LOGGER.info(message);
		return new ResponseEntity<List<PlanDTO>>(allPlans,HttpStatus.OK);
	}

}
