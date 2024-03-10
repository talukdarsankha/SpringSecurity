package com.xyz.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.xyz.entity.Employee;
import com.xyz.repository.EmployeeRepo;
import com.xyz.service.EmployeeService;
import com.xyz.service.EmployeeServiceImpl;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthFailerHandaler  extends SimpleUrlAuthenticationFailureHandler{

	@Autowired
	private EmployeeRepo er;
	
	@Autowired
	private EmployeeService es;
	
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		// TODO Auto-generated method stub
		
		String email = request.getParameter("username");
		Employee ee= er.findByEmail(email);
		if(ee!=null) {
			if(ee.isEnable()) {
				if(ee.isAccountNonLock()) {
					if(ee.getFailedAttempt()<EmployeeServiceImpl.attempt-1) {
					    es.increaseAttempt(ee);
					}else {
						es.lockAccount(ee);
						exception= new LockedException("3 Failure Attempt your account locked for while");   
					}
				}else if(!ee.isAccountNonLock()) {
					if(es.lockTimeExpire(ee)) {
						exception = new LockedException("You Account unlocked Please try Again");
					}else {
						exception = new LockedException("Your Account Locked try after 10 seccond");  
					}
				}
			}
			else {
				exception = new LockedException("Your Account need to be verify!!");
			}
		}else {
			exception = new LockedException("You are not a registered Person!!!!");
		}
		super.setDefaultFailureUrl("/userLogin?error");
		super.onAuthenticationFailure(request, response, exception);
	}

	
	
	
}
