package tn.esprit.spring.controller;

import java.util.Date;
import java.util.List;

import org.ocpsoft.rewrite.el.ELBeanName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.entity.DemandeContrat;
import tn.esprit.spring.entity.Type_sante;
import tn.esprit.spring.services.DemandeContratService;
import tn.esprit.spring.services.Type_santeService;
import tn.esprit.spring.services.UserService;

@RestController(value = "demandeContratController") // Name of the bean in Spring IoC
@ELBeanName(value = "demandeContratController")
public class DemandeContratController {
	
	@Autowired
	DemandeContratService demandeContratService ;
	
	@Autowired
	Type_santeService type_santeService ;

	private int    iduser = UserController.getIdpublic();

	
	@PostMapping("/add")
	@ResponseBody
	public ResponseEntity<DemandeContrat> ajoutDemandeContratController(@RequestBody DemandeContrat  d 	){
		//int iduser = d.getUsers().getUser_ID();
		String s ;
		String type1 = "Assurance vie";
		String type2 = "Assurance non vie";
		String nomvie1 ="Capital différé";
		String nomvie2 = "Rente viagére";
		String nomdeces= "Vie entiére";
		String sante = "Santé";
		if(d.getSante() == 0 ){
			
			if(d.getVie()==1 && d.getDeces() == 0){
						if(d.getCapitalOuRente()== 0){s =" rente viag";System.out.println(s);demandeContratService.ajoutDemandeContrat(d ,iduser,type1,nomvie1);}
						else{s = "capital diff";System.out.println(s);demandeContratService.ajoutDemandeContrat(d ,iduser, type1,nomvie2);}
			}
			else if (d.getVie()==0 && d.getDeces() == 1){   s="vie entiere";System.out.println();demandeContratService.ajoutDemandeContrat(d ,iduser, type1,nomdeces);}
			else if (d.getVie()==1 && d.getDeces() == 1){  s="on peut pas vie et deces"; System.out.println(s);}	
			else{System.out.println("hello");}
}
		else{s="sante";System.out.println(s);demandeContratService.ajoutDemandeContrat(d ,iduser, type2,sante);
		
		}

		return new ResponseEntity<DemandeContrat>(HttpStatus.OK);
	}
	

	@GetMapping("/liste")
	@ResponseBody
	public List<DemandeContrat> afficheDemandeContratController(){
		List<DemandeContrat> list = demandeContratService.afficheDemandeContrat();
		return list;	
	}
/// employee !!! lkoll ma3neha ..
	@GetMapping("/liste/nonTraite")
	@ResponseBody
	public List<DemandeContrat> afficheDemandeContratTraiteNonController(){
		List<DemandeContrat> list = demandeContratService.afficheDemandeContratNonTraite();
		return list;		
	}
	@GetMapping("/liste/traite")
	@ResponseBody
	public List<DemandeContrat> afficheDemandeContratTraiteController(){
		List<DemandeContrat> list = demandeContratService.afficheDemandeContratTraite();
		return list;			
	}
	/// employee !!! lkoll ma3neha ..
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

	@PostMapping("/refuse/{numDemande}")
	@ResponseBody
	public ResponseEntity<DemandeContrat> verifedemande(@PathVariable("numDemande")  int numDemande,@RequestBody DemandeContrat cause ){
		demandeContratService.checkDocument(numDemande , cause);		
		return new ResponseEntity<DemandeContrat>(HttpStatus.OK);
		
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
