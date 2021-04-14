package tn.esprit.spring.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import tn.esprit.spring.entity.Reinsurance_contract;
import tn.esprit.spring.services.IReinsuranceCService;


@Controller
public class ReinsuranceContractController {
	@Autowired
	IReinsuranceCService reinService;
	
	@GetMapping("/rein")
	@ResponseBody
	public List<Reinsurance_contract> getReins() {
	List<Reinsurance_contract> list = reinService.retrieveRe_cs();
	return list;
	}
	
	@GetMapping("/retrieve-rein/{user-id}")
	@ResponseBody
	public Reinsurance_contract retrieveRein(@PathVariable("user-id") String Id) {
	return reinService.retrieveRe_s(Id);
	}
		
	@GetMapping("/add-rein")
	@ResponseBody
	public String addRein(){
	return reinService.addReinCont();
	}
	
	@DeleteMapping("/remove-rein/{user-id}")
	@ResponseBody
	public void removeUser(@PathVariable("user-id") String userId) {
	reinService.deleteRe_s(userId);
	}
	
	@PutMapping("/modify-rein")
	@ResponseBody
	public Reinsurance_contract modifyUser(@RequestBody Reinsurance_contract u) {
	return reinService.updateRe_s(u);
	}
}
