package tn.esprit.spring.services;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entity.Data;
import tn.esprit.spring.entity.Data_sante;
import tn.esprit.spring.entity.Prime;
import tn.esprit.spring.repository.IContratRepo;
import tn.esprit.spring.repository.IDataRepo;
import tn.esprit.spring.repository.IData_santeRepo;
import tn.esprit.spring.repository.IReclamationSinistreRepos;
import tn.esprit.spring.repository.ISinistreRepos;
import tn.esprit.spring.repository.ReinsuranceContractRepository;
import tn.esprit.spring.repository.IUserRepo;
import tn.esprit.spring.repository.Type_santeRepo;
import tn.esprit.spring.entity.Contrat;
import tn.esprit.spring.entity.ReclamationSinistre;
import tn.esprit.spring.entity.Reinsurance_contract;
import tn.esprit.spring.entity.Sinistre;

@Service
public class SinistreService {
	private static final Logger k = LogManager.getLogger(ReinsuranceContractService.class);
	
	@Autowired
	IReclamationSinistreRepos reclamationSinistre ;

	@Autowired
	ISinistreRepos sinistre ;

	@Autowired
	IContratRepo contrat ;

	@Autowired
	IDataRepo data ;
	
	@Autowired
	Type_santeRepo sante ;
	
	@Autowired
	IData_santeRepo data_sante ;
	@Autowired
	IUserRepo user ;

	
	@Autowired
	ReinsuranceContractRepository reinsuranceRepository;
	
 /*******************Chourou********************************/
      public void addSinistre(Sinistre sin,int id){
    	 Contrat c= reinsuranceRepository.ContratavecSinistre(sin.getReclamationSinitre().getContrats().getNumContrat());
    	 if(c.getReassure()==1)
    	 {
    		List<Reinsurance_contract> r = reinsuranceRepository.findReinByCont(sin.getReclamationSinitre().getContrats().getNumContrat());
    		if(r.size()!=0)
    		{
    		k.info("Somme remboursee est egale a : "+r.get(0).getRemboursement());	
    		}
    	 }
    	 sinistre.save(sin);
 		
 	}

 	/************************Sarah*******************************/
      @Transactional
  	public List<Sinistre> afficheSinistre(){
  		VersementEcheanceVie();
  		return sinistre.findAll();
  	}



  	public void deleteSinistre(int idSinistre){

  		sinistre.deleteById(idSinistre);

  	}

     /* Calcul du capital en cas de deces de l'assuré*/
  	public float capitaldeces(int idrec,double i){
  		float capital=0;

  		ReclamationSinistre rec=reclamationSinistre.findById(idrec).get();
  		Contrat c=rec.getContrats();

  		/**************************** Type du contrat: vie entière******************************/
  		if(c.getDemandeContrat().getNomContrat().equalsIgnoreCase("vie entiere")){
              float score= (c.getDemandeContrat().getUsers().getScoring()*c.getRemboursement())/100;
  			capital=c.getRemboursement()+score;
  			

  		}
  		/**************************** Type du contrat: Capital differe *****************************/
  		else if (c.getDemandeContrat().getNomContrat().equalsIgnoreCase("capital differe")){
  			capital=(float) capitalisation(rec.getNumReclamation(),i);

  		}
  		/**************************** Type du contrat: rente viagere ******************************/
  		else if (c.getDemandeContrat().getNomContrat().equalsIgnoreCase("rente viagere")){

  			capital=(float) capitalisation(rec.getNumReclamation(),i);
  		}

  		return capital;

  	}
  	
  	/* Calcul du capital en cas de rachat total de l'assuré*/
  	public float capitalrachatotal(int idrec,double i){
  		float pm=0;
  		float capital=0;
  		Date currentDate = new Date();
  		ReclamationSinistre rec=reclamationSinistre.findById(idrec).get();
  		Contrat c=rec.getContrats();
  		
  		/**************************** PMR pour rente viagere******************************/
  		if(c.getDemandeContrat().getNomContrat().equalsIgnoreCase("rente viagere"))            
  		{ 
  		   pm= (float) calculPMR(rec.getNumReclamation(),i);
  		}
  		/**************************** PM pour capital differe******************************/
  		else if (c.getDemandeContrat().getNomContrat().equalsIgnoreCase("capital differe"))                                                                                   
  		{   
  			pm= (float) calculPMvcapdiff(rec.getNumReclamation(),i);
  		}
  		/**************************** PM pour vie entiere******************************/
  		else if (c.getDemandeContrat().getNomContrat().equalsIgnoreCase("vie entiere"))                                                                                   
  		{   
  			pm= (float) calculPMvieentiere(rec.getNumReclamation(),i);

  		}
  		
  	
  		int t= currentDate.getYear()- c.getDate_debut().getYear();
  		/*Si le rachat a lieu avant 2 ans de vie du contrat*/
  		if(t<2) 
  		{  
  			capital=0;   // aucun remboursement
  			
  		}

  		/*Si le rachat a lieu entre 2 et 5 ans de vie du contrat*/
  		else if((t>=2)&&(t<5)) 
  		{  
  			double pmmoinsfrais=pm-(pm*0.05); // - 5% pénalité sur tout le capital
  			capital=(float) pmmoinsfrais;
  		}

  		/*Si le rachat a lieu après 5 ans de vie du contrat*/
  		else
  		{	
  			capital=pm;
  		}
  		return capital;
  	}

  	/* Calcul du capital en cas de rachat partiel de l'assuré*/
  	public float capitalrachapartiel(int idrec,double i){
  		Date currentDate = new Date();
  		ReclamationSinistre rec=reclamationSinistre.findById(idrec).get();
  		Contrat c=rec.getContrats();
  		float capital=0;
  		float capitaltotal= (float) capitalisation(rec.getNumReclamation(),i); 


  		float versements= totprimes(rec.getNumReclamation());// Total des versements jusqu à la date de rachat
  		float rachat=rec.getFrais();
  		float gains= rachat-((versements*rachat)/capitaltotal);
  		


  		//calcul de l'impot sur les gains
  		int t= currentDate.getYear()- c.getDate_debut().getYear();
  		
  		/*Si le rachat a lieu entre 4 et 8 ans de vie du contrat*/
  		if(t<8 && t>=3)
  		{
  			float fraissurgains=(float) (gains*0.7);  //-70 % frais sur les gains
  			capital=rachat-fraissurgains;
  		} 
  		/*Si le rachat a lieu après 8 ans de vie du contrat*/
  		else if(t>=8)
  		{ 
  			float fraissurgains=(float) (gains*0.55);   //-55 % frais sur les gains
  			capital=rachat-fraissurgains;
  		}
  		/*Si le rachat a lieu avant 4 ans de vie du contrat*/
  		else
  		{
  			capital=0; // aucun remboursement
  		}

  		if(c.getDemandeContrat().getNomContrat().equalsIgnoreCase("rente viagere"))
  			c.setRemboursement(c.getRemboursement()-(c.getRemboursement()*capital/c.getPrimeCommerciale()));
  		else
  		c.setRemboursement(c.getRemboursement()-capital);
  		return capital;

  	}

  	/* Ajout d'un sinistre selon le type du contrat et le type de réclamation faite par l'assuré*/

  	@Transactional
  	public void AjoutSinistreselonReclamation( int id){
  		Sinistre s = new Sinistre();
  		double i = 0.002; // interet
  		Date currentDate = new Date();
  		ReclamationSinistre rec = reclamationSinistre.findById(id).get();
  		Contrat c= rec.getContrats();
  		rec.setTraiteReclamation(1);  // la réclamation sera traitée une fois le sinistre ajouté ou réfusé
  		s.setDateReglement(currentDate); 
  		s.setReclamationSinitre(rec);
  		/*calcul du capital remboursé pour chaque type de sinistre ****************************************************************************************/

  		/*rachat total *********************************/

  		if(rec.getTypeReclamation().equalsIgnoreCase("rachat total"))                         
  		{  float a = capitalrachatotal(rec.getNumReclamation(),i) ;
  			c.setEtat("résilié");
  			if(a == 0 ){
  				// vous ne pouvez pas dsl .. mail
  				//String ss = rec.getContrats().getDemandeContrat().getUsers().getEmail();
  				String ss = "sarah.gmiha@esprit.tn";
  				mail(rec,ss);
  			}
  			else{
  			s.setCapitalRembourse(a);
  			sinistre.save(s);

  			}

  		}
  		/*rachat partiel *********************************/

  		else if(rec.getTypeReclamation().equalsIgnoreCase("rachat partiel"))
  		{    float b= capitalrachapartiel(rec.getNumReclamation(),i);  
  		if(b==0)
  		{
  			// vous ne pouvez pas dsl .. mail
  			//String ss = rec.getContrats().getDemandeContrat().getUsers().getEmail();
  			String ss = "sarah.gmiha@esprit.tn";
  			mail(rec,ss);
  		}
  		else{
  			s.setCapitalRembourse(b); 
  			sinistre.save(s);
  		}
  			
  		}

  		/* Deces ***********************************************************************/

  		else if(rec.getTypeReclamation().equalsIgnoreCase("deces"))
  		{ 
  		   c.setEtat("échu");
             s.setCapitalRembourse((float) capitaldeces(rec.getNumReclamation(),i));
     		sinistre.save(s);

  		}
  		/*Maladie**************************************************/

  		else 
  		{
  			s.setCapitalRembourse(calculremboursementsante(rec.getNumReclamation()));
  			sinistre.save(s);

  		}
  	}

  	/*calcul total des primes versées jusqu'à la date t: prime * nb années t   (cas de la prime periodique)*/
  	public float totprimes(int idrec){
  		ReclamationSinistre rec=reclamationSinistre.findById(idrec).get();
  		Date currentDate = new Date();
  		int t= currentDate.getYear()- rec.getContrats().getDate_debut().getYear();
  		float tot=0;
  		if(rec.getContrats().getDemandeContrat().getChoixPrime().equals(Prime.Prime_Periodique))
  		{ 
  			tot= t* rec.getContrats().getPrimeCommerciale();
  		}
  		else // Prime_Unique
  		{
  			tot=rec.getContrats().getPrimeCommerciale();
  		}
  		
  		return tot;
  	}

  	/* calcul de l'age depuis la date de naissance*/
  	public int age(Date birthdate){
  		Date currentDate = new Date();
  		DateFormat formatter = new SimpleDateFormat("yyyyMMdd"); 
  		int d1 = Integer.parseInt(formatter.format(birthdate));                            
  		int d2 = Integer.parseInt(formatter.format(currentDate));                          
  		int age = (d2 - d1) / 10000; 
  		return age;
  	}

  	/*Calcul de la provision mathématique pour le contrat vie entiere*/
  	public double calculPMvieentiere(int idrec, double i){
  		Date currentDate = new Date();
  		//Date currentDate = new Date(124,10,10);
  		ReclamationSinistre rec=reclamationSinistre.findById(idrec).get();
  		

  		/*Variables*/
  		double pm=0; //retour
  		float prime=rec.getContrats().getPrimeCommerciale();
  		System.out.println("yalla" + prime);
  		float capital=rec.getContrats().getRemboursement();  // c'est le capital assuré fel contrat
  		Date naissance=rec.getContrats().getDemandeContrat().getUsers().getBirth_date();
  		int x=age(naissance);
  		
  		//System.out.println("x  "+x);
  		//int n = rec.getContrats().getDate_fin().getYear()-rec.getContrats().getDate_debut().getYear();
  		int w=104;
  		float v = (float) (1/(1+(i)));
  		int t= currentDate.getYear()- rec.getContrats().getDate_debut().getYear();
  		//int x=age(naissance)-t;
        
  		//int t=  rec.getContrats().getDate_fin().getYear()-currentDate.getYear();
  		float sum1=0;
  		float sum2=0;

  		String sexe= rec.getContrats().getDemandeContrat().getUsers().getSexe();
  		/*Si Homme*/
  		if(sexe.equalsIgnoreCase("homme")) 
  		{ 
  			Data d1=data.findById(x+t).get();
  			float lxt=d1.getLxH();

  			for(int k=0;k<(w-t-x);k++)
  			{
  				Data d3= data.findById(x+t+k).get();
  				float dxtk=d3.getDxH();
  				sum1+= (dxtk/lxt)*puissance(v,(k+(1/2)));
  	
  			}
  			for(int k=0;k<(w-t-x);k++)
  			{
  				Data d4= data.findById(x+t+k).get();
  				float lxtk=d4.getLxH();
  				sum2+= (lxtk/lxt)*puissance(v,k);

  			}
  			if(rec.getContrats().getDemandeContrat().getChoixPrime().equals(Prime.Prime_Periodique))  
  			{ pm=(capital*sum1)-(prime*sum2);

  			}
  			else{                                                                  // Prime_Unique
  				pm=prime;
  				
  			}
  			//return pm;
  		}


  		/*si femme*/

  		else
  		{ 
  			Data d1=data.findById(x+t).get();
  			float lxt=d1.getLxF();

  			for(int k=0;k<(w-t-x);k++)
  			{
  				Data d3= data.findById(x+t+k).get();
  				float dxtk=d3.getDxF();
  				sum1= (float) (sum1+ (dxtk/lxt)*puissance(v,k+(1/2)));
  				
  				
  			}
  			for(int k=0;k<(w-t-x);k++)
  			{
  				Data d4= data.findById(x+t+k).get();
  				float lxtk=d4.getLxF();
  				float h=lxtk/lxt;
  				sum2+= h*puissance(v,k);
  				
  			
  			}
  			if(rec.getContrats().getDemandeContrat().getChoixPrime().equals(Prime.Prime_Periodique))  
  			{ pm=(capital*sum1)-(prime*sum2);
  			

  			}
  			else{                                                                  // Prime_Unique
  				pm=prime;	
  			}
  		}

  		return pm;

  	}

  	/*Calcul de la provision mathématique pour le contrat capital differe*/
  	public double calculPMvcapdiff(int idrec, double i){
  		Date currentDate = new Date();
  		ReclamationSinistre rec=reclamationSinistre.findById(idrec).get();

  		/*Variables*/
  		double pm=0; //retour
  		float prime=rec.getContrats().getPrimeCommerciale();
  		float capital=rec.getContrats().getRemboursement();  // c'est le capital assuré fel contrat
  		Date naissance=rec.getContrats().getDemandeContrat().getUsers().getBirth_date();
  		int x=age(naissance);
  		int n = rec.getContrats().getDate_fin().getYear()-rec.getContrats().getDate_debut().getYear();

  		float v = (float) (1/(1+(i)));
  		int t= currentDate.getYear()- rec.getContrats().getDate_debut().getYear();
  		float sum1=0;

  		String sexe= rec.getContrats().getDemandeContrat().getUsers().getSexe();
  		/*Si Homme*/
  		if(sexe.equalsIgnoreCase("homme")) 
  		{ 
  			Data d= data.findById(x+n).get();
  			float lxn=d.getLxH();

  			Data d1=data.findById(x+t).get();
  			float lxt=d1.getLxH();

  			for(int k=0;k<(n-t-1);k++)
  			{
  				Data d3= data.findById(x+t+k).get();
  				float lxtk=d3.getLxH();
  				sum1+= (lxtk/lxt)*puissance(v,k);
  			}
  			if(rec.getContrats().getDemandeContrat().getChoixPrime().equals(Prime.Prime_Periodique))  
  			{ pm=(capital*(lxn/lxt)*puissance(v,n-t))-(prime*sum1);

  			}
  			else{                                                                  // Prime_Unique
  				pm=prime;	
  			}
  		}
  		/*si femme*/
  		else
  		{ 
  			Data d= data.findById(x+n).get();
  			float lxn=d.getLxF();

  			Data d1=data.findById(x+t).get();
  			float lxt=d1.getLxF();

  			for(int k=0;k<(n-t-1);k++)
  			{
  				Data d3= data.findById(x+t+k).get();
  				float lxtk=d3.getLxF();
  				sum1+= (lxtk/lxt)*puissance(v,k);
  			}
  			if(rec.getContrats().getDemandeContrat().getChoixPrime().equals(Prime.Prime_Periodique))  
  			{ pm=(capital*(lxn/lxt)*puissance(v,n-t))-prime*sum1;

  			}
  			else{                                                                  // Prime_Unique
  				pm=prime;
  				
  			}
  		}
  		return pm;
  	}


  	/*Calcul de la provision mathématique des rentes pour le contrat rente viagere */
  	public double calculPMR(int idrec, double i){
  		Date currentDate = new Date();
  		ReclamationSinistre rec=reclamationSinistre.findById(idrec).get();

  		/*Variables*/
  		double pm=0; //retour
  		float prime=rec.getContrats().getPrimeCommerciale();
  		float capital=rec.getContrats().getRemboursement();  // c'est le capital assuré fel contrat
  		Date naissance=rec.getContrats().getDemandeContrat().getUsers().getBirth_date();
  		int x=age(naissance);
  		int m = rec.getContrats().getDate_fin().getYear()-rec.getContrats().getDate_debut().getYear();
  		int w=104;
  		float v = (float) (1/(1+(i)));
  		int t= currentDate.getYear()- rec.getContrats().getDate_debut().getYear();
  		float sum1=0;
  		float sum2=0;
  		float sum3=0;

  		String sexe= rec.getContrats().getDemandeContrat().getUsers().getSexe();
  		/*Si Homme*/
  		if(sexe.equalsIgnoreCase("homme")) 
  		{ 
  			Data d1=data.findById(x+t).get();
  			float lxt=d1.getLxH();

  			for(int k=m;k<(w-x-t);k++)
  			{
  				Data d3= data.findById(x+t+k).get();
  				float lxtk=d3.getLxH();
  				sum1+= (lxtk/lxt)*puissance(v,k);
  			}

  			for(int k=0;k<(m-1-t);k++)
  			{
  				Data d4= data.findById(x+t+k).get();
  				float lxtk=d4.getLxH();
  				sum2+= (lxtk/lxt)*puissance(v,k);
  			}

  			for(int k=1;k<(m-1-t);k++)
  			{
  				Data d5= data.findById(x+t+k).get();
  				float lxtk=d5.getLxH();
  				sum3+= (lxtk/lxt)*puissance(v,k);
  			}

  			if(rec.getContrats().getDemandeContrat().getChoixPrime().equals(Prime.Prime_Periodique))  
  			{ pm=(capital*sum1)-prime*sum2;

  			}
  			else{                                                                  // Prime_Unique
  				pm=prime;	
  			}
  			return pm;
  		}
  		/*si femme*/

  		else
  		{ 
  			Data d1=data.findById(x+t).get();
  			float lxt=d1.getLxF();

  			for(int k=m;k<(w-x-t);k++)
  			{
  				Data d3= data.findById(x+t+k).get();
  				float lxtk=d3.getLxF();
  				sum1+= (lxtk/lxt)*puissance(v,k);
  			}
  			for(int k=0;k<(m-1-t);k++)
  			{
  				Data d4= data.findById(x+t+k).get();
  				float lxtk=d4.getLxF();
  				sum2+= (lxtk/lxt)*puissance(v,k);
  			}
  			for(int k=1;k<(m-1-t);k++)
  			{
  				Data d5= data.findById(x+t+k).get();
  				float lxtk=d5.getLxF();
  				sum3+= (lxtk/lxt)*puissance(v,k);
  			}
  			if(rec.getContrats().getDemandeContrat().getChoixPrime().equals(Prime.Prime_Periodique))  
  			{ pm=(capital*sum1)-prime*sum2;

  			}
  			else{                                                                  // Prime_Unique
  				pm=prime;	
  			}
  			return pm;
  		}
  	}

  	/*Capitalisation sur n années avec n = date actuelle-date debut contrat*/	
  	public double capitalisation(int idrec, double i){
  		// c'est la valeur acquise du contrat : capitalisation
  		Date currentDate = new Date();
  		ReclamationSinistre rec=reclamationSinistre.findById(idrec).get();
  		double va=0;
  		int n = (currentDate.getYear()-rec.getContrats().getDate_debut().getYear());
  		float p=rec.getContrats().getPrimeCommerciale();
  		if(rec.getContrats().getDemandeContrat().getChoixPrime().equals(Prime.Prime_Periodique))
  			va= p*((puissance((1+i),n)-1)/i);
  		else 
  			va=p*puissance((1+i),n);
  		return va;

  	}

  	
  	public static double puissance (double i , double p ) {
  		double x1 = i ;
  		double x2 = Math.log(( x1) ) ;
  		double x3 = x2*p ;
  		double taux = Math.exp((x3));	
  		return   taux ;
  	}	

  	/* Contrat Capital différé ou Rente viagere arrivé à terme: appel fonction se fait dans l'affichage des contrats*/
  	public void VersementEcheanceVie(){ 
  		List<Contrat> list=contrat.findAll();
  		for(int i=0;i<list.size();i++)
  		{
  			Contrat c=list.get(i);
  			if(c.getDemandeContrat().getNomContrat().equalsIgnoreCase("capital differe"))	
  			{ Date currentDate = new Date();
  			  int result = currentDate.compareTo(c.getDate_fin());
  			  if(result>0)
  			  { c.setEtat("échu");
  			  float score= (c.getDemandeContrat().getUsers().getScoring()*c.getRemboursement())/100;
  			  Sinistre s=new Sinistre();
  			  s.setDateReglement(currentDate);
  			/* 2 pts = 2 %*/
  			  s.setCapitalRembourse(c.getRemboursement()+score);
  			  sinistre.save(s);

  			  }	
  			} 
  			else if(c.getDemandeContrat().getNomContrat().equalsIgnoreCase("rente viagere"))	
  			{ Date currentDate = new Date();
  				int result = currentDate.compareTo(c.getDate_fin());
  				if(result>0)
  				{ c.setEtat("échu");
  				float score= (c.getDemandeContrat().getUsers().getScoring()*c.getRemboursement())/100;
  				Sinistre s=new Sinistre();
  				s.setDateReglement(currentDate);
  				/* 2 pts = 20 %*/
  				s.setCapitalRembourse(c.getRemboursement()+score*10);
  				sinistre.save(s);

  				}	
  			} 


  		}  

  	}

  	public float calculPSAP(){ // se calcul chaque arrêt de compte: fin d'année
  		float psap=0;
  		float x=0;
  		float y=0;
  		float z=0;
  		double i=0.002;
  		List<ReclamationSinistre> reclist= reclamationSinistre.findByTraiteReclamation(0);
  		/*Toutes les réclamations déclarées mais non encore traitées*/
  		for(int j=0;j<reclist.size();j++){
  			if(reclist.get(j).getTypeReclamation().equalsIgnoreCase("deces")){
  				x+= capitaldeces(reclist.get(j).getNumReclamation(),i);
  			}
  			else if(reclist.get(j).getTypeReclamation().equalsIgnoreCase("rachat total")){
  				y+= capitalrachatotal(reclist.get(j).getNumReclamation(),i);
  			}
  			else if(reclist.get(j).getTypeReclamation().equalsIgnoreCase("rachat partiel")){
  				z+= capitalrachapartiel(reclist.get(j).getNumReclamation(),i);;
  			}			
  		}
  		psap=x+y+z;
  		return psap;

  	}
  	
  	public float calculremboursementsante(int idrec){
  		ReclamationSinistre rec=reclamationSinistre.findById(idrec).get();
  		Data_sante remboursement = data_sante.findById(27).get();
  		Data_sante plafond = data_sante.findById(28).get();
  		
  		float r;
  		float p;
  		
  		if(rec.getContrats().getDemandeContrat().getNomContrat().equalsIgnoreCase("sante"))
  			
  		{
  			if(rec.getTypeReclamation().equalsIgnoreCase("consultations_Visites")){
  			 r=(remboursement.getConsultations_Visites()/100)*rec.getFrais();
  			 p=plafond.getConsultations_Visites();
  			if(r>p) return p;
  			else return r;
  		
  		}
  		if(rec.getTypeReclamation().equalsIgnoreCase("frais_Pharmaceutiques")){
  			r=(remboursement.getFrais_Pharmaceutiques()/100)*rec.getFrais();
  			 p=plafond.getFrais_Pharmaceutiques();
  			if(rec.getFrais()>p) return p;
  			else return r;
  		
  		}
  		if(rec.getTypeReclamation().equalsIgnoreCase("actes_Medicaux_Courants")){
  			 r=(remboursement.getActes_Medicaux_Courants()/100)*rec.getFrais();
  			 p=plafond.getActes_Medicaux_Courants();
  			if(r>p) return p;
  			else return r;
  		
  		}
  		if(rec.getTypeReclamation().equalsIgnoreCase("hospitalisation")){
  			 r=(remboursement.getHospitalisation()/100)*rec.getFrais();
  			 p=plafond.getHospitalisation();
  			if(r>p) return p;
  			else return r;
  		
  		}
  		if(rec.getTypeReclamation().equalsIgnoreCase("analyse")){
  			 r=(remboursement.getAnalyse()/100)*rec.getFrais();
  			 p=plafond.getAnalyse();
  			if(r>p) return p;
  			else return r;
  		
  		}
  		if(rec.getTypeReclamation().equalsIgnoreCase("radio_Physio")){
  			 r=(remboursement.getRadio_Physio()/100)*rec.getFrais();
  			 p=plafond.getRadio_Physio();
  			if(r>p) return p;
  			else return r;
  		
  		}
  		if(rec.getTypeReclamation().equalsIgnoreCase("optique")){
  			 r=(remboursement.getOptique()/100)*rec.getFrais();
  			 p=plafond.getOptique();
  			if(r>p) return p;
  			else return r;
  		
  		}
  		if(rec.getTypeReclamation().equalsIgnoreCase("frais_Chirugicaux")){
  			 r=(remboursement.getFrais_Chirurgicaux()/100)*rec.getFrais();
  			 p=plafond.getFrais_Chirurgicaux();
  			if(r>p) return p;
  			else return r;
  		
  		}
  		if(rec.getTypeReclamation().equalsIgnoreCase("dentaires")){
  			 r=(remboursement.getDentaires()/100)*rec.getFrais();
  			 p=plafond.getDentaires();
  			if(r>p) return p;
  			else return r;
  		
  		}
  		if(rec.getTypeReclamation().equalsIgnoreCase("maternite")){
  			 r=(remboursement.getMaternite()/100)*rec.getFrais();
  			 p=plafond.getMaternite();
  			if(r>p) return p;
  			else return r;
  		
  		}
  		
  		else
  			r=(remboursement.getAutres()/100)*rec.getFrais();
  			p=plafond.getAutres();
  			if(r>p) return p;
  			else return r;
  		}
  		else return 0;
  	}
  	
  	/*se fait à l'affichage des contrats*/
  	public void calculScoreSanté(){
  		/* pour les contrats de type santé, le score sera déduit de la prochaine prime*/
  		List<Contrat> list=contrat.findAll();
  		for(int i=0;i<list.size();i++)
  		{
  			Contrat c=list.get(i);
  			if(c.getDemandeContrat().getNomContrat().equalsIgnoreCase("sante")){
  				float score= (c.getDemandeContrat().getUsers().getScoring()*10*c.getPrimeCommerciale())/100;
  				c.setPrimeCommerciale(c.getPrimeCommerciale()-score);	
  			}

  		}

  	}
  	
  	/***************** API MAIL********************************/
  	 public  void mail(ReclamationSinistre rec , String s) {

  	        final String username = "pidevassurance@gmail.com";
  	        final String password = "wadiebelghith1";
  	    	
  	        Properties prop = new Properties();
  	        prop.put("mail.smtp.host", "smtp.gmail.com");
  	        prop.put("mail.smtp.port", "587");
  	        prop.put("mail.smtp.auth", "true");
  	        prop.put("mail.smtp.starttls.enable", "true"); //TLS
  	        
  	        Session session = Session.getInstance(prop,
  	                new javax.mail.Authenticator() {
  	                    protected PasswordAuthentication getPasswordAuthentication() {
  	                        return new PasswordAuthentication(username, password);
  	                    }
  	                });

  	        try {

  	            Message message = new MimeMessage(session);
  	            String msg = "<html xmlns='http://www.w3.org/1999/xhtml' xmlns:o='urn:schemas-microsoft-com:office:office' xmlns:v='urn:schemas-microsoft-com:vml'>"
  	            		+"<head>"
  	            		+"<meta content='text/html; charset=utf-8' http-equiv='Content-Type'/>"
  	            		+"<meta content='width=device-width' name='viewport'/>"
  	            		+"<meta content='IE=edge' http-equiv='X-UA-Compatible'/>"
  	            		+"<title></title>"
  	            		+"<style type='text/css'>"
  	            		+"		body {"
  	            		+"			margin: 0;"
  	            		+"			padding: 0;"
  	            		+"		}"

  	            		+"		table,"
  	            		+"		td,"
  	            		+"		tr {"
  	            		+"			vertical-align: top;"
  	            		+"			border-collapse: collapse;"
  	            		+"		}"

  	            		+"		* {"
  	            		+"			line-height: inherit;"
  	            		+"		}"

  	            		+"		a[x-apple-data-detectors=true] {"
  	            		+"			color: inherit !important;"
  	            		+"			text-decoration: none !important;"
  	            		+"		}"
  	            		+"	</style>"
  	            		+"<style id='media-query' type='text/css'>"
  	            		+"		@media (max-width: 670px) {"

  	            		+"			.block-grid,"
  	            		+"			.col {"
  	            					+"	min-width: 320px !important;"
  	            		+"				max-width: 100% !important;"
  	            		+"				display: block !important;"
  	            		+"			}"

  	            					+".block-grid {"
  	            					+"	width: 100% !important;"
  	            					+"}"

  	            					+".col {"
  	            					+"	width: 100% !important;"
  	            		+"			}"

  	            		+"			.col_cont {"
  	            				+"		margin: 0 auto;"
  	            		+"			}"

  	            					+"img.fullwidth,"
  	            					+"img.fullwidthOnMobile {"
  	            		+"				max-width: 100% !important;"
  	            		+"			}"

  	            					+".no-stack .col {"
  	            		+"				min-width: 0 !important;"
  	            		+"				display: table-cell !important;"
  	            		+"			}"

  	            					+".no-stack.two-up .col {"
  	            		+"				width: 50% !important;"
  	            		+"			}"

  	            					+".no-stack .col.num2 {"
  	            		+"				width: 16.6% !important;"
  	            		+"			}"

  	            				+"	.no-stack .col.num3 {"
  	            		+"				width: 25% !important;"
  	            		+"			}"

  	            		+"			.no-stack .col.num4 {"
  	            		+"			width: 33% !important;"
  	            		+"		}"

  	            			+"		.no-stack .col.num5 {"
  	            				+"		width: 41.6% !important;"
  	            				+"}"

  	            					+".no-stack .col.num6 {"
  	            						+"width: 50% !important;"
  	            						+"}"

  	            					+".no-stack .col.num7 {"
  	            					+"	width: 58.3% !important;"
  	            					+"}"

  	            					+".no-stack .col.num8 {"
  	            						+"width: 66.6% !important;"
  	            						+"}"

  	            					+".no-stack .col.num9 {"
  	            						+"width: 75% !important;"
  	            						+"}"

  	            					+".no-stack .col.num10 {"
  	            					+"	width: 83.3% !important;"
  	            					+"}"

  	            					+".video-block {"
  	            					+"	max-width: none !important;"
  	            					+"}"

  	            					+".mobile_hide {"
  	            					+"	min-height: 0px;"
  	            					+"	max-height: 0px;"
  	            					+"	max-width: 0px;"
  	            					+"	display: none;"
  	            					+"	overflow: hidden;"
  	            					+"	font-size: 0px;"
  	            					+"}"

  	            					+".desktop_hide {"
  	            					+"	display: block !important;"
  	            					+"	max-height: none !important;"
  	            					+"}"
  	            					+"}"
  	            			+"</style>"
  	            		+"</head>"
  	            		+"<body class='clean-body' style='margin: 0; padding: 0; -webkit-text-size-adjust: 100%; background-color: #FFFFFF;'>"
  	            		+"<!--[if IE]><div class='ie-browser'><![endif]-->"
  	            		+"<table bgcolor='#FFFFFF' cellpadding='0' cellspacing='0' class='nl-container' role='presentation' style='table-layout: fixed; vertical-align: top; min-width: 320px; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; background-color: #FFFFFF; width: 100%;' valign='top' width='100%'>"
  	            		+"<tbody>"
  	            		+"<tr style='vertical-align: top;' valign='top'>"
  	            		+"<td style='word-break: break-word; vertical-align: top;' valign='top'>"
  	            		+"<!--[if (mso)|(IE)]><table width='100%' cellpadding='0' cellspacing='0' border='0'><tr><td align='center' style='background-color:#FFFFFF'><![endif]-->"
  	            		+"<div style='background-color:#82a1df;'>"
  	            		+"<div class='block-grid mixed-two-up' style='min-width: 320px; max-width: 650px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; Margin: 0 auto; background-color: transparent;'>"
  	            		+"<div style='border-collapse: collapse;display: table;width: 100%;background-color:transparent;'>"
  	            		+"<div class='col num4' style='display: table-cell; vertical-align: top; max-width: 320px; min-width: 216px; width: 216px;'>"
  	            		+"<div class='col_cont' style='width:100% !important;'>"
  	            		+"<div style='border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;'>"
  	            		+"<div align='center' class='img-container center fixedwidth' style='padding-right: 0px;padding-left: 0px;'>"
  	            		+"<div style='font-size:1px;line-height:60px'> </div><img align='center' alt='I'm an image' border='0' class='center fixedwidth' src='cid:lift' style='text-decoration: none; -ms-interpolation-mode: bicubic; height: auto; border: 0; width: 100%; max-width: 216px; display: block;' title='I'm an image' width='216'/>"
  	            		+"<div style='font-size:1px;line-height:60px'> </div>"
  	            		+"</div>"
  	            		+"</div>"
  	            		+"</div>"
  	            		+"</div>"
  	            		+"<div class='col num8' style='display: table-cell; vertical-align: top; max-width: 320px; min-width: 432px; width: 433px;'>"
  	            		+"<div class='col_cont' style='width:100% !important;'>"
  	            		+"<div style='border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;'>"
  	            		+"<div style='color:#555555;font-family:Arial, Helvetica Neue, Helvetica, sans-serif;line-height:1.2;padding-top:10px;padding-right:10px;padding-bottom:10px;padding-left:10px;'>"
  	            		+"<div class='txtTinyMce-wrapper' style='line-height: 1.2; font-size: 12px; font-family: Arial, Helvetica Neue, Helvetica, sans-serif; color: #555555; mso-line-height-alt: 14px;'>"
  	            		+"<p style='font-size: 34px; line-height: 1.2; text-align: left; word-break: break-word; font-family: Arial, Helvetica Neue, Helvetica, sans-serif; mso-line-height-alt: 41px; margin: 0;'><span style='font-size: 34px; color: #ffffff;'><em><strong>Claim declaration</strong></em></span></p>"
  	            		+"</div>"
  	            		+"</div>"
  	            		+"<table border='0' cellpadding='0' cellspacing='0' class='divider' role='presentation' style='table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; min-width: 100%; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;' valign='top' width='100%'>"
  	            		+"<tbody>"
  	            		+"<tr style='vertical-align: top;' valign='top'>"
  	            		+"<td class='divider_inner' style='word-break: break-word; vertical-align: top; min-width: 100%; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; padding-top: 10px; padding-right: 10px; padding-bottom: 10px; padding-left: 10px;' valign='top'>"
  	            		+"<table align='center' border='0' cellpadding='0' cellspacing='0' class='divider_content' role='presentation' style='table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; border-top: 1px solid #BBBBBB; width: 100%;' valign='top' width='100%'>"
  	            		+"<tbody>"
  	            		+"<tr style='vertical-align: top;' valign='top'>"
  	            		+"<td style='word-break: break-word; vertical-align: top; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;' valign='top'><span></span></td>"
  	            		+"</tr>"
  	            		+"</tbody>"
  	            		+"</table>"
  	            		+"</td>"
  	            		+"</tr>"
  	            		+"</tbody>"
  	            		+"</table>"
  	            		+"<div style='color:#ffffff;font-family:Arial, Helvetica Neue, Helvetica, sans-serif;line-height:1.2;padding-top:10px;padding-right:10px;padding-bottom:10px;padding-left:10px;'>"
  	            		+"<div class='txtTinyMce-wrapper' style='font-size: 12px; line-height: 1.2; color: #ffffff; font-family: Arial, Helvetica Neue, Helvetica, sans-serif; mso-line-height-alt: 14px;'>"
  	            		+"<p style='font-size: 20px; line-height: 1.2; word-break: break-word; mso-line-height-alt: 24px; margin: 0;'><span style='font-size: 20px; color: #ffffff;'><span style='color: #800080;'> Hello Mr/Mrs "+rec.getContrats().getDemandeContrat().getUsers().getFirst_name()+" "+rec.getContrats().getDemandeContrat().getUsers().getLast_name()+"Your claim request  for the contract cannot be taken in charge, you contract must be at least made since 2 years  </span> </span></p>"
  	            		+"<p style='font-size: 12px; line-height: 1.2; word-break: break-word; mso-line-height-alt: 14px; margin: 0;'> </p>"
  	            		+"</div>"
  	            		+"</div>"
  	            		+"<p style='font-size: 12px; line-height: 1.2; word-break: break-word; mso-line-height-alt: 14px; margin: 0;'> </p>"
  	            		+"</div>"
  	            		+"</div>"
  	            		+"</div>"
  	            		+"</div>"
  	            		+"</div>"
  	            		+"</div>"
  	            		+"</div>"
  	            		+"</div>"
  	            		+"<div style='background-color:#82a1df;'>"
  	            		+"<div class='block-grid' style='min-width: 320px; max-width: 650px; overflow-wrap: break-word; word-wrap: break-word; word-break: break-word; Margin: 0 auto; background-color: transparent;'>"
  	            		+"<div style='border-collapse: collapse;display: table;width: 100%;background-color:transparent;'>"
  	            		+"<div class='col num12' style='min-width: 320px; max-width: 650px; display: table-cell; vertical-align: top; width: 650px;'>"
  	            		+"<div class='col_cont' style='width:100% !important;'>"
  	            		+"<div style='border-top:0px solid transparent; border-left:0px solid transparent; border-bottom:0px solid transparent; border-right:0px solid transparent; padding-top:5px; padding-bottom:5px; padding-right: 0px; padding-left: 0px;'>"
  	            		+"<table border='0' cellpadding='0' cellspacing='0' class='divider' role='presentation' style='table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; min-width: 100%; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;' valign='top' width='100%'>"
  	            		+"<tbody>"
  	            		+"<tr style='vertical-align: top;' valign='top'>"
  	            		+"<td class='divider_inner' style='word-break: break-word; vertical-align: top; min-width: 100%; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%; padding-top: 10px; padding-right: 10px; padding-bottom: 10px; padding-left: 10px;' valign='top'>"
  	            		+"<table align='center' border='0' cellpadding='0' cellspacing='0' class='divider_content' role='presentation' style='table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; border-top: 1px solid #BBBBBB; width: 100%;' valign='top' width='100%'>"
  	            		+"<tbody>"
  	            		+"<tr style='vertical-align: top;' valign='top'>"
  	            		+"<td style='word-break: break-word; vertical-align: top; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;' valign='top'><span></span></td>"
  	            		+"</tr>"
  	            		+"</tbody>"
  	            		+"</table>"
  	            		+"</td>"
  	            		+"</tr>"
  	            		+"</tbody>"
  	            		+"</table>"
  	            		+"<table cellpadding='0' cellspacing='0' class='social_icons' role='presentation' style='table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt;' valign='top' width='100%'>"
  	            		+"<tbody>"
  	            		+"<tr style='vertical-align: top;' valign='top'>"
  	            		+"<td style='word-break: break-word; vertical-align: top; padding-top: 10px; padding-right: 10px; padding-bottom: 10px; padding-left: 10px;' valign='top'>"
  	            		+"<table align='center' cellpadding='0' cellspacing='0' class='social_table' role='presentation' style='table-layout: fixed; vertical-align: top; border-spacing: 0; border-collapse: collapse; mso-table-tspace: 0; mso-table-rspace: 0; mso-table-bspace: 0; mso-table-lspace: 0;' valign='top'>"
  	            		+"<tbody>"
  	            		+"<tr align='center' style='vertical-align: top; display: inline-block; text-align: center;' valign='top'>"
  	            		+"<td style='word-break: break-word; vertical-align: top; padding-bottom: 0; padding-right: 2.5px; padding-left: 2.5px;' valign='top'><a href='https://www.facebook.com/' target='_blank'><img alt='Facebook' height='32' src='cid:facebook' style='text-decoration: none; -ms-interpolation-mode: bicubic; height: auto; border: 0; display: block;' title='facebook' width='32'/></a></td>"
  	            		+"<td style='word-break: break-word; vertical-align: top; padding-bottom: 0; padding-right: 2.5px; padding-left: 2.5px;' valign='top'><a href='https://www.twitter.com/' target='_blank'><img alt='Twitter' height='32' src='cid:twitter' style='text-decoration: none; -ms-interpolation-mode: bicubic; height: auto; border: 0; display: block;' title='twitter' width='32'/></a></td>"
  	            		+"<td style='word-break: break-word; vertical-align: top; padding-bottom: 0; padding-right: 2.5px; padding-left: 2.5px;' valign='top'><a href='https://www.linkedin.com/' target='_blank'><img alt='Linkedin' height='32' src='cid:linkedin' style='text-decoration: none; -ms-interpolation-mode: bicubic; height: auto; border: 0; display: block;' title='linkedin' width='32'/></a></td>"
  	            		+"<td style='word-break: break-word; vertical-align: top; padding-bottom: 0; padding-right: 2.5px; padding-left: 2.5px;' valign='top'><a href='https://www.instagram.com/' target='_blank'><img alt='Instagram' height='32' src='cid:instagram' style='text-decoration: none; -ms-interpolation-mode: bicubic; height: auto; border: 0; display: block;' title='instagram' width='32'/></a></td>"
  	            		+"</tr>"
  	            		+"</tbody>"
  	            		+"</table>"
  	            		+"</td>"
  	            		+"</tr>"
  	            		+"</tbody>"
  	            		+"</table>"
  	            		+"<div style='color:#393d47;font-family:Arial, Helvetica Neue, Helvetica, sans-serif;line-height:1.2;padding-top:10px;padding-right:10px;padding-bottom:10px;padding-left:10px;'>"
  	            		+"<div class='txtTinyMce-wrapper' style='line-height: 1.2; font-size: 12px; color: #393d47; font-family: Arial, Helvetica Neue, Helvetica, sans-serif; mso-line-height-alt: 14px;'>"
  	            		+"<p style='font-size: 14px; line-height: 1.2; word-break: break-word; text-align: center; mso-line-height-alt: 17px; margin: 0;'>©2021 Micro-Lift. all rights reserved.</p>"
  	            		+"</div>"
  	            		+"</div>"
  	            		+"</div>"
  	            		+"</div>"
  	            		+"</div>"
  	            		+"</div>"
  	            		+"</div>"
  	            		+"</div>"
  	            		+"</td>"
  	            		+"</tr>"
  	            		+"</tbody>"
  	            		+"</table>"
  	            		+"</body>"
  	            		+"</html>";
  	            		
  	            message.setFrom(new InternetAddress("Tarunsunny143@gmail.com"));
  	            message.setRecipients(
  	                    Message.RecipientType.TO,
  	                    InternetAddress.parse(s)
  	            );
  	            message.setSubject("Ya weldi me3anekich l 7a9");
  	          //  message.setContent(msg, "text/html; charset=utf-8");
  	        //    message.setText(msg, "utf-8", "html");
  	            message.setContent(msg, "text/html; charset=utf-8");
  	            message.saveChanges();
//  	            message.setText(msg);

  	            Transport.send(message);

  	            System.out.println("Done");

  	        } catch (MessagingException e) {
  	            e.printStackTrace();
  	        }
  	    }

  	 
  			
}
