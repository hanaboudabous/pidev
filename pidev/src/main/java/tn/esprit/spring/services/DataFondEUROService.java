package tn.esprit.spring.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entity.DataFondEURO;
import tn.esprit.spring.entity.Rendement;
import tn.esprit.spring.repository.IActifFinancier;
import tn.esprit.spring.repository.IDataFondEURORepo;
import tn.esprit.spring.repository.IRendement;

@Service
public class DataFondEUROService {
	
	@Autowired
	IDataFondEURORepo dataEuro ;
	@Autowired
	IRendement rendement ;
	

    @Autowired
    IActifFinancier actifFinancierRepo ;
	
	public void addDataEuro(DataFondEURO d) {
		float prixAchat = d.getMontantSouscription()*d.getRemunerationPrincipale();
		float tauxrendement =( d.getTauxCoupon()*d.getMontantSouscription())/prixAchat;			
		int echeanceYear = d.getDateSouscription().getYear() + d.getMaturite();
		int echeancedd = d.getDateSouscription().getDay() ;
		int echeancemm = d.getDateSouscription().getMonth();
		d.setDateEcheance(new Date(echeanceYear,echeancemm,echeancedd));
		d.setTauxRendement(tauxrendement);	
		d.setPrixAchat(prixAchat);
		dataEuro.save(d);	
		calculerendementTotal();
	}
	
	public List<DataFondEURO> listeBTA(){	
		return dataEuro.findByTypBonde("BTA") ;
	}
	public List<DataFondEURO> listeAll(){	
		return dataEuro.findAll();
	}
	
	public void deleteFondEURO( int id){
		dataEuro.deleteById(id);
		calculerendementTotal();
			}
	
	
	public List<Rendement> listeRendementPortefeuille(){	
		return rendement.findAll();
	}
	
	
		/// hne en general  ne7seb f simulateur mouch fil prime cumule fi date s7i7a
		public void calculeprofitUnique(){ // kol 3am na3tik rendement eli na3mlou ena .. w ken a9al min 0.15 nzidek ena 0.015 ... etheka l min 
			float primeUnique = 1000 ;
			int dureeContrat = 5 ;
			double frais_gestion = 0.03 ;
			double primerelle  =primeUnique - primeUnique*frais_gestion;
			double f ;
			float rachat ;
			int year = 2021 ; 
			for (int i = 0 ; i < dureeContrat ; i++){
				Rendement r = rendement.findByAnnee(year+i);
				double t = r.getRendement();
				System.out.println("************-------*-----------***********" + t);
				double m = t*0.85 ;
				//double pb = m - frais_gestion;
				if (m < 0.015 ){
					rachat = (float) primerelle ; // rachat
					System.out.println("---------" + rachat );

					primerelle = 	primerelle+(float) (primerelle*0.015) ;					
					primerelle = primerelle - primerelle*frais_gestion;
					System.out.println(" negative : "+primerelle);
					double rachat_totale =   primerelle - (primerelle - rachat )*0.8  ;
					System.out.println("---------" + rachat_totale );
					}
				else {
					rachat = (float) primerelle ; // rachat
					System.out.println("---------" + rachat );

					primerelle = primerelle +	(float) (primerelle*m) ;
					primerelle = primerelle - primerelle*frais_gestion;
					System.out.println("positive  : "+primerelle);
					double rachat_totale =   primerelle - (primerelle - rachat )*0.8  ;
					System.out.println("---------" + rachat_totale );
					}				
				}			
		}
		  
		
		
		public void calculeprofitPeriodique(){
			float primePeriodiquePrincipale = 1000 ;
			int dureeContrat = 5 ;
			double frais_gestion = 0.03 ;
			float	primePeriodique  =(float) (primePeriodiquePrincipale - primePeriodiquePrincipale*frais_gestion);
			float rachat ;
			int year = 2021 ; 
			for (int i = 0 ; i < dureeContrat ; i++){
				Rendement r = rendement.findByAnnee(year+i);
				double t = r.getRendement();
				double m = t*0.85 ;
				//double pb = m - frais_gestion;
				if (m < 0.015 ){
					
					rachat = primePeriodique ; // rachat
					System.out.println("rachat  : " + rachat);
					
					primePeriodique = primePeriodiquePrincipale +	primePeriodique+(float) (primePeriodique*0.015) ;					
					primePeriodique = (float) (primePeriodique - primePeriodique*frais_gestion);
					System.out.println(" negative : "+primePeriodique);
					double ry =   primePeriodique - (primePeriodique - rachat - primePeriodiquePrincipale)*0.8  ;
					System.out.println("---------" + ry );
					}
				else {
					
					rachat = primePeriodique ; // rachat
					System.out.println("rachat  : " + rachat);
					primePeriodique =primePeriodiquePrincipale+ primePeriodique +	(float) (primePeriodique*m) ;
					primePeriodique = (float) (primePeriodique - primePeriodique*frais_gestion);
					System.out.println("positive  : "+primePeriodique);
					double ry =  primePeriodique -(primePeriodique - rachat - primePeriodiquePrincipale)*0.8  ;
					System.out.println("---------" + ry );					}						
		}	
		}
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//	Le fonds euro-croissance monosupport ( fond en euro ) et multisupport ( fond en euro + unité de compte )
		
		public float rendementPortefeuilleEUROcroissance(){
			float s = 0 ;
			float f = 0 ;
			Date dd  = new Date();
			List<DataFondEURO> bonds = dataEuro.findByTypBondeAndDateSouscriptionLessThanEqualAndDateEcheanceGreaterThanEqual("BTA" , dd , dd);
			//List<DataFondEURO> bonds = dataEuro.findByTypBonde("BTA");
			for (DataFondEURO bond: bonds){
				s = s + bond.getMontantSouscription() ;	
			}
			
			System.out.println("----------"+ s );
			for (DataFondEURO bond: bonds){
				f = f + (bond.getTauxRendement()*bond.getMontantSouscription())/s ;
				System.out.println("----------"+ bond.getId() );
			}
			System.out.println("----------"+ f );
			
			// f * 0.85 mil benefices lkol
			return f ;
		}
	
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		public void liste(){
			for ( int i = 2021 ; i < 2027 ; i++ ){
			List<DataFondEURO> ls = 	dataEuro.listeParAnneeBTA(i);
				for ( DataFondEURO l : ls ){
					System.out.println("-----"+ l.getDateSouscription()+ "-------"+ l.getDateEcheance() + "year : " + i);
				}
			
			}
		}

	

		public void calculerendementTotal(){
			rendement.deleteAll();
			int currentYear = 0 ; //echeance	
			int s = 0 ;
			int m = 0 ;
			List<DataFondEURO> ls = 	dataEuro.findAllByOrderByDateEcheanceAsc();
			for(DataFondEURO l : ls){ currentYear= l.getDateEcheance().getYear();			 m=currentYear+1900;  }
			List<DataFondEURO> ls2 = 	dataEuro.findAllByOrderByDateSouscriptionDesc();
			for(DataFondEURO l : ls2){ currentYear= l.getDateSouscription().getYear(); 
			System.out.println("----------------------------" + l.getDateSouscription() + "------" + l.getDateEcheance());
			 s=currentYear+1900; 
			}
			for(int i = s ; i <= m ; i++){
				Date d = new Date();
				d.setMonth(0);
				d.setDate(1);
				d.setYear(i);
				//////////////////////////////////////////////////////////////////////////////////////////////////////////////
				///////////////////////////////////  Rendement portefeuille   /////////////////////////////////////////////////
				List<DataFondEURO> liste = 	dataEuro.listeParAnnee(i);
				float somme_montant = 0 ;
				float f = 0 ;
				List<DataFondEURO> l = new ArrayList<DataFondEURO>();
				for ( DataFondEURO all : liste ){      l.add(all);      	}
				for (DataFondEURO all: l){		somme_montant = somme_montant + all.getMontantSouscription() ;		}
				for (DataFondEURO all : l){
					if ((all.getDateSouscription().getYear()+1900) == d.getYear() ){
						System.out.println("annnnnnnneeee " + (all.getDateSouscription().getYear()+1900) );
						float taux = 0 ;
						long t  = 0 ;
						t = 12 - all.getDateSouscription().getMonth();	
						double x1 = 1+ all.getTauxRendement() ;
						double x2 = Math.log(( x1) ) ;
						double x3 = x2*t/12 ;
						taux = (float) Math.exp((x3))-1;	
						f = f + (taux*all.getMontantSouscription())/somme_montant ;	
					
						System.out.println("xxxxx1111 : " + x1 + "   xxxxxxx2222222 : "+ x2 + "     xxxxxxx3333 : " + x3);
						System.out.println("daaaate" +all.getDateEcheance().getYear() );
						System.out.println("tttttt" +t );
						System.out.println("taux" +taux );
						System.out.println("fffffffffffff" +f );
					}
					else if((all.getDateEcheance().getYear()+1900) == d.getYear()) {
						float taux = 0 ;
						long t  = 0 ;
						t =  all.getDateEcheance().getMonth() - d.getMonth();	
						double x1 = 1+ all.getTauxRendement() ;
						double x2 = Math.log(( x1) ) ;
						double x3 = x2*t/12 ;
						taux = (float) Math.exp((x3))-1;	
						f = f + (taux*all.getMontantSouscription())/somme_montant ;						
					}
					else{
						f = f + (all.getTauxRendement()*all.getMontantSouscription())/somme_montant ;									
					}					}				
				//////////////////////////////////////////////////////////////////////////////////////////////////////////////
				///////////////////////////////////  Rendement portefeuille BTA  /////////////////////////////////////////////////				
				List<DataFondEURO> listeBTA = 	dataEuro.listeParAnneeBTA(i);
				float somme_montantBTA = 0 ;
				float fBTA = 0 ;
				List<DataFondEURO> lBTA = new ArrayList<DataFondEURO>();
				for ( DataFondEURO all : listeBTA ){      lBTA.add(all);      	}

				for (DataFondEURO all: lBTA){		somme_montantBTA = somme_montantBTA + all.getMontantSouscription() ;		}
				for (DataFondEURO all : lBTA){
					if ((all.getDateSouscription().getYear()+1900) == d.getYear() ){
						System.out.println("annnnnnnneeee " + (all.getDateSouscription().getYear()+1900) );
						float taux = 0 ;
						long t  = 0 ;
						t = 12 - all.getDateSouscription().getMonth();	
						double x1 = 1+ all.getTauxRendement() ;
						double x2 = Math.log(( x1) ) ;
						double x3 = x2*t/12 ;
						taux = (float) Math.exp((x3))-1;	
						fBTA = fBTA + (taux*all.getMontantSouscription())/somme_montantBTA ;	
					
						System.out.println("xxxxx1111 : " + x1 + "   xxxxxxx2222222 : "+ x2 + "     xxxxxxx3333 : " + x3);
						System.out.println("daaaate" +all.getDateEcheance().getYear() );
						System.out.println("tttttt" +t );
						System.out.println("taux" +taux );
						System.out.println("fffffffffffff" +fBTA );
					}
					else if((all.getDateEcheance().getYear()+1900) == d.getYear()) {
						float taux = 0 ;
						long t  = 0 ;
						t =  all.getDateEcheance().getMonth() - d.getMonth();	
						double x1 = 1+ all.getTauxRendement() ;
						double x2 = Math.log(( x1) ) ;
						double x3 = x2*t/12 ;
						taux = (float) Math.exp((x3))-1;	
						fBTA = fBTA + (taux*all.getMontantSouscription())/somme_montantBTA ;						
					}
					else{
						fBTA = fBTA + (all.getTauxRendement()*all.getMontantSouscription())/somme_montantBTA ;									
					}		
				}
				
				System.out.println("iiiiiiiiiiiiiiiiii" + i);
				Rendement r = new Rendement();

				r.setAnnee(i);
				r.setRendement(f);
				r.setRendementBTA(fBTA);
				rendement.save(r);	
				/*	Rendement r = rendement.findByAnnee(i) ;
					if(r == null){
						Rendement r1 = new Rendement();
						r1.setAnnee(i);
						r1.setRendement(f);
						r1.setRendementBTA(fBTA);
						rendement.save(r1);
					}
					else{
					//r.setAnnee(i);
					r.setRendement(f);
					r.setRendementBTA(fBTA);
					rendement.save(r);	
					}*/
				}
			}
		
		
		
		
		
		

		public static double puissance(double a, double p){
		        double result = 1;        
		        for(int i = 0; i < p; i++) {
		            result = result * a;
		        }	         
		        return(result);
		    }
	
		
		
	
}
/*		
		public  void montant_actuelProfitUnique(){		
			int id  = 1 ;
			double frais_gestion = 0.03 ;
			ActifFinancier a = actifFinancierRepo.findById(id).get();
			float primerelle = (float) (a.getMontant_investi() - (a.getMontant_investi()*frais_gestion)) ;
			Date d1 = new Date();   // sys date frr
			Date debut = a.getDate_debut() ;
			Date djanv = new Date();
			djanv.setMonth(0);
			djanv.setDate(1);
			djanv.setYear(debut.getYear());
			////////////////////////////////////////////////////////// na9smou l intervale de temps sur 2 : loul ndwrou 3lih l for w etheni ne5dmouh wa7dou
			float res  =  ((d1.getTime() -  a.getDate_debut().getTime())/ (1000 * 60 * 60 * 24)) ;
			int nombre_annee = (int) (res/365) ;
			float tit = res/365 -  nombre_annee ;
			int jours_restantes = (int) (tit * 365) ;
			long diff1 = (debut.getTime() - djanv.getTime()) ;
			int days1 = 365 - (int) (diff1 / (1000 * 60 * 60 * 24)) ;
			System.out.println("diffffffffffffffffff     " + nombre_annee + "       " + tit + "    "  + jours_restantes  + "     " + days1);
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
						if ( m < 0.015){ primerelle = 	primerelle+(float) (primerelle*0.015) ;	}
						else {	primerelle = 	primerelle+(float) (primerelle*m) ;				}					
				System.out.println("calcule prime cumulé " + primerelle  + " yeaaaar  : " + i + "           " + m);	
			}
			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
			float primefinale=0 ;
			if(jours_restantes < days1){
				System.out.println("9eiiiinnnnnnn    " +  jours_restantes	);
				Rendement rend = rendement.findByAnnee(debut.getYear()+nombre_annee+1900);
				double q = ((float)jours_restantes/(float)365) ;
				System.out.println("qqqqqqqqqqq    " + q);
				primefinale = 	primerelle+(float) (primerelle*rend.getRendement()*(q)*0.85) ;
				System.out.println((primerelle*rend.getRendement()*(jours_restantes/365)*0.85) + "amaaaaaaan1 " + primefinale+ "           "+rend.getRendement());
			}
			else {
				int jours_restantes_2eme_annee = jours_restantes- days1 ;
				Rendement rend = rendement.findByAnnee(debut.getYear()+nombre_annee+1900);
				Rendement rend2 = rendement.findByAnnee(debut.getYear()+nombre_annee+1+1900);
				float q =(float) jours_restantes/(float)365 ;
				float q2 = (float)jours_restantes_2eme_annee/(float)365 ;

				primefinale = 	primerelle+(float) (primerelle*rend.getRendement()*(q)*0.85) ;
				primefinale = 	primefinale+(float) (primefinale*rend2.getRendement()*(q2)*0.85) ;
				System.out.println("amaaaaaaan2 " + primefinale);			
			}			
			System.out.println("calcule prime cumulé  haya " + primefinale  );	
		}
			
		
		
}




/*			//	float res = (t / (1000*60*60*24)); taux par jour
Prenons l’exemple d’un épargnant ayant versé 10.000 euros nets de frais sur son contrat investi en fonds en euros. Si à l’issue de la première année, 
l’assureur annonce servir un taux de 2% net de prélèvements sociaux, le contrat sera crédité de 200 euros supplémentaires. Et c’est sur un capital
 de 10.200 euros que porteront les intérêts de l’année suivante..
 
 
 * taux rendement - frais de gestion = pb
 * 
 * Le rendement des fonds en euros provient du taux d’intérêt technique et de la participation aux bénéfices.
 * 
 * EXEMPLE 

Pour mieux comprendre comment se calcule la rémunération, rien de mieux qu’un exemple.

Imaginons un assuré qui a placé une somme de 10000 € nette de frais d’entrée sur un fonds en euros.

Supposons un taux minimum garanti fixé à 2,5%. 10000 X 2,5 % = 250 €

Et une participation aux bénéfices fixée à 0,6 %. 10000 X 0,6 % = 60 €

De ce total de 310 €, vont venir se déduire les frais de gestion appliqués sur la totalité des fonds gérés (et en général compris entre 0,5 et 1 % du total). Retenons donc 0,70 %.

Les frais s’appliqueront donc sur la somme de 10310 € => 10310 X 0,70 %, soit 72,17 €.

Le bénéfice réellement acquis sera donc calculé de la façon suivante : 250 + 60 – 72,17 = 237,83 €.

Le taux réellement attribué par l’assureur sera donc de 2,37 %.

Mais le gain réalisé par l’assuré ne sera pas de 237,83 € car il sera soumis aux prélèvements sociaux à 17,2 %. Le gain réel de l’investisseur se montera donc à 237,83 – 17,2 % (40,90) = 196,93€.

Le pourcentage de revalorisation réel des capitaux serait de 1,9693 %. Soit inférieur à 2 %.
*/

