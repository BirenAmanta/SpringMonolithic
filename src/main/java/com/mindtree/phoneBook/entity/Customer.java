package com.mindtree.phoneBook.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Customer {
	@Id
	private Long phoneNo;
	private String name;
	private Integer age;
	private String address;
	private String password;
	private char gender;
	@OneToOne(cascade=CascadeType.REFRESH)
	@JoinColumn(name = "plan_id")
	private Plan plan;
	@OneToMany(cascade=CascadeType.MERGE,fetch = FetchType.EAGER)
	@JoinColumn(name ="phoneNo" )
	private List<FriendFamily> friends=new ArrayList<>();

}
