package com.mindtree.phoneBook.dto;
import java.util.List;


import com.mindtree.phoneBook.entity.FriendFamily;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerDTO {

	private Long phoneNo;
	private String name;
	private Integer age;
	private String address;
	private String password;
	private char gender;
	private PlanDTO plan;
	private List<FriendFamily> friends;

}
