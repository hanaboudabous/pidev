package tn.esprit.spring.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import tn.esprit.spring.services.SinistreService;
import tn.esprit.spring.entity.Sinistre;

@Scope(value = "session")

@Controller(value = "SinistreController") // Name of the bean in Spring IoC
@RequestMapping("/Sinistre")
public class SinistreController {


	@Autowired
	SinistreService sinistreService ;
	

	@RequestMapping("/add/{idrec}")
	@PostMapping
	public ResponseEntity<Sinistre> ajoutReclamationSinistreController(
			@PathVariable("idrec")  int id		){
		

		sinistreService.AjoutSinistreselonReclamation(id);
		return new ResponseEntity<Sinistre>(HttpStatus.OK);  
	}
	
	@GetMapping("/liste")
	@ResponseBody
	public List<Sinistre> afficheSinistreController(){
		List<Sinistre> list = sinistreService.afficheSinistre();
		return list;
			
	}

	@RequestMapping("/deletesin/{id}")
	@PostMapping
	public ResponseEntity<Sinistre> deleteSinistreController(@PathVariable("id")  int id){
		
		sinistreService.deleteSinistre(id);
		return new ResponseEntity<Sinistre>(HttpStatus.OK);
	}
	
	
	@RequestMapping("/psap")
	@ResponseBody
	public float psap(){
		float x=sinistreService.calculPSAP();
		return x;

	}
	
	
	
	
}
