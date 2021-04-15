package tn.esprit.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.spring.entity.Rendement;
@Repository
public interface IRendement extends JpaRepository <Rendement , Integer>{

	Rendement findByAnnee(int annee);
	
}

