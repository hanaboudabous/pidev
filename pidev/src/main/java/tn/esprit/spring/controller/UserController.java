package tn.esprit.spring.controller;

import java.text.ParseException;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import tn.esprit.spring.entity.Role_User;
import tn.esprit.spring.entity.User;
import tn.esprit.spring.services.IUserService;
import tn.esprit.spring.services.MailService;

@Controller
public class UserController {
	@Autowired
	MailService notificationService;
	
	@Autowired
	IUserService userService;
	
	@Autowired
	BCryptPasswordEncoder passcrypt;
	
	
	@GetMapping("/users")
	@ResponseBody
	public List<User> getUsers() {
	List<User> list = userService.retrieveAllUsers();
	return list;
	}
	
	@GetMapping("/retrieve-user/{user-id}")
	@ResponseBody
	public User retrieveUser(@PathVariable("user-id") String Id) {
	return userService.retrieveUser(Id);
	}
		
	@PostMapping("/add-user")
	@ResponseBody
	public User addUser(@RequestBody User u) throws MessagingException {
	String pwd=u.getPassword();
	String crypt=passcrypt.encode(pwd);
	u.setPassword(crypt);
	User user = userService.addUser(u);
	notificationService.sendEmailVerify(u);
	return user;
	}
	
	@DeleteMapping("/remove-user/{user-id}")
	@ResponseBody
	public void removeUser(@PathVariable("user-id") String userId) {
	userService.deleteUser(userId);
	}
	
	@PutMapping("/modify-user")
	@ResponseBody
	public User modifyUser(@RequestBody User user) {
	return userService.updateUser(user);
	}
	
	@GetMapping("/retrieve-userR/{role}")
	@ResponseBody
	public List<User> retrieveUserByRole(@PathVariable("role") Role_User role) {
	return userService.retrieveUsersByRole(role);
	}
	
	@GetMapping("/verifie-user/{user-id}")
	@ResponseBody
	public void verifieUser(@PathVariable("user-id") String Id) {
	userService.verifie_account(Id);
	}
	
	@GetMapping("/retrieve-userbydate/{d1}/{d2}")
	@ResponseBody
	public List<User> retrieveUsersByDate(@PathVariable("d1") String d1,@PathVariable("d2") String d2) throws ParseException {
	return userService.retrieveUsersByDate(d1, d2);
	}
	
	@GetMapping("/login/{d1}/{d2}")
	@ResponseBody
	public String login(@PathVariable("d1") String email,@PathVariable("d2") String password)  {
	if(userService.login(email, password))
	{
		return "user logged in";
	}
	else{
		return "erreur";
	}
	}
	
	@GetMapping("/sendmail/{d1}")
	@ResponseBody
	public void sendmail(@PathVariable("d1") String email) throws MessagingException  {

	User u=userService.getcode(email);
    if(u!=null)
    {    try {
          
    	notificationService.sendEmailForgot(u);
    
    
    } catch (MailException mailException) {
		System.out.println(mailException);
	}
    	System.out.println("User exist !!!");	

    }
    else{
    	
    	System.out.println("User doesnt exist !!!");	
    }
	}
}
