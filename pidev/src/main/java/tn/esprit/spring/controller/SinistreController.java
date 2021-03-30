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

import tn.eprit.spring.services.SinistreService;
import tn.esprit.spring.entity.ReclamationSinistre;
import tn.esprit.spring.entity.Sinistre;

@Controller(value = "sinistreController")
public class SinistreController {

	@Autowired
	SinistreService serv ;

	@GetMapping("/sinistres")
	@ResponseBody
	public List<Sinistre> getSinsitres(){
		List<Sinistre> list = serv.SinistreList();
		return list;

	}



	@RequestMapping("/addsin")
	@ResponseBody
	public ResponseEntity<Sinistre> insertSinsitre(@RequestBody Sinistre sin){
		serv.addSinistre(sin,1); 
		return new ResponseEntity<Sinistre>(HttpStatus.OK);
	}



	@DeleteMapping("/removesin/{sin-id}")
	@ResponseBody
	public void removeSinistre(@PathVariable("sin-id") int Id){
		serv.deleteSinistre(Id);
	}

	@PutMapping("/updatesin")
	@ResponseBody
	public ResponseEntity<Sinistre> updateSinsitre(@RequestBody Sinistre sin){
		serv.updateSinistre(sin);
		return new ResponseEntity<Sinistre>(HttpStatus.OK);
	}


}
