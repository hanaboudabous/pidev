
package tn.esprit.spring.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;

import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.MailException;
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
import tn.esprit.spring.entity.Geographical_area;
import tn.esprit.spring.entity.Role_User;
import tn.esprit.spring.entity.Status;
import tn.esprit.spring.entity.User;
import tn.esprit.spring.services.IUserService;
import tn.esprit.spring.services.MailService;


@Scope(value = "session")
@Controller(value = "UserController") // Name of the bean in Spring IoC
@ELBeanName(value = "UserController") // Name of the bean used by JSF
public class UserController {
	@Autowired
	MailService notificationService;
	
	@Autowired
	IUserService userService;
	
	@Autowired
	BCryptPasswordEncoder passcrypt;
	String im;
	private int User_ID;
	private String First_name;
	private String Last_name;
	private int Number;
	private long CIN;
	Geographical_area Geographical_area;
	private int Motorisation;
	private Date Birth_date;
	private String Address;
	private int Postal_code;
	private String Job;
	Status Status;
    private String sexe;
	private int shifting;
	private String email;
	private String password;
	private String Confirm_password;
	private int Scoring;
	private int random;
	private Date Hiring_date;
	Role_User Role_User;
	private Boolean loggedIn;
	private User user;


	
	
	@GetMapping("/register/users")
	@ResponseBody
	public List<User> getUsers() {
	List<User> list = userService.retrieveAllUsers();
	return list;
	}
	
	public String doLogin(){
		String navigateTo = "null";
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); 
		User u=userService.getcode(email);
		encoder.matches(password, u.getPassword());
		if (u != null && u.getRole_User() == tn.esprit.spring.entity.Role_User.Client && encoder.matches(password, u.getPassword()))
		{
		navigateTo = "/template/index.xhtml?faces-redirect=true";
		loggedIn = true; }
		else {
		FacesMessage facesMessage =

		new FacesMessage("Login Failed: please check your username/password and try again.");

		FacesContext.getCurrentInstance().addMessage("form:btn",facesMessage);
		}
		return navigateTo;
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
		user.setImage(im);
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
	public String sendmail(@PathVariable("d1") String email) throws MessagingException  {

	User u=userService.getcode(email);
    if(u!=null)
    {    try {
          
    	notificationService.sendEmailForgot(u);
    
    
    } catch (MailException mailException) {
		System.out.println(mailException);
	}
    	return "success !"	;

    }
    else{
    	
    	return "user doesnt exist !!"	;
    }
	}

	public MailService getNotificationService() {
		return notificationService;
	}

	public void setNotificationService(MailService notificationService) {
		this.notificationService = notificationService;
	}

	public IUserService getUserService() {
		return userService;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public BCryptPasswordEncoder getPasscrypt() {
		return passcrypt;
	}

	public void setPasscrypt(BCryptPasswordEncoder passcrypt) {
		this.passcrypt = passcrypt;
	}

	public String getIm() {
		return im;
	}

	public void setIm(String im) {
		this.im = im;
	}

	public int getUser_ID() {
		return User_ID;
	}

	public void setUser_ID(int user_ID) {
		User_ID = user_ID;
	}

	public String getFirst_name() {
		return First_name;
	}

	public void setFirst_name(String first_name) {
		First_name = first_name;
	}

	public String getLast_name() {
		return Last_name;
	}

	public void setLast_name(String last_name) {
		Last_name = last_name;
	}

	public int getNumber() {
		return Number;
	}

	public void setNumber(int number) {
		Number = number;
	}

	public long getCIN() {
		return CIN;
	}

	public void setCIN(long cIN) {
		CIN = cIN;
	}

	public Geographical_area getGeographical_area() {
		return Geographical_area;
	}

	public void setGeographical_area(Geographical_area geographical_area) {
		Geographical_area = geographical_area;
	}

	public int getMotorisation() {
		return Motorisation;
	}

	public void setMotorisation(int motorisation) {
		Motorisation = motorisation;
	}

	public Date getBirth_date() {
		return Birth_date;
	}

	public void setBirth_date(Date birth_date) {
		Birth_date = birth_date;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public int getPostal_code() {
		return Postal_code;
	}

	public void setPostal_code(int postal_code) {
		Postal_code = postal_code;
	}

	public String getJob() {
		return Job;
	}

	public void setJob(String job) {
		Job = job;
	}

	public Status getStatus() {
		return Status;
	}

	public void setStatus(Status status) {
		Status = status;
	}

	public String getSexe() {
		return sexe;
	}

	public void setSexe(String sexe) {
		this.sexe = sexe;
	}

	public int getShifting() {
		return shifting;
	}

	public void setShifting(int shifting) {
		this.shifting = shifting;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email1) {
		email = email1;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password1) {
		password = password1;
	}

	public String getConfirm_password() {
		return Confirm_password;
	}

	public void setConfirm_password(String confirm_password) {
		Confirm_password = confirm_password;
	}

	public int getScoring() {
		return Scoring;
	}

	public void setScoring(int scoring) {
		Scoring = scoring;
	}

	public int getRandom() {
		return random;
	}

	public void setRandom(int random) {
		this.random = random;
	}

	public Date getHiring_date() {
		return Hiring_date;
	}

	public void setHiring_date(Date hiring_date) {
		Hiring_date = hiring_date;
	}

	public Role_User getRole_User() {
		return Role_User;
	}

	public void setRole_User(Role_User role_User) {
		Role_User = role_User;
	}

	public Boolean getLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(Boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
}