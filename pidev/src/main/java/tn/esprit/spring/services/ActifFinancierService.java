package tn.esprit.spring.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entity.ActifFinancier;
import tn.esprit.spring.entity.Fond;
import tn.esprit.spring.entity.Prime;
import tn.esprit.spring.entity.Rendement;
import tn.esprit.spring.entity.User;
import tn.esprit.spring.repository.IActifFinancier;
import tn.esprit.spring.repository.IRendement;
import tn.esprit.spring.repository.IUserRepo;

@Service

public class ActifFinancierService {
	
	 @Autowired
     IActifFinancier actifFinancierRepo ;
     
 	@Autowired
 	IUserRepo userRepo;
 	
 	@Autowired
	IRendement rendement ;
 	
 	
     public void addActifFinancier( ActifFinancier actif , int idUser){
 		User u = userRepo.findById(idUser).get();		
 		Date currentUtilDate = new Date();
		int a = currentUtilDate.getYear() + actif.getMaturite() ;
		Date currentUtilDate2 = new Date();
		currentUtilDate2.setYear(a);	  		
		actif.setDate_debut(currentUtilDate);
		actif.setDate_fin(currentUtilDate2);
		actif.setEtat("En cours");
		actif.setAccepte_rachat(0);
		actif.setUserActif(u);
 		actifFinancierRepo.save(actif);
     }

     @Transactional
 	public List<ActifFinancier> listetouslesfond(){ /*** emp  : all  */
 		return actifFinancierRepo.findAll();
 	}

     public List<ActifFinancier> listemontant_actuelFond(Fond f){ /*** emp  : all par fond */
   	  List<ActifFinancier> l = new ArrayList<>();
   	  if(f == Fond.Fond_Euro){
   		  l =  actifFinancierRepo.findByNomFond(Fond.Fond_Euro);
   	  }
   	  else{
   		  l =  actifFinancierRepo.findByNomFond(Fond.Euro_Croissance);
   	  }
   	  return l;
     }

     public List<ActifFinancier> listemontant_actuelFondparUser(Fond f,int idUser){ /*** user  : all par fond */
   	  User u = userRepo.findById(idUser).get();
   	  List<ActifFinancier> l2 = new ArrayList<>();
   	  List<ActifFinancier> l = actifFinancierRepo.findByUserActif(u);
   	 for(ActifFinancier actif : l){
   	  if( actif.getNomFond() == f){
   		  l2.add(actif);
   	  }	  
   	 }
   	  return l2;
     }
   
		public  void montant_actuelFondEuro(int id){		
			double frais_gestion = 0.03 ;
			ActifFinancier a = actifFinancierRepo.findById(id).get();
			float primerelle = (float) (a.getMontant_investi() - (a.getMontant_investi()*frais_gestion)) ;
			float montant_add_pp = a.getMontant_investi() ;
			float rach ;
			double TMG = 0.015;
			Date d1 = new Date(125,10,10);   // sys date frr
			//Date d1 = new Date(); hethi es7i7a ..
			Date debut = a.getDate_debut() ;
			Date djanv = new Date();
			djanv.setMonth(0);
			djanv.setDate(1);
			djanv.setYear(debut.getYear());
			////////////////////////////////////////////////////////// na9smou l intervale de temps sur 2 : loul ndwrou 3lih l for w etheni ne5dmouh wa7dou
			float res  =  ((d1.getTime() -  a.getDate_debut().getTime())/ (1000 * 60 * 60 * 24)) ;
			int nombre_annee = (int) (res/365);
			float tit = res/365 -  nombre_annee ;
			int jours_restantes = (int) (tit * 365);
			long diff1 = (debut.getTime() - djanv.getTime()) ;
			int days1 = 365 - (int) (diff1 / (1000 * 60 * 60 * 24)) ;
			System.out.println("diffffffffffffffffff     " + nombre_annee + "       " + tit + "    "  + jours_restantes  + "     " + days1);
			
			float montant_investi = (float) (a.getMontant_investi() - a.getMontant_investi()*0.03) ;
			/////////////////////////////////////////////////////////////////////////////////////////////////////////////
			for (int i = 0 ; i < nombre_annee ; i++)  {					
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
				double t1 = r1.getRendementBTA()*((double) days11/(double)365);
				double t2 = r2.getRendementBTA()*((double)days2/(double)365);
				double m =( t1+t2 - TMG )* 0.85 ;	  
				if(a.getChoixPrime() == Prime.Prime_Periodique){
				montant_investi = montant_investi + a.getMontant_investi();
						if ( m < 0){ 	primerelle =  primerelle+(float) (primerelle*TMG) ;
											primerelle = (float) (primerelle - primerelle*frais_gestion) ;}
						else {				primerelle =	primerelle+(float) (primerelle*(m+TMG)) ;	
											primerelle = (float) (primerelle - primerelle*frais_gestion) ;}					
				System.out.println("calcule prime cumulé " + primerelle  + " yeaaaar  : " + i + "           " + m);	
				primerelle = montant_add_pp +  primerelle ;

				}
				else if(a.getChoixPrime() == Prime.Prime_Unique){
					if ( m < 0){ primerelle =  primerelle+(float) (primerelle*TMG) ;
					primerelle = (float) (primerelle - primerelle*frais_gestion) ;}
					else {	primerelle = 	primerelle+(float) (primerelle*(m+TMG)) ;	
					primerelle = (float) (primerelle - primerelle*frais_gestion) ;}					
			System.out.println("calcule prime cumulé " + primerelle  + " yeaaaar  : " + i + "           " + m);	}
				
			}
			System.out.println("ya miiiimtiiiii    " +  primerelle	);

			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
			float primefinale=0 ;
			if(jours_restantes < days1){
				rach = primerelle ; // rachat
				System.out.println("9eiiiinnnnnnn    " +  jours_restantes	);
				Rendement rend = rendement.findByAnnee(debut.getYear()+nombre_annee+1900);
				double q = ((float)jours_restantes/(float)365) ;
				System.out.println("qqqqqqqqqqq    " + q);
//				primefinale = primerelle+(float) (primerelle*rend.getRendementBTA()*(q)*0.85) ;
				primefinale = primerelle+(float) (primerelle*TMG*(q)*0.85) ;

				primerelle = (float) (primerelle - primerelle*frais_gestion) ;
				if(a.getChoixPrime() == Prime.Prime_Unique){
					double rachat =   primerelle - (rach - a.getMontant_investi())*0.8  ;
					a.setMontant_rachat((float) rachat);

				}
				else if(a.getChoixPrime() == Prime.Prime_Periodique){
					double rachat =   primerelle - (rach - montant_investi)*0.8  ;
					System.out.println("heeeeeeeeeeeeeeeyyyy    " + rach  + " noooooooooooo    "  + montant_investi);

					a.setMontant_rachat((float) rachat);
				}
				System.out.println((primerelle*rend.getRendement()*(jours_restantes/365)*0.85) + "amaaaaaaan1 " + primefinale+ "           "+rend.getRendement());
			}
			else {
				rach = primerelle ; // rachat
				int jours_restantes_2eme_annee = jours_restantes- days1 ;
				Rendement rend = rendement.findByAnnee(debut.getYear()+nombre_annee+1900);
				Rendement rend2 = rendement.findByAnnee(debut.getYear()+nombre_annee+1+1900);
				float q =(float) jours_restantes/(float)365 ;
				float q2 = (float)jours_restantes_2eme_annee/(float)365 ;

				primefinale = 	primerelle+(float) (primerelle*rend.getRendementBTA()*(q)*0.85) ;
				primefinale =	primefinale+(float) (primefinale*TMG*(q2)*0.85) ;
				primerelle = (float) (primerelle - primerelle*frais_gestion) ;
				
				if(a.getChoixPrime() == Prime.Prime_Unique){
					double rachat =   primerelle - (rach - a.getMontant_investi())*0.8  ;	
					a.setMontant_rachat((float) rachat);
				}
				else if(a.getChoixPrime() == Prime.Prime_Periodique){
					double rachat =   primerelle - (rach - montant_investi)*0.8  ;	
					a.setMontant_rachat((float) rachat);
				}
				System.out.println("amaaaaaaan2 " + primefinale);			
			}			
			System.out.println("calcule prime cumulé  haya " + primefinale  );	

			a.setMontant_cumule(primefinale);
			a.setDate_actuel(d1);
			actifFinancierRepo.save(a);	
		}
		
     public void FondEuro_to_EuroCroissance(int id){
   	  	montant_actuelFondEuro(id);
   	  	ActifFinancier a_croissance = new ActifFinancier() ;
			ActifFinancier a = actifFinancierRepo.findById(id).get();
			a.setEtat("Résilié");
			actifFinancierRepo.save(a);
			a_croissance.setUserActif(a.getUserActif());
			a_croissance.setMontant_investi(a.getMontant_cumule());
			a_croissance.setAccepte_rachat(0);
			a_croissance.setChoixPrime(Prime.Prime_Periodique);
			a_croissance.setDate_debut(new Date());
			a_croissance.setNomFond(Fond.Euro_Croissance);
			a_croissance.setMaturite(8);
			actifFinancierRepo.save(a_croissance); 
   	   
     }
     
 	public  void montant_actuelEuroCroissance(int id){		
		double frais_gestion = 0.03 ;
		ActifFinancier a = actifFinancierRepo.findById(id).get();
		float primerelle = (float) (a.getMontant_investi() - (a.getMontant_investi()*frais_gestion)) ;
		float rach ;
		float montant_add_pp = a.getMontant_investi() ;
		Date d1 = new Date(125,10,10);   // sys date frr
		//Date d1 = new Date();
		Date debut = a.getDate_debut() ;
		Date djanv = new Date();
		djanv.setMonth(0);
		djanv.setDate(1);
		djanv.setYear(debut.getYear());
		////////////////////////////////////////////////////////// na9smou l intervale de temps sur 2 : loul ndwrou 3lih l for w etheni ne5dmouh wa7dou
		float res  =  ((d1.getTime() -  a.getDate_debut().getTime())/ (1000 * 60 * 60 * 24)) ;
		int nombre_annee = (int) (res/365);
		float tit = res/365 -  nombre_annee ;
		int jours_restantes = (int) (tit * 365);
		long diff1 = (debut.getTime() - djanv.getTime()) ;
		int days1 = 365 - (int) (diff1 / (1000 * 60 * 60 * 24)) ;
		System.out.println("diffffffffffffffffff     " + nombre_annee + "       " + tit + "    "  + jours_restantes  + "     " + days1);
		
		float montant_investi = (float) (a.getMontant_investi() - a.getMontant_investi()*0.03) ;
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////
		for (int i = 0 ; i < nombre_annee ; i++)  {					
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
			double t1 = r1.getRendement()*((double) days11/(double)365);
			double t2 = r2.getRendement()*((double)days2/(double)365);
			double m =( t1+t2 )* 0.85 ;	
			if(a.getChoixPrime() == Prime.Prime_Periodique){
			montant_investi = montant_investi + a.getMontant_investi();
						primerelle =	primerelle+(float) (primerelle*m) ;	
						primerelle = (float) (primerelle - primerelle*frais_gestion) ;
			System.out.println("calcule prime cumulé " + primerelle  + " yeaaaar  : " + i + "           " + m);
			primerelle = montant_add_pp +  primerelle ;
}
			else if(a.getChoixPrime() == Prime.Prime_Unique){
				primerelle = 	primerelle+(float) (primerelle*m) ;	
				primerelle = (float) (primerelle - primerelle*frais_gestion) ;
		System.out.println("calcule prime cumulé " + primerelle  + " yeaaaar  : " + i + "           " + m);	}		
		}
		
		if (nombre_annee >= 8 ){
			if(a.getChoixPrime() == Prime.Prime_Periodique){
				if (primerelle <montant_investi){	primerelle= montant_investi ;}
			}
				}
				else if(a.getChoixPrime() == Prime.Prime_Unique){
					if (primerelle <a.getMontant_investi()){
						primerelle = a.getMontant_investi();
					}
		}
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
		float primefinale=0 ;
		if(jours_restantes < days1){
			rach = primerelle ; // rachat
			System.out.println("9eiiiinnnnnnn    " +  jours_restantes	);
			Rendement rend = rendement.findByAnnee(debut.getYear()+nombre_annee+1900);
			double q = ((float)jours_restantes/(float)365) ;
			System.out.println("qqqqqqqqqqq    " + q);
			primefinale = primerelle+(float) (primerelle*rend.getRendement()*(q)*0.85) ;
			primerelle = (float) (primerelle - primerelle*frais_gestion) ;
			if(a.getChoixPrime() == Prime.Prime_Unique){
				double rachat =   primerelle - (rach - a.getMontant_investi())*0.8  ;
				a.setMontant_rachat((float) rachat);

			}
			else if(a.getChoixPrime() == Prime.Prime_Periodique){
				double rachat =   primerelle - (rach - montant_investi)*0.8  ;
				a.setMontant_rachat((float) rachat);
			}
			System.out.println((primerelle*rend.getRendement()*(jours_restantes/365)*0.85) + "amaaaaaaan1 " + primefinale+ "           "+rend.getRendement());
		}
		else {
			rach = primerelle ; // rachat
			int jours_restantes_2eme_annee = jours_restantes- days1 ;
			Rendement rend = rendement.findByAnnee(debut.getYear()+nombre_annee+1900);
			Rendement rend2 = rendement.findByAnnee(debut.getYear()+nombre_annee+1+1900);
			float q =(float) jours_restantes/(float)365 ;
			float q2 = (float)jours_restantes_2eme_annee/(float)365 ;

			primefinale = 	primerelle+(float) (primerelle*rend.getRendement()*(q)*0.85) ;
			primefinale =	primefinale+(float) (primefinale*rend2.getRendement()*(q2)*0.85) ;
			primerelle = (float) (primerelle - primerelle*frais_gestion) ;
			
			if(a.getChoixPrime() == Prime.Prime_Unique){
				double rachat =   primerelle - (rach - a.getMontant_investi())*0.8  ;	
				a.setMontant_rachat((float) rachat);
			}
			else if(a.getChoixPrime() == Prime.Prime_Periodique){
				double rachat =   primerelle - (rach - montant_investi)*0.8  ;	
				a.setMontant_rachat((float) rachat);
			}
			System.out.println("amaaaaaaan2 " + primefinale);			
		}			
		System.out.println("calcule prime cumulé  haya " + primefinale  );	

		a.setMontant_cumule(primefinale);
		a.setDate_actuel(d1);
		actifFinancierRepo.save(a);	
	}
	
		
	
		
}