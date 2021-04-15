package tn.esprit.spring.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entity.Data;
import tn.esprit.spring.repository.IContratRepo;
import tn.esprit.spring.repository.IDataRepo;

@Service
public class DataService {

	@Autowired
	IContratRepo contrat ;
	
	@Autowired
	IDataRepo data ;
	
	@Autowired
	Taux_InteretService taux_InteretService;
	
	
	
	
	String homme = "homme";
	String femme = "femme";
	// prime unique ------------------------------------------ Capital differe ----------------------------------------------------------------------	
	public double calculePUCapital_vieCapital(float capital ,int age , int annee, double interet , String sexe){
		double p = 0 ;
		if(sexe.equals(homme)  ){
		Data d = data.findById(age).get();
		Data d_anne = data.findById(age+annee).get();
		double s = puissance(1/(1+interet), annee);
		System.out.println("***"+ s);
		 p = capital*(d_anne.getLxH()/d.getLxH())*s;
		// d_anne.getLxF();
		 System.out.println("======== "+p );
		}
		else if (sexe.equals(femme)  ){
			Data d = data.findById(age).get();
			Data d_anne = data.findById(age+annee).get();
			double s = puissance(1/(1+interet), annee);
			System.out.println("***"+ s);
			 p = capital*(d_anne.getLxF()/d.getLxF())*s;
			// d_anne.getLxF();
			 System.out.println("======== "+p );
			}			
		return p;	
	}
	public double calculePUCapital_viePrime(float prime ,int age , int annee, double interet , String sexe){
		double p = 0 ;
		if(sexe.equals(homme)  ){
		Data d = data.findById(age).get();
		Data d_anne = data.findById(age+annee).get();
		double s = puissance(1/(1+interet), annee);
		System.out.println("***"+ s);
		 p = prime/((d_anne.getLxH()/d.getLxH())*s);
		// d_anne.getLxF();
		 System.out.println("======== "+p );
		}
		else if (sexe.equals(femme)   ){
			Data d = data.findById(age).get();
			Data d_anne = data.findById(age+annee).get();
			double s = puissance(1/(1+interet), annee);
			System.out.println("***"+ s);
			 p = prime/((d_anne.getLxF()/d.getLxF())*s);
			// d_anne.getLxF();
			 System.out.println("======== "+p );
			}				
		return p;
	}
	// prime unique ------------------------------------------ Rente viagere ----------------------------------------------------------------------	
	public double calculePURenteillimteRente(float rente,int age ,  double interet , String sexe){ // à terme échue)
		double somme = 0 ;
		Data d = data.findById(age).get();
		if(sexe.equals(homme) ){
		for ( int i= 1 ; i <(104-age)+1; i ++ ){	
			Data d_anne = data.findById(age+i).get();
			double s = puissance(1/(1+interet), i);
			System.out.println("----------"+s);
			somme = somme + (d_anne.getLxH()/d.getLxH())*s;
		}		
		System.out.println("******"+somme*rente);}
		else if (sexe.equals(femme)   ){
			for ( int i= 1 ; i <(104-age)+1; i ++ ){	
				Data d_anne = data.findById(age+i).get();
				double s = puissance(1/(1+interet), i);
				System.out.println("----------"+s);
				somme = somme + (d_anne.getLxF()/d.getLxF())*s;
			}		
			System.out.println("******"+somme*rente);}
		
		return somme*rente;
	}
	public double calculePURenteillimtePrime(float prime,int age ,  double interet , String sexe){ // à terme échue)
		double somme = 0 ;
		Data d = data.findById(age).get();
		if(sexe.equals(homme)  ){
		for ( int i= 1 ; i <(104-age)+1; i ++ ){	
			Data d_anne = data.findById(age+i).get();
			double s = puissance(1/(1+interet), i);
			System.out.println("----------"+s);
			somme = somme + (d_anne.getLxH()/d.getLxH())*s;
		}		
		System.out.println("******"+prime/somme);}
		else if (sexe.equals(femme)   ){
			for ( int i= 1 ; i <(104-age)+1; i ++ ){	
				Data d_anne = data.findById(age+i).get();
				double s = puissance(1/(1+interet), i);
				System.out.println("----------"+s);
				somme = somme + (d_anne.getLxF()/d.getLxF())*s;
			}		
			System.out.println("******"+prime/somme);}
		
		return prime/somme;
	}
	// prime unique ------------------------------------------ vie entiere  ----------------------------------------------------------------------	

	public double calculePUVieEntiereCapital(float capital,int age ,  double interet ,String sexe){
		double somme = 0 ;
		Data d = data.findById(age).get();
		if(sexe.equals(homme)  ){
		for ( int i= 0 ; i <(104-age); i ++ ){	
			Data d_anne = data.findById(age+i).get();
			double s = puissance(1/(1+interet), (i+0.5));
			System.out.println("----------"+s);
			somme = somme + (d_anne.getDxH()/d.getLxH())*s;
		}	
		System.out.println("******"+somme*capital);
		}
		else if (sexe.equals(femme)   ){
			for ( int i= 0 ; i <(104-age); i ++ ){	
				Data d_anne = data.findById(age+i).get();
				double s = puissance(1/(1+interet), (i+0.5));
				System.out.println("----------"+s);
				somme = somme + (d_anne.getDxF()/d.getLxF())*s;
			}	
			System.out.println("******"+somme*capital);
		}
		return somme*capital;
	}
	public double calculePUVieEntierePrime(float prime ,int age ,  double interet ,String sexe){
		double somme = 0 ;
		Data d = data.findById(age).get();
		if(sexe.equals(homme)  ){
		for ( int i= 0 ; i <(104-age); i ++ ){	
			Data d_anne = data.findById(age+i).get();
			double s = puissance(1/(1+interet), (i+0.5));
			System.out.println("----------"+s);
			somme = somme + (d_anne.getDxH()/d.getLxH())*s;
		}	
		System.out.println("******"+prime/somme);
		}
		else if (sexe.equals(femme)  ){
			for ( int i= 0 ; i <(104-age); i ++ ){	
				Data d_anne = data.findById(age+i).get();
				double s = puissance(1/(1+interet), (i+0.5));
				System.out.println("----------"+s);
				somme = somme + (d_anne.getDxF()/d.getLxF())*s;
			}	
			System.out.println("******"+prime/somme);
		}
		return prime/somme;
	}
	
	//*************************************************************************************************************************************
	//*************************************************************************************************************************************
	//*************************************************************************************************************************************
	//*************************************************************************************************************************************
	//*************************************************************************************************************************************
	//*************************************************************************************************************************************
	//*************************************************************************************************************************************
	//*************************************************************************************************************************************
	//*************************************************************************************************************************************
	//*************************************************************************************************************************************
	//*************************************************************************************************************************************
	//*************************************************************************************************************************************
	//*************************************************************************************************************************************
	//*************************************************************************************************************************************
	//*************************************************************************************************************************************
	
	
	//prime periodique -----------------------------------------------Capital differe--------------------------------------------------------------------------
	public double calculePPCapital_vie(float capital ,float prime ,int age , int annee, double interet , String sexe){
		double somme = 0 ;
		double finale = 0;
		Data d = data.findById(age).get();
		if(sexe.equals(homme)){
			System.out.println("hellllllllllllllllllloooooooooooo");
		Data d_n = data.findById(age+annee).get(); // lx a l annee n
		double s_n = puissance(1/(1+interet), annee ); // interet a l annee n : vn
		double t = (d_n.getLxH()/d.getLxH())*s_n; // l actualisation .. lx+n/lx * vn
		// la somme lx+k/lx * vk jsuqu a n-1
		for ( int i= 0 ; i <(annee-1); i ++ ){	
			Data d_anne = data.findById(age+i).get();
			double s = puissance(1/(1+interet), i);
			System.out.println("----------"+s);
			somme = somme + (d_anne.getLxH()/d.getLxH())*s; }
		if (capital != 0){ // alors on va calculer la rente	
			 finale = (capital*t)/somme;
			 System.out.println("****** "+"  on a calculé la prime periodique pour n annee "+finale);
				 }
		else {			
			 finale = (prime *somme)/t ;
			 System.out.println("****** "+"  on a calculé le capital obtenu si on donnne une prime periodique pour n annee "+finale);
				}
		}
		else if (sexe.equals(femme)  ){
			Data d_n = data.findById(age+annee).get(); // lx a l annee n
			double s_n = puissance(1/(1+interet), annee ); // interet a l annee n : vn
			double t = (d_n.getLxF()/d.getLxF())*s_n; // l actualisation .. lx+n/lx * vn
			// la somme lx+k/lx * vk jsuqu a n-1
			for ( int i= 0 ; i <(annee-1); i ++ ){	
				Data d_anne = data.findById(age+i).get();
				double s = puissance(1/(1+interet), i);
				System.out.println("----------"+s);
				somme = somme + (d_anne.getLxF()/d.getLxF())*s; }
			if (capital != 0){ // alors on va calculer la rente	
				 finale = (capital*t)/somme;
				 System.out.println("****** "+"  on a calculé la prime periodique pour n annee "+finale);
					 }
			else {			
				 finale = (prime *somme)/t ;
				 System.out.println("****** "+"  on a calculé le capital obtenu si on donnne une prime periodique pour n annee "+finale);
					}
			
		}
		 System.out.println("****** "+"  on a calculé le capital obtenu si on donnne une prime periodique pour n annee "+finale);

		return finale ;
		
		
	}
	
	//prime periodique -----------------------------------------------Rente viagere -------------------------------------------------------------------------

		public double calculePPRente( float rente, float prime ,int age , int annee, double interet , String sexe ){// a terme d’avance
			double somme_rente = 0 ;
			double somme_prime = 0 ;
			double finale = 0;
			if(sexe.equals(homme)){
			Data d = data.findById(age).get();
			Data d_n = data.findById(age+annee).get(); // lx a l annee n
			double s_n = puissance(1/(1+interet), annee ); // interet a l annee n : vn
			
						// la somme lx+k/lx * vk jsuqu a n-1
						for ( int i= 0 ; i <(annee-1); i ++ ){	
							Data d_anne = data.findById(age+i).get();
							double s = puissance(1/(1+interet), i);
							System.out.println("----------"+s);
							somme_prime = somme_prime + (d_anne.getLxH()/d.getLxH())*s; }
						
						//  la somme lx+k/lx * vk de n jsuqu a w-x
						for ( int i= annee ; i <(104-age); i ++ ){	
							Data d_anne = data.findById(age+i).get();
							double s = puissance(1/(1+interet), i);
							System.out.println("----------"+s);
							somme_rente = somme_rente + (d_anne.getLxH()/d.getLxH())*s; }		
			if (rente != 0){ // alors on va calculer la rente	
				 finale = (rente*somme_rente)/somme_prime;
				 System.out.println("****** "+"  on a calculé la prime periodique pour n annee "+finale);	}
			else {			
				 finale = (prime *somme_prime)/somme_rente ;
				 System.out.println("****** "+"  on a calculé les rentes si on donnne une prime periodique pour n annee "+finale);}
			}
			if(sexe.equals(femme)){			
				Data d = data.findById(age).get();
				Data d_n = data.findById(age+annee).get(); // lx a l annee n
				double s_n = puissance(1/(1+interet), annee ); // interet a l annee n : vn
				
							// la somme lx+k/lx * vk jsuqu a n-1
							System.out.println("****** za3ma ! !"+ annee);
							for ( int i= 0 ; i <=(annee-1); i ++ ){	
								Data d_anne = data.findById(age+i).get();
								double s = puissance(1/(1+interet), i);
								somme_prime = somme_prime + (d_anne.getLxF()/d.getLxF())*s;
								System.out.println("****** chfama hne !"+ somme_prime);}
							
							//  la somme lx+k/lx * vk de n jsuqu a w-x
							for ( int i= annee ; i <=(104-age); i ++ ){	
								Data d_anne = data.findById(age+i).get();
								double s = puissance(1/(1+interet), i);
								somme_rente = somme_rente + (d_anne.getLxF()/d.getLxF())*s;}		
				if (rente != 0){ // alors on va calculer la rente	
					 finale = (rente*somme_rente)/somme_prime;
					 System.out.println("****** "+"  on a calculé la prime periodique pour n annee "+finale);}
				else {	
					System.out.println(prime + " mmmm    " + somme_prime + "   mmmm   "+ somme_rente  );
					 finale = (prime *somme_prime)/somme_rente ;
					 System.out.println("****** "+"  on a calculé les rentes si on donnne une prime periodique pour n annee  .. "+finale); }
				
			}
			return finale ;
			
		}
		
		
//prime periodique ----------------------------------------------- Vie entiere  -------------------------------------------------------------------------	
		public double calculePPVieEntiere(float prime ,float capital ,int age , int annee, double interet , String sexe ){// a terme d’avance
			double somme_capital = 0 ;
			double somme_prime = 0 ;
			double finale = 0;
			if(sexe.equals(homme)){
			Data d = data.findById(age).get();
			double s_n = puissance(1/(1+interet), annee ); // interet a l annee n : vn			
						//  la somme lx+k/lx * vk de n jsuqu a w-x
						for ( int i= 0 ; i <(104-age); i ++ ){	
							Data d_anne = data.findById(age+i).get();
							double s = puissance(1/(1+interet), i);
							System.out.println("----------"+s);
							somme_prime = somme_prime + (d_anne.getLxH()/d.getLxH())*s; }
										
						for ( int i= 0 ; i <(104-age); i ++ ){	
							Data d_anne = data.findById(age+i).get();
							double s = puissance(1/(1+interet), (i+0.5));
							System.out.println("----------"+s);
							somme_capital = somme_capital + (d_anne.getDxH()/d.getLxH())*s;}
			
			if (capital != 0){ // alors on va calculer la rente	
				 finale = (capital*somme_capital)/somme_prime;
				 System.out.println("****** "+"  on a calculé la prime periodique pour n annee "+finale);			}
			else {			
				 finale = (prime *somme_prime)/somme_capital ;
				 System.out.println("****** "+"  on a calculé les rentes si on donnne une prime periodique pour n annee "+finale); }
			}
			if(sexe.equals(femme)){
				Data d = data.findById(age).get();
				double s_n = puissance(1/(1+interet), annee ); // interet a l annee n : vn			
							//  la somme lx+k/lx * vk de n jsuqu a w-x
							for ( int i= 0 ; i <(104-age); i ++ ){	
								Data d_anne = data.findById(age+i).get();
								double s = puissance(1/(1+interet), i);
								System.out.println("----------"+s);
								somme_prime = somme_prime + (d_anne.getLxF()/d.getLxF())*s; }
											
							for ( int i= 0 ; i <(104-age); i ++ ){	
								Data d_anne = data.findById(age+i).get();
								double s = puissance(1/(1+interet), (i+0.5));
								System.out.println("----------"+s);
								somme_capital = somme_capital + (d_anne.getDxF()/d.getLxF())*s;}
				
				if (capital != 0){ // alors on va calculer la rente	
					 finale = (capital*somme_capital)/somme_prime;
					 System.out.println("****** "+"  on a calculé la prime periodique pour n annee "+finale);				}
				else {			
					 finale = (prime *somme_prime)/somme_capital ;
					 System.out.println("****** "+"  on a calculé les rentes si on donnne une prime periodique pour n annee "+finale); }
			}
			return finale ;
		}
	
	
	
	
/*	public static double puissance(double a, double p){
	        double result = 1;        
	        for(int i = 0; i < p; i++) {
	            result = result * a;
	        }	         
	        return(result);
	    }
*/	    
	public static double puissance (double i , double p ) {
		double x1 = i ;
		double x2 = Math.log(( x1) ) ;
		double x3 = x2*p ;
		double taux = Math.exp((x3));	
		return   taux ;
	}	
	
	
	
}
