package tn.eprit.spring.services;

import java.util.List;

import tn.esprit.spring.entity.Reinsurance_sinistre;

public interface IReinsuranceSService {
	 List<Reinsurance_sinistre> retrieveRe_ss();
	 Reinsurance_sinistre addRe_s(Reinsurance_sinistre u);
	 void deleteRe_s(String id);
	 Reinsurance_sinistre updateRe_s(Reinsurance_sinistre u);
	 Reinsurance_sinistre retrieveRe_s(String id);
}
