package tn.esprit.spring.services;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import tn.esprit.spring.entity.User;
import tn.esprit.spring.repository.UserRepository;
import tn.esprit.spring.services.UserService;
import tn.esprit.spring.entity.Role_User;

@Service
public class UserService implements IUserService {
	@Autowired
	UserRepository userRepository;
	private static final Logger l = LogManager.getLogger(UserService.class);

	public List<User> retrieveAllUsers() {
		List<User> users = (List<User>) userRepository.findAll();
		for(User user : users)
		{
			l.info("user ++ :"+user);
		}
		return users;
		
	}

	public User addUser(User u) {
		User userSaved = null;
		userSaved=userRepository.save(u);
		return userSaved;
		
	}

	public void deleteUser(String id) {
		userRepository.deleteById(Integer.parseInt(id));
		
	}

	public User updateUser(User u) {
		User userAdded = userRepository.save(u);
		return userAdded;
	}

	public User retrieveUser(String id) {
	
		l.info("in retrieveUser id= "+id);
		User u = userRepository.findById(Integer.parseInt(id)).orElse(null);
		l.info("user returned : "+u);
		return u;
		
	}
	
	public List<User> retrieveUsersByRole(Role_User role)
	{
		List<User> users = (List<User>) userRepository.retrieveUsersByRole(role);
		for(User user : users)
		{
			l.info("user ++ :"+user);
		}
		return users;	
	}
	
	public void verifie_account(String id)
	{
		userRepository.verifiedaccount(Integer.parseInt(id));
		
	}
	
	public List<User> retrieveUsersByDate(String d1,String d2) throws ParseException
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date d11 = dateFormat.parse(d1);
		Date d22 = dateFormat.parse(d2);
		List<User> users = (List<User>) userRepository.retrieveUsersByDate(d11,d22);
		for(User user : users)
		{
			l.info("user ++ :"+user);
		}
		return users;	
	}
	
	public User login(String email,String password)
	{
		User u=userRepository.login(email, password);
		System.out.println(u);
        return u;
		
	}
	
	 public List<Object> getUserbydate()
	 {
		 List<Object> u =userRepository.getUserbydate();
		 return u;
	 }
	 public List<Object> getUserbyarea()
	 {
		 List<Object> u =userRepository.getUserbyarea();
		 return u; 
	 }

	
	public User getcode(String email)
	{
		User u=userRepository.getcode(email);
		if(u!=null)
		{
       return u;}
		else{
			return null;
		}
		
		
	}
	public User getRandom(String random)
	{
		User u=userRepository.getRandom(Integer.parseInt(random));
		if(u!=null)
		{
       return u;}
		else{
			return null;
		}
		
		
	}
	public void upMdp(String random,String mdp)
	{
		userRepository.upMdp(Integer.parseInt(random), mdp);

	}
	public void upRandom(Integer random,String random1)
	{
		userRepository.upRandom(random, Integer.parseInt(random1));

	}
	
	public void uploadFile(MultipartFile file) throws IllegalStateException, IOException
	{
    file.transferTo(new File("C:\\wamp\\www\\images\\"+file.getOriginalFilename()));

	}
}
