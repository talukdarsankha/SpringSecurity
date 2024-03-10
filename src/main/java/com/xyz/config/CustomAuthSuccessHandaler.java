package com.xyz.config;

import java.io.IOException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.xyz.entity.Employee;
import com.xyz.service.EmployeeService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class CustomAuthSuccessHandaler implements AuthenticationSuccessHandler {

	@Autowired 
	private EmployeeService es;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		
		Set<String> role= AuthorityUtils.authorityListToSet(authentication.getAuthorities());
		 CustomUserDetils cusD= (CustomUserDetils) authentication.getPrincipal();
		Employee emp = cusD.getEmp();
		if(emp!=null) {
			es.resetAttempt(emp.getEmail());
		}
		
		if(role.contains("ROLE_USER")) {
			response.sendRedirect("/emp/profile");
		}else {
			response.sendRedirect("/admin/profile");
		}
		
	}

}
