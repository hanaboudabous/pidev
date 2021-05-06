package tn.esprit.spring.controller;

import java.util.Date;

import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import tn.esprit.spring.entity.DataFondEURO;
import tn.esprit.spring.services.DataFondEUROService;

 

@Scope(value = "session")

@Controller(value = "dataFondEUROControllerJSF") // Name of the bean in Spring IoC
@ELBeanName(value = "dataFondEUROControllerJSF")
@RequestMapping("/jsf")

public class DataFondEUROControllerJSF {
	
	@Autowired
	DataFondEUROService dataFondEURO ;
	
	
	private String libelle ;
	private String typBonde ;
	private float montantSouscription ;
	private Date dateSouscription; 
	private int maturite ; 
	private float tauxCoupon ;
	private String typeTaux ;
	private float nominal ;	
	private float remunerationPrincipale ;
	
	public String ajoutDataFondEUROControllerJSF(){
		DataFondEURO d = new DataFondEURO();
		d.setLibelle(libelle);
		d.setTypBonde(typBonde);
		d.setMontantSouscription(montantSouscription);
		d.setDateSouscription(dateSouscription);
		d.setMaturite(maturite);
		d.setTauxCoupon(tauxCoupon);
		d.setTypeTaux(typeTaux);
		d.setNominal(nominal);
		d.setRemunerationPrincipale(remunerationPrincipale);
		
		dataFondEURO.addDataEuro(d);	
		
		return null ;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
		public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public String getTypBonde() {
		return typBonde;
	}
	public void setTypBonde(String typBonde) {
		this.typBonde = typBonde;
	}
	public float getMontantSouscription() {
		return montantSouscription;
	}
	public void setMontantSouscription(float montantSouscription) {
		this.montantSouscription = montantSouscription;
	}
	public Date getDateSouscription() {
		return dateSouscription;
	}
	public void setDateSouscription(Date dateSouscription) {
		this.dateSouscription = dateSouscription;
	}

	public int getMaturite() {
		return maturite;
	}
	public void setMaturite(int maturite) {
		this.maturite = maturite;
	}

	public float getTauxCoupon() {
		return tauxCoupon;
	}
	public void setTauxCoupon(float tauxCoupon) {
		this.tauxCoupon = tauxCoupon;
	}
	public String getTypeTaux() {
		return typeTaux;
	}
	public void setTypeTaux(String typeTaux) {
		this.typeTaux = typeTaux;
	}
	public float getNominal() {
		return nominal;
	}
	public void setNominal(float nominal) {
		this.nominal = nominal;
	}

	public float getRemunerationPrincipale() {
		return remunerationPrincipale;
	}
	public void setRemunerationPrincipale(float remunerationPrincipale) {
		this.remunerationPrincipale = remunerationPrincipale;
	}

	@GetMapping("/rendement2")
	@ResponseBody
	public ResponseEntity<DataFondEURO>calculeRendementBTA2(){
		dataFondEURO.calculerendementTotal();
		return new ResponseEntity<DataFondEURO>(HttpStatus.OK);
			
	}

	@GetMapping("/l")
	@ResponseBody
	public ResponseEntity<DataFondEURO>l(){
		dataFondEURO.liste();
		return new ResponseEntity<DataFondEURO>(HttpStatus.OK);

	}

	@GetMapping("/listePortefeuilleBTA")
	@ResponseBody
	public ResponseEntity<DataFondEURO>listeBTA(){
		dataFondEURO.listeBTA();
		return new ResponseEntity<DataFondEURO>(HttpStatus.OK);

	}
	@GetMapping("/listePortefeuille")
	@ResponseBody
	public ResponseEntity<DataFondEURO>listeAll(){
		dataFondEURO.listeAll();
		return new ResponseEntity<DataFondEURO>(HttpStatus.OK);

	}

	@GetMapping("/calculeprofitUnique")
	@ResponseBody
	public ResponseEntity<DataFondEURO>calculeprofitUnique(){
		dataFondEURO.calculeprofitUnique();
		return new ResponseEntity<DataFondEURO>(HttpStatus.OK);
			
	}
	@GetMapping("/calculeprofitPeriodique")
	@ResponseBody
	public ResponseEntity<DataFondEURO>calculeprofitPeriodique(){
		dataFondEURO.calculeprofitPeriodique();
		return new ResponseEntity<DataFondEURO>(HttpStatus.OK);
			
	}


	
	/*@GetMapping("/date2")
	@ResponseBody
	public ResponseEntity<DataFondEURO>montant_actuelProfitUnique(){
		dataFondEURO.montant_actuelProfitUnique();
		return new ResponseEntity<DataFondEURO>(HttpStatus.OK);			
	}*/

	
	

}
