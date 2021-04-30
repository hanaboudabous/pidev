package tn.esprit.spring.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import tn.esprit.spring.services.ReclamationSinistreService;
import tn.esprit.spring.services.UserService;
import tn.esprit.spring.entity.ReclamationSinistre;


@Scope(value = "session")

@Controller(value = "ReclamationSinistreController") // Name of the bean in Spring IoC
@RequestMapping("/ReclamationSinistre")

public class ReclamationSinistreController {
	

	@Autowired
	ReclamationSinistreService reclamationSinistreService ;
	@Autowired
	UserService userService;
	
	@RequestMapping("/add/{id}")
	@PostMapping
	public ResponseEntity<ReclamationSinistre> ajoutReclamationSinistreController(
			@RequestBody ReclamationSinistre    dem 	,@PathVariable("id")  int id,Authentication auth		){
		
		 	
		reclamationSinistreService.AjoutReclamationSelonType(dem ,id);
		return new ResponseEntity<ReclamationSinistre>(HttpStatus.OK);
	}
	
	
	
	
	@GetMapping("/liste")
	@ResponseBody
	public List<ReclamationSinistre> afficheReclamationSinistreController(){
		List<ReclamationSinistre> list = reclamationSinistreService.afficheReclamationSinistre();
		return list;
			
	}
	

	@RequestMapping("/deleterec/{id}")
	@PostMapping
	public ResponseEntity<ReclamationSinistre> deleteReclamationSinistreController(@PathVariable("id")  int id){
		
		reclamationSinistreService.deleteReclamationSinistre(id);
		return new ResponseEntity<ReclamationSinistre>(HttpStatus.OK);
	}
	
	@GetMapping("/liste/nonTraite")
	@ResponseBody
	public List<ReclamationSinistre> afficheRecNonTraiteController(){
		List<ReclamationSinistre> list = reclamationSinistreService.afficheRecSinNonTraite();
		return list;		
	}
	@GetMapping("/liste/traite")
	@ResponseBody
	public List<ReclamationSinistre> afficheRecTraiteController(){
		List<ReclamationSinistre> list = reclamationSinistreService.afficheRecSinTraite();
		return list;			
	}
	
	
	@GetMapping("/listee/nonTraite")
	@ResponseBody
	public List<ReclamationSinistre> afficheRecNonTraiteUserController(Authentication auth){
		List<ReclamationSinistre> list = reclamationSinistreService.afficheRecSinNonTraiteUser( userService.getcode(auth.getName()).getUser_ID());
		return list;		
	}
	
	@GetMapping("/listee/traite")
	@ResponseBody
	public List<ReclamationSinistre> afficheRecTraiteUserController(Authentication auth){
		List<ReclamationSinistre> list = reclamationSinistreService.afficheRecSinTraiteUser(userService.getcode(auth.getName()).getUser_ID());
		return list;			
	}

}
