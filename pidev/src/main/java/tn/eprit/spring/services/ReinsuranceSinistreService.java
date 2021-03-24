package tn.eprit.spring.services;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entity.Reinsurance_sinistre;
import tn.esprit.spring.repository.ReinsuranceSinistreRepository;
import tn.eprit.spring.services.IReinsuranceSService;
import tn.eprit.spring.services.UserService;

@Service
public class ReinsuranceSinistreService implements IReinsuranceSService {
	@Autowired
	ReinsuranceSinistreRepository reinsuranceRepository;
	private static final Logger l = LogManager.getLogger(UserService.class);
	
	public List<Reinsurance_sinistre> retrieveRe_ss() {
		List<Reinsurance_sinistre> reins = (List<Reinsurance_sinistre>) reinsuranceRepository.findAll();
		for(Reinsurance_sinistre rein : reins)
		{
			l.info("user ++ :"+rein);
		}
		return reins;
	}

	public Reinsurance_sinistre addRe_s(Reinsurance_sinistre r) {
		Reinsurance_sinistre reinSaved = null;
		reinSaved=reinsuranceRepository.save(r);
		return reinSaved;
	}

	public void deleteRe_s(String id) {
		reinsuranceRepository.deleteById(Integer.parseInt(id));
		
	}

	public Reinsurance_sinistre updateRe_s(Reinsurance_sinistre r) {
		Reinsurance_sinistre reinAdded = reinsuranceRepository.save(r);
		return reinAdded;
	}

	public Reinsurance_sinistre retrieveRe_s(String id) {
		
		l.info("in retrieveRein id= "+id);
		Reinsurance_sinistre r = reinsuranceRepository.findById(Integer.parseInt(id)).orElse(null);
		l.info("user returned : "+r);
		return r;
	}
}

