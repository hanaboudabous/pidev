package tn.esprit.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entity.MyUserDetails;
import tn.esprit.spring.entity.User;
import tn.esprit.spring.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRep;
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
		User users=userRep.getcode(email);
		if(users.getEmail()=="")
		{
			System.out.println("user not found");
		}
		System.out.println(new MyUserDetails(users));
		return new MyUserDetails(users);
		
	}

}
