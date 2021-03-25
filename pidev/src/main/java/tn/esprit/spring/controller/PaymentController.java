/*package tn.esprit.spring.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.eprit.spring.services.LeaveTestServiceImpl;
import tn.eprit.spring.services.PaymentServiceImpl;
import tn.esprit.spring.entity.LeaveTest;
import tn.esprit.spring.entity.Payment;
@RestController
public class PaymentController {
@Autowired
	private PaymentServiceImpl payment;
	
	
	
	@GetMapping("/payments")
	@ResponseBody
	public List<Payment> getAllPayment(){
		return payment.getAllPayment();
	}
	
	

	@RequestMapping("/AddPayment")
	@PostMapping
		public int addbillM(@RequestBody Payment p) {
		payment.addPayment(p);
		return p.getPayment_ID();
		}

	}

















*/

