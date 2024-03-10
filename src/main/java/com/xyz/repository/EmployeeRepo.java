package com.xyz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.xyz.entity.Employee;

public interface EmployeeRepo extends JpaRepository<Employee, Integer> {

	public Employee findByEmail(String em);
	
	public Employee findByVerificationCode(String vc);
	
	@Query("update Employee u set u.failedAttempt=?2 Where email=?1")
	@Modifying
	public void updateAttempt(String em,int attempt);
	
}
