package tn.esprit.spring.controller;

import java.util.List;

import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import tn.esprit.spring.entity.Offers;
import tn.esprit.spring.services.IOffersService;

@Controller(value = "offerController") // Name of the bean in Spring IoC
@ELBeanName(value = "offerController") // Name of the bean used by JSF
public class OffersControllerJSF {
	
	@Autowired
	IOffersService offerService;
	private List<Offers> offers;
	public List<Offers> getOffers() {
		offers = offerService.retrieveAll_Offers();
		return offers;
	}
	public void setOffers(List<Offers> offers) {
		this.offers = offers;
	} 
	

}
