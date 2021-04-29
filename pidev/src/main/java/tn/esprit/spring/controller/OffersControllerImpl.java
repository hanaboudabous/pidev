package tn.esprit.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.entity.Offers;
import tn.esprit.spring.services.IOffersService;



@RestController
public class OffersControllerImpl {
	@Autowired
	IOffersService offerService;
	

	// http://localhost:8081/MicroAssurance/AllOffers
	 @GetMapping("AllOffers")
	 @ResponseBody
	 public List<Offers> getAllOffers() {
	List<Offers> listOffers = offerService.retrieveAll_Offers();
	 return listOffers;
	}
	 
	// http://localhost:8081/MicroAssurance/removeOffer/{offer-id}
	 @DeleteMapping("removeOffer/{offer-id}")
	 @ResponseBody
	  public void removeOffer(@PathVariable("offer-id") int IdOffer) {
	 offerService.delete_Offers(IdOffer);
	  }
	// Ajouter User : http://localhost:8081/MicroAssurance/addOffer
	 @PostMapping("addOffer")
	  @ResponseBody
	  public Offers addOffers(@RequestBody Offers O) {
	  System.out.println("Ajouter avec succes");
	  Offers offer = offerService.add_Offers(O);
	  return offer;
	  }
	  // http://localhost:8081/MicroAssurance/modify-Offer
	  @PutMapping("/modify-Offer")
	  @ResponseBody
	  public Offers modifyOffer(@RequestBody Offers offre) {
	  return offerService.update_Offers(offre);
	  }

}
