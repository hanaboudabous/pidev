package tn.eprit.spring.services;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import tn.esprit.spring.entity.Payment;
import tn.esprit.spring.entity.User;
import tn.esprit.spring.repository.PaymentRepository;
import tn.esprit.spring.repository.UserRepository;

@Service
public class PaymentServiceImpl implements IPaymentService{


	@Autowired
	private PaymentRepository paymentRepository;	
	@Autowired
	UserRepository userRepository;
	
	@Override
	public int addPayment(Payment payment) {
		return paymentRepository.save(payment).getPayment_ID();
	}

	@Override
	public List<Payment> getAllPayment() {
		return(List<Payment>) (paymentRepository.findAll());
	}
	@Override
	public Payment updatePayment(Payment p) {

		return paymentRepository.save(p);

	}

	public void affecterPaymentToUser(int Payment_ID, int User_ID) {
		User user = userRepository.findById(User_ID).get();
		Payment payment =paymentRepository.findById(Payment_ID).get();
		if (!ObjectUtils.isEmpty(user) && !ObjectUtils.isEmpty(payment))
			payment.setUser(user);
		userRepository.save(user);
	}

	@Override
	public void deleteById(int Payment_ID) {

		paymentRepository.deleteById(Payment_ID );
	}


}