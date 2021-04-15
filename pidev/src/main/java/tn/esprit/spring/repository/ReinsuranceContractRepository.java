package tn.esprit.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.entity.Contrat;
import tn.esprit.spring.entity.DataFondEURO;
import tn.esprit.spring.entity.DemandeContrat;
import tn.esprit.spring.entity.Reinsurance_contract;
import tn.esprit.spring.entity.Role_User;
import tn.esprit.spring.entity.Sinistre;
import tn.esprit.spring.entity.User;

@Repository
public interface ReinsuranceContractRepository extends CrudRepository<Reinsurance_contract,Integer> {
	@Query("SELECT d FROM DemandeContrat d WHERE d.accepte=1 AND d.capitalOuRente=1")
	List<DemandeContrat> retrieveDemandeContrat();
	@Query("SELECT c FROM Contrat c WHERE c.reassure=0 ORDER BY c.primeCommerciale DESC")
	List<Contrat> Contratnonreassure();
	@Query("SELECT c FROM Contrat c WHERE c.NumContrat= ?1")
	Contrat ContratavecSinistre(int id);
	@Query("SELECT c FROM Sinistre c")
	List<Sinistre> FindSinistres();
	@Query(value = "SELECT * FROM reinsurance_contract WHERE contrat_id= :c", nativeQuery = true)
	public List<Reinsurance_contract> findReinByCont(@Param("c") int c );
	@Modifying
	@Transactional
	@Query("update Contrat c set c.reassure=1 where c.NumContrat = ?1")
	void updatecont(Integer id);
}
