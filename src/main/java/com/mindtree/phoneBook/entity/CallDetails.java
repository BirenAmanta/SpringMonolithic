package com.mindtree.phoneBook.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "calldetails")
public class CallDetails {

	@Id
	@GeneratedValue
	private Integer id;
	private Long calledBy;
	private Long calledTo;
	private LocalDate calledOn;
	private Integer duration;
	

}
