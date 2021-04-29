package tn.esprit.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entity.ActifFinancier;
import tn.esprit.spring.entity.DemandeContrat;
import tn.esprit.spring.entity.Fond;
import tn.esprit.spring.entity.User;


@Repository
public interface IActifFinancier extends JpaRepository<ActifFinancier, Integer> {
	
	
	List<ActifFinancier> findByNomFond(Fond nom_fond) ;
	
	 List<ActifFinancier> findByUserActif( User user);



}
