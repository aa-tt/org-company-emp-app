package com.socgen.empapp.common;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.slf4j.Slf4j;

@Component
@Order(value = 1)
@Slf4j
public class EmpAppEntryFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			if (request.getHeader(AppConstants.CORRELATION_ID) != null
					&& request.getHeader(AppConstants.CORRELATION_ID).length() > 0) {
				MDC.put(AppConstants.CORRELATION_ID, request.getHeader(AppConstants.CORRELATION_ID));
			} else {
				MDC.put(AppConstants.CORRELATION_ID, UUID.randomUUID().toString());
			}
		} catch (Exception ex) {
			log.error("EntryFilter >> doFilterInternal() >> Unexpection exception", ex);
		}
		if (MDC.get(AppConstants.CORRELATION_ID) != null) {
			response.setHeader(AppConstants.CORRELATION_ID, MDC.get(AppConstants.CORRELATION_ID));
		}
		filterChain.doFilter(request, response);
	}
}
