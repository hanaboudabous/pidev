package tn.esprit.spring.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import tn.esprit.spring.entity.Reinsurance_contract;
import tn.esprit.spring.entity.Role_User;
import tn.esprit.spring.entity.Sinistre;
import tn.esprit.spring.services.IReinsuranceCService;
import tn.esprit.spring.services.MyUserDetailsService;
import tn.esprit.spring.services.UserService;


@Controller
public class ReinsuranceContractController {
	@Autowired
	IReinsuranceCService reinService;
	@Autowired
	UserService userService;

	
	@GetMapping("/rein")
	@ResponseBody
	public List<Reinsurance_contract> getReins() throws Exception {
	List<Reinsurance_contract> list = reinService.retrieveRe_cs();
	return list;
	}
	
	@GetMapping("/retrieve-rein/{user-id}")
	@ResponseBody
	public Reinsurance_contract retrieveRein(@PathVariable("user-id") String Id) {
	return reinService.retrieveRe_s(Id);
	}
		
	@GetMapping("/sin-rein")
	@ResponseBody
	public String ReinSin(Authentication auth){
		if(userService.getcode(auth.getName()).getRole_User().equals(Role_User.Reinsurance_Agent))
		{	
	return reinService.ReinSinistre(); }
		else {
			return "this user don't have the access !!!";
		}
	}
	
	@GetMapping("/add-rein")
	@ResponseBody
	public String addRein(Authentication auth){
		if(userService.getcode(auth.getName()).getRole_User().equals(Role_User.Reinsurance_Agent))
		{	
	return reinService.addReinCont(userService.getcode(auth.getName())); }
		else {
			return "this user don't have the access !!!";
		}
	}
	@GetMapping("/reff-rein")
	@ResponseBody
	public void reffRein(){
	reinService.ReffReinCont();
	}
	
	@DeleteMapping("/remove-rein/{user-id}")
	@ResponseBody
	public ResponseEntity<Reinsurance_contract> removeUser(Authentication auth,@PathVariable("user-id") String userId) {
		if(userService.getcode(auth.getName()).getRole_User().equals(Role_User.Reinsurance_Agent))
		{
	reinService.deleteRe_s(userId);
	return new ResponseEntity<Reinsurance_contract>(HttpStatus.OK);
		}
		else {
			return new ResponseEntity<Reinsurance_contract>(HttpStatus.UNAUTHORIZED);
		}
	}
	
	@PutMapping("/modify-rein")
	@ResponseBody
	public Reinsurance_contract modifyUser(@RequestBody Reinsurance_contract u) {
	return reinService.updateRe_s(u);
	}
}
