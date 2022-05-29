package com.mindtree.phoneBook.exception;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mindtree.phoneBook.utility.ErrorInfo;

@Component
public class PhoneBookAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private final Log LOGGER = LogFactory.getLog(PhoneBookAuthenticationEntryPoint.class);

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		String exception = authException.getMessage();
		if (request.getAttribute("Exception") != null) {
			LOGGER.error(exception + "\n" + request.getAttribute("Exception"));
		} else {
			LOGGER.error(exception);
		}
		ErrorInfo error = new ErrorInfo();
		error.setErrorCode(HttpStatus.UNAUTHORIZED.value());
		error.setErrorMsg(exception);
		error.setTimeStamp(LocalDateTime.now());
		OutputStream out = response.getOutputStream();
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		mapper.writeValue(out, error);
		out.flush();
	}

}
