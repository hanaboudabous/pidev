package tn.esprit.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entity.Taux_Interet;

@Repository
public interface Taux_InteretRepo extends JpaRepository<Taux_Interet, Integer>{

	
	Taux_Interet findFirstByOrderByIdDesc();
	
}
