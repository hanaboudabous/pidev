package tn.esprit.spring.controller;

import java.util.List;


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

import tn.esprit.spring.entity.Cagnotte;
import tn.esprit.spring.entity.Contribution;
import tn.esprit.spring.entity.ReclamationSinistre;
import tn.esprit.spring.entity.Sinistre;
import tn.esprit.spring.services.CagnotteService;

@Scope(value = "session")

@Controller(value = "CagnotteController") // Name of the bean in Spring IoC
@RequestMapping("/Cagnotte")
public class CagnotteController {
	@Autowired
	CagnotteService cagnotteService ;
	
	

	@RequestMapping("/addcagnotte/{idcon}")
	@PostMapping
	public ResponseEntity<Cagnotte> ajoutCagnotteController(
			@PathVariable("idcon")  int idcon			){
		

		cagnotteService.entrerCagnotte(idcon);
		return new ResponseEntity<Cagnotte>(HttpStatus.OK);
	}
	
	
	@GetMapping("/liste")
	@ResponseBody
	public List<Cagnotte> afficheCagnotteController(){
		List<Cagnotte> list = cagnotteService.afficheCagnotte();
		return list;
			
	}
	
	
	
	@GetMapping("/listeuser")
	@ResponseBody
	public String affichageCagnotteAuUsers(){
		String list = cagnotteService.affichageCagnotteAuUsers();
		return list;
			
	}

}
