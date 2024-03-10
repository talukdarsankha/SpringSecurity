package com.xyz.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xyz.entity.Employee;
import com.xyz.repository.EmployeeRepo;

@Controller
@RequestMapping(path = "/admin")
public class AC {

	@Autowired
	private EmployeeRepo er;
	@ModelAttribute
	public void commomvar(Principal p , Model m) {
		String email = p.getName();
		Employee ee= er.findByEmail(email);
		m.addAttribute("va", ee);
	}
	
	@GetMapping(path = "/profile")
	public String profile() {
		return "adminprofile";
	}
	
}
