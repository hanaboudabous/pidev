package tn.esprit.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entity.Payment;
import tn.esprit.spring.entity.Type_Payment;


@Repository
public interface PaymentRepository extends CrudRepository<Payment,Integer>{
	@Query("SELECT p FROM Payment p WHERE p.Type_Payment= ?1")
	List<Payment> retrievePaymentByType(Type_Payment type);
	
}
