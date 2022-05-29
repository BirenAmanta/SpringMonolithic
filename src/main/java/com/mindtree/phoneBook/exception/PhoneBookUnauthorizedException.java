package com.mindtree.phoneBook.exception;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mindtree.phoneBook.utility.ErrorInfo;

@Component
public class PhoneBookUnauthorizedException implements AccessDeniedHandler {

	@Autowired
	private Environment environment;
	
	private final Log LOGGER=LogFactory.getLog(PhoneBookUnauthorizedException.class);
	
	@Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
		LOGGER.error(e.getMessage());
		ErrorInfo error=new ErrorInfo();
		error.setErrorCode(HttpStatus.FORBIDDEN.value());
		error.setErrorMsg(environment.getProperty("Service.UNUTHORIZED"));
		error.setTimeStamp(LocalDateTime.now());
        OutputStream out = httpServletResponse.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.writeValue(out, error);
        out.flush();
    }
}


