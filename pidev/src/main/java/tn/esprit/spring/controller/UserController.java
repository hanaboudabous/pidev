
package tn.esprit.spring.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Random;


import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.mail.MessagingException;
import javax.servlet.http.Part;

import org.ocpsoft.rewrite.el.ELBeanName;
import org.richfaces.model.UploadedFile;
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

@ViewScoped
@ManagedBean
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
	private Part im1;
	String im;
	private int User_ID;
	private String first_name;
	private String last_name;
	private int number;
	private long cin;
	private Geographical_area geographical_area;
	private boolean motorisation;
	private Date birth_date;
	private String address;
	private int postal_code;
	private String job;
	Status status;
    private String sexe;
	private boolean shifting;
	private String email;
	private String password;
	private String confirm_password;
	private int scoring;
	private String random;
	private Date Hiring_date;
	Role_User Role_User;
	private Boolean loggedIn;
	private User user;


	public String doLogin(){
		String navigateTo = "null";
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); 
		User u=userService.getcode(email);
		if (email.length()>=8 && password.length()>=8)
		{
			this.setIm(u.getImage());
			encoder.matches(password, u.getPassword());
			if(u.getRole_User() == tn.esprit.spring.entity.Role_User.Client && encoder.matches(password, u.getPassword()))
			{
		
		navigateTo = "/template/template.jsf?faces-redirect=true";
		loggedIn = true; 
		}
			else{FacesMessage facesMessage =

					new FacesMessage("Login Failed: please check your username/password and try again.");

					FacesContext.getCurrentInstance().addMessage("form:btn",facesMessage);}}
		else {
		FacesMessage facesMessage =

		new FacesMessage("Login Failed: please check your username/password and try again.");

		FacesContext.getCurrentInstance().addMessage("form:btn",facesMessage);
		}
		return navigateTo;
		}
	
	
	public String sendmail() throws MessagingException{
		String navigateTo = "null";

		User u=userService.getcode(email);
			if(u!=null)
			{
			notificationService.sendEmailForgot(u);
		navigateTo = "forgot.jsf?faces-redirect=true";
			}
		else {
		FacesMessage facesMessage =

		new FacesMessage("Sending email Failed: please check your email and try again.");

		FacesContext.getCurrentInstance().addMessage("form:btn",facesMessage);
		}
		return navigateTo;
		}
	
	
	public String forgot() throws MessagingException{
		String navigateTo = "null";

		if (random!=null && password.length()>=8)
		{if(userService.getRandom(random)!=null)
		{
			String crypt=passcrypt.encode(password);
			userService.upMdp(random, crypt);
			Random rand = new Random();
	        userService.upRandom(rand.nextInt(9999999)+1111111, random);
	        navigateTo = "login.jsf?faces-redirect=true";
		}
		else{
			FacesMessage facesMessage =

					new FacesMessage("Changing password Failed: please check your random code and try again.");

					FacesContext.getCurrentInstance().addMessage("form:btn",facesMessage);
		}
		}
		else {
		FacesMessage facesMessage =

		new FacesMessage("Changing password Failed: please check your random code and try again.");

		FacesContext.getCurrentInstance().addMessage("form:btn",facesMessage);
		}
		return navigateTo;
		}
	
	public String doLogout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/amine/login.jsf?faces-redirect=true";
		}
	
	    public String addUser() throws MessagingException{
	  System.out.println(password+"  "+confirm_password);
	    	if(password.equals(confirm_password))
	    	{
	    	  	User u=userService.getcode(email);
		    	Date currentUtilDate = new Date();
		    	Random rand = new Random();
		    	String crypt=passcrypt.encode(password);
	    	if(u==null)
	    	{
	    	if(motorisation==true)
	    	{
	    		if(shifting==true)
	    		{
	    			if(sexe.equals("homme"))
	    			{
	    		    	userService.addUser(new User(first_name,last_name,number,cin,
	    		    			geographical_area,1,birth_date,
	    		    			address,postal_code,job,status,sexe,
	    		    			1,email,crypt,confirm_password,"1.png",rand.nextInt(9999999)+1111111,
	    		    			currentUtilDate,tn.esprit.spring.entity.Role_User.Client));
	    		    	
	    		    	notificationService.sendEmailVerify(new User(first_name,last_name,number,cin,
	    		    			geographical_area,0,birth_date,
	    		    			address,postal_code,job,status,sexe,
	    		    			1,email,crypt,confirm_password,"1.png",rand.nextInt(9999999)+1111111,
	    		    			currentUtilDate,tn.esprit.spring.entity.Role_User.Client));
	    		    	return "login.jsf?faces-redirect=true";
	    			}
	    			else {
	    				
	    		    	userService.addUser(new User(first_name,last_name,number,cin,
	    		    			geographical_area,1,birth_date,
	    		    			address,postal_code,job,status,sexe,
	    		    			1,email,crypt,confirm_password,"2.png",rand.nextInt(9999999)+1111111,
	    		    			currentUtilDate,tn.esprit.spring.entity.Role_User.Client));	
	    		    	notificationService.sendEmailVerify(new User(first_name,last_name,number,cin,
	    		    			geographical_area,0,birth_date,
	    		    			address,postal_code,job,status,sexe,
	    		    			1,email,crypt,confirm_password,"2.png",rand.nextInt(9999999)+1111111,
	    		    			currentUtilDate,tn.esprit.spring.entity.Role_User.Client));
	    		    	return "login.jsf?faces-redirect=true";
	    			}

	    		}
	    		else{
	    			if(sexe.equals("homme"))
	    			{
	    		    	userService.addUser(new User(first_name,last_name,number,cin,
	    		    			geographical_area,1,birth_date,
	    		    			address,postal_code,job,status,sexe,
	    		    			0,email,crypt,confirm_password,"1.png",rand.nextInt(9999999)+1111111,
	    		    			currentUtilDate,tn.esprit.spring.entity.Role_User.Client));
	    		    	notificationService.sendEmailVerify(new User(first_name,last_name,number,cin,
	    		    			geographical_area,0,birth_date,
	    		    			address,postal_code,job,status,sexe,
	    		    			1,email,crypt,confirm_password,"1.png",rand.nextInt(9999999)+1111111,
	    		    			currentUtilDate,tn.esprit.spring.entity.Role_User.Client));
	    		    	return "login.jsf?faces-redirect=true";
	    			}
	    			else {
	    				
	    		    	userService.addUser(new User(first_name,last_name,number,cin,
	    		    			geographical_area,1,birth_date,
	    		    			address,postal_code,job,status,sexe,
	    		    			0,email,crypt,confirm_password,"2.png",rand.nextInt(9999999)+1111111,
	    		    			currentUtilDate,tn.esprit.spring.entity.Role_User.Client));	
	    		    	
	    		    	notificationService.sendEmailVerify(new User(first_name,last_name,number,cin,
	    		    			geographical_area,0,birth_date,
	    		    			address,postal_code,job,status,sexe,
	    		    			1,email,crypt,confirm_password,"2.png",rand.nextInt(9999999)+1111111,
	    		    			currentUtilDate,tn.esprit.spring.entity.Role_User.Client));
	    		    	return "login.jsf?faces-redirect=true";
	    			}
	    			

	    		}
	    	}
	    	else{
	    		if(shifting==true)
	    		{
	    			if(sexe.equals("homme"))
	    			{
	    		    	userService.addUser(new User(first_name,last_name,number,cin,
	    		    			geographical_area,0,birth_date,
	    		    			address,postal_code,job,status,sexe,
	    		    			1,email,crypt,confirm_password,"1.png",rand.nextInt(9999999)+1111111,
	    		    			currentUtilDate,tn.esprit.spring.entity.Role_User.Client));
	    		    	
	    		    	notificationService.sendEmailVerify(new User(first_name,last_name,number,cin,
	    		    			geographical_area,0,birth_date,
	    		    			address,postal_code,job,status,sexe,
	    		    			1,email,crypt,confirm_password,"1.png",rand.nextInt(9999999)+1111111,
	    		    			currentUtilDate,tn.esprit.spring.entity.Role_User.Client));
	    		    	return "login.jsf?faces-redirect=true";
	    			}
	    			else {
	    				
	    		    	userService.addUser(new User(first_name,last_name,number,cin,
	    		    			geographical_area,0,birth_date,
	    		    			address,postal_code,job,status,sexe,
	    		    			1,email,crypt,confirm_password,"2.png",rand.nextInt(9999999)+1111111,
	    		    			currentUtilDate,tn.esprit.spring.entity.Role_User.Client));	
	    		    	
	    		    	notificationService.sendEmailVerify(new User(first_name,last_name,number,cin,
	    		    			geographical_area,0,birth_date,
	    		    			address,postal_code,job,status,sexe,
	    		    			1,email,crypt,confirm_password,"2.png",rand.nextInt(9999999)+1111111,
	    		    			currentUtilDate,tn.esprit.spring.entity.Role_User.Client));
	    		    	return "login.jsf?faces-redirect=true";
	    			}
	    		}
	    		else{
	    			if(sexe.equals("homme"))
	    			{
	    		    	userService.addUser(new User(first_name,last_name,number,cin,
	    		    			geographical_area,0,birth_date,
	    		    			address,postal_code,job,status,sexe,
	    		    			0,email,crypt,confirm_password,"1.png",rand.nextInt(9999999)+1111111,
	    		    			currentUtilDate,tn.esprit.spring.entity.Role_User.Client));
	    		    	
	    		    	notificationService.sendEmailVerify(new User(first_name,last_name,number,cin,
	    		    			geographical_area,0,birth_date,
	    		    			address,postal_code,job,status,sexe,
	    		    			1,email,crypt,confirm_password,"1.png",rand.nextInt(9999999)+1111111,
	    		    			currentUtilDate,tn.esprit.spring.entity.Role_User.Client));
	    		    	return "login.jsf?faces-redirect=true";
	    			}
	    			else {
	    				
	    		    	userService.addUser(new User(first_name,last_name,number,cin,
	    		    			geographical_area,0,birth_date,
	    		    			address,postal_code,job,status,sexe,
	    		    			0,email,crypt,confirm_password,"2.png",rand.nextInt(9999999)+1111111,
	    		    			currentUtilDate,tn.esprit.spring.entity.Role_User.Client));	
	    		    	
	    		    	notificationService.sendEmailVerify(new User(first_name,last_name,number,cin,
	    		    			geographical_area,0,birth_date,
	    		    			address,postal_code,job,status,sexe,
	    		    			1,email,crypt,confirm_password,"2.png",rand.nextInt(9999999)+1111111,
	    		    			currentUtilDate,tn.esprit.spring.entity.Role_User.Client));
	    		    	return "login.jsf?faces-redirect=true";
	    			}
	    		}
	    		
	    	}
	    	}
	    	else{
	    		FacesMessage facesMessage =

						new FacesMessage("Sign up Failed: the acount does exist !");

						FacesContext.getCurrentInstance().addMessage("form:btn",facesMessage);
						return "null";
	    		
	    	}
	    	}
	    	else{
	    		FacesMessage facesMessage =

						new FacesMessage("Sign up Failed: password must be same to confirm_password !");

						FacesContext.getCurrentInstance().addMessage("form:btn",facesMessage);	
						return "null";
	    	}
	

		}
	    
	    public void upload() throws IllegalStateException, IOException {
	    	System.out.println(im1+"");
	    	//userService.uploadFile(im1);
	    	im=im1.getName();
		}

	
	
	
	
	/*****************************************************/
	
	
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
		
	//userService.uploadFile(file);
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
		return first_name;
	}

	public void setFirst_name(String first_name1) {
		first_name = first_name1;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name1) {
		last_name = last_name1;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number1) {
		number = number1;
	}

	public long getCin() {
		return cin;
	}

	public void setCin(long cIN1) {
		cin = cIN1;
	}

	public Geographical_area[] getGeographical_areas() {
		return Geographical_area.values();
	}

	public void setGeographical_areas(Geographical_area geographical_area1) {
		geographical_area = geographical_area1;
	}

	public boolean getMotorisation() {
		return motorisation;
	}

	public void setMotorisation(boolean motorisation1) {
		motorisation = motorisation1;
	}

	public Date getBirth_date() {
		return birth_date;
	}

	public void setBirth_date(Date birth_date1) {
		birth_date = birth_date1;
	}

	public String getAddress() {
		return address;
	}

	public void setaddress(String address1) {
		address = address1;
	}

	public int getPostal_code() {
		return postal_code;
	}

	public void setPostal_code(int postal_code1) {
		postal_code = postal_code1;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job1) {
		job = job1;
	}

	public Status[] getStatuss() {
		return Status.values();
	}

	public void setStatuss(Status status1) {
		status = status1;
	}

	public String getSexe() {
		return sexe;
	}

	public void setSexe(String sexe) {
		this.sexe = sexe;
	}

	public boolean getShifting() {
		return shifting;
	}

	public void setShifting(boolean shifting) {
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
		return confirm_password;
	}

	public void setConfirm_password(String confirm_password1) {
		confirm_password = confirm_password1;
	}

	public int getScoring() {
		return scoring;
	}

	public void setScoring(int scoring1) {
		scoring = scoring1;
	}

	public String getRandom() {
		return random;
	}

	public void setRandom(String random) {
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


	public Part getIm1() {
		return im1;
	}


	public void setIm1(Part im1) {
		this.im1 = im1;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public Geographical_area getGeographical_area() {
		return geographical_area;
	}


	public void setGeographical_area(Geographical_area geographical_area) {
		this.geographical_area = geographical_area;
	}


	public Status getStatus() {
		return status;
	}


	public void setStatus(Status status) {
		this.status = status;
	}
	
	
}