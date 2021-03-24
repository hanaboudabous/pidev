package tn.eprit.spring.services;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entity.Reinsurance_contract;
import tn.esprit.spring.repository.ReinsuranceContractRepository;
import tn.eprit.spring.services.IReinsuranceCService;
import tn.eprit.spring.services.UserService;

@Service
public class ReinsuranceContractService implements IReinsuranceCService {
	@Autowired
	ReinsuranceContractRepository reinsuranceRepository;
	private static final Logger l = LogManager.getLogger(UserService.class);
	
	public List<Reinsurance_contract> retrieveRe_cs() {
		List<Reinsurance_contract> reins = (List<Reinsurance_contract>) reinsuranceRepository.findAll();
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
		l.info("in retrieveRein id= "+id);
		Reinsurance_contract r = reinsuranceRepository.findById(Integer.parseInt(id)).orElse(null);
		l.info("user returned : "+r);
		return r;
	}
}

