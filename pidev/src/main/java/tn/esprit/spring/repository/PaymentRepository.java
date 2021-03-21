package tn.esprit.spring.repository;

import org.springframework.data.repository.CrudRepository;

import tn.esprit.spring.entity.Payment;

public interface PaymentRepository extends CrudRepository<Payment,Integer>{

}
