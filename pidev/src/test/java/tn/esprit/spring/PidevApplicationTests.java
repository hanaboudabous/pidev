package tn.esprit.spring;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tn.esprit.spring.entity.EmployeeSalary;
import tn.esprit.spring.entity.LeaveTest;
import tn.esprit.spring.entity.Leave_reason;
import tn.esprit.spring.entity.Payment;
import tn.esprit.spring.entity.Type_Leave;
import tn.esprit.spring.services.IEmployeeSalaryService;
import tn.esprit.spring.services.ILeaveTestService;
import tn.esprit.spring.services.IPaymentService;
import tn.esprit.spring.services.LeaveTestServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PidevApplicationTests  {
	@Autowired(required = false)
	IPaymentService ps;
	
	@Test
	public void contextLoads1(){
		ps.getAllPayment();
		
	}

}
