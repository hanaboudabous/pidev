package tn.esprit.spring.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.entity.User;
import tn.esprit.spring.entity.Role_User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>{
	@Query("SELECT u FROM User u WHERE u.Role_User= ?1")
	List<User> retrieveUsersByRole(Role_User role);
	
	@Modifying
	@Transactional
	@Query("update User u set u.Verified_account=1 where u.User_ID = ?1")
	void verifiedaccount(Integer id);
	
	@Query("SELECT u FROM User u WHERE u.Hiring_date between ?1 and ?2")
	List<User> retrieveUsersByDate(Date d1,Date d2);
	
	@Query("SELECT u FROM User u WHERE u.Email= ?1 and u.Password= ?2")
	User login(String email,String password);
	
	@Query("SELECT u FROM User u WHERE u.Email= ?1")
	User getcode(String email);

}
