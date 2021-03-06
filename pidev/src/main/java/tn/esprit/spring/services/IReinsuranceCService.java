package tn.esprit.spring.services;

import java.util.List;

import tn.esprit.spring.entity.Contrat;
import tn.esprit.spring.entity.DemandeContrat;
import tn.esprit.spring.entity.Reinsurance_contract;
import tn.esprit.spring.entity.User;

public interface IReinsuranceCService {
	 List<Reinsurance_contract> retrieveRe_cs();
	 Reinsurance_contract addRe_s(Reinsurance_contract u);
	 void deleteRe_s(String id);
	 Reinsurance_contract updateRe_s(Reinsurance_contract u);
	 Reinsurance_contract retrieveRe_s(String id);
	 String addReinCont(User user);
	 void ReffReinCont();
	 String ReinSinistre();

}
