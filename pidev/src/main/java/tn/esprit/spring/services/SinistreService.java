package tn.esprit.spring.services;


import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entity.Contrat;
import tn.esprit.spring.entity.ReclamationSinistre;
import tn.esprit.spring.entity.Reinsurance_contract;
import tn.esprit.spring.entity.Sinistre;
import tn.esprit.spring.repository.ISinistreRepos;
import tn.esprit.spring.repository.ReinsuranceContractRepository;

@Service
public class SinistreService {
	private static final Logger k = LogManager.getLogger(ReinsuranceContractService.class);
	@Autowired
	ISinistreRepos repos;
	
	@Autowired
	ReinsuranceContractRepository reinsuranceRepository;
	
   public List<Sinistre> SinistreList(){
		
		return repos.findAll() ;
		
	}
      public void addSinistre(Sinistre sin,int id){
    	 Contrat c= reinsuranceRepository.ContratavecSinistre(sin.getReclamationSinitre().getContrats().getNumContrat());
    	 if(c.getReassure()==1)
    	 {
    		List<Reinsurance_contract> r = reinsuranceRepository.findReinByCont(sin.getReclamationSinitre().getContrats().getNumContrat());
    		if(r.size()!=0)
    		{
    		k.info("Somme remboursee est egale a : "+r.get(0).getRemboursement());	
    		}
    	 }
    	 repos.save(sin);
 		
 	}

 	public void deleteSinistre(int Id){
 		repos.deleteById(Id);
 		
 	}
 	
 	public Sinistre updateSinistre(Sinistre sin) {
 		Sinistre sinadded = repos.save(sin);
		return sinadded;
	}

}
