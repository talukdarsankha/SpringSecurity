package com.xyz.config;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.xyz.entity.Employee;

public class CustomUserDetils implements UserDetails {

	private Employee emp;
	
	public CustomUserDetils(Employee emp) {
		this.emp=emp;
	}
	
	public Employee getEmp() {
		return emp;
	}

	public void setEmp(Employee emp) {
		this.emp = emp;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		SimpleGrantedAuthority sga = new SimpleGrantedAuthority(emp.getRole());
		
		return Arrays.asList(sga);
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return emp.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return emp.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return emp.isAccountNonLock();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return emp.isEnable();
	}

}
