package tn.esprit.spring.controller;


import java.util.Map;
import java.util.LinkedHashMap;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import tn.esprit.spring.repository.ISinistreRepos;

@Scope(value = "session")

@Controller(value = "SinistreController") // Name of the bean in Spring IoC
@RequestMapping("/Sinistre")
public class SinistreController {


	@Autowired
	SinistreService sinistreService ;
	
	@Autowired
	ISinistreRepos sinistre ;
	

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
