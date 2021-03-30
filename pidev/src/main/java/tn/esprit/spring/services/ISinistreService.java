package tn.esprit.spring.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entity.ReclamationSinistre;
import tn.esprit.spring.entity.Sinistre;
import tn.esprit.spring.repository.ISinistreRepos;


public interface ISinistreService {


	public List<Sinistre> SinistreList()
	public void addSinistre(Sinistre sin,int id)
	public void deleteSinistre(int Id)
	public Sinistre updateSinistre(Sinistre sin)

}
      