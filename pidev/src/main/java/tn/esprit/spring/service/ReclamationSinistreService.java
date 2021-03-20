package Pidev.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Pidev.entity.Contrat;
import Pidev.entity.ReclamationSinistre;
import Pidev.repository.IReclamationSinistreRepos;




@Service
public class ReclamationSinistreService {
	@Autowired
	IReclamationSinistreRepos repos ;
	
      public List<ReclamationSinistre> ReclamationSinistreList(){
		
		return repos.findAll() ;
		
	}
      
      
     
      public void addReclamationSinistre(ReclamationSinistre rec,int id){
    	  Contrat d = contrats.findById(id).get();
  		  rec.setContrats(d);
 		  repos.save(rec);
 		
 	}

 	public void deleteReclamationSinistre(int Id){
 		repos.deleteById(Id);
 		
 	}
 	
 	

}
