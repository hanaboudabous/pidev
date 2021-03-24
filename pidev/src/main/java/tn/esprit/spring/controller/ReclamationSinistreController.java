package tn.esprit.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import tn.esprit.spring.entity.ReclamationSinistre;
import tn.esprit.spring.service.ReclamationSinistreService;






@Controller(value = "reclamationsinistreController") // Name of the bean in Spring IoC
public class ReclamationSinistreController {
	
	@Autowired
	ReclamationSinistreService serv ;
	
	@GetMapping("/reclamationsinistres")
	@ResponseBody
	public List<ReclamationSinistre> getReclamationSinsitres(){
		List<ReclamationSinistre> list = serv.ReclamationSinistreList();
		 return list;
		
	}
	
	

	@RequestMapping("/addrec")
	@PostMapping
	public ResponseEntity<ReclamationSinistre> insertReclamationSinsitre(@RequestBody ReclamationSinistre rec){
		 serv.addReclamationSinistre(rec,1); 
		  return new ResponseEntity<ReclamationSinistre>(HttpStatus.OK);
	}
	
	
	
	    @DeleteMapping("/removerec/{rec-id}")
	    @PostMapping
		public void removeReclamationSinistre(@PathVariable("rec-id") int Id){
		serv.deleteReclamationSinistre(Id);
	}

	    
	   
}
