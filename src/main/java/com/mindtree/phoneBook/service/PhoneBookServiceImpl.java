package com.mindtree.phoneBook.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.mindtree.phoneBook.dto.CallDetailsDTO;
import com.mindtree.phoneBook.dto.CustomerDTO;
import com.mindtree.phoneBook.dto.FriendFamilyDTO;
import com.mindtree.phoneBook.dto.LoginDTO;
import com.mindtree.phoneBook.dto.PlanDTO;
import com.mindtree.phoneBook.dto.TokenDTO;
import com.mindtree.phoneBook.entity.CallDetails;
import com.mindtree.phoneBook.entity.Customer;
import com.mindtree.phoneBook.entity.FriendFamily;
import com.mindtree.phoneBook.entity.Plan;
import com.mindtree.phoneBook.exception.PhoneBookException;
import com.mindtree.phoneBook.repository.CallDetailsRepository;
import com.mindtree.phoneBook.repository.CustomerRepository;
import com.mindtree.phoneBook.repository.FriendFamilyRepository;
import com.mindtree.phoneBook.repository.PlanRepository;
import com.mindtree.phoneBook.utility.Conversion;
import com.mindtree.phoneBook.utility.LoginUtility;

@Service
@Transactional
public class PhoneBookServiceImpl implements PhoneBookService {

	@Autowired
	private CustomerRepository customerReposritory;

	@Autowired
	private FriendFamilyRepository familyRepository;
	@Autowired
	private PlanRepository planRepository;

	@Autowired
	private CallDetailsRepository callDetailsRepository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private LoginUtility loginUtilty;

	@Override
	public Long createCustomer(CustomerDTO custDTO) throws PhoneBookException {
		Optional<Plan> plans = planRepository.findById(custDTO.getPlan().getPlanId());
		Plan plan = plans.orElseThrow(() -> new PhoneBookException("Service.PLAN_NOT_FOUND"));
		custDTO.setPlan(new Conversion<Plan, PlanDTO>().getConvertedObject(plan, PlanDTO.class));
		Customer customer = new Conversion<CustomerDTO, Customer>().getConvertedObject(custDTO, Customer.class);
		customerReposritory.save(customer);
		return custDTO.getPhoneNo();
	}

	@Override
	public TokenDTO login(LoginDTO loginDTO) throws PhoneBookException {
		TokenDTO token = new TokenDTO();
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
		} catch (AuthenticationException exception) {
			throw new PhoneBookException(exception.getMessage());
		}
		String tokenValue = loginUtilty.generateToken(loginDTO.getUsername());
		token.setTimestamp(LocalDate.now());
		token.setToken(tokenValue);
		return token;
	}

	@Override
	public CustomerDTO getCustomerProfile(Long phoneNo) throws PhoneBookException {
		Optional<Customer> data = customerReposritory.findById(phoneNo);
		Customer customer = data.orElseThrow(() -> new PhoneBookException("Service.PROFILE_NOTFOUND"));
		CustomerDTO profile = new Conversion<Customer, CustomerDTO>().getConvertedObject(customer, CustomerDTO.class);
		return profile;
	}

	@Override
	public List<CallDetailsDTO> getCustomerCallDetails(Long phoneNo) throws PhoneBookException {
		List<CallDetails> details = callDetailsRepository.findByCalledBy(phoneNo);
		if (details.isEmpty()) {
			throw new PhoneBookException("Service.CALLDETAILS_NOT_FOUND");
		}
		return details.stream().map(
				(data) -> new Conversion<CallDetails, CallDetailsDTO>().getConvertedObject(data, CallDetailsDTO.class))
				.collect(Collectors.toList());
	}

	@Override
	public void saveFriend(Long phoneNo, FriendFamilyDTO friendDTO) throws PhoneBookException {
		Optional<Customer> data = customerReposritory.findById(phoneNo);
		Customer customer = data.orElseThrow(() -> new PhoneBookException("Service.PROFILE_NOTFOUND"));
		friendDTO.setPhoneNo(customer.getPhoneNo());
		familyRepository.save(
				new Conversion<FriendFamilyDTO, FriendFamily>().getConvertedObject(friendDTO, FriendFamily.class));

	}

	@Override
	public List<PlanDTO> getAllPlans() throws PhoneBookException {
		Iterable<Plan> plans = planRepository.findAll();
		List<PlanDTO> data = new ArrayList<>();
		plans.forEach((temp) ->

		data.add(new Conversion<Plan, PlanDTO>().getConvertedObject(temp, PlanDTO.class)));
		if (data.isEmpty()) {
			throw new PhoneBookException("Service.PLANS_NOT_FOUND");
		}
		return data;
	}

}
