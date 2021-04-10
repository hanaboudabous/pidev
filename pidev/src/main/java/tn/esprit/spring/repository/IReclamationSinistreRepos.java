package tn.esprit.spring.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entity.ReclamationSinistre;



@Repository
public interface IReclamationSinistreRepos extends JpaRepository<ReclamationSinistre, Integer> {
	@Query(value = "SELECT * FROM reclamation_sinistre WHERE traite_reclamation=1", nativeQuery = true)
	public List<ReclamationSinistre> IafficheRecSinTraite();


	@Query(value = "SELECT * FROM reclamation_sinistre WHERE traite_reclamation=0", nativeQuery = true)
	public List<ReclamationSinistre> IafficheRecSinNonTraite();
	
	 List<ReclamationSinistre> findByTraiteReclamation(int traiteReclamation);

}
