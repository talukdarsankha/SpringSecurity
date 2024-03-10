package com.xyz.entity;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String fullName;
	private String email;
	private String password;
	private String role;
	private boolean enable;
	private String verificationCode;
	
	private boolean accountNonLock;
	private int failedAttempt;
	private java.util.Date lockTime;
	
	
	
	
	public boolean isAccountNonLock() {
		return accountNonLock;
	}
	public void setAccountNonLock(boolean accountNonLock) {
		this.accountNonLock = accountNonLock;
	}
	public int getFailedAttempt() {
		return failedAttempt;
	}
	public void setFailedAttempt(int failedAttempt) {
		this.failedAttempt = failedAttempt;
	}
	public java.util.Date getLockTime() {
		return lockTime;
	}
	public void setLockTime(java.util.Date lockTime) {
		this.lockTime = lockTime;
	}
	public boolean isEnable() {
		return enable;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	public String getVerificationCode() {
		return verificationCode;
	}
	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "Employee [id=" + id + ", fullName=" + fullName + ", email=" + email + ", password=" + password
				+ ", role=" + role + ", enable=" + enable + ", verificationCode=" + verificationCode
				+ ", accountNonLock=" + accountNonLock + ", failedAttempt=" + failedAttempt + ", lockTime=" + lockTime
				+ "]";
	}
	
	
}
