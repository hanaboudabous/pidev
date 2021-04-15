package tn.esprit.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entity.DemandeContrat;
import tn.esprit.spring.entity.Type_sante;
import tn.esprit.spring.repository.IDemandeContratRepo;
import tn.esprit.spring.repository.Type_santeRepo;

@Service
public class Type_santeService {
	@Autowired
	Type_santeRepo type_santeRepo ;

	@Autowired
	IDemandeContratRepo demandeContrat;
	

	public void addType(DemandeContrat d , Type_sante t ){

		t.setDemandeContrat(d);
		type_santeRepo.save(t);
	}

	public void addType(int iddemande , Type_sante t ){
		DemandeContrat d = demandeContrat.findById(iddemande).get();
		t.setDemandeContrat(d);
		type_santeRepo.save(t);
	}

	

	public Type_sante affichetypesanteparDemande(int iddemande  ){
		DemandeContrat d = demandeContrat.findById(iddemande).get();
		//Type_sante type = type_santeRepo.findByDemandeContrat(d);	
		return type_santeRepo.findByDemandeContrat(d);	
	}

}
