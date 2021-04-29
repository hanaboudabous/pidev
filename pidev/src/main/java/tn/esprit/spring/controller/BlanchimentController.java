package tn.esprit.spring.controller;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import tn.esprit.spring.entity.Sinistre;
import tn.esprit.spring.repository.ISinistreRepos;

@Scope(value = "session")

@Controller(value = "blanchimentController") // Name of the bean in Spring IoC

public class BlanchimentController {

	
	
	@Autowired
	ISinistreRepos sinistre ;

	
	@GetMapping("/displayBarGraph")
	public String barGraph(Model model) {	
		Map<String, Integer> surveyMap = new LinkedHashMap<>();
		Map<Float, Float> yminmax = new LinkedHashMap<>();
		float ymin = 0 ;
		float ymax = 0 ;
		int mind = sinistre.mindate().get(0).getDateReglement().getYear()+1900;
		int maxd = sinistre.maxdate().get(0).getDateReglement().getYear()+1900;
		for (int i = mind ; i<=maxd ; i++){
		List<Sinistre> l = sinistre.findbyDateReglement(i);
		float somme = 0 ;
		
		for(Sinistre ll : l){
		somme = somme + ll.getCapitalRembourse() ;
		}	
		System.out.println("sommmmmm" + somme);
		surveyMap.put(String.valueOf(i), (int) somme);	

		if(ymin>=somme ){
			ymin = somme ;
		}
		if(ymax<= somme ){
			ymax = somme ;
		}
		yminmax.put(ymin,ymax);
		} 
		//hashmap l key n7ot fih fil ymin  w value n7ot fih fil ymax w na3di fil hashmap l chart .. genre nzidha b hethi model.addattribute
		
		
		model.addAttribute("yminmax", yminmax);
		model.addAttribute("surveyMap", surveyMap);
		
		return "barGraph";
	}
	

	
}
