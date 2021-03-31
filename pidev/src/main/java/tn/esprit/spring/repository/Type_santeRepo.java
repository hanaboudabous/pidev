package tn.esprit.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entity.DemandeContrat;
import tn.esprit.spring.entity.Type_sante;

@Repository
public interface Type_santeRepo extends JpaRepository<Type_sante, Integer> {

	Type_sante findByDemandeContrat(DemandeContrat i);
	
	
}
