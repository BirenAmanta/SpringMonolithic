package com.mindtree.phoneBook.dto;

import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "users")
@Data
@NoArgsConstructor
public class LoginDTO {
	private String username;
	private String password;

}
