package tn.esprit.spring.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.spring.entity.DemandeContrat;
import tn.esprit.spring.entity.Type_sante;
import tn.esprit.spring.services.Type_santeService;



@Scope(value = "session")

@RestController(value = "type_santeController") // Name of the bean in Spring IoC

public class Type_santeController {
	
	
	@Autowired
	Type_santeService type_santeService ;



	
	/////// http://localhost:8082/addTypesante/10
	@PostMapping("/addTypesante/{iddemande}")
	@ResponseBody
	public ResponseEntity<DemandeContrat> ajoutDemandeContratSanteController(@RequestBody Type_sante  t ,@PathVariable("iddemande")  int iddemande	){
		type_santeService.addType(iddemande, t);              
		return new ResponseEntity<DemandeContrat>(HttpStatus.OK);
	}
	///// http://localhost:8082/addTypesante/69
	@GetMapping("/affichetypesante/{iddemande}")
	@ResponseBody
	public Type_sante affichetypesanteparDemandeController(@PathVariable("iddemande")  int iddemande	){
		
		return  type_santeService.affichetypesanteparDemande( iddemande);
	}
	
	

}
