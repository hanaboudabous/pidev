package tn.esprit.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entity.Contrat;
import tn.esprit.spring.entity.DemandeContrat;

@Repository

public interface IContratRepo extends JpaRepository<Contrat, Integer>{
	
	List<Contrat> findByAcceptation(int acceptation);


	@Query(value = "SELECT * FROM contrat INNER JOIN demande_contrat ON contrat.demande_contrat_id=demande_contrat.id WHERE acceptation=0 and demande_contrat.users_user_id=:w", nativeQuery = true)
	public List<Contrat> ProposeContrat(@Param("w") int w);
	
	@Query(value = "SELECT * FROM contrat INNER JOIN demande_contrat ON contrat.demande_contrat_id=demande_contrat.id WHERE acceptation=1 and demande_contrat.users_user_id=:w ORDER BY date_debut ASC", nativeQuery = true)
	public List<Contrat> LesContratsClient(@Param("w") int w);

	
	@Query(value = "SELECT * FROM sinistre INNER JOIN reclamation_sinistre ON sinistre.demande_contrat_id=demande_contrat.id WHERE acceptation=1 and demande_contrat.users_user_id=:w ORDER BY date_debut ASC", nativeQuery = true)
	public List<Contrat> LesSinistresClient(@Param("w") int w);
	
	
}
