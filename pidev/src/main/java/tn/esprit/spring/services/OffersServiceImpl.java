package tn.esprit.spring.services;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;

import tn.esprit.spring.entity.Offers;
import tn.esprit.spring.repository.OffersRepository;
import org.springframework.stereotype.Service;



@Service
public class OffersServiceImpl implements IOffersService {
	@Autowired 
	OffersRepository offerRepository;

	@Override
	public List<Offers> retrieveAll_Offers() {
		List<Offers> offers =(List<Offers>) offerRepository.findAll();

		return offers;
	}

	@Override
	public Offers add_Offers(Offers O) {
		Offers OfferSaved = null;
		OfferSaved =offerRepository.save(O);
		return OfferSaved;
		
	}

	@Override
	public void delete_Offers(int IdOffer) {

		offerRepository.deleteById(IdOffer);
	}

	@Override
	public Offers update_Offers(Offers O) {
		Offers OffreAdded = offerRepository.save(O);
		return OffreAdded;
		
	}

	@Override
	public Offers retrieve_Offers(int IdOffer) {
		System.out.println("offer id =" + IdOffer);
		Offers O = offerRepository.findById(IdOffer).orElse(null);
		System.out.println("offre returned "+O);
		return O ;
		
	}
	
	
	
	

}
