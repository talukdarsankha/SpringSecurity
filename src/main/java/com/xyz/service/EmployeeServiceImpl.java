package com.xyz.service;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.xyz.entity.Employee;
import com.xyz.repository.EmployeeRepo;

import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Component
@Transactional
public class EmployeeServiceImpl implements EmployeeService{

	@Autowired
	private EmployeeRepo ur;
	
	@Autowired
	private BCryptPasswordEncoder bcp;
	
	@Autowired
	private JavaMailSender jms;
	
	@Override
	public Employee saveEmp(Employee em,String url) {
		String pas= em.getPassword();
		String enPas= bcp.encode(pas);
		em.setPassword(enPas);
		em.setRole("ROLE_ADMIN");
		em.setEnable(false);
		em.setVerificationCode(UUID.randomUUID().toString());
		em.setAccountNonLock(true);
		em.setFailedAttempt(0);
		em.setLockTime(null);
		Employee emp= ur.save(em);
		if(emp!=null) {
			sendEmail(emp, url);
		}
		return emp;
	}
	@Override
	public void removeSession() {
		HttpSession hs= ((ServletRequestAttributes)(RequestContextHolder.getRequestAttributes())).getRequest().getSession();
		hs.removeAttribute("msg");
	}
	@Override
	public void sendEmail(Employee ee, String url) {
		String from = "sagarayantalukdar@gmail.com";
		String to = ee.getEmail();
		String subject = "Account Verification";
		String content = "Dear [[name]],<br>"+"please click to below link to verify Your Account.<br>"
		+"<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY   </a></h3>"+"Thank You,<br>"+"Coder";
		
		try {
			MimeMessage mm = jms.createMimeMessage();
			MimeMessageHelper mh = new MimeMessageHelper(mm);
			mh.setFrom(from);
			mh.setTo(to);
			mh.setSubject(subject);
			content = content.replace("[[name]]", ee.getFullName());
			String siteUrl = url+"/verify?code="+ee.getVerificationCode();
			content = content.replace("[[URL]]", siteUrl);
			mh.setText(content,true);
			jms.send(mm);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
	
	@Override
	public boolean verifiedEmp(String verificationCode) {
		// TODO Auto-generated method stub
		Employee em= ur.findByVerificationCode(verificationCode);
		if(em!=null) {
			em.setEnable(true);
			em.setVerificationCode(null);
			ur.save(em);
			return true;
		}
		return false;
	}
	
	
	@Override
	public void increaseAttempt(Employee emp) {
		// TODO Auto-generated method stub
		
		int attempt = emp.getFailedAttempt()+1;
		ur.updateAttempt(emp.getEmail(),attempt);
	}
	@Override
	public void lockAccount(Employee ee) {
		// TODO Auto-generated method stub
		ee.setAccountNonLock(false);
		ee.setLockTime(new Date() );
		ur.save(ee);
	}
	
	private static final long lock_Duaration = 10000;
	public static final long attempt=3;
	
	@Override
	public boolean lockTimeExpire(Employee emp) {
		// TODO Auto-generated method stub
		long lockTimeMills = emp.getLockTime().getTime();
		long currentTime= System.currentTimeMillis();
		if(lockTimeMills+lock_Duaration<currentTime) {
			emp.setAccountNonLock(true);
			emp.setLockTime(null);
			emp.setFailedAttempt(0);
			ur.save(emp);
			return true;
		}else {
			return false;
		}
		
	}
	@Override
	public void resetAttempt(String email) {
		// TODO Auto-generated method stub
		ur.updateAttempt(email, 0);
	}

	
	
	
	
	
	
}
