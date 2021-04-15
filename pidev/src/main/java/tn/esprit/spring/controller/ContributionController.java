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

import tn.esprit.spring.entity.Contribution;
import tn.esprit.spring.entity.ReclamationSinistre;
import tn.esprit.spring.services.ContributionService;

@Scope(value = "session")

@Controller(value = "ContributionController") // Name of the bean in Spring IoC
@RequestMapping("/Contribution")
public class ContributionController {

	@Autowired
	ContributionService contributionService ;
	

	@RequestMapping("/addcon/{iduser}")
	@PostMapping
	public ResponseEntity<Contribution> ajoutContributionController(
			@RequestBody Contribution    dem ,@PathVariable("iduser")  int iduser		){
		

		contributionService.ajouterContribution(dem ,iduser);
		return new ResponseEntity<Contribution>(HttpStatus.OK);
	}

	@GetMapping("/liste")
	@ResponseBody
	public List<Contribution> afficherContributionController(){
		List<Contribution> list = contributionService.afficherContribution();
		return list;
			
	}
	
	@GetMapping("/tot")
	@ResponseBody
	public int afficherTotController(){
		int tot = contributionService.calculerSommeTickets(1);
		return tot;
			
	}
	
	
}
