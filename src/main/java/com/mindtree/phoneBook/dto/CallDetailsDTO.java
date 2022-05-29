package com.mindtree.phoneBook.dto;

import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class CallDetailsDTO {
	private Integer id;
	private Long calledBy;
	private Long calledTo;
	private LocalDate calledOn;
	private Integer duration;
}
