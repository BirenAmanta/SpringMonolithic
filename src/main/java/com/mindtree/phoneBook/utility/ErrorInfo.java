package com.mindtree.phoneBook.utility;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorInfo {
	private String errorMsg;
	private Integer errorCode;
	@DateTimeFormat(pattern="yyyy.MM.dd HH:mm:ss")
	private LocalDateTime timeStamp;

}
