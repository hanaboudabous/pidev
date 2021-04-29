package tn.esprit.spring.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entity.Offers;
@Repository
public interface OffersRepository extends CrudRepository<Offers, Integer> {

}
