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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import tn.esprit.spring.entity.ActifFinancier;
import tn.esprit.spring.entity.Fond;
import tn.esprit.spring.entity.Prime;
import tn.esprit.spring.services.ActifFinancierService;


@Scope(value = "session")

@Controller(value = "actifFinancierControllerJSF") // Name of the bean in Spring IoC
@ELBeanName(value = "actifFinancierControllerJSF")
@RequestMapping("/jsf")
public class ActifFinancierControllerJSF {
	
	@Autowired
	ActifFinancierService actifFinancierService ;


    
    
	private int    iduser = UserController.getIdpublic();
	public String ajouActif(){
		ActifFinancier f = new ActifFinancier();

		f.setMaturite(this.maturite);
		f.setMontant_investi(this.montant_investi);
		f.setNomFond(this.fond);
		f.setChoixPrime(this.prime);


		actifFinancierService.addActifFinancier(f, iduser);

		return "/DemandeContrat.xhtml?faces-redirect=true" ;
	}
	////////////////////////////// liste Actif actuel par fond Fond_Euro et User ////////////////////////////////
	List<ActifFinancier>  listeActifactuelparFond_EuroUser ;

	public List<ActifFinancier> getListeActifactuelparFond_EuroUser() {
		return actifFinancierService.listemontant_actuelFondparUser(Fond.Fond_Euro,iduser);
	}

	public void setListeActifactuelparFond_EuroUser(List<ActifFinancier> listeActifactuelparfondUser) {
		this.listeActifactuelparFond_EuroUser = listeActifactuelparfondUser;
	}
	//////////////////////////////liste Actif actuel par fond Fond_Euro et User ////////////////////////////////
	List<ActifFinancier>  listeActifactuelparEuro_CroissanceUser ;

	public List<ActifFinancier> getListeActifactuelparEuro_CroissanceUser() {
		return actifFinancierService.listemontant_actuelFondparUser(Fond.Euro_Croissance,iduser);
	}

	public void setListeActifactuelparEuro_CroissanceUser(List<ActifFinancier> listeActifactuelparfondUser) {
		this.listeActifactuelparEuro_CroissanceUser = listeActifactuelparfondUser;
	}
	private float rachat ;
	
	public float getRachat() {
		return rachat;
	}

	public void setRachat(float rachat) {
		this.rachat = rachat;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	public String actifactuelfondeuro(ActifFinancier a){  
		System.out.println(a.getId() + "            ------11111111");
		ActifFinancier actif = actifFinancierService.montant_actuelFondEuro(a.getId());
		System.out.println(actif.getMontant_cumule()+ "            ------22222222222222");

		this.setMontant_cumule(actif.getMontant_cumule());	          
		this.setRachat(actif.getMontant_rachat());
		return "/user/actifcumule.xhtml?faces-redirect=true" ;
	}
	public String actifactueleuroeroissance(ActifFinancier a){
		ActifFinancier actif = actifFinancierService.montant_actuelEuroCroissance(a.getId());
		this.setMontant_cumule(actif.getMontant_cumule());
		this.setRachat(actif.getMontant_rachat());
		return "/user/actifcumule.xhtml?faces-redirect=true" ;
	}
	////////////////////////////////////////////////////////////////////////////////////////////////
	public String changeFondEuro_to_EuroCroissance(ActifFinancier a){
		actifFinancierService.FondEuro_to_EuroCroissance(a.getId());
		return "/user/listeactifEC.xhtml?faces-redirect=true" ;
	}
/////////////////////////////////////liste fond euro ///////////////////////////////////////////////////////////
	List<ActifFinancier>  listeActifactuelparFond_Euro ;
	List<ActifFinancier>  listeActifactuelparEuro_Croissance ;

	public List<ActifFinancier> getListeActifactuelparFond_Euro() {
		return actifFinancierService.listemontant_actuelFond(Fond.Fond_Euro);
	}

	public void setListeActifactuelparFond_Euro(List<ActifFinancier> listeActifactuelparFond_Euro) {
		this.listeActifactuelparFond_Euro = listeActifactuelparFond_Euro;
	}

	public List<ActifFinancier> getListeActifactuelparEuro_Croissance() {
		return actifFinancierService.listemontant_actuelFond(Fond.Euro_Croissance);
	}

	public void setListeActifactuelparEuro_Croissance(List<ActifFinancier> listeActifactuelparEuro_Croissance) {
		this.listeActifactuelparEuro_Croissance = listeActifactuelparEuro_Croissance;
	}

	@PostMapping("/addactif/{iduser}")
	@ResponseBody
	public ResponseEntity<ActifFinancier> ajoutActifFinancierController(@RequestBody ActifFinancier  a , @PathVariable("iduser") int    iduser	){
		
		actifFinancierService.addActifFinancier(a, iduser);
		return new ResponseEntity<ActifFinancier>(HttpStatus.OK);
	}

	
	@GetMapping("/listeActifactuel")  /*** emp  : all  */
	@ResponseBody
	public List<ActifFinancier>listemontant_actuelFondEuro(){
		return actifFinancierService.listetouslesfond();
	}
	
	@GetMapping("/listeActifactuelparfond/{fond}")  /*** emp  : all par fond  */
	@ResponseBody
	public List<ActifFinancier>listemontant_actuelFond(@PathVariable("fond") Fond    fond){
		return actifFinancierService.listemontant_actuelFond(fond);
	}
	@GetMapping("/listeActifactuelparfondUser/{fond}/{id}")  /*** user  : all par fond  */
	@ResponseBody
	public List<ActifFinancier>listemontant_actuelFondUser(@PathVariable("fond") Fond    fond ,@PathVariable("id") int    iduser){
		return actifFinancierService.listemontant_actuelFondparUser(fond,iduser);
	}	
	
	@GetMapping("/change/{id}")
	@ResponseBody
	public ResponseEntity<ActifFinancier>FondEuro_to_EuroCroissance(@PathVariable("id") int    id){
		actifFinancierService.FondEuro_to_EuroCroissance(id);
		return new ResponseEntity<ActifFinancier>(HttpStatus.OK);		
	}       
	         
	@GetMapping("/actifactuel/{id}")
	@ResponseBody
	public ResponseEntity<ActifFinancier>montant_actuelFondEuro(@PathVariable("id") int    id){
		actifFinancierService.montant_actuelFondEuro(id);
		return new ResponseEntity<ActifFinancier>(HttpStatus.OK);		
	}
	
	@GetMapping("/actifactuelC/{id}")
	@ResponseBody
	public ResponseEntity<ActifFinancier>montant_actuelEuroCroissance(@PathVariable("id") int    id){
		actifFinancierService.montant_actuelEuroCroissance(id);
		return new ResponseEntity<ActifFinancier>(HttpStatus.OK);		
	}
	
	
             
	
	public Fond[] getFonds() { return Fond.values(); }
	public Prime[] getPrimes() { return Prime.values(); 	}
	
	private Fond fond ;
	private Prime prime ;
	
	

	public Prime getPrime() {
		return prime;
	}

	public Fond getFond() {
		return fond;
	}

	public void setFond(Fond fond) {
		this.fond = fond;
	}

	public void setPrime(Prime prime) {
		this.prime = prime;
	}

	private Date date_debut ;
	private Date date_fin ;
	private int maturite ;
	private float montant_investi ;
	private float montant_cumule ;
	private float montant_rachat ;
	private int accepte_rachat ;
	private Date date_actuel ;
	

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}


	private String etat ; // resilié ou en cours



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


	public int getMaturite() {
		return maturite;
	}


	public void setMaturite(int maturite) {
		this.maturite = maturite;
	}


	public float getMontant_investi() {
		return montant_investi;
	}


	public void setMontant_investi(float montant_investi) {
		this.montant_investi = montant_investi;
	}


	public float getMontant_cumule() {
		return montant_cumule;
	}


	public void setMontant_cumule(float montant_cumule) {
		this.montant_cumule = montant_cumule;
	}


	public float getMontant_rachat() {
		return montant_rachat;
	}


	public void setMontant_rachat(float montant_rachat) {
		this.montant_rachat = montant_rachat;
	}


	public int getAccepte_rachat() {
		return accepte_rachat;
	}


	public void setAccepte_rachat(int accepte_rachat) {
		this.accepte_rachat = accepte_rachat;
	}


	public Date getDate_actuel() {
		return date_actuel;
	}


	public void setDate_actuel(Date date_actuel) {
		this.date_actuel = date_actuel;
	}
	


}
