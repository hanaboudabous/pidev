package tn.esprit.spring.services;

import java.util.List;

import tn.esprit.spring.entity.Payment;

public interface IPaymentService {

	int addPayment(Payment payment);

	List<Payment> getAllPayment();

	Payment updatePayment(Payment p);

	void deleteById(int Payment_ID);

}
