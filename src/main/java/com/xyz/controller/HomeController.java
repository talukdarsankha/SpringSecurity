package com.xyz.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xyz.entity.Employee;
import com.xyz.repository.EmployeeRepo;
import com.xyz.service.EmployeeService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

	@Autowired
	private EmployeeService es;
	@Autowired
	private EmployeeRepo er;
	
	@RequestMapping(path = "/")
	public String home() {
		return "home";
	}
	
	@GetMapping(path = "/about")
	public String about() {
		return "about";
	}
	
	@GetMapping(path = "/index")
	public String index() {
		return "index";
	}
	
	@GetMapping(path = "/userLogin")
	public String loginPage() {
		return "login";
	}
	
	
	
	
	
//	@GetMapping(path = "/er")
//	public String err() {
//		return "er";
//	}
//	
//	@GetMapping(path = "/su")
//	public String ls() {
//		return "logoutSuccess";
//	}
	
	@GetMapping(path = "/bs")
	public String ba() {
		return "base";
	}
	
	@GetMapping(path = "/register")
	public String register() {
		return "register";
	}
	
	@PostMapping(path = "/userRegister")
	public String  userReg(@ModelAttribute Employee emp,HttpSession hs,HttpServletRequest req) {
		String url = req.getRequestURL().toString();
		url= url.replace(req.getServletPath(), "");
		System.out.println(url);
		Employee ee = es.saveEmp(emp,url);
		System.out.println(ee);
		if(ee!=null) {
		hs.setAttribute("msg","Your Account Successfully Registered");	
		}else hs.setAttribute("ermsg", "Something went Wrong On Server!!!");
		return "redirect:/register";
	}
	
	@ModelAttribute
	public void commonData(Principal p,Model m) {
		if(p!=null) {
		String email= p.getName();
		Employee ee= er.findByEmail(email);
		m.addAttribute("var", ee);
		}
	}
//	@GetMapping(path = "/emp/profile")
//	public String profile() {
//		
//		return "UserProfile";
//	}
	
	@RequestMapping(path = "/verify")
	public String verifyEmp(@Param("code") String code,Model m) {
		 if(es.verifiedEmp(code)) {
			 m.addAttribute("msg", "Successfully Verified!!!");
		 }else {
			 m.addAttribute("msg", "Incorrect verification code or Already verified ");  
		 }
		 return "verificationMessage";
	}
	
	
	
}
