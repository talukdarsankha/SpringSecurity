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
@RequestMapping(path = "/emp")
public class EC {

	@Autowired
	private EmployeeRepo er;
	@ModelAttribute
	public void commonData(Principal p,Model m) {
		if(p!=null) {
		String email= p.getName();
		Employee ee= er.findByEmail(email);
		m.addAttribute("var", ee);
		}
	}
	@GetMapping(path = "/profile")
	public String pro() {
		return "UserProfile";
	}
}
