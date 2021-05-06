package tn.esprit.spring.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entity.Cagnotte;
import tn.esprit.spring.entity.Contrat;





@Repository			
public interface ICagnotteRepo extends JpaRepository<Cagnotte, Integer>{
	@Query(value = "SELECT sum(montantfinal) FROM cagnotte", nativeQuery = true)
	public int ISommeMontantfinal();
	
	@Query(value = "SELECT sum(montantactuel) FROM cagnotte", nativeQuery = true)
	public int ISommeMontantactuel();

	/*@Query(value = "SELECT * FROM cagnotte order by date_ajout where etat=:en cours", nativeQuery = true)
	public Cagnotte IFirstCagnotte();*/
	

	 List<Cagnotte> findByEtatOrderByDateAjout(String etat);// eli etat=en cours
	 List<Cagnotte> findAllByEtat(String etat);
	 
	 /*****************************************************************/
	 Optional<Cagnotte> findByContrat(Contrat l);
	 
	 @Query(value = "SELECT * FROM cagnotte ca JOIN  contrat c on ca.contrat_id=c.id JOIN demande_contrat d on c.demande_contrat_id=d.id JOIN user u on d.users_user_id=u.user_id WHERE u.user_id=:us ", nativeQuery = true)
		public List<Cagnotte> Cagnottesduuser(@Param("us") int us);

}
