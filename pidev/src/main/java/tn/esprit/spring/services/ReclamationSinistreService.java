package tn.esprit.spring.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import picrud.repository.IContratRepos;
import tn.esprit.spring.entity.Contrat;
import tn.esprit.spring.entity.ReclamationSinistre;
import tn.esprit.spring.repository.IReclamationSinistreRepos;




@Service
public class ReclamationSinistreService {
	@Autowired
	IReclamationSinistreRepos repos ;
	
	@Autowired
	IContratRepos contrat;
	
	
      public List<ReclamationSinistre> ReclamationSinistreList(){
		
		return repos.findAll() ;
		
	}
      
      
     
      public void addReclamationSinistre(ReclamationSinistre rec,int id){
    	  Contrat d = contrat.findById(id).get();
  		  rec.setContrats(d);
 		  repos.save(rec);
 		
 	}
      
    
 	public void deleteReclamationSinistre(int Id){
 		repos.deleteById(Id);
 		
 	}
 	
 	
	public ReclamationSinistre updateReclamationSinistre(ReclamationSinistre rec) {
		ReclamationSinistre recadded = repos.save(rec);
		return recadded;
	}
 	

}
