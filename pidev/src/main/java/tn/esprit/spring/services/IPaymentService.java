package tn.esprit.spring.services;

import java.util.List;

import tn.esprit.spring.entity.Payment;
import tn.esprit.spring.entity.Type_Payment;


public interface IPaymentService {
	List<Payment> retrieveAllPayments() ;
	Payment addPayment(Payment p);
	void deletePayment(String id);
	Payment updatePayment(Payment p);
	Payment retrievePayment(String id);
	List<Payment> retrievePaymentByType(Type_Payment type);
	
}
