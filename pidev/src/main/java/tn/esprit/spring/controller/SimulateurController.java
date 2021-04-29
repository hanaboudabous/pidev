package tn.esprit.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import tn.esprit.spring.entity.DataFondEURO;
import tn.esprit.spring.entity.Fond;
import tn.esprit.spring.entity.Prime;     
import tn.esprit.spring.entity.Simulateur;
import tn.esprit.spring.services.SimulateurService;
@Scope(value = "session")

@Controller(value = "simulateurController") // Name of the bean in Spring IoC

public class SimulateurController {

	
	@Autowired
	SimulateurService simulateurService ;
	
	
	
	
	 @GetMapping("/simulateurFond/{versement}/{prime}/{fond}/{maturite}")
	@ResponseBody
		public List<Simulateur> simu(@PathVariable("versement") int    versement,
				@PathVariable("prime") Prime    prime,
				@PathVariable("fond") Fond    fond,
				@PathVariable("maturite") int  maturite){
		 List<Simulateur> l = simulateurService.montant_actuelFondEuro(versement , prime , fond , maturite);
			return l;
		}
	
	
	
	
	
	
	
	
	
	
	
	
	
	////////// jsf
	
	private Fond fond ;
	private Prime prime ;
	private float versement ;
	private int maturite ;
	
	
	public Fond[] getFonds() { return Fond.values(); }

	public Prime[] getPrimes() { return Prime.values(); }
	public Fond getFond() {
		return fond;
	}
	public void setFond(Fond fond) {
		this.fond = fond;
	}
	public Prime getPrime() {
		return prime;
	}
	public void setPrime(Prime prime) {
		this.prime = prime;
	}
	public float getVersement() {
		return versement;
	}
	public void setVersement(float versement) {
		this.versement = versement;
	}
	public int getMaturite() {
		return maturite;
	}
	public void setMaturite(int maturite) {
		this.maturite = maturite;
	}
	
	public String ajousimu(){
		this.setFond(fond);
		this.setMaturite(maturite);
		this.setPrime(prime);
		this.setVersement(versement);
		return "/simulateur/simulateur.xhtml?faces-redirect=true" ;
	}
	
	List<Simulateur> listes ;
	

	public void setListes(List<Simulateur> listes) {
		this.listes = listes;
	}
	public List<Simulateur> getListes() {	
		if(fond == Fond.Fond_Euro){
		listes =  simulateurService.montant_actuelFondEuro(versement , prime , fond , maturite);
		}
		else if(fond == Fond.Euro_Croissance){
		listes =  simulateurService.montant_actuelEuroCroissance(versement , prime , fond , maturite);
		}
		
		 return listes ;
			}
	
	

	
}

/*
 * 
 * @GetMapping("/UniqueEUROcroissance")
	@ResponseBody
	public ResponseEntity<DataFondEURO>calculeprofitUniqueEUROcroissance(){
		simulateurService.calculeprofitUniqueEUROcroissance();
		
		return new ResponseEntity<DataFondEURO>(HttpStatus.OK);		
	}
*/
