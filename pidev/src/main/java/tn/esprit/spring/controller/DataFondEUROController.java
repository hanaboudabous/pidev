package tn.esprit.spring.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import tn.esprit.spring.entity.ActifFinancier;
import tn.esprit.spring.entity.DataFondEURO;
import tn.esprit.spring.entity.Rendement;
import tn.esprit.spring.entity.Simulateur;
import tn.esprit.spring.services.DataFondEUROService;

@Scope(value = "session")

@Controller(value = "dataFondEUROController") // Name of the bean in Spring IoC

public class DataFondEUROController {
	
	@Autowired
	DataFondEUROService dataFondEURO ;
	

	@GetMapping("/listePortefeuilleBTA")
	@ResponseBody
	public List<DataFondEURO>listeBTA(){
			return dataFondEURO.listeBTA();
	}
	@GetMapping("/listePortefeuille")
	@ResponseBody
	public List<DataFondEURO>listeAll(){
		return 	dataFondEURO.listeAll();
		
	}
	@GetMapping("/listeRendementPortefeuille")
	@ResponseBody
	public List<Rendement>listeRendementPortefeuille(){
		return 	dataFondEURO.listeRendementPortefeuille();
		
	}
	

	
	@PostMapping("/addPorteufeuille")
	@ResponseBody
	public ResponseEntity<DataFondEURO> ajoutBondController(@RequestBody DataFondEURO  a 	){
		
		dataFondEURO.addDataEuro(a);
		return new ResponseEntity<DataFondEURO>(HttpStatus.OK);
	}
	@GetMapping("/DeletePorteufeuille/{id}")
	@ResponseBody
	public ResponseEntity<DataFondEURO> DeleteBondController( @PathVariable("id") int    id	){
		
		dataFondEURO.deleteFondEURO(id);
		return new ResponseEntity<DataFondEURO>(HttpStatus.OK);
	}
	
	
	
	@GetMapping("/rendement2")
	@ResponseBody
	public ResponseEntity<DataFondEURO>calculeRendementBTA2(){
		dataFondEURO.calculerendementTotal();
		return new ResponseEntity<DataFondEURO>(HttpStatus.OK);
			
	}
	
	@GetMapping("/l")
	@ResponseBody
	public ResponseEntity<DataFondEURO>l(){
		dataFondEURO.liste();
		return new ResponseEntity<DataFondEURO>(HttpStatus.OK);
			
	}
	

	@GetMapping("/calculeprofitUnique")
	@ResponseBody
	public ResponseEntity<DataFondEURO>calculeprofitUnique(){
		dataFondEURO.calculeprofitUnique();
		return new ResponseEntity<DataFondEURO>(HttpStatus.OK);
			
	}
	@GetMapping("/calculeprofitPeriodique")
	@ResponseBody
	public ResponseEntity<DataFondEURO>calculeprofitPeriodique(){
		dataFondEURO.calculeprofitPeriodique();
		return new ResponseEntity<DataFondEURO>(HttpStatus.OK);
			
	}

	
	/*@GetMapping("/date2")
	@ResponseBody
	public ResponseEntity<DataFondEURO>montant_actuelProfitUnique(){
		dataFondEURO.montant_actuelProfitUnique();
		return new ResponseEntity<DataFondEURO>(HttpStatus.OK);			
	}*/
	

}
