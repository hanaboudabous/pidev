package tn.esprit.spring.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import tn.esprit.spring.entity.Contrat;
import tn.esprit.spring.services.ContratService;
import tn.esprit.spring.services.UserService;


@Scope(value = "session")

@Controller(value = "ContratController") // Name of the bean in Spring IoC

public class ContratController {


	
	@Autowired
	ContratService contratService ;
	

	@GetMapping("/calculeContrat/{id}")
	public ResponseEntity<Contrat> calculeContratController(@PathVariable("id") int    id	){
				
		contratService.calculeContrat(id);
		return new ResponseEntity<Contrat>(HttpStatus.OK);
	}
	@GetMapping("/emp/listeC")
	@ResponseBody
	public List<Contrat> afficheContratController(){
		List<Contrat> list = contratService.afficheContrat();
		return list;
			
	}
	@GetMapping("/emp/liste/Nonaccpet")
	@ResponseBody
	public List<Contrat> listeNonREPContratController(){
		List<Contrat> list = contratService.listeNonREPContrat();
		return list;
			
	}
	@GetMapping("/emp/liste/accpet")
	@ResponseBody
	public List<Contrat> listeREPContratController(){
		List<Contrat> list = contratService.listeREPContrat();
		return list;
			
	}
	
	@GetMapping("/liste/propose/{idUser}")
	@ResponseBody
	public List<Contrat> ProposeContratController(@PathVariable("idUser") int idUser){
		List<Contrat> list = contratService.ProposeContrat(idUser);
		return list;			
	}

	@RequestMapping("/deleteC/{id}")
	@PostMapping
	public ResponseEntity<Contrat> deleteContratController(@PathVariable("id")  int id){
		
		contratService.deleteContrat(id);
		return new ResponseEntity<Contrat>(HttpStatus.OK);
	}

	
	@PostMapping("/acceptContrat/{rep}/{idContrat}")
	@ResponseBody
	public void acceptContratController(@PathVariable("rep") int rep,@PathVariable("idContrat") int idContrat) throws Exception{
		 contratService.acceptContrat(rep,idContrat);
	
			
	}
	
	
}
