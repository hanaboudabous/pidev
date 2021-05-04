package tn.esprit.spring.services;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.servlet.http.Part;

import org.springframework.web.multipart.MultipartFile;

import tn.esprit.spring.entity.User;
import tn.esprit.spring.entity.Role_User;

public interface IUserService {

	 List<User> retrieveAllUsers();
	 User addUser(User u);
	 void deleteUser(String id);
	 User updateUser(User u);
	 User retrieveUser(String id);
	 List<User> retrieveUsersByRole(Role_User role);
	 void verifie_account(String id);
	 List<User> retrieveUsersByDate(String d1,String d2) throws ParseException;
	 User login(String email,String password);
	 User getcode(String email);
	 User getRandom(String random);
	 void upMdp(String random,String mdp);
	 void upRandom(Integer random,String random1);
	 List<Object> getUserbydate();
	 List<Object> getUserbyarea();
	 void uploadFile(Part file)throws IllegalStateException, IOException;
}
