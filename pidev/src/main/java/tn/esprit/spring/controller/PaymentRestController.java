package tn.esprit.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import tn.esprit.spring.entity.Payment;
import tn.esprit.spring.entity.Type_Payment;
import tn.esprit.spring.services.IPaymentService;

@Controller
public class PaymentRestController {
	@Autowired
	IPaymentService paymentService;
	
	
	@GetMapping("/payments")
	@ResponseBody
	public List<Payment> getPayments() {
	List<Payment> list = paymentService.retrieveAllPayments();
	return list;
	}
	
	
	@GetMapping("/retrievePayment/{paymentId}")
	@ResponseBody
	public Payment retrievePayment(@PathVariable("paymentId") String Id) {
	return paymentService.retrievePayment(Id);
	}
	
	@GetMapping("/retrievePaymentType/{type}")
	@ResponseBody
	public List<Payment> retrievePaymentByType(@PathVariable("type") Type_Payment type) {
	return paymentService.retrievePaymentByType(type);
	}
	
	
	@PostMapping("/addPayment")
	@ResponseBody
	public String addPayment(@RequestBody Payment p) {
	Payment payment = paymentService.addPayment(p);
	return "Payment successfully added: /n " +p;
	
	
	}
	
	@DeleteMapping("/removePayment/{paymentId}")
	@ResponseBody
	public String removePayment(@PathVariable("paymentId") String paymentId) {
		paymentService.deletePayment(paymentId);
		return "Payment successfully deleted and its Id= " +paymentId;
	}
	
	@PutMapping("/updatePayment")
	@ResponseBody
	public Payment updatePayment(@RequestBody Payment payment) {
	return paymentService.updatePayment(payment);
	}
	
	@PutMapping("/affecterPaymentToUser/{Payment_ID}/{User_ID}")
	@ResponseBody
	public void affecterPaymentToUser(@PathVariable("Payment_ID") int idp,@PathVariable("User_ID") int id) {
		paymentService.affecterPaymentToUser(idp, id);
	}
	
}
