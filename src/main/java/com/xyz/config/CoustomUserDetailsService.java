package com.xyz.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.xyz.entity.Employee;
import com.xyz.repository.EmployeeRepo;

public class CoustomUserDetailsService  implements UserDetailsService{

	@Autowired
	private EmployeeRepo ur;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Employee emp= ur.findByEmail(email);
		return new CustomUserDetils(emp);
	}

}
