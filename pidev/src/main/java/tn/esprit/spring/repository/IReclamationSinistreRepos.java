package tn.esprit.spring.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entity.ReclamationSinistre;



@Repository
public interface IReclamationSinistreRepos extends JpaRepository<ReclamationSinistre, Integer> {
	@Query(value = "SELECT * FROM reclamation_sinistre WHERE traite_reclamation=1", nativeQuery = true)
	public List<ReclamationSinistre> IafficheRecSinTraite();


	@Query(value = "SELECT * FROM reclamation_sinistre WHERE traite_reclamation=0", nativeQuery = true)
	public List<ReclamationSinistre> IafficheRecSinNonTraite();
	
	@Query(value = "SELECT * FROM reclamation_sinistre r JOIN  contrat c on r.contrats_id=c.id JOIN demande_contrat d on c.demande_contrat_id=d.id JOIN user u on d.users_user_id=u.user_id WHERE u.user_id=:us and traite_reclamation=0", nativeQuery = true)
	public List<ReclamationSinistre> IafficheRecSinNonTraiteUser(@Param("us") int us);
	
	@Query(value = "SELECT * FROM reclamation_sinistre r JOIN  contrat c on r.contrats_id=c.id JOIN demande_contrat d on c.demande_contrat_id=d.id JOIN user u on d.users_user_id=u.user_id WHERE u.user_id=:us and traite_reclamation=1", nativeQuery = true)
	public List<ReclamationSinistre> IafficheRecSinTraiteUser(@Param("us") int us);
	
	 List<ReclamationSinistre> findByTraiteReclamation(int traiteReclamation);
	 
	 

}
