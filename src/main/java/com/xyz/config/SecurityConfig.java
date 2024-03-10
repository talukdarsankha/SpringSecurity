package com.xyz.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private AuthenticationSuccessHandler ash;
	
	@Autowired
	private AuthenticationFailureHandler afh;
	
//	@Bean
//	public BCryptPasswordEncoder bPE() {
//		return new BCryptPasswordEncoder();
//	}
//	
//	@Bean
//	public UserDetailsService uDS() {
//		UserDetails user1= (User) User.withUsername("sankha").password(bPE().encode("123")).roles("USER").build();
//	    UserDetails user2= (User) User.withUsername("ram").password(bPE().encode("101")).roles("ADMIN").build();
//	
//	    return new InMemoryUserDetailsManager(user1,user2);
//	}
//	
//	@Bean
//	public SecurityFilterChain sef(HttpSecurity hhs) throws Exception {
//		hhs.csrf().disable().authorizeHttpRequests().
//		anyRequest().authenticated().and().formLogin();
//		return hhs.build();
//	}
	
	
	
	
	
	@Bean
	public BCryptPasswordEncoder bcp() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
  public UserDetailsService uds() {
	  return new CoustomUserDetailsService();
  }
  
	@Bean
  public DaoAuthenticationProvider getdap() {
	  DaoAuthenticationProvider dap = new DaoAuthenticationProvider();
	  dap.setUserDetailsService(uds());
	  dap.setPasswordEncoder(bcp());
	  return dap;
  }
  
	@Bean
	public SecurityFilterChain getSec(HttpSecurity hhs) throws Exception {
	hhs.csrf().disable().authorizeHttpRequests()
	     .requestMatchers("/","/userRegister","/register","/about","/bs","/index").permitAll()
	     .requestMatchers("/**").permitAll()
		.requestMatchers("/emp/**").hasRole("USER")
		.requestMatchers("/admin/**").hasRole("ADMIN")
//	     .anyRequest().permitAll() 
	     .and().formLogin().loginPage("/userLogin").loginProcessingUrl("/logg")
//		 .defaultSuccessUrl("/about")
//		 .failureUrl("/er")
	     .failureHandler(afh)
		 .successHandler(ash)
		
//		.logoutSuccessUrl("/su")
		.permitAll();
		
		
		return hhs.build();
	}
	
	
	
	
	
	
	
}
