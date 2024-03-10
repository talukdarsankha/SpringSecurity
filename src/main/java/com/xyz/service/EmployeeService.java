package com.xyz.service;

import com.xyz.entity.Employee;

public interface EmployeeService {

	public Employee saveEmp(Employee em,String url);
	public void removeSession();
	
	public void sendEmail(Employee ee,String url);
	
	public boolean verifiedEmp(String verificationCode);
	
	public void increaseAttempt(Employee emp) ;
	public void lockAccount(Employee ee);
	public boolean lockTimeExpire(Employee emp);
	public void resetAttempt(String email);
	
}
