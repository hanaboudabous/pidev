package tn.esprit.spring.services;


import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entity.Contrat;
import tn.esprit.spring.entity.DemandeContrat;
import tn.esprit.spring.entity.Reinsurance_contract;
import tn.esprit.spring.repository.ReinsuranceContractRepository;
import tn.esprit.spring.services.IReinsuranceCService;


@Service
public class ReinsuranceContractService implements IReinsuranceCService {
	@Autowired
	ReinsuranceContractRepository reinsuranceRepository;

	private static final Logger l = LogManager.getLogger(ReinsuranceContractService.class);
	
	public List<Reinsurance_contract> retrieveRe_cs() {
		List<Reinsurance_contract> reins =  (List<Reinsurance_contract>) reinsuranceRepository.findAll();
		for(Reinsurance_contract rein : reins)
		{
			l.info("user ++ :"+rein);
		}
		return reins;
	}
	public Reinsurance_contract addRe_s(Reinsurance_contract r) {
		Reinsurance_contract reinSaved = null;
		reinSaved=reinsuranceRepository.save(r);
		return reinSaved;
	}
	public void deleteRe_s(String id) {
		reinsuranceRepository.deleteById(Integer.parseInt(id));
		
	}
	public Reinsurance_contract updateRe_s(Reinsurance_contract r) {
		Reinsurance_contract reinAdded = reinsuranceRepository.save(r);
		return reinAdded;
	}
	public Reinsurance_contract retrieveRe_s(String id) {
		Reinsurance_contract r = reinsuranceRepository.findById(Integer.parseInt(id)).orElse(null);
		return r;
	}
	@Override
	public String addReinCont() {
		List<DemandeContrat> reins = (List<DemandeContrat>) reinsuranceRepository.retrieveDemandeContrat();
		List<Reinsurance_contract> reinsu = (List<Reinsurance_contract>) reinsuranceRepository.findAll();
		List<Contrat> contrats = (List<Contrat>) reinsuranceRepository.Contratnonreassure();
       float i= reinsu.size();
       System.out.println(i);
       float j=reins.size();
       int ii=0;
       if((i/j)<0.5){
    	  for( Contrat contrat : contrats)
    	  {
    		  if ((i/j)<0.5)
    	       {
                  i++;
                  System.out.println(i+"       1");
    			  Reinsurance_contract r= new Reinsurance_contract();
    			  r.setPrimeCommerciale((float) (contrat.getPrimeCommerciale()*0.7));
    			  r.setPrimePure((float) (contrat.getPrimePure()*0.7));
    			  r.setContrat(contrat);
    			  reinsuranceRepository.updatecont(contrat.getNumContrat());
    			  reinsuranceRepository.save(r);
    			  ii++;  
    	        }

    	  }
    	  return ii+" contrat(s) ont ete ajoute";
       }
       else {
    	   return "annule";
       }
       }
	@Override
	public void ReffReinCont() {
		List<Reinsurance_contract> reinsu = (List<Reinsurance_contract>) reinsuranceRepository.findAll();
		Date currentUtilDate = new Date();

		for(Reinsurance_contract rein : reinsu)
		{
			if(rein.getContrat().getDate_fin().compareTo(currentUtilDate)<0)
			{
				rein.setPrimeCommerciale((float) (rein.getContrat().getPrimeCommerciale()*0.7));
				rein.setPrimePure((float) (rein.getContrat().getPrimePure()*0.7));
			}
			else {
				reinsuranceRepository.delete(rein);		
			}
		}
		
	}

		

}



