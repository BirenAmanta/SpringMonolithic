package com.mindtree.phoneBook.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Plan {

	@Id
	private Integer planId;
	private String planName;
	private Integer nationalRate;
	private Integer localRate;

}
