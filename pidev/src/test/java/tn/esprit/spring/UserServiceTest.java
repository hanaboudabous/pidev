package tn.esprit.spring;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tn.eprit.spring.services.IUserService;
import tn.esprit.spring.entity.Accounting;
import tn.esprit.spring.entity.Geographical_area;
import tn.esprit.spring.entity.Role_User;
import tn.esprit.spring.entity.Status;
import tn.esprit.spring.entity.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {



	@Autowired
	IUserService us;

	@Test
	public void RetrieveUsers() {
		List<User> list=us.retrieveAllUsers();
		Assert.assertEquals(0,list.size());
	}

	@Test
	public void addUser() throws ParseException {
		Random rand = new Random();

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date d = dateFormat.parse("1997-08-30");
		Accounting a = new Accounting(1,25,26,58,59);
		User u = new User("Chourou","Amine", 99216983,00236547,
				Geographical_area.Ben_arous, 1, d,
				"Ezzahra city",2034,"DGA",Status.Single,0,
				"hduahd@dhaud.com","kkkkk","kkkkk", 1,rand.nextInt(9999999)+1111111,d,Role_User.Admin,a);
		User userAdded = us.addUser(u);
		Assert.assertEquals(u.getFirst_name(), userAdded.getFirst_name());
	}
}


