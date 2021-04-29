package tn.esprit.spring.controller;

import java.util.Date;

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

import tn.esprit.spring.entity.Contrat;
import tn.esprit.spring.entity.Data;
import tn.esprit.spring.services.DataService;

@Scope(value = "session")

@Controller(value = "DataController") // Name of the bean in Spring IoC
public class DataController {
	
	@Autowired
	DataService	dataService ;
	
//////////////// prime unique : capital differé ////////////
	@GetMapping("/Capital_vieC/{c}/{age}/{an}/{inte}/{sexe}")
	@ResponseBody
	public ResponseEntity<Data> dataCapitalControllerCapital(@PathVariable("c") float c,
			@PathVariable("age") int age,
			@PathVariable("an") int an,
			@PathVariable("inte") double inte,
			@PathVariable("sexe") String sexe){
				
		dataService.calculePUCapital_vieCapital(c, age, an, inte,sexe);
		return new ResponseEntity<Data>(HttpStatus.OK);
	}
	@GetMapping("/Capital_vieP/{p}/{age}/{an}/{inte}/{sexe}")
	@ResponseBody
	public ResponseEntity<Data> dataCapitalControllerPrime(@PathVariable("p") float p,
			@PathVariable("age") int age,
			@PathVariable("an") int an,
			@PathVariable("inte") double inte,
			@PathVariable("sexe") String sexe){
				
		dataService.calculePUCapital_viePrime(p, age, an, inte,sexe);
		return new ResponseEntity<Data>(HttpStatus.OK);
	}

	
	///////////////  prime unique : rente viagere /////////////////
	@GetMapping("/calcRenteC/{r}/{age}/{inte}/{sexe}")
	@ResponseBody
	public ResponseEntity<Data> calculePURenteillimteRenteController(@PathVariable("r") float r,
			@PathVariable("age") int age,	
			@PathVariable("inte") double inte,
			@PathVariable("sexe") String sexe){
				
		dataService.calculePURenteillimteRente(r, age,  inte,sexe);
		return new ResponseEntity<Data>(HttpStatus.OK);
	}
	@GetMapping("/calcRenteP/{p}/{age}/{inte}/{sexe}")
	@ResponseBody
	public ResponseEntity<Data> calculePURenteillimtePrimeController(@PathVariable("p") float p,
			@PathVariable("age") int age,	
			@PathVariable("inte") double inte,
			@PathVariable("sexe") String sexe){
				
		dataService.calculePURenteillimtePrime(p, age,  inte,sexe);
		return new ResponseEntity<Data>(HttpStatus.OK);
	}
	
	///////////////  prime unique : vie enitere /////////////////

	@GetMapping("/calcvieEntiereC/{c}/{age}/{inte}/{sexe}")
	@ResponseBody
	public ResponseEntity<Data> calculePUVieEntiereCapital(@PathVariable("c") float c, 
			@PathVariable("age") int age,	
			@PathVariable("inte") double inte,
			@PathVariable("sexe") String sexe){
				
		dataService.calculePUVieEntiereCapital(c, age,  inte , sexe);
		return new ResponseEntity<Data>(HttpStatus.OK);
	}
	@GetMapping("/calcvieEntiereP/{p}/{age}/{inte}/{sexe}")
	@ResponseBody
	public ResponseEntity<Data> calculePUVieEntierePrime(@PathVariable("p") float p, 
			@PathVariable("age") int age,	
			@PathVariable("inte") double inte,
			@PathVariable("sexe") String sexe){
				
		dataService.calculePUVieEntierePrime(p, age,  inte , sexe);
		return new ResponseEntity<Data>(HttpStatus.OK);
	}	
	
////////////////prime Periodique : capital differé ////////////
	
	@GetMapping("/calccapitaldiffere/{capital}/{p}/{age}/{annee}/{inte}/{sexe}")
	@ResponseBody
	public ResponseEntity<Data> calculePPCapital_vie(@PathVariable("capital") float capital,
			@PathVariable("p") float p, 
			@PathVariable("age") int age,	
			@PathVariable("annee") int annee,	

			@PathVariable("inte") double inte
		,@PathVariable("sexe") String sexe
			){
				
		dataService.calculePPCapital_vie( capital,p, age, annee,  inte , sexe );
		return new ResponseEntity<Data>(HttpStatus.OK);
	}

	@GetMapping("/calculePPRente/{p}/{rente}/{age}/{annee}/{inte}/{sexe}")
	@ResponseBody
	public ResponseEntity<Data> calculePPRente(
			@PathVariable("p") float p, 
			@PathVariable("rente") float rente,
			@PathVariable("age") int age,	
			@PathVariable("annee") int annee,	

			@PathVariable("inte") double inte
		,@PathVariable("sexe") String sexe
			){
				
		dataService.calculePPRente(p,rente , age, annee,  inte , sexe );
		return new ResponseEntity<Data>(HttpStatus.OK);
	}
	@GetMapping("/calculePPVieEntiere/{p}/{capital}/{age}/{annee}/{inte}/{sexe}")
	@ResponseBody
	public ResponseEntity<Data> calculePPVieEntiere(
			@PathVariable("p") float p, 
			@PathVariable("capital") float capital,
			@PathVariable("age") int age,	
			@PathVariable("annee") int annee,	

			@PathVariable("inte") double inte
		,@PathVariable("sexe") String sexe
			){
				
		dataService.calculePPVieEntiere(p,capital , age, annee,  inte , sexe );
		return new ResponseEntity<Data>(HttpStatus.OK);
	}

}
