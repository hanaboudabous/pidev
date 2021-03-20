package Pidev.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Pidev.entity.ReclamationSinistre;
import Pidev.entity.Sinistre;
import Pidev.repository.ISinistreRepos;

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
