package tn.esprit.spring.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entity.Fond;
import tn.esprit.spring.entity.Prime;
import tn.esprit.spring.entity.Rendement;
import tn.esprit.spring.entity.Simulateur;
import tn.esprit.spring.repository.IActifFinancier;
import tn.esprit.spring.repository.IRendement;

@Service
public class SimulateurService {
	
    @Autowired
    IActifFinancier actifFinancierRepo ;
    
	
	@Autowired
	IRendement rendement ;

	public  List<Simulateur>  montant_actuelFondEuro( float montant , Prime prime ,Fond fond , int maturite){		
		List<Simulateur> l = new ArrayList<>();
		double TMG = 0.015;

		double frais_gestion = 0.03 ;
		float primerelle = (float) (montant- (montant*frais_gestion)) ;
		float primerelle2 = (float) (montant- (montant*frais_gestion)) ;
		float rach ;
		//Date d1 = new Date();   // sys date frr
		Date debut = new Date() ;
		Date djanv = new Date();
		djanv.setMonth(0);
		djanv.setDate(1);
		djanv.setYear(debut.getYear());
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////
		for (int i = 0 ; i < maturite ; i++)  {					
			long diff11 = (debut.getTime() - djanv.getTime()) ;
			int days11 = 365 - (int) (diff11 / (1000 * 60 * 60 * 24)) ; // n9oulou 285
			int days2 = 365 - days11 ; // hetha n9oul l be9i mta3 285 men 365  = 80 		
			System.out.println("Difference between  " + djanv + " and "+ debut+" is " + days11 + " days."  + days2 + " days.");
			int annee1 =  debut.getYear()+ i+ 1900 ;
			int annee2 = debut.getYear()+i+ 1901 ;
			System.out.println("hellllllllllllllll    " + annee1);
			System.out.println("hellllllllllllllll    " + i);	
			Rendement r1 = rendement.findByAnnee(annee1);
			Rendement r2 = rendement.findByAnnee(annee2);
			System.out.println("  wwwooo  " + (double)days11/(double)365);	
			System.out.println("  wwwooo2  " +  r1.getRendement());
			double t1 = 0 ;
			double t2 = 0 ;
				 t1 = r1.getRendementBTA()*((double) days11/(double)365);
				 t2 = r2.getRendementBTA()*((double)days2/(double)365);				
			double m =( t1+t2 - TMG )* 0.85 ;	
			float rachat = 0 ;
			if(prime == Prime.Prime_Periodique){
					if ( m < 0 ){ 
						System.out.println(" hani hneeeeeeeee");
							rach = primerelle ; // rachat
							primerelle =   primerelle+(float) (primerelle*TMG) ;
							primerelle = (float) (primerelle - primerelle*frais_gestion) ;
							rachat =   (float) (primerelle - (primerelle- rach)*0.8)  ;}
					else {	
							System.out.println(" winekkkkkkkkkkkkkkkkkkk" + m );
							rach = primerelle ;
							primerelle =	primerelle+(float) (primerelle*(m+TMG)) ;	
							primerelle = (float) (primerelle - primerelle*frais_gestion) ;
							System.out.println("haya bara !! " + rach + " haya 3aaaddd    " + primerelle);
							rachat =   (float) (primerelle - (primerelle- rach)*0.8)  ;
							System.out.println("haya bara !! " + rachat + " haya 3aaaddd    " + primerelle);}					
			System.out.println("calcule prime cumulé " + primerelle  + " yeaaaar  : " + i + "           " + m);	
			Simulateur s = new Simulateur();
			s.setAnnee(annee1);
			s.setCapital((float) primerelle);
			s.setRachat(rachat);
			l.add(s) ;
			primerelle = montant +  primerelle ;
			}
			else if(prime == Prime.Prime_Unique){
				if ( m < 0){
							rach = primerelle ; // rachat
							primerelle =  primerelle+(float) (primerelle*TMG) ;
							rachat =   (float) (primerelle - (primerelle- rach)*0.8)  ;}
				else {		rach = primerelle ; // rachat
							primerelle = 	primerelle+(float) (primerelle*(m+TMG)) ;	
							rachat =   (float) (primerelle - (primerelle- rach)*0.8)  ;		
								
				Simulateur s = new Simulateur();
				s.setAnnee(annee1);
				s.setCapital((float) primerelle);
				s.setRachat(rachat);
				l.add(s) ;
				}
		System.out.println("calcule prime cumulé " + primerelle  + " yeaaaar  : " + i + "           " + m);	}
				}			
		return l ;

		
	}
	


	public  List<Simulateur>  montant_actuelEuroCroissance( float montant , Prime prime ,Fond fond , int maturite){		
		List<Simulateur> l = new ArrayList<>();
		double frais_gestion = 0.03 ;
		
		float primerelle = (float) (montant- (montant*frais_gestion)) ;
		float rach ;
		//Date d1 = new Date();   // sys date frr
		Date debut = new Date() ;
		Date djanv = new Date();
		djanv.setMonth(0);
		djanv.setDate(1);
		djanv.setYear(debut.getYear());
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////
		for (int i = 0 ; i < maturite ; i++)  {					
			long diff11 = (debut.getTime() - djanv.getTime()) ;
			int days11 = 365 - (int) (diff11 / (1000 * 60 * 60 * 24)) ; // n9oulou 285
			int days2 = 365 - days11 ; // hetha n9oul l be9i mta3 285 men 365  = 80 		
			System.out.println("Difference between  " + djanv + " and "+ debut+" is " + days11 + " days."  + days2 + " days.");
			int annee1 =  debut.getYear()+ i+ 1900 ;
			int annee2 = debut.getYear()+i+ 1901 ;
			System.out.println("hellllllllllllllll    " + annee1);
			System.out.println("hellllllllllllllll    " + i);	
			Rendement r1 = rendement.findByAnnee(annee1);
			Rendement r2 = rendement.findByAnnee(annee2);
			System.out.println("  wwwooo  " + (double)days11/(double)365);	
			System.out.println("  wwwooo2  " +  r1.getRendement());
			double t1 = 0 ;
			double t2 = 0 ;
				 t1 = r1.getRendement()*((double) days11/(double)365);
				 t2 = r2.getRendement()*((double)days2/(double)365);				
			double m =( t1+t2 )* 0.85 ;	
			float rachat = 0 ;
			if(prime == Prime.Prime_Periodique){
							System.out.println(" winekkkkkkkkkkkkkkkkkkk" + m );
							rach = primerelle ;
							primerelle =	primerelle+(float) (primerelle*m) ;	
							primerelle = (float) (primerelle - primerelle*frais_gestion) ;
							System.out.println("haya bara !! " + rach + " haya 3aaaddd    " + primerelle);
							rachat =   (float) (primerelle - (primerelle- rach)*0.8)  ;
							System.out.println("haya bara !! " + rachat + " haya 3aaaddd    " + primerelle);				
			System.out.println("calcule prime cumulé " + primerelle  + " yeaaaar  : " + i + "           " + m);	
			Simulateur s = new Simulateur();
			s.setAnnee(annee1);
			s.setCapital((float) primerelle);
			s.setRachat(rachat);
			l.add(s) ;
			primerelle = montant +  primerelle ;
			}
			else if(prime == Prime.Prime_Unique){
						rach = primerelle ; // rachat
							primerelle = 	primerelle+(float) (primerelle*m) ;	
							rachat =   (float) (primerelle - (primerelle- rach)*0.8)  ;									
				Simulateur s = new Simulateur();
				s.setAnnee(annee1);
				s.setCapital((float) primerelle);
				s.setRachat(rachat);
				l.add(s) ;
				
		System.out.println("calcule prime cumulé " + primerelle  + " yeaaaar  : " + i + "           " + m);	}
				}			
		return l ;

		
	}
	

}
/*


public List<Simulateur>  calculeprofitUniqueEUROcroissance(){
	float capital_rendu ;
	float primeUnique = 1000 ;
	int dureeContrat = 15 ;
	double frais_gestion = 0.03 ;
	double primerelle  =primeUnique - primeUnique*frais_gestion;		
	int year = 2021 ; 			
	double var_stable = 0 ;
	List<Simulateur> l = new ArrayList<>();
	Date d = new Date();
	System.out.println("date    :   " +( d.getYear()+1900));
	for (int i = 0 ; i < dureeContrat ; i++){
		double t =0 ;
		if ( rendement.findByAnnee(year+i) != null){
			Rendement r = rendement.findByAnnee(year+i);
			 t = r.getRendement() ;
			 System.out.println("----------------  voir t : " + t);

		}
		else {
			 t = var_stable ;
			 System.out.println("----------------  voir t : " + t);
		}				
			double m = t*0.85 ;			
			primerelle = primerelle +	(float) (primerelle*m) ;
			primerelle = primerelle - primerelle*frais_gestion;
			System.out.println("positive  : "+primerelle);
			var_stable = t ;
			
		Simulateur s = new Simulateur();
		s.setAnnee(year+i);
		s.setCapital((float) primerelle);
		l.add(s) ;
		
		
	}	
	

	return l ;
}


public List<Simulateur>  calculeprofitPeriodiqueEUROcroissance(){
	float capital_rendu ;
	float primeUnique = 1000 ;
	int dureeContrat = 15 ;
	double frais_gestion = 0.03 ;
	double primerelle  =primeUnique - primeUnique*frais_gestion;		
	int year = 2021 ; 			
	double var_stable = 0 ;
	List<Simulateur> l = new ArrayList<>();
	Date d = new Date();
	System.out.println("date    :   " +( d.getYear()+1900));
	for (int i = 0 ; i < dureeContrat ; i++){
		double t =0 ;
		if ( rendement.findByAnnee(year+i) != null){
			Rendement r = rendement.findByAnnee(year+i);
			 t = r.getRendement() ;
			 System.out.println("----------------  voir t : " + t);

		}
		else {
			 t = var_stable ;
			 System.out.println("----------------  voir t : " + t);
		}				
			double m = t*0.85 ;			
			primerelle =  primerelle +	(float) (primerelle*m) ;
			primerelle = primerelle - primerelle*frais_gestion;
			System.out.println("positive  : "+primerelle);
			var_stable = t ;
			
		Simulateur s = new Simulateur();
		s.setAnnee(year+i);
		s.setCapital((float) primerelle);
		l.add(s) ;
	}	
	return l ;
}

*/