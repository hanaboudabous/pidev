package tn.esprit.spring.services;

import java.util.List;

import tn.esprit.spring.entity.Offers;



public interface IOffersService {
	 List<Offers> retrieveAll_Offers();
	 Offers add_Offers(Offers f);
	 void delete_Offers(int id);
	 Offers update_Offers(Offers f);
	 Offers retrieve_Offers(int id);
	 


}
