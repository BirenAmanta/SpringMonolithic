package com.mindtree.phoneBook.service;

import java.util.List;

import com.mindtree.phoneBook.dto.CallDetailsDTO;
import com.mindtree.phoneBook.dto.CustomerDTO;
import com.mindtree.phoneBook.dto.FriendFamilyDTO;
import com.mindtree.phoneBook.dto.LoginDTO;
import com.mindtree.phoneBook.dto.PlanDTO;
import com.mindtree.phoneBook.dto.TokenDTO;
import com.mindtree.phoneBook.exception.PhoneBookException;

public interface PhoneBookService {
	

	public Long createCustomer(CustomerDTO custDTO) throws PhoneBookException;

	public TokenDTO login(LoginDTO loginDTO) throws PhoneBookException;

	public CustomerDTO getCustomerProfile(Long phoneNo) throws PhoneBookException;

	public List<CallDetailsDTO> getCustomerCallDetails(Long phoneNo) throws PhoneBookException;

	public void saveFriend(Long phoneNo, FriendFamilyDTO friendDTO) throws PhoneBookException;

	public List<PlanDTO> getAllPlans() throws PhoneBookException;
}
