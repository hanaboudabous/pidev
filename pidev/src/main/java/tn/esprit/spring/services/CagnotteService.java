package tn.esprit.spring.services;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entity.Cagnotte;
import tn.esprit.spring.entity.Contrat;
import tn.esprit.spring.entity.DemandeContrat;
import tn.esprit.spring.entity.Prime;
import tn.esprit.spring.entity.Sinistre;
import tn.esprit.spring.repository.ICagnotteRepo;
import tn.esprit.spring.repository.IContratRepo;

@Service
public class CagnotteService {
	@Autowired
	IContratRepo contrat ;

	@Autowired
	ICagnotteRepo cag ;
	
	public static Date addYear(Date date, int i) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, i);
        return cal.getTime();
    }
	
	/*AJOUT D'UN CONTRAT DANS LA CAGNOTTE*/
	public void entrerCagnotte(int id){
        Cagnotte ca= new Cagnotte();
		Contrat c = contrat.findById(id).get();
		Date currentDate = new Date();
		int year=currentDate.getYear();
		
		Date dateDebut= c.getDate_debut();
		dateDebut.setYear(year);
		
		
		Date  dateFin = new Date();
		dateFin=(Date) dateDebut.clone();
		dateFin.setYear(year+1);
	
		
		
		if((c.getDemandeContrat().getChoixPrime().equals(Prime.Prime_Periodique))&&(c.getEtat().equalsIgnoreCase("en cours")))
		{	ca.setDateEcheance(dateFin);
			ca.setDateAjout(dateDebut);
			ca.setMontantactuel(0);
			ca.setMontantfinal(c.getPrimeCommerciale());
			ca.setEtat("en cours");
			ca.setContrat(c);
			cag.save(ca);
			
		}
		
		

	}
	
	
/*Changement d'état D'un CONTRAT DE LA CAGNOTTE: de en cours --> suspendu*/
	public void sortirCagnotte(){
		List<Cagnotte> list=cag.findAll();
		for(int i=0;i<list.size();i++){
			Cagnotte cagnotte=list.get(i);
			Contrat c=cagnotte.getContrat();
			Date currentDate = new Date();
			Date dateFin=cagnotte.getDateEcheance();
			int result = currentDate.compareTo(dateFin);
		
		    
			if((cagnotte.getMontantactuel()<cagnotte.getMontantfinal())&&result>0){ // echeance fetet
				cagnotte.setEtat("suspendu"); //La somme n'a pas pu être récoltée à temps, le contrat sera résilié
				System.out.println(cagnotte.getEtat());
				c.setEtat("résilié");
				cag.save(cagnotte);
				contrat.save(c);
				
			}
		}
		
		

	}
	/*AFFICHER LES CONTRATS courants de la cagnotte*/
	
	@Transactional
	public List<Cagnotte> afficheCagnotte(){
		sortirCagnotte();
		return cag.findAll();
	}

	/* somme  montantfinal de tous les contrats courants de la cagnotte*/
	public float TotalMontantfinal(){
		List<Cagnotte> list= afficheCagnotte();
		float totapayer=0;
		for(int i=0;i<list.size();i++){
			if(list.get(i).getEtat().equalsIgnoreCase("en cours"))
			totapayer+=list.get(i).getMontantfinal();		
			
		}
		return totapayer;
	}
	/* somme  montantactuel de tous les contrats courants de la cagnotte*/
	public float TotalMontantactuel(){
		List<Cagnotte> list= afficheCagnotte();
		float totactuel=0;
		for(int i=0;i<list.size();i++){
			if(list.get(i).getEtat().equalsIgnoreCase("en cours"))
				totactuel+=list.get(i).getMontantactuel();
		
		}
		return totactuel;
	}
	
	
/*Affichage cagnottre pour utilisateurs*/	
	public String affichageCagnotteAuUsers(){
		float totapayer=TotalMontantfinal();
		float totactuel=TotalMontantactuel();
		String x="Actuellement:"+totactuel+"/"+totapayer;
		return x;
	}
	

		
	public List<Cagnotte> afficheCagnotteuser(int iduser){
		
		return cag.Cagnottesduuser(iduser);
	}
		
	
	



}

