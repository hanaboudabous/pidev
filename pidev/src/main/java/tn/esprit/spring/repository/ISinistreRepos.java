package tn.esprit.spring.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entity.Sinistre;



@Repository
public interface ISinistreRepos extends JpaRepository<Sinistre, Integer> {

	@Query(value = "SELECT * FROM Sinistre WHERE extract(year from date_reglement)=:s ", nativeQuery = true)
	public List<Sinistre> findbyDateReglement(@Param("s") int s );
	
	@Query(value = "SELECT * FROM sinistre  ORDER BY date_reglement ASC", nativeQuery = true)
	public List<Sinistre> mindate();

	@Query(value = "SELECT * FROM sinistre  ORDER BY date_reglement DESC", nativeQuery = true)
	public List<Sinistre> maxdate();

	/***********************************************************************************************/
	@Query(value = "SELECT * FROM sinistre s JOIN reclamation_sinistre r on s.reclamation_sinistre_id=r.id JOIN contrat c on r.contrats_id=c.id JOIN demande_contrat d on c.demande_contrat_id=d.id JOIN user u on d.users_user_id=u.user_id WHERE u.user_id=:us", nativeQuery = true)
	public List<Sinistre> IafficheSinUser(@Param("us") int us);
}
