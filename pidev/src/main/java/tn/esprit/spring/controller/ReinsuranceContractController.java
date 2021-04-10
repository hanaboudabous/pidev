package tn.esprit.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import tn.esprit.spring.services.IReinsuranceCService;


@Controller
public class ReinsuranceContractController {
	@Autowired
	IReinsuranceCService reinService;
	
	
}
