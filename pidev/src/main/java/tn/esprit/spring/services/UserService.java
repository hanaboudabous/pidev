package tn.esprit.spring.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	public boolean login(String email,String password)
	{
		User u=userRepository.login(email, password);
		if(u.getVerified_account()==1)
		{
			return true;
		}
		else {
			return false;
		}
		
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

}
