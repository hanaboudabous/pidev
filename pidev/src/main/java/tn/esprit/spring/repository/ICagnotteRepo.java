package tn.esprit.spring.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entity.Cagnotte;



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
}
