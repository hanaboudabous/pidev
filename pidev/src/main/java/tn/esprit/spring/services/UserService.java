package tn.esprit.spring.services;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entity.User;
import tn.esprit.spring.repository.UserRepository;
import tn.esprit.spring.services.UserService;

@Service
public class UserService implements IUserService {

	@Autowired
	UserRepository userRepository;

	private static final Logger l = LogManager.getLogger(UserService.class);

	@Override
	public List<User> retrieveAllUsers() {
		List<User> users = (List<User>) userRepository.findAll();
		for(User user : users)
		{
			l.info("user ++ :"+user);
		}
		return users;
	}

	@Override
	public User addUser(User u) {
		User userSaved = null;
		userSaved=userRepository.save(u);
		return userSaved;
	}

	@Override
	public void deleteUser(String id) {
		userRepository.deleteById(Integer.parseInt(id));

	}

	@Override
	public User updateUser(User u) {
		User userAdded = userRepository.save(u);
		return userAdded;
	}

	@Override
	public User retrieveUser(String id) {
		l.info("in retrieveUser id= "+id);
		User u = userRepository.findById(Integer.parseInt(id)).orElse(null);
		l.info("user returned : "+u);
		return u;
	}

}
