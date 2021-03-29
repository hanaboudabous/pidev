package tn.esprit.spring.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entity.Contrat;
import tn.esprit.spring.entity.ReclamationSinistre;
import tn.esprit.spring.repository.IReclamationSinistreRepos;


public interface IReclamationSinistreService {


	public List<ReclamationSinistre> ReclamationSinistreList()
	public void addReclamationSinistre(ReclamationSinistre rec,int id)
	public void deleteReclamationSinistre(int Id)


 
}
  