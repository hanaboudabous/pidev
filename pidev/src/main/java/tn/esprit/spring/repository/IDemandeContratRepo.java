package tn.esprit.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entity.User;
import tn.esprit.spring.entity.DemandeContrat;



@Repository
public interface IDemandeContratRepo  extends JpaRepository<DemandeContrat, Integer>{
	

	@Query(value = "SELECT * FROM demande_contrat WHERE traite=1", nativeQuery = true)
	public List<DemandeContrat> IafficheDemandeContratTraite();


	@Query(value = "SELECT * FROM demande_contrat WHERE traite=0", nativeQuery = true)
	public List<DemandeContrat> IafficheDemandeContratNonTraite();
	
	 List<DemandeContrat> findByTraite(int traite);
	 List<DemandeContrat> findByUsers( User user);
}
