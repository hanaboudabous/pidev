package Pidev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Pidev.entity.DemandeContrat;
import Pidev.entity.Type_sante;

@Repository
public interface Type_santeRepo extends JpaRepository<Type_sante, Integer> {

	Type_sante findByDemandeContrat(DemandeContrat i);
	
	
}
