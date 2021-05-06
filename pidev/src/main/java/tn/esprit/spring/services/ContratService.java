package tn.esprit.spring.services;

import java.awt.Color;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entity.Cagnotte;
import tn.esprit.spring.repository.ICagnotteRepo;
import tn.esprit.spring.entity.Contrat;
import tn.esprit.spring.entity.DemandeContrat;
import tn.esprit.spring.entity.Prime;
import tn.esprit.spring.entity.Type_sante;
import tn.esprit.spring.repository.IContratRepo;
import tn.esprit.spring.repository.IData_santeRepo;
import tn.esprit.spring.repository.IDemandeContratRepo;
import tn.esprit.spring.repository.Taux_InteretRepo;
import tn.esprit.spring.repository.Type_santeRepo;


@Service
public class ContratService {
	
	/*******************************************/
	@Autowired	
	ICagnotteRepo  cag ;
	
	@Autowired
	IContratRepo contrat ;
	
	@Autowired
	IDemandeContratRepo demandeContrat ;
	@Autowired
	DataService dataService ;
	
	
	@Autowired
	Taux_InteretRepo taux_Interet ;
	
	@Autowired
	Type_santeRepo type_santeRepo ;
	@Autowired
	IData_santeRepo data_santeRepo ;
	@Autowired	
	BlanchimentSerive  blanchimentSerive ;
	
	@Transactional
	public List<Contrat> afficheContrat(){
		return contrat.findAll();
	}
	

	@Transactional
	public void deleteContrat(int idContrat){
		contrat.deleteById(idContrat);
	}
	
	String sante = "Santé" ;
	String CapitalDiffere = "Capital différé";
	String renteViagere = "Rente viagére";
	String vieEntiere = "Vie entiére";
	Prime primeUnique = Prime.Prime_Unique ;
	Prime primePeriodique =  Prime.Prime_Periodique;
	
	// faire le calcul et la tarification , si l assurance accpete la demande ( elle peut refuser )
	@Transactional
	public void calculeContrat(int id){
		DemandeContrat d = demandeContrat.findById(id).get();
		//double interet = 0.01*taux_Interet.findFirstByOrderByIdDesc().getTauxInteret();   /// le dernier interet  publié par la bct
		double interet = 0.002; /// 0.2 %
		String sexe = d.getUsers().getSexe(); // hethi lazem nbadalha , njibha m table user mta3 chirou !... badaltha
		int age = age(d.getUsers().getBirth_date()) ; // hethi njibha mil user men 3and chirou zeda !!! .. kif kif
		double frais = 0.03 ; // on va considerer tous les frais du contrat .. 3 %
		

		//Date currentUtilDate = new Date();
		Date currentUtilDate = d.getDateDemande();

		int a = currentUtilDate.getYear() + d.getNombreAnnee() ;
		Date currentUtilDate2 = new Date();
		currentUtilDate2.setYear(a);
		String name_contrat = d.getNomContrat();
		if(  /* d.getSalaire()*/  (2000 * 10)   == d.getCapitalAssure()){
			// scenario : si il va refusé la demande 
			//demandeContrat.deleteById(id);
			String s = "votre demande est annulé , votre salaire ne peut pas supporter votre demande de capital assuré " ;
			d.setCause(s);
			//d.setTraite(1);
			demandeContrat.save(d);
			System.out.println(s); }
		else {
			///// on va faire ici le calcule de tarification , on va considerer 1 pour chaque valeur 
			Contrat c = new Contrat();
			c.setEtat("en cours");
			c.setDemandeContrat(d);
			c.setDate_debut(currentUtilDate);
			c.setDate_fin(currentUtilDate2);	
			c.setAcceptation(0);
			//////////////////////////////////////////// capital differe ////////////////////////////////////////////////////////
			if(d.getNomContrat().equals(CapitalDiffere)){  // choix du contrat
				if(d.getChoixPrime().equals(primeUnique)){ // choix type de prime 		
						if(d.getCapitalAssure() != 0){ // on a  ici calculer le capital demande et on va calculé les primes
//	public double calculePUCapital_vieCapital(float capital ,int age , int annee, double interet , String sexe){
							double calculePUCapital_viePrime = dataService.calculePUCapital_vieCapital(d.getCapitalAssure(),age ,d.getNombreAnnee(),interet , sexe );
							c.setRemboursement(d.getCapitalAssure());
							c.setPrimePure((float) calculePUCapital_viePrime);
							c.setPrimeCommerciale((float) (calculePUCapital_viePrime+calculePUCapital_viePrime*frais));	}
						else{
							float primeP = (float) (d.getVal_prime()-d.getVal_prime()*frais) ;
							double calculePUCapital_vieCapital = dataService.calculePUCapital_viePrime(primeP,age ,d.getNombreAnnee(),interet , sexe );
							c.setPrimeCommerciale(d.getVal_prime());
							c.setPrimePure(primeP);
							c.setRemboursement((float) calculePUCapital_vieCapital); }
				}
				else if (d.getChoixPrime().equals(primePeriodique)){
						if(d.getCapitalAssure() != 0){
							double calculePPCapital_viePrime = dataService.calculePPCapital_vie( d.getCapitalAssure()  , 0 , age ,  d.getNombreAnnee(),  interet ,  sexe);
							double primeCommerciale = calculePPCapital_viePrime +calculePPCapital_viePrime*frais ;
							c.setRemboursement(d.getCapitalAssure());
							c.setPrimePure((float) calculePPCapital_viePrime);
							c.setPrimeCommerciale((float) primeCommerciale);}
						else {
							double primePure = d.getVal_prime()-d.getVal_prime()*frais ;
							double calculePPCapital_vieCapital = dataService.calculePPCapital_vie( 0,(float) primePure , age ,  d.getNombreAnnee(),  interet ,  sexe);
							c.setRemboursement((float) calculePPCapital_vieCapital);
							c.setPrimePure((float) primePure);
							c.setPrimeCommerciale(d.getVal_prime()); }
				}				
			}
			//////////////////////////////////////////// rente  Viagere /////////////////////////////////////////////
			else if(d.getNomContrat().equals(renteViagere)){ 
				if(d.getChoixPrime().equals(primeUnique)){ // choix type de prime 		
					if(d.getCapitalAssure() != 0){ // on a  ici calculer le capital demande et on va calculé les primes
//	public double calculePURenteillimteRente(float rente,int age ,  double interet , String sexe){ // à terme échue)						
						double calculePURenteillimtePrime = dataService.calculePURenteillimteRente(d.getCapitalAssure(),age ,interet , sexe );
						double calculePURenteillimtePrimeCommerciale =  calculePURenteillimtePrime + calculePURenteillimtePrime*frais ;
						c.setRemboursement(d.getCapitalAssure());
						c.setPrimePure((float) calculePURenteillimtePrime);
						c.setPrimeCommerciale((float) calculePURenteillimtePrimeCommerciale);	}
					else{
	//	public double calculePURenteillimtePrime(float prime,int age ,  double interet , String sexe){ // à terme échue)
						float primeP = (float) (d.getVal_prime()-d.getVal_prime()*frais) ;
						double calculePURenteillimteRente = dataService.calculePURenteillimtePrime(primeP,age,interet , sexe );
						c.setPrimeCommerciale(d.getVal_prime());
						c.setPrimePure(primeP);
						c.setRemboursement((float) calculePURenteillimteRente); }
			}
			else if (d.getChoixPrime().equals(primePeriodique)){
//		public double calculePPRente( float rente, float prime ,int age , int annee, double interet , String sexe ){// a terme d’avance
					if(d.getCapitalAssure() != 0){
						double calculePPRentePrime = dataService.calculePPRente( d.getCapitalAssure()  , 0 , age ,  d.getNombreAnnee(),  interet ,  sexe);
						double primeCommerciale = calculePPRentePrime +calculePPRentePrime*frais ;
						c.setRemboursement(d.getCapitalAssure()); // ici le capital est considere come des rente viagere !!! attention ..
						c.setPrimePure((float) calculePPRentePrime);
						c.setPrimeCommerciale((float) primeCommerciale);}
					else {
						double primePure = d.getVal_prime()-d.getVal_prime()*frais ;
						double calculePPRenteCapital = dataService.calculePPRente( 0,(float) primePure , age ,  d.getNombreAnnee(),  interet ,  sexe);
						c.setRemboursement((float) calculePPRenteCapital);
						c.setPrimePure((float) primePure);
						c.setPrimeCommerciale(d.getVal_prime()); }
				}		
			}
//////////////////////////////////////////// Vie entiere /////////////////////////////////////////////
			else if(d.getNomContrat().equals(vieEntiere)){  // choix du contrat
				if(d.getChoixPrime().equals(primeUnique)){ // choix type de prime 		
					if(d.getCapitalAssure() != 0){ // on a  ici calculer le capital demande et on va calculé les primes
// 	public double calculePUVieEntiereCapital(float capital,int age ,  double interet ,String sexe){
							double calculePUVieEntierePrime = dataService.calculePUVieEntiereCapital(d.getCapitalAssure(),age ,interet , sexe );
							c.setRemboursement(d.getCapitalAssure());
							c.setPrimePure((float) calculePUVieEntierePrime);
							c.setPrimeCommerciale((float) (calculePUVieEntierePrime+calculePUVieEntierePrime*frais));	}
						else{
	//  	public double calculePUVieEntierePrime(float prime ,int age ,  double interet ,String sexe){
							float primeP = (float) (d.getVal_prime()-d.getVal_prime()*frais) ;
							double calculePUVieEntiereCapital = dataService.calculePUVieEntierePrime(primeP,age ,interet , sexe );
							c.setPrimeCommerciale(d.getVal_prime());
							c.setPrimePure(primeP);
							c.setRemboursement((float) calculePUVieEntiereCapital); }
				}
				else if (d.getChoixPrime().equals(primePeriodique)){
//		public double calculePPVieEntiere(float prime ,float capital ,int age , int annee, double interet , String sexe ){// a terme d’avance
						if(d.getCapitalAssure() != 0){
							double calculePPVieEntierePrime = dataService.calculePPVieEntiere(0, d.getCapitalAssure()   , age ,  d.getNombreAnnee(),  interet ,  sexe);
							double primeCommerciale = calculePPVieEntierePrime +calculePPVieEntierePrime*frais ;
							c.setRemboursement(d.getCapitalAssure());
							c.setPrimePure((float) calculePPVieEntierePrime);
							c.setPrimeCommerciale((float) primeCommerciale);}
						else {
							double primePure = d.getVal_prime()-d.getVal_prime()*frais ;
							double calculePPVieEntiereCapital = dataService.calculePPVieEntiere( (float) primePure, 0 , age ,  d.getNombreAnnee(),  interet ,  sexe);
							c.setRemboursement((float) calculePPVieEntiereCapital);
							c.setPrimePure((float) primePure);
							c.setPrimeCommerciale(d.getVal_prime()); }
				}				
			}
			//////////////////////////////////////////// santeee /////////////////////////////////////////////
			else if(d.getNomContrat().equals(sante)){ 
		
				float primePure2  = calculesante( age ,  id ,  sexe) ;
				System.out.println("     ....      " + primePure2);
				float primeCommerciale = (float) (primePure2 + primePure2*frais) ;
				c.setPrimePure(primePure2);
				c.setPrimeCommerciale(primeCommerciale);
				
				c.setAcceptation(1);
				c.setRemboursement(0);

				}
			d.setTraite(1);
			contrat.save(c);
			// on va changer l attribut traite .. la demande n'est plus en cours
			//d.setTraite(1);
			//demandeContrat.save(d);	
			mail(c);
		}
	
	}
	
	public float calculesante(int age , int iddemande , String gender){	
		float somme = 0  ;	
		String homme = "homme";
		String femme= "femme";		
	
		DemandeContrat d = demandeContrat.findById(iddemande).get();
		Type_sante l = type_santeRepo.findByDemandeContrat(d);
		int nombrefille = l.getNombrefille() ;
		int nombreenfant = l.getNombreenfant();
		System.out.println("trah nchoufou   ");	
		if(age < 5 ){
			if(homme == gender){	somme = sommeSante(1,iddemande);}
			else if(femme == gender){		somme = sommeSante(2,iddemande);  }
		}
		else if (age >= 5 && age <15 ){
			if(homme == gender){	somme = sommeSante(3,iddemande);}
			else if(femme == gender){		somme = sommeSante(4,iddemande);  }
		}
		else if (age >= 15 && age <25){
			if(homme == gender){	
				if(l.isAjouconjoint() == true){
					if(l.isAjoutEnf()== true){
						somme = sommeSante(7,iddemande) + sommeSante(10,iddemande) +nombreenfant*sommeSante(5,iddemande) +nombrefille*sommeSante(6,iddemande) ;
					}
					else {
						somme = sommeSante(7,iddemande) + sommeSante(10,iddemande) ;
					}
				}
				else {
					if(l.isAjoutEnf()== true){
						somme = sommeSante(7,iddemande) +nombreenfant*sommeSante(5,iddemande) +nombrefille*sommeSante(6,iddemande) ;
					}
					else {
						somme = sommeSante(7,iddemande) ;
					}				
				}
			}
			else {
				if(l.isAjouconjoint() == true){
					if(l.isAjoutEnf()== true){
						somme = sommeSante(8,iddemande) + sommeSante(9,iddemande) +nombreenfant*sommeSante(5,iddemande) +nombrefille*sommeSante(6,iddemande) ;
					}
					else {
						somme = sommeSante(8,iddemande) + sommeSante(9,iddemande) ;
					}
				}
				else {
					if(l.isAjoutEnf()== true){
						somme = sommeSante(8,iddemande) +nombreenfant*sommeSante(5,iddemande) +nombrefille*sommeSante(6,iddemande) ;
					}
					else {
						somme = sommeSante(8,iddemande) ;
					}				
				}
			}	
		}
		else if (age >= 25 && age <35){	
			if(homme == gender){	
				if(l.isAjouconjoint() == true){

					somme =  sommeSante(11,iddemande) +  sommeSante(14,iddemande) ;
				}
				else {
					somme =  sommeSante(11,iddemande);
				}			
			}
			else if (femme == gender){
				if(l.isAjouconjoint() == true){
					somme =  sommeSante(12,iddemande) +  sommeSante(13,iddemande) ;
				}
				else {
					somme =  sommeSante(12,iddemande);
				}						
			}			
		}
		else if (age >= 35 && age <45){	
			System.out.println("hollaaa");
			if(homme == gender){	
				if(l.isAjouconjoint() == true){
					somme =  sommeSante(15,iddemande) +  sommeSante(18,iddemande) ;
				}
				else {
					somme =  sommeSante(15,iddemande);
				}			
			}
			else if (femme == gender){
				if(l.isAjouconjoint() == true){
					somme =  sommeSante(16,iddemande) +  sommeSante(17,iddemande) ;
				}
				else {
					somme =  sommeSante(16,iddemande);
				}						
			}			
		}
		else if (age >= 45 && age <60){	
			if(homme.equals(gender)){
				if(l.isAjouconjoint() == true){
					System.out.println("jiiiit !!!!  ");

					somme =  sommeSante(19,iddemande) +  sommeSante(22,iddemande) ;
				}
				else {
					System.out.println("jiiiit 2222222222 !!!!  ");

					somme =  sommeSante(19,iddemande);
				}			
			}
			else if (femme == gender){
				if(l.isAjouconjoint() == true){
					somme =  sommeSante(20,iddemande) +  sommeSante(21,iddemande) ;
				}
				else {
					somme =  sommeSante(20,iddemande);
				}						
			}			
		}
		else if (age >= 60 ){	
			if(homme == gender){	
				if(l.isAjouconjoint() == true){
					somme =  sommeSante(23,iddemande) +  sommeSante(26,iddemande) ;
				}
				else {
					somme =  sommeSante(23,iddemande);
				}			
			}
			else if (femme == gender){
				if(l.isAjouconjoint() == true){
					somme =  sommeSante(24,iddemande) +  sommeSante(25,iddemande) ;
				}
				else {
					somme =  sommeSante(24,iddemande);
				}						
			}			
		}
		
	System.out.println("yyeeeeeeeeey   " + somme);
	return somme ;
	}
	
	
	public float sommeSante(int i , int  iddemande){
		DemandeContrat d = demandeContrat.findById(iddemande).get();
		float somme = 0 ;
		Type_sante l = type_santeRepo.findByDemandeContrat(d);
		if(l.isConsultations_Visites()== true){ 
			somme = somme + data_santeRepo.findById(i).get().getConsultations_Visites() ;
		}
		 if (l.isFrais_Pharmaceutiques()== true){
			somme = somme + data_santeRepo.findById(i).get().getFrais_Pharmaceutiques();
		}
		 if(l.isHospitalisation()==true){
			somme = somme + data_santeRepo.findById(i).get().getHospitalisation();
		}
		 if(l.isActes_Medicaux_Courants()==true){
			somme = somme + data_santeRepo.findById(i).get().getActes_Medicaux_Courants() ;
		}
		 if(l.isAnalyse()==true){
			somme = somme + data_santeRepo.findById(i).get().getAnalyse() ;
		}
		 if(l.isRadio_Physio()==true){
			somme = somme + data_santeRepo.findById(i).get().getRadio_Physio() ;
		}
		 if(l.isOptique()==true){
			somme = somme + data_santeRepo.findById(i).get().getOptique() ;
		}
		 if(l.isFrais_Chirurgicaux()==true){
			somme = somme + data_santeRepo.findById(i).get().getFrais_Chirurgicaux() ;
		}
		 if(l.isDentaires()==true){
			somme = somme + data_santeRepo.findById(i).get().getDentaires() ;
		}
		 if(l.isMaternite()==true){
			somme = somme + data_santeRepo.findById(i).get().getMaternite() ;
		}
		 if(l.isAutres()==true){
			somme = somme + data_santeRepo.findById(i).get().getAutres() ;
		}
		//System.out.println(" yaaaallllaaaa    " + somme);
		return somme ;
		
	}
	
	
	
	
		// liste les contrats non pas encore repondu par le client
		@Transactional
		public List<Contrat> listeNonREPContrat(){
			return contrat.findByAcceptation(0);
		}
		// liste les contrats non pas encore repondu par le client
		@Transactional
		public List<Contrat> listeREPContrat(){
			return contrat.findByAcceptation(1);
		}
	
		
		
		// liste des contrats proposés pour le client ... hethom yetl3ou 3and l client w houa mb3d yaccepti wela la ..
		@Transactional
		public List<Contrat> ProposeContrat(int idUser){	
			return contrat.ProposeContrat(idUser);
		}
	   /// liste des contrats acceptés 
		@Transactional
		public List<Contrat> Contrataccepter(int idUser){	
			return contrat.Contrataccepter(idUser);
		}
	   	  
	
		
		// ici , si le client va accepter ou pas le contrat
		@Transactional
		public void acceptContrat(int rep ,int idContrat) throws Exception{
			Contrat c = contrat.findById(idContrat).get();
			//s il ne va pas accepter
			if (rep == 0){
				demandeContrat.deleteById(c.getDemandeContrat().getNumDemande());
			}
			else if (rep == 1){
				c.setAcceptation(1);
				contrat.save(c);
				pdf2(c);
				blanchimentSerive.verifNombreContratBlanchiment(c); /* hetha lil blanchiment */
				blanchimentSerive.verifMontantContratBlanchiment(c); /* hetha lil blanchiment */
				
			}
		
		}
		
		
		/* calcul de l'age depuis la date de naissance*/
		public int age(Date birthdate){
			Date currentDate = new Date();
			DateFormat formatter = new SimpleDateFormat("yyyyMMdd"); 
			int d1 = Integer.parseInt(formatter.format(birthdate));                            
			int d2 = Integer.parseInt(formatter.format(currentDate));                          
			int age = (d2 - d1) / 10000; 
			System.out.println("voici l'age:"+age);
			return age;
		}

		

	  	/***************** API MAIL********************************/
	  	 public  void mail(Contrat c) {

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
	  	            		+"<p style='font-size: 34px; line-height: 1.2; text-align: left; word-break: break-word; font-family: Arial, Helvetica Neue, Helvetica, sans-serif; mso-line-height-alt: 41px; margin: 0;'><span style='font-size: 34px; color: #ffffff;'><em><strong>Contract suggestion</strong></em></span></p>"
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
	  	            		+"<p style='font-size: 20px; line-height: 1.2; word-break: break-word; mso-line-height-alt: 24px; margin: 0;'><span style='font-size: 20px; color: #ffffff;'><span style='color: #800080;'> Hello Mr/Mrs "+c.getDemandeContrat().getUsers().getFirst_name()+" "+c.getDemandeContrat().getUsers().getLast_name()+", please check your account for the contract suggestion  </span> </span></p>"
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
	  	                    InternetAddress.parse(c.getDemandeContrat().getUsers().getEmail())
	  	            );
	  	            message.setSubject("Contrat proposition");
	  	          //  message.setContent(msg, "text/html; charset=utf-8");
	  	        //    message.setText(msg, "utf-8", "html");
	  	            message.setContent(msg, "text/html; charset=utf-8");
	  	            message.saveChanges();
//	  	            message.setText(msg);

	  	            Transport.send(message);

	  	            System.out.println("Done");

	  	        } catch (MessagingException e) {
	  	            e.printStackTrace();
	  	        }
	  	    }
		
	     //// hethi eli 5demet biha es7i7a .. lo5rin njarab
	     public void pdf2(Contrat c) throws Exception {
	         String outputFileName = "contrat.pdf";
	      
	         // Create a document and add a page to it
	         PDDocument document = new PDDocument();
	         PDPage page1 = new PDPage(PDRectangle.A4);
	             // PDRectangle.LETTER and others are also possible
	         PDRectangle rect = page1.getMediaBox();
	             // rect can be used to get the page width and height
	         document.addPage(page1);
	  
	         // Create a new font object selecting one of the PDF base fonts
	         PDFont fontPlain = PDType1Font.HELVETICA;
	         PDFont fontBold = PDType1Font.HELVETICA_BOLD;
	         PDFont fontItalic = PDType1Font.HELVETICA_OBLIQUE;
	         PDFont fontMono = PDType1Font.COURIER;
	  
	         // Start a new content stream which will "hold" the content that's about to be created
	         PDPageContentStream cos = new PDPageContentStream(document, page1);
	  
	         try {
	             PDImageXObject ximage = PDImageXObject.createFromFile("chourou.png", document);
	             float scale = 0.08f; // alter this value to set the image size
	             cos.drawImage(ximage, 500, 765, ximage.getWidth()*scale, ximage.getHeight()*scale);
	         } catch (IOException ioex) {
	             System.out.println("No image for you");
	         }
	         int line = 0;
	         cos.beginText();
	         cos.setStrokingColor(Color.blue);
	         cos.setFont(PDType1Font.HELVETICA, 26);
	         cos.newLineAtOffset(250, rect.getHeight() - 50*(++line));
	         cos.setStrokingColor(Color.blue);
	         cos.drawString("Contract");
	         cos.endText();
	     
	         cos.beginText();
	         cos.setFont(fontPlain, 16);
	         cos.newLineAtOffset(100, rect.getHeight() - 50*(++line));
	         cos.showText("Type d'assurance : ");
	         cos.showText(c.getDemandeContrat().getTypeContrat());
	         cos.endText();
	         
	         cos.beginText();
	         cos.setFont(fontPlain, 16);
	         cos.newLineAtOffset(100, rect.getHeight() - 50*(++line));
	         cos.showText("Nom assurance : ");
	         cos.showText(c.getDemandeContrat().getNomContrat());
	         cos.endText();
	  
	         
	         cos.beginText();
	         cos.setFont(fontPlain, 12);
	         cos.newLineAtOffset(100, rect.getHeight() - 50*(++line));
	         cos.showText("Prenom : ");
	         cos.showText(c.getDemandeContrat().getUsers().getLast_name());
	         cos.endText();
	         
	         cos.beginText();
	         cos.setFont(fontPlain, 12);
	         cos.newLineAtOffset(100, rect.getHeight() - 50*(++line));
	         cos.showText("Nom : ");
	         cos.showText(c.getDemandeContrat().getUsers().getFirst_name());
	         cos.endText();
	  
	         cos.beginText();
	         cos.setFont(fontPlain, 12);
	         cos.newLineAtOffset(100, rect.getHeight() - 50*(++line));
	         cos.showText("Dure de contrat : ");
	         cos.showText(String.valueOf(c.getDemandeContrat().getNombreAnnee()));
	         cos.endText();
	         
	         cos.beginText();
	         cos.setFont(fontPlain, 12);
	         cos.newLineAtOffset(100, rect.getHeight() - 50*(++line));
	         cos.showText("Prime commerciale : ");
	         cos.showText(String.valueOf(c.getPrimeCommerciale()));
	         cos.endText();
	  
	         
	     
	         
	         cos.setNonStrokingColor(Color.BLACK);
	         cos.setStrokingColor(Color.BLACK);
	         cos.setLineWidth(1);
	         cos.moveTo(10, 25);
	         cos.lineTo(580, 25);
	         cos.closeAndStroke();
	         cos.setLineWidth(5);
	         cos.moveTo(10, 10);
	         cos.lineTo(580, 10);
	         cos.closeAndStroke();
	  

	         cos.close();
	         document.save(outputFileName);
	         document.close();
	         /*********************************************** Page 2  ******************************************************/
	    
	     }
	     /************************************** JSF  **********************/
			// liste les contrats par id client qui peuvent entrer en cagnotte
			@Transactional
			public List<Contrat> listeContratPossiblesDansCagnotte(int iduser){

				List<Contrat> list = new ArrayList<>();
				for(Contrat l : contrat.IafficheContratUser(iduser))
				{
					Optional<Cagnotte> c= cag.findByContrat(l); // mouch mawjoud fel cagnotte
					System.out.println("ccc  "+c);
					if(!c.isPresent())
					{System.out.println("mouch mawjouuuuuud");
					list.add(l);
					}

				}
				return list ;

			}
			
			//liste des contrats d'un certain user
			public List<Contrat> listeContratdeUser(int iduser){

				return contrat.LesContratsClient(iduser);

			}
}
