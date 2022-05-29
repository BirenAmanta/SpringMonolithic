package com.mindtree.phoneBook.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.mindtree.phoneBook.entity.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

	Optional<Customer> findByName(String username);
}
