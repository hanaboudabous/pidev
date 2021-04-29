package tn.esprit.spring.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
			d.setTraite(1);
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
		
				float primePure  = calculesante( age ,  id ,  sexe) ;
				float primeCommerciale = (float) (primePure + primePure*frais) ;
				c.setPrimePure(primePure);
				c.setPrimeCommerciale(primeCommerciale);
				
				c.setAcceptation(1);
				c.setRemboursement(0);

				}
			contrat.save(c);
			// on va changer l attribut traite .. la demande n'est plus en cours
			//d.setTraite(1);
			//demandeContrat.save(d);		
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
	
		
	
		
		// ici , si le client va accepter ou pas le contrat
		@Transactional
		public void acceptContrat(int rep ,int idContrat){
			Contrat c = contrat.findById(idContrat).get();
			//s il ne va pas accepter
			if (rep == 0){
				demandeContrat.deleteById(c.getDemandeContrat().getNumDemande());
			}
			else if (rep == 1){
				c.setAcceptation(1);
				contrat.save(c);
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

}
