package tn.esprit.spring.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Random;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import tn.esprit.spring.entity.Role_User;
import tn.esprit.spring.entity.User;
import tn.esprit.spring.security.SecurityConfig;
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
	String im;

	
	
	@GetMapping("/register/users")
	@ResponseBody
	public List<User> getUsers() {
	List<User> list = userService.retrieveAllUsers();
	return list;
	}
	
	@GetMapping("/register/userstats")
	@ResponseBody
	public List<Object> getStats() {
	List<Object> list = userService.getUserbydate();
	return list;
	}
	
	@GetMapping("/register/userstatsarea")
	@ResponseBody
	public List<Object> getStatsarea() {
	List<Object> list = userService.getUserbyarea();
	return list;
	}
	
	@GetMapping("/register/retrieve-user/{user-id}")
	@ResponseBody
	public User retrieveUser(@PathVariable("user-id") String Id) {
	return userService.retrieveUser(Id);
	}
		
	@PostMapping("/add-user")
	@ResponseBody
	public User addUser(@RequestBody User u) throws MessagingException {
	String pwd=u.getPassword();
	Random rand = new Random();
	String crypt=passcrypt.encode(pwd);
	u.setImage(im);
	u.setPassword(crypt);
	u.setRandom(rand.nextInt(9999999)+1111111);
	User user = userService.addUser(u);
	notificationService.sendEmailVerify(u);
	return user;
	}
	
	@PostMapping("/upload")
    public void upload(@RequestParam("file") MultipartFile file) throws MessagingException, IllegalStateException, IOException 
	{
		
	userService.uploadFile(file);
	im=file.getOriginalFilename();
		
	} 
	
	@DeleteMapping("/register/remove-user/{user-id}")
	@ResponseBody
	public void removeUser(@PathVariable("user-id") String userId) {
	userService.deleteUser(userId);
	}
	
	@PutMapping("/register/modify-user")
	@ResponseBody
	public User modifyUser(@RequestBody User user) {
		String pwd=user.getPassword();
		Random rand = new Random();
		String crypt=passcrypt.encode(pwd);
		user.setPassword(crypt);
		user.setRandom(rand.nextInt(9999999)+1111111);
	return userService.updateUser(user);
	}
	
	@GetMapping("/register/retrieve-userR/{role}")
	@ResponseBody
	public List<User> retrieveUserByRole(@PathVariable("role") Role_User role) {
	return userService.retrieveUsersByRole(role);
	}
	
	@GetMapping("/register/verifie-user/{user-id}")
	@ResponseBody
	public void verifieUser(@PathVariable("user-id") String Id) {
	userService.verifie_account(Id);
	}
	
	@GetMapping("/register/retrieve-userbydate/{d1}/{d2}")
	@ResponseBody
	public List<User> retrieveUsersByDate(@PathVariable("d1") String d1,@PathVariable("d2") String d2) throws ParseException {
	return userService.retrieveUsersByDate(d1, d2);
	}
	
	@GetMapping("/register/login/{d1}/{d2}")
	@ResponseBody
	public String login(@PathVariable("d1") String email,@PathVariable("d2") String password)  {
		String crypt=passcrypt.encode(password);
	if(userService.login(email, crypt)!=null)
	{
		return "user logged in";
	}
	else{
		return "erreur";
	}
	}
	
	@GetMapping("/register/mdpoub/{d1}/{d2}")
	@ResponseBody
	public String mdpoub(@PathVariable("d1") String random,@PathVariable("d2") String password)  {
	if(userService.getRandom(random)!=null)
	{
		String crypt=passcrypt.encode(password);
		userService.upMdp(random, crypt);
		Random rand = new Random();
        userService.upRandom(rand.nextInt(9999999)+1111111, random);
		return "password changed !";
	}
	else{
		return "erreur";
	}
	}
	
	@GetMapping("/register/sendmail/{d1}")
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
