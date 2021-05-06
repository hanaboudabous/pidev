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

		@Query(value = "SELECT * FROM demande_contrat INNER JOIN contrat ON demande_contrat.id=contrat.demande_contrat_id WHERE traite=1 and contrat.acceptation=0 and demande_contrat.users_user_id=:w", nativeQuery = true)
		public List<DemandeContrat> afficheDemandeContratTraiteUSERRepo(@Param("w") int w);

	
}
