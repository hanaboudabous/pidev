package tn.esprit.spring.controller;

import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import tn.esprit.spring.entity.DemandeContrat;
import tn.esprit.spring.entity.Prime;
import tn.esprit.spring.entity.Type_sante;
import tn.esprit.spring.services.ContratService;
import tn.esprit.spring.services.DemandeContratService;
import tn.esprit.spring.services.Type_santeService;
import tn.esprit.spring.services.apriori;



@Scope(value = "session")

@Controller(value = "demandeContratControllerJSF") // Name of the bean in Spring IoC
@ELBeanName(value = "demandeContratControllerJSF")
@RequestMapping("/jsf")
@ManagedBean
@ViewScoped
public class DemandeContratControllerJSF {
	
	@Autowired
	DemandeContratService demandeContratService ;
	///////////////////////////////////////////////////////// question pour definir le type de sante  //////////////////////////////////////////////////////////
	private boolean actes_Medicaux_Courants ;    
	private boolean analyse ;
	private boolean radio_Physio ;
	private boolean optique ;
	private boolean frais_Chirurgicaux ;
	private boolean dentaires ;
	private boolean maternite ;
	private boolean autres ;
	private boolean ajoutEnf ;   
	private boolean ajouconjoint ;
	
	
	public int nombrefille ;
	public int nombreenfant ;
	
	private boolean  consultations_Visites ;
	private boolean frais_Pharmaceutiques ;
	private boolean hospitalisation ;

	// des question pour distinction du type de contrat
	private int deces ;	
	private int vie ;
	private int capitalOuRente ;
	private int sante ;
	// question pour la tarification
	private int fumeur ;	 
	private int alcool ; // oui ou nn
	private int sportif ;  // sportif ou non
	private int maladie; // maladie chronique	 
	private int accepte ;  //  demande calculé et accepte ou non ( pour l assureur )
	private int traite ; // demande traité ou non
	/// decision type contrat
	private String nomContrat ;
	private String typeContrat;
	
///////////////////////////////////////// tarification //////////////////////////////////////////////////////////////////
	 private float capitalAssure ;
	 private Prime choixPrime ;
	 private float val_prime;
	private int nombreAnnee ;
///////////////////////////////////////// change boolean to int //////////////////////////////////////////////////////////////////
	private boolean deces_b ;	
	private boolean vie_b ;
	private boolean capitalOuRente_b ;
	private boolean sante_b ;
	// question pour la tarification
	private boolean fumeur_b ;	 
	private boolean alcool_b ; // oui ou nn
	private boolean sportif_b ;  // sportif ou non
	private boolean maladie_b; // maladie chronique	 
	
	private int  iduser = UserController.getIdpublic();


	@Autowired
	ContratService contratService ;
	
	@Autowired
	Type_santeService type_santeService ;


	@Autowired
	apriori aprio ;
	 public int getNombrefille() {
		return nombrefille;
	}

	public void setNombrefille(int nombrefille) {
		this.nombrefille = nombrefille;
	}

	public int getNombreenfant() {
		return nombreenfant;
	}

	public void setNombreenfant(int nombreenfant) {
		this.nombreenfant = nombreenfant;
	}
	 public boolean isAjoutEnf() {
		return ajoutEnf;
	}

	public void setAjoutEnf(boolean ajoutEnf) {
		this.ajoutEnf = ajoutEnf;
	}

	public boolean isAjouconjoint() {
		return ajouconjoint;
	}

	public void setAjouconjoint(boolean ajouconjoint) {
		this.ajouconjoint = ajouconjoint;
	}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	
	
	
	
////////////////////////////// ajout contrat ( chargement des donnees dans le controller sans enregistrement )//////////////////////////
	 DemandeContrat  d = new DemandeContrat();
	public String ajoutDemandeContratController(){
		if(deces_b == true){d.setDeces(1);}
		else{d.setDeces(0);}		
		if(vie_b == true){d.setVie(1);}
		else{d.setVie(0);}
		if(capitalOuRente_b != true){d.setCapitalOuRente(1);}
		else{d.setCapitalOuRente(0);}
		if(sante_b == true){d.setSante(1);}
		else{d.setSante(0);}
		
		if(fumeur_b == true){d.setFumeur(1);}
		else{d.setFumeur(0);}		
		if(alcool_b == true){d.setAlcool(1);}
		else{d.setAlcool(0);}
		if(sportif_b == true){d.setSportif(1);}
		else{d.setSportif(0);}
		if(maladie_b == true){d.setMaladie(1);}
		else{d.setMaladie(0);}
		
	
		String s ;
		String type1 = "Assurance vie";
		String type2 = "Assurance non vie";
		String nomvie1 ="Capital différé";
		String nomvie2 = "Rente viagére";
		String nomdeces= "Vie entiére";
		String sante = "Santé";		
		if(d.getSante() == 0 ){			
			if(d.getVie()==1 && d.getDeces() == 0){						
				if(d.getCapitalOuRente()== 0){s ="capital diff";System.out.println(s);
				this.setTypeContrat(type1);d.setTypeContrat(typeContrat);
				this.setNomContrat(nomvie1);d.setNomContrat(nomContrat);
						}						
						else{s = " rente viag";System.out.println(s);
						this.setTypeContrat(type1);d.setTypeContrat(typeContrat);
						this.setNomContrat(nomvie2);d.setNomContrat(nomContrat);
						}
			}
			else if (d.getVie()==0 && d.getDeces() == 1){ 
				s="vie entiere";System.out.println();
				this.setTypeContrat(type1);d.setTypeContrat(typeContrat);
				this.setNomContrat(nomdeces);d.setNomContrat(nomContrat);
				}
			else if (d.getVie()==1 && d.getDeces() == 1){  s="on peut pas vie et deces"; System.out.println(s);}	
			else{System.out.println("hello");}
}
		else{s="sante";System.out.println(s);
		this.setTypeContrat(type2);d.setTypeContrat(typeContrat);
		this.setNomContrat(sante);d.setNomContrat(nomContrat);
		}
		String retu = null ;
		if(nomContrat == sante){
			
			retu = "/DemandeContratQuestionTypeSante.xhtml?faces-redirect=true" ;
		}
		else{
			retu = "/DemandeContratQuestion.xhtml?faces-redirect=true" ;		}
		return retu ;
	}
////////////////////// update contrat ///////////////////////////
	public String updateDemandeContratController(){
		if(fumeur_b == true){d.setFumeur(1);}
		else{d.setFumeur(0);}		
		if(alcool_b == true){d.setAlcool(1);}
		else{d.setAlcool(0);}
		if(sportif_b == true){d.setSportif(1);}   
		else{d.setSportif(0);}   
		if(maladie_b == true){d.setMaladie(1);}                    
		else{d.setMaladie(0);}		   
		return "/DemandeContratTarification.xhtml?faces-redirect=true" ;
	}
	
	public int f = 0 ;  
	private String variable_confirm ;
	Type_sante t = new Type_sante();
	public String getVariable_confirm() {
		return variable_confirm;
	}

	public void setVariable_confirm(String variable_confirm) {
		this.variable_confirm = variable_confirm;
	}
	

	//////////////////////update contrat2222222222222 ///////////////////////////	
	public String updateDemandeContratController2sante() throws FileNotFoundException{
		if (this.a == 2 ){
			d.setVal_prime(0);    
			d.setCapitalAssure(this.capitalAssure);
		}
		else{ 
		d.setCapitalAssure(0);
		d.setVal_prime(this.val_prime);
		}
		d.setNombreAnnee(this.nombreAnnee);
		d.setChoixPrime(this.choixPrime);
		
		System.out.println("-------------------------"+ this.nombreAnnee + "---" + this.val_prime);	
		demandeContratService.ajoutDemandeContrat(d ,iduser,   typeContrat,nomContrat);	
		/********************************************************/
		if(this.sante_b){
		
		t.setActes_Medicaux_Courants(this.actes_Medicaux_Courants);
		t.setAnalyse(this.analyse);
		t.setAutres(this.autres);
		t.setConsultations_Visites(true);
		t.setDentaires(this.dentaires);
		t.setFrais_Chirurgicaux(this.frais_Chirurgicaux);
		t.setFrais_Pharmaceutiques(true);
		t.setRadio_Physio(this.radio_Physio);
		t.setOptique(this.optique);
		t.setMaternite(this.maternite);
		t.setHospitalisation(true);	  
		t.setAjouconjoint(this.ajouconjoint);
		t.setAjoutEnf(this.ajoutEnf);   
		t.setNombreenfant(this.nombreenfant);
		t.setNombrefille(this.nombrefille);
		
		
		String s = type_santeService.aprioriText2(t);
		HashMap<String,Double> map = aprio.ExecuteApriori(s);
		String chaine = " " ;
		for (Map.Entry mapentry : map.entrySet()) {	
			chaine =  mapentry.getKey().toString()+chaine ;
			System.out.println("clé: "+mapentry.getKey() + " | valeur: " + mapentry.getValue());
		}	       
		String[] words = chaine.split(" ");
		int count = words.length;
		for(int i = 0 ; i < count ; i++){
			System.out.println("      ***///****  " + words[i]);
		}
		//System.out.println("--------*********------***********----" + words[1]) ;
		//		type_santeService.addType(d.getNumDemande(), t);              
		System.out.println("etsawar te5dem !! hahaha   " +  this.a);
		System.out.println("  !!     " );
		if (words.length == 0){
			variable_confirm =" il y a pas de proposition a ajoutée " ;
		}
		else{
			variable_confirm = words[1].toString() ;
		}
		}
		return "/DemandeContratQuestionTypeSanteConfirm.xhtml?faces-redirect=true" ;
	}	

	
	
private String benif ;
private int benifCin ;

	public String getBenif() {
	return benif;
}

public void setBenif(String benif) {
	this.benif = benif;
}

public int getBenifCin() {
	return benifCin;
}

public void setBenifCin(int benifCin) {
	this.benifCin = benifCin;
}

	//////////////////////update contrat2222222222222 ///////////////////////////	
	public String updateDemandeContratController2() {
		if (this.a == 2 ){
			d.setVal_prime(0);    
			d.setCapitalAssure(this.getCapitalAssure());
		}
		else{ 
		d.setCapitalAssure(0);
		d.setVal_prime(this.getVal_prime());
		}
		d.setNombreAnnee(this.getNombreAnnee());
		d.setChoixPrime(this.getChoixPrime());
		d.setBeneficiaire(this.getBenif());
		d.setCinBeneficiaire(this.getBenifCin());
		
		System.out.println("-------------------------"+ this.nombreAnnee + "---" + this.val_prime + "     ** " + this.a);	
		demandeContratService.ajoutDemandeContrat(d ,iduser,   typeContrat,nomContrat);	
		/********************************************************/
	
		
		d.setNumDemande(0);
		//return "/DemandeContrat.xhtml?faces-redirect=true" ;
		return "/DemandeContrat.xhtml?faces-redirect=true" ;
	}	

	
	public String AccepteDM_typeSanteController3() throws FileNotFoundException{
	 	Field fld[] = DemandeContratControllerJSF.class.getDeclaredFields();
	 	for (int i = 0; i < 10; i++)
        {
            System.out.println("Variable Name is : " + fld[i].getName() + "   " + i);
            if(  fld[i].getName().toString().equals(variable_confirm.toLowerCase()) ){
            	 f = i ;      	
            }
        } 
	 	ajoutproposition_DM(t , f) ;
        System.out.println("heeeyyyy" + f ) ;
        type_santeService.addType(d.getNumDemande(), t); 
        d.setNumDemande(0);
        t.setId(0);
        return "/DemandeContrat.xhtml?faces-redirect=true" ;
	}
	public String RefusDM_typeSanteController3() throws FileNotFoundException{
        type_santeService.addType(d.getNumDemande(), t); 
        d.setNumDemande(0);
        t.setId(0);
        return "/DemandeContrat.xhtml?faces-redirect=true" ;
	} 
	public void ajoutproposition_DM(Type_sante t , int f){
		if(f == 1){	
			t.setActes_Medicaux_Courants(true);
		}
		if(f == 2){	
			t.setAnalyse(true);
		}
		if(f == 3){	
			t.setRadio_Physio(true);
		}
		if(f == 4){	
			t.setOptique(true);
		}
		if(f == 5){	
			t.setFrais_Chirurgicaux(true);
		}
		if(f == 6){	
			t.setDentaires(true);
		}
		if(f == 7){	
			t.setMaternite(true);
		}
		if(f == 8){	
			t.setAutres(true);
		}
		if(f == 9){	
			t.setAjoutEnf(true);
		}
		if(f == 10){	
			t.setAjouconjoint(true);
		}}
                      
	
/////////////// affichet liste des demandes non traité //////////////////	
		List<DemandeContrat> listeDemandeNT ;
		
		public List<DemandeContrat> getListeDemandeNT() {
			listeDemandeNT = demandeContratService.afficheDemandeContratNonTraite();
		return listeDemandeNT;		}

		public void setListeDemandeNT(List<DemandeContrat> listeDemandeNT) {
			this.listeDemandeNT = listeDemandeNT;
		}
/////////////// affichet liste des demandes traité //////////////////	
	List<DemandeContrat> listeDemandeT ;
	
	public List<DemandeContrat> getListeDemandeT() {
		listeDemandeT = demandeContratService.afficheDemandeContratTraite();
	return listeDemandeT;		}

	public void setListeDemandeT(List<DemandeContrat> listeDemandeT) {
		this.listeDemandeT = listeDemandeT;
	}	
/////////////// valider input  //////////////////	  

    
//////////////////// delete demande non triaté ///////////////////		 
		public void deleteDemandeNontraite(int id){
		
			demandeContratService.deleteDemandeContrat(id);
		}
////////////////////verife document demande contrat  ///////////////////		 
		private String cause ;

		public String getCause() {
			return cause;
		}

		public void setCause(String cause) {
			this.cause = cause;
		}
		private int numDemande ;

		public int getNumDemande() {
			return numDemande;
		}

		public void setNumDemande(int numDemande) {
			this.numDemande = numDemande;
		}

		public String verifedemande( ){
			demandeContratService.checkDocument(numDemande , cause);
			return "/emp/listeDemandeContratNT.xhtml?faces-redirect=true" ;

			
		}
		
////////////////////  accepte demande contrat  ///////////////////		 
		public String acceptedemande(){
			contratService.calculeContrat(numDemande);
			return "/emp/listeDemandeContratNT.xhtml?faces-redirect=true" ;

			
		}
//////////////////// liste demande contrat  par user ///////////////////		 
	
		List<DemandeContrat> listeDemandeNTUser ;
		List<DemandeContrat> listeDemandeTUser ;

//	public int iduser = 6 ;
	
		public List<DemandeContrat> getListeDemandeNTUser() {
		List<DemandeContrat> list = demandeContratService.afficheDemandeContratNonTraiteUSER(iduser);
		return list;	
		}

		public void setListeDemandeNTUser(List<DemandeContrat> listeDemandeNTUser) {
			this.listeDemandeNTUser = listeDemandeNTUser;
		}

		public List<DemandeContrat> getListeDemandeTUser() {
			List<DemandeContrat> list = demandeContratService.afficheDemandeContratTraiteUSER(iduser);
			return list;	
		}

		public void setListeDemandeTUser(List<DemandeContrat> listeDemandeTUser) {
			this.listeDemandeTUser = listeDemandeTUser;
		}
//////////////////////////////////////////////////////////////////////////////////		 
	@GetMapping("/liste")
	@ResponseBody
	public List<DemandeContrat> afficheDemandeContratController(){
		List<DemandeContrat> list = demandeContratService.afficheDemandeContrat();
		return list;
			
	}

	@GetMapping("/liste/traite")
	@ResponseBody
	public List<DemandeContrat> afficheDemandeContratTraiteController(){
		List<DemandeContrat> list = demandeContratService.afficheDemandeContratTraite();
		return list;
			
	}
	@GetMapping("/liste/nonTraite/{id}")
	@ResponseBody
	public List<DemandeContrat> afficheDemandeContratTraiteNoUsernController(@PathVariable("id")  int iduser){
		List<DemandeContrat> list = demandeContratService.afficheDemandeContratNonTraiteUSER(iduser);
		return list;		
	}
	@GetMapping("/liste/traite/{id}")
	@ResponseBody
	public List<DemandeContrat> afficheDemandeContratTraiteUserController(@PathVariable("id")  int iduser){
		List<DemandeContrat> list = demandeContratService.afficheDemandeContratTraiteUSER(iduser);
		return list;			
	}
	
	
	
	@RequestMapping("/delete/{id}")
	@PostMapping
	public ResponseEntity<DemandeContrat> deleteDemandeContratController(@PathVariable("id")  int id){
		
		demandeContratService.deleteDemandeContrat(id);
		return new ResponseEntity<DemandeContrat>(HttpStatus.OK);
	}
	@GetMapping("/verifContrat/{id}")
	@ResponseBody
	public ResponseEntity<DemandeContrat> verifieTypeContratController(@PathVariable("id")  int id){
		demandeContratService.verifieTypeContrat(id);
		return new ResponseEntity<DemandeContrat>(HttpStatus.OK);
	}
	
	@GetMapping("/verifEtat/{id}")
	@ResponseBody
	public ResponseEntity<DemandeContrat>verifeEtatDemandeContratController(@PathVariable("id")  int id){
		demandeContratService.verifeEtatDemandeContrat(id);
		return new ResponseEntity<DemandeContrat>(HttpStatus.OK);
			
	}
	
	
	
	
	
	

	
	
	
	
	
	 
	public boolean isActes_Medicaux_Courants() {
		return actes_Medicaux_Courants;
	}
	public void setActes_Medicaux_Courants(boolean actes_Medicaux_Courants) {
		this.actes_Medicaux_Courants = actes_Medicaux_Courants;
	}
	public boolean isAnalyse() {
		return analyse;
	}
	public void setAnalyse(boolean analyse) {
		this.analyse = analyse;
	}
	public boolean isRadio_Physio() {
		return radio_Physio;
	}
	public void setRadio_Physio(boolean radio_Physio) {
		this.radio_Physio = radio_Physio;
	}
	public boolean isOptique() {
		return optique;
	}
	public void setOptique(boolean optique) {
		this.optique = optique;
	}
	public boolean isFrais_Chirurgicaux() {
		return frais_Chirurgicaux;
	}
	public void setFrais_Chirurgicaux(boolean frais_Chirurgicaux) {
		this.frais_Chirurgicaux = frais_Chirurgicaux;
	}
	public boolean isDentaires() {
		return dentaires;
	}
	public void setDentaires(boolean dentaires) {
		this.dentaires = dentaires;
	}
	public boolean isMaternite() {
		return maternite;
	}
	public void setMaternite(boolean maternite) {
		this.maternite = maternite;
	}
	public boolean isAutres() {
		return autres;
	}
	public void setAutres(boolean autres) {
		this.autres = autres;
	}
	public DemandeContratService getDemandeContratService() {
		return demandeContratService;
	}

	public void setDemandeContratService(DemandeContratService demandeContratService) {
		this.demandeContratService = demandeContratService;
	}



	public int getNombreAnnee() {
		return nombreAnnee;
	}

	public void setNombreAnnee(int nombreAnnee) {
		this.nombreAnnee = nombreAnnee;
	}

	public int getDeces() {
		return deces;
	}

	public void setDeces(int deces) {
		this.deces = deces;
	}

	public int getVie() {
		return vie;
	}

	public void setVie(int vie) {
		this.vie = vie;
	}

	public int getCapitalOuRente() {
		return capitalOuRente;
	}

	public void setCapitalOuRente(int capitalOuRente) {
		this.capitalOuRente = capitalOuRente;
	}

	public int getSante() {
		return sante;
	}

	public void setSante(int sante) {
		this.sante = sante;
	}

	public float getCapitalAssure() {
		return capitalAssure;
	}

	public void setCapitalAssure(float capitalAssure) {
		this.capitalAssure = capitalAssure;
	}

	public int getFumeur() {
		return fumeur;
	}

	public void setFumeur(int fumeur) {
		this.fumeur = fumeur;
	}

	public int getAlcool() {
		return alcool;
	}

	public void setAlcool(int alcool) {
		this.alcool = alcool;
	}

	public int getSportif() {
		return sportif;
	}

	public void setSportif(int sportif) {
		this.sportif = sportif;
	}

	public int getMaladie() {
		return maladie;
	}

	public void setMaladie(int maladie) {
		this.maladie = maladie;
	}

	public int getAccepte() {
		return accepte;
	}

	public void setAccepte(int accepte) {
		this.accepte = accepte;
	}

	public int getTraite() {
		return traite;
	}

	public void setTraite(int traite) {
		this.traite = traite;
	}

	public String getNomContrat() {
		return nomContrat;
	}

	public void setNomContrat(String nomContrat) {
		this.nomContrat = nomContrat;
	}

	public String getTypeContrat() {
		return typeContrat;
	}

	public void setTypeContrat(String typeContrat) {
		this.typeContrat = typeContrat;
	}

	public boolean isDeces_b() {
		return deces_b;
	}

	public void setDeces_b(boolean deces_b) {
		this.deces_b = deces_b;
	}

	public boolean isVie_b() {
		return vie_b;
	}

	public void setVie_b(boolean vie_b) {
		this.vie_b = vie_b;
	}

	public boolean isCapitalOuRente_b() {
		return capitalOuRente_b;
	}

	public void setCapitalOuRente_b(boolean capitalOuRente_b) {
		this.capitalOuRente_b = capitalOuRente_b;
	}

	public boolean isSante_b() {
		return sante_b;
	}

	public void setSante_b(boolean sante_b) {
		this.sante_b = sante_b;
	}

	public boolean isFumeur_b() {
		return fumeur_b;
	}

	public void setFumeur_b(boolean fumeur_b) {
		this.fumeur_b = fumeur_b;
	}

	public boolean isAlcool_b() {
		return alcool_b;
	}

	public void setAlcool_b(boolean alcool_b) {
		this.alcool_b = alcool_b;
	}

	public boolean isSportif_b() {
		return sportif_b;
	}

	public void setSportif_b(boolean sportif_b) {
		this.sportif_b = sportif_b;
	}

	public boolean isMaladie_b() {
		return maladie_b;
	}

	public void setMaladie_b(boolean maladie_b) {
		this.maladie_b = maladie_b;
	}




	public Prime getChoixPrime() {
		return choixPrime;
	}
	public void setChoixPrime(Prime choixPrime) {
		this.choixPrime = choixPrime;
	}
	public float getVal_prime() {
		return val_prime;  
	}
	public void setVal_prime(float val_prime) {
		this.val_prime = val_prime;
	}
	public Prime[] getPrimes() { return Prime.values(); }



	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Date getDatebirth() {
		return datebirth;
	}

	public void setDatebirth(Date datebirth) {
		this.datebirth = datebirth;
	}

	public Date getDatedemande() {
		return Datedemande;
	}

	public void setDatedemande(Date datedemande) {
		Datedemande = datedemande;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}



   

	private String firstname ;
	private String lastname ;
	private Date datebirth;
	private Date Datedemande ;     
	private String email ;
	private String chaine_sante ;
	public String getChaine_sante() {
		return chaine_sante;
	}

	public void setChaine_sante(String chaine_sante) {
		this.chaine_sante = chaine_sante;
	}

	public String displayDemande(DemandeContrat d){
		
		this.setTypeContrat(d.getTypeContrat());
		this.setDatedemande(d.getDateDemande());
		this.setNomContrat(d.getNomContrat());

		
		this.setFirstname(d.getUsers().getFirst_name());
		this.setLastname(d.getUsers().getLast_name());
		this.setDatebirth(d.getUsers().getBirth_date());
		this.setEmail(d.getUsers().getEmail()); 
		this.setNumDemande(d.getNumDemande());
		System.out.println("ccccccccc -------------------"+d.getCapitalAssure());
		if(d.getNomContrat().equals("Santé")){
	
			chaine_sante = type_santeService.aprioriText3(d.getType_sante());
		/*	this.setActes_Medicaux_Courants(d.getType_sante().isActes_Medicaux_Courants());
			this.setAnalyse(d.getType_sante().isAnalyse());
			this.setRadio_Physio(d.getType_sante().isRadio_Physio());
			this.setOptique(d.getType_sante().isOptique());
			this.setFrais_Chirurgicaux(d.getType_sante().isFrais_Chirurgicaux());
			this.setDentaires(d.getType_sante().isDentaires());
			this.setMaternite(d.getType_sante().isMaternite());
			this.setAutres(d.getType_sante().isAutres());
			this.setAjouconjoint(d.getType_sante().isAjouconjoint());
			this.setAjoutEnf(d.getType_sante().isAjoutEnf());
			*/
			
			
			return "/emp/demandecontratDetailssante.xhtml?faces-redirect=true" ;
		}
		else{
		this.setCapitalAssure(d.getCapitalAssure());
		this.setNombreAnnee(d.getNombreAnnee());
		this.setVal_prime(d.getVal_prime());
		this.setChoixPrime(d.getChoixPrime());
		
	
	
		return "/emp/demandecontratDetails.xhtml?faces-redirect=true" ;
		}
	}
    
   
	///// champ hidden .. n3adi fih parametre m js lil jsf //////      
	private int a ;                                             
	public int getA() {
		return a;
	}
	public void setA(int a) {
		this.a = a;  
	}
	 
	
	
///////////////////////////    validator    ///////////////////////////////////
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if(this.val_prime == 0  && this.capitalAssure == 0 ){

        	FacesMessage msg = 
        			new FacesMessage("E-mail validation failed.", 
        					"Invalid E-mail format.");
        	msg.setSeverity(FacesMessage.SEVERITY_ERROR);
        	throw new ValidatorException(msg);
        
    }
   
}
}







/// ajout , autre methode

/*	@RequestParam("adresse" ) String adresse,
@RequestParam("profession")	String profession,
@RequestParam("cin")	long cin, 
@RequestParam("dateDemande")	Date dateDemande,
@RequestParam("ville")	String ville,
@RequestParam("salaire")	float salaire, 
@RequestParam("nombreAnnee")	int nombreAnnee, 
@RequestParam("deces")	int deces,
@RequestParam("vie")	int vie,
@RequestParam("capitalOuRente")	int capitalOuRente,
@RequestParam("sante")	int sante,
@RequestParam("capitalAssure")	float capitalAssure,
@RequestParam("fumeur")	int fumeur,
@RequestParam("alcool")	int alcool, 
@RequestParam("sportif")	int sportif, 
@RequestParam("maladie")	int maladie, 
@RequestParam("accepte")	int accepte, 
@RequestParam("traite")	int traite

*/	

/*	demandeContratService.ajoutDemandeContrat(new DemandeContrat( adresse,  profession,  cin,  dateDemande,  ville,
 salaire,  nombreAnnee,  deces,  vie,  capitalOuRente,  sante,  capitalAssure,
 fumeur,  alcool,  sportif,  maladie,  accepte,  traite), 2);*/
