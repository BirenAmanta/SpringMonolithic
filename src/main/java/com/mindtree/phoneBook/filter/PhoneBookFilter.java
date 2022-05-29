package com.mindtree.phoneBook.filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.mindtree.phoneBook.service.UserServiceImpl;
import com.mindtree.phoneBook.utility.LoginUtility;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class PhoneBookFilter extends OncePerRequestFilter {

	@Autowired
	private LoginUtility loginUtilty;
	@Autowired
	private UserServiceImpl userServiceImpl;
	
	private final Log LOGGER=LogFactory.getLog(PhoneBookFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			FilterChain filterChain) throws ServletException, IOException {

		String authorizationHeader = httpServletRequest.getHeader("Authorization");

		String token = null;
		String userName = null;
		try {
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			token = authorizationHeader.substring(7);
			userName = loginUtilty.extractUsername(token);
		}

		if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails = userServiceImpl.loadUserByUsername(userName);

			if (loginUtilty.validateToken(token, userDetails)) {

				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		}
		catch(Exception exception)
		{
			LOGGER.error(exception.getMessage());
		}
			filterChain.doFilter(httpServletRequest, httpServletResponse);
		}

	}

