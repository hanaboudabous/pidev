package tn.esprit.spring.controller;

import java.util.Date;
import java.util.List;

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

import tn.esprit.spring.controller.ReclamationSinistreControllerJSF;
import tn.esprit.spring.entity.Contrat;
import tn.esprit.spring.entity.DemandeContrat;
import tn.esprit.spring.entity.Prime;
import tn.esprit.spring.services.ContratService;



@Scope(value = "session")

@Controller(value = "contratControllerJSF") // Name of the bean in Spring IoC
@ELBeanName(value = "contratControllerJSF")
@RequestMapping("/jsf")

public class ContratControllerJSF {

	
	
	@Autowired
	ContratService contratService ;
	@Autowired	
	ReclamationSinistreControllerJSF reclamationSinistreControllerJSF ;
	
	
	private int    idUser = UserController.getIdpublic();
	List<Contrat> listeContratA ;
	List<Contrat> listeContratNA ;
	List<Contrat> listeContratAEmp ;
	List<Contrat> listeContratNAEmp ;

/////////////// affichet liste des Contrat  //////////////////	
	
	public List<Contrat> getListeContratA() {
		return listeContratA;
	}

	public void setListeContratA(List<Contrat> listeContratA) {
		this.listeContratA = listeContratA;
	}

	public List<Contrat> getListeContratNA() {
		return listeContratNA;
	}

	public void setListeContratNA(List<Contrat> listeContratNA) {
		this.listeContratNA = listeContratNA;
	}

	public List<Contrat> getListeContratAEmp() {
		return listeContratAEmp;
	}

	public void setListeContratAEmp(List<Contrat> listeContratAEmp) {
		this.listeContratAEmp = listeContratAEmp;
	}

	public List<Contrat> getListeContratNAEmp() {
		return listeContratNAEmp;
	}

	public void setListeContratNAEmp(List<Contrat> listeContratNAEmp) {
		this.listeContratNAEmp = listeContratNAEmp;
	}

/////////////////////////////////////////////////////////////////////////////////////////	
	
	@RequestMapping("/calcule/{id}")
	@PostMapping
	public ResponseEntity<Contrat> calculeContratController(@PathVariable("id") int    id	){
				
		contratService.calculeContrat(id);
		//ajoutContrat(id ,1);
		return new ResponseEntity<Contrat>(HttpStatus.OK);
	}
	
	
	@GetMapping("/listeC")
	@ResponseBody
	public List<Contrat> afficheContratController(){
		List<Contrat> list = contratService.afficheContrat();
		return list;
			
	}
	@GetMapping("/liste/Nonaccpet")
	@ResponseBody
	public List<Contrat> listeNonREPContratController(){
		List<Contrat> list = contratService.listeNonREPContrat();
		return list;
			
	}
	@GetMapping("/liste/accpet")
	@ResponseBody
	public List<Contrat> listeREPContratController(){
		List<Contrat> list = contratService.listeREPContrat();
		return list;
			
	}


	@RequestMapping("/deleteC/{id}")
	@PostMapping
	public ResponseEntity<Contrat> deleteContratController(@PathVariable("id")  int id){
		
		contratService.deleteContrat(id);
		return new ResponseEntity<Contrat>( HttpStatus.OK);
	}
///////////////////////////////////// 
           
	

/////////////////////////////////////// accepte contrat  /////////////////////////////////////////////
	public String accepteContrat( int idContrat ) throws Exception{
	//	int idContrat = c.getNumContrat();
		 contratService.acceptContrat(1,idContrat);
			return "/user/listeDemandeContratT.xhtml?faces-redirect=true" ;
	}
	public String refusContrat(int idContrat ) throws Exception{
	//	int idContrat = c.getNumContrat();
	 contratService.acceptContrat(0,idContrat);
		return "/user/listeDemandeContratT.xhtml?faces-redirect=true" ;
	}
////////////////////////////////////////// liste contrat accepter ///////////////////////////////////////////////////////
	private List<Contrat> listeContrataccepter ;
	
	public List<Contrat> getListeContrataccepter() {
		List<Contrat> list = contratService.Contrataccepter(idUser);
		return list;
	}
   
	public void setListeContrataccepter(List<Contrat> listeContrataccepter) {
		this.listeContrataccepter = listeContrataccepter;
	}
	///////////////////////////////// liste contrat proposer //////////////////////////
	private List<Contrat> listeProposeContrat ;
		
	public List<Contrat> getListeProposeContrat() {
		List<Contrat> list = contratService.ProposeContrat(idUser);
		return list;
	}

	public void setListeProposeContrat(List<Contrat> listeProposeContrat) {
		this.listeProposeContrat = listeProposeContrat;
	}
	////////////////////////////////////////////////////////////////////////////////////

	private String cause ;
	
	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public String redirectionContrat(DemandeContrat d){

		if(d.getContrat() != null){
	
		Contrat c = d.getContrat();
		this.setDate_debut(c.getDate_debut());
		this.setDate_fin(c.getDate_fin());
		if(d.getCause() == null){
		this.setPrimeCommerciale(c.getPrimeCommerciale());
		this.setPrimePure(c.getPrimePure());
		this.setAcceptation(c.getAcceptation());
		this.setEtat(c.getEtat());
		this.setRemboursement(c.getRemboursement());
		this.setIdContrat(c.getNumContrat());
		return "/user/contrat.xhtml?faces-redirect=true" ;
		}  
		else{
		
			this.setCause(d.getCause());
			return "/user/contratrefuse.xhtml?faces-redirect=true" ;
		}
		}
		else {
			this.setDate_debut(d.getDateDemande());
			this.setCause(" votre contrat a été supprimé") ;
			return "/user/contratrefuse.xhtml?faces-redirect=true" ;
		}
	}
	
	public String contartDtail(Contrat c){
		this.setDate_debut(c.getDate_debut());
		this.setDate_fin(c.getDate_fin());
		this.setPrimeCommerciale(c.getPrimeCommerciale());
		this.setPrimePure(c.getPrimePure());
		this.setAcceptation(c.getAcceptation());
		this.setEtat(c.getEtat());
		this.setRemboursement(c.getRemboursement());
		this.setFirst_name(c.getDemandeContrat().getUsers().getFirst_name());
		this.setLast_name(c.getDemandeContrat().getUsers().getLast_name());
		this.setEmail(c.getDemandeContrat().getUsers().getEmail());
		this.setChoixPrime(c.getDemandeContrat().getChoixPrime());
		this.setNombreAnnee(c.getDemandeContrat().getNombreAnnee());
		this.setTypeContrat(c.getDemandeContrat().getTypeContrat());
		this.setNomContrat(c.getDemandeContrat().getNomContrat());
		this.setVal_prime(c.getDemandeContrat().getVal_prime());
		
		if(c.getDemandeContrat().getNomContrat().equals("Santé")){
			return "/user/contratDetailssante.xhtml?faces-redirect=true" ;

		}    
		else{
		return "/user/contratDetails.xhtml?faces-redirect=true" ;
		}
		
		
	}
	
private int idContrat ;
	public int getIdContrat() {
	return idContrat;
}

public void setIdContrat(int idContrat) {
	this.idContrat = idContrat;
}

	private Date date_debut ;
	private Date date_fin ;
	private float primeCommerciale ;
	private float primePure ;
	private String etat ; // resilié ou en cours	
	private int acceptation ; // contat accepte par le client ou pas encore
	private float remboursement; 
	////////
	private String First_name;
	private String Last_name;
	private String Email;
	private int nombreAnnee ;
	 private Prime choixPrime ;
	 private float val_prime;
	 private String nomContrat ;
	 private String typeContrat;            
	 ///////

	public Date getDate_debut() {
		return date_debut;
	}

	public void setDate_debut(Date date_debut) {
		this.date_debut = date_debut;
	}

	public Date getDate_fin() {
		return date_fin;
	}

	public void setDate_fin(Date date_fin) {
		this.date_fin = date_fin;
	}

	public float getPrimeCommerciale() {
		return primeCommerciale;
	}

	public void setPrimeCommerciale(float primeCommerciale) {
		this.primeCommerciale = primeCommerciale;
	}

	public float getPrimePure() {
		return primePure;
	}

	public void setPrimePure(float primePure) {
		this.primePure = primePure;
	}

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	public int getAcceptation() {
		return acceptation;
	}

	public void setAcceptation(int acceptation) {
		this.acceptation = acceptation;
	}

	public float getRemboursement() {
		return remboursement;
	}

	public void setRemboursement(float remboursement) {
		this.remboursement = remboursement;
	}

	public String getFirst_name() {
		return First_name;
	}

	public void setFirst_name(String first_name) {
		First_name = first_name;
	}

	public String getLast_name() {
		return Last_name;
	}

	public void setLast_name(String last_name) {
		Last_name = last_name;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public int getNombreAnnee() {
		return nombreAnnee;
	}

	public void setNombreAnnee(int nombreAnnee) {
		this.nombreAnnee = nombreAnnee;
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

	/**********************************************************/
	public String doRedirection(int idcon){
		reclamationSinistreControllerJSF.test(idcon);
		System.out.println("idcon"+idcon);
		return "/recsinistre/formrecquestions.xhtml?faces-redirect=true";
		
	}
	
	List<Contrat> listeContrat;

	public List<Contrat> getListeContrat() {  
		
		
		return contratService.listeContratdeUser(1);                                        //Changer id user de statique -> automatique
	}
/*************************************************************/
	
	
}
