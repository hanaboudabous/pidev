package tn.esprit.spring.services;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import tn.esprit.spring.entity.LeaveTest;
import tn.esprit.spring.entity.Payment;
import tn.esprit.spring.entity.Type_Payment;
import tn.esprit.spring.entity.User;
import tn.esprit.spring.repository.PaymentRepository;
import tn.esprit.spring.repository.UserRepository;


@Service
public class PaymentServiceImpl implements IPaymentService{
		@Autowired
		PaymentRepository paymentRepository;
		@Autowired
		UserRepository userRepository;
		private static final Logger l = LogManager.getLogger(PaymentServiceImpl.class);

		public List<Payment> retrieveAllPayments() {
			List<Payment> payments = (List<Payment>) paymentRepository.findAll();
			for(Payment payment : payments)
			{
				l.info("payment ++ :"+payment);
			}
			return payments;
			
		}

		public Payment addPayment(Payment p) {
			Payment paymentSaved = null;
			paymentSaved=paymentRepository.save(p);
			return paymentSaved;
			
		}

		public void deletePayment(String id) {
			paymentRepository.deleteById(Integer.parseInt(id));
			
		}

		public Payment updatePayment(Payment p) {
			Payment paymentAdded = paymentRepository.save(p);
			return paymentAdded;
		}

		public Payment retrievePayment(String id) {
		
			l.info("in retrievePayment id= "+id);
			Payment p = paymentRepository.findById(Integer.parseInt(id)).orElse(null);
			l.info("Payment returned : "+p);
			return p;
			
		}
		
		public List<Payment> retrievePaymentByType(Type_Payment type)
		{
			List<Payment> payments = (List<Payment>) paymentRepository.retrievePaymentByType(type);
			for(Payment payment : payments)
			{
				l.info("payment ++ :"+payment);
			}
			return payments;	
		}
		
		@Override
		public void affecterPaymentToUser(int idp, int id) {
		Payment payment = paymentRepository.findById(idp).get();
		User user = userRepository.findById(id).get();
		if (!ObjectUtils.isEmpty(payment) && !ObjectUtils.isEmpty(user))
			payment.setUser(user);
			userRepository.save(user);
		}

}
