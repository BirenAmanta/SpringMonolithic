package com.mindtree.phoneBook.repository;

import org.springframework.data.repository.CrudRepository;

import com.mindtree.phoneBook.entity.CallDetails;
import java.util.List;

public interface CallDetailsRepository extends CrudRepository<CallDetails, Integer> {
	List<CallDetails> findByCalledBy(Long phoneNo);
}
