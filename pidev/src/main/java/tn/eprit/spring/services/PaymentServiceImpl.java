package tn.eprit.spring.services;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import tn.esprit.spring.entity.Payment;
import tn.esprit.spring.repository.PaymentRepository;


@Service
public class PaymentServiceImpl implements IPaymentService{


	@Autowired
	PaymentRepository paymentRepository;
	
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

	/*public void affecterPaymentToUser(int Payment_ID, int User_ID) {
		User user = userRepository.findById(User_ID).get();
		Payment payment =paymentRepository.findById(Payment_ID).get();
		if (ObjectUtils.isEmpty(user) && !ObjectUtils.isEmpty(payment))
			user.setPayment(payment);
		userRepository.save(user);
	}*/

	@Override
	public void deleteById(int Payment_ID) {

		paymentRepository.deleteById(Payment_ID );
	}

	//Salary

	public static double Salary (int Work_hours)
	{
	double Salary;
	if (Work_hours < 160)
	{
		Salary = Work_hours * 15;
	}
	else if (Work_hours < 200)
	{
		Salary = 160 * 15 + (Work_hours - 160) * 20;
	}
	else
	{
		Salary = (160 * 15) + (40 * 20) + ((Work_hours - 200)*25);
	}
	return Salary;
	}
}