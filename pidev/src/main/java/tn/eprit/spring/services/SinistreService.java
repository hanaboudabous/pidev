/*package tn.eprit.spring.services;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entity.ReclamationSinistre;
import tn.esprit.spring.entity.Sinistre;
import tn.esprit.spring.repository.ISinistreRepos;

@Service
public class SinistreService {
	@Autowired
	ISinistreRepos repos;
	
   public List<Sinistre> SinistreList(){
		
		return repos.findAll() ;
		
	}
      public void addSinistre(Sinistre sin,int id){
  		
 	
    	  
		repos.save(sin);
 		
 	}

 	public void deleteSinistre(int Id){
 		repos.deleteById(Id);
 		
 	}

}
*/