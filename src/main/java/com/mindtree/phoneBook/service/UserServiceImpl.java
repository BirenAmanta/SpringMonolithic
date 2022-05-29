package com.mindtree.phoneBook.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mindtree.phoneBook.dto.CustomerDTO;
import com.mindtree.phoneBook.entity.Customer;
import com.mindtree.phoneBook.repository.CustomerRepository;
import com.mindtree.phoneBook.utility.Conversion;

@Service
public class UserServiceImpl implements UserDetailsService {

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Customer> data = customerRepository.findByName(username);
		Customer user = data.orElseThrow(() -> new UsernameNotFoundException("Service.LOGIN_NON"));
		CustomerDTO dto = new Conversion<Customer, CustomerDTO>().getConvertedObject(user, CustomerDTO.class);
		return new User(dto.getName(), dto.getPassword(), new ArrayList<>());
	}

}
