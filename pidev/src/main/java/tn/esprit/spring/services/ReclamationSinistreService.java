package tn.esprit.spring.services;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entity.DemandeContrat;
import tn.esprit.spring.entity.Type_sante;
import tn.esprit.spring.entity.User;
import tn.esprit.spring.repository.IReclamationSinistreRepos;
import tn.esprit.spring.repository.IUserRepo;
import tn.esprit.spring.repository.Type_santeRepo;
import tn.esprit.spring.entity.Contrat;
import tn.esprit.spring.entity.ReclamationSinistre;
import tn.esprit.spring.repository.IContratRepo;


@Service

public class ReclamationSinistreService {


	@Autowired
	IReclamationSinistreRepos reclamationSinistree ;

	@Autowired
	IContratRepo contrat ;

	@Autowired
	Type_santeRepo sante ;
	
	@Autowired
	IUserRepo user ;

	@Transactional
	public void ajoutReclamationSinistre(ReclamationSinistre r , int id){

		Contrat c = contrat.findById(id).get();
		r.setContrats(c);
		reclamationSinistree.save(r);



	}


	

	@Transactional
	public List<ReclamationSinistre> afficheReclamationSinistre(){
		return reclamationSinistree.findAll();
	}


	@Transactional
	public void deleteReclamationSinistre(int idReclamationSinistre){
		reclamationSinistree.deleteById(idReclamationSinistre);
	}


	/* Ajout d'une reclamation selon son type*/
	@Transactional
	public void AjoutReclamationSelonType(ReclamationSinistre r,int id){ 
		Date currentDate = new Date();
		Contrat c = contrat.findById(id).get();
		r.setContrats(c);
		r.setDateReclamation(currentDate);
		r.setTraiteReclamation(0);
		reclamationSinistree.save(r);

		//VerifierEtatReclamation(r.getNumReclamation()); 
		VerifierTypeReclamation(r.getNumReclamation());


	}	




	/* verifer l'etat de la réclamation de sinsitre ( traitée, en attente ... )*/

	/*@Transactional
	public void VerifierEtatReclamation(int idRec){
		ReclamationSinistre rec = reclamationSinistree.findById(idRec).get();
		if (rec.getTraiteReclamation()==0) { System.out.println("reclamation n'est pas encore traitée (en attente)");}
		else {System.out.println("reclamation traitée");}
	}*/

	
	

	/* donner un type à la réclamation (rachat total, partiel, deces..) selon les reponses du client aux questions*/		
	@Transactional
	public void  VerifierTypeReclamation(int idRec){
		ReclamationSinistre rec = reclamationSinistree.findById(idRec).get();
		/*Type Demande contrat vie entiere, rente viagere ou cap differe*/
		if(rec.getContrats().getDemandeContrat().getNomContrat().equalsIgnoreCase("vie entiere")|| rec.getContrats().getDemandeContrat().getNomContrat().equalsIgnoreCase("capital differe")||rec.getContrats().getDemandeContrat().getNomContrat().equalsIgnoreCase("rente viagere")){

			 if((rec.getRachattotal()==1)&&(rec.getRachatpartiel()==0)&&(rec.getDeces()==0)){ // oui pour rachat total
				rec.setTypeReclamation("rachat total");
			}
			else if((rec.getRachattotal()==0)&&(rec.getRachatpartiel()==1)&&(rec.getDeces()==0)){ // oui pour rachat partiel
				rec.setTypeReclamation("rachat partiel");
			}
			else if((rec.getRachattotal()==0)&&(rec.getRachatpartiel()==0)&&(rec.getDeces()==1)){ // oui pour deces
				rec.setTypeReclamation("deces");
			}
		}	

		/*Type Demande contrat Sante*/

		else if(rec.getContrats().getDemandeContrat().getNomContrat().equalsIgnoreCase("sante") ){	
			Contrat c = rec.getContrats();
			DemandeContrat d=c.getDemandeContrat();
			Type_sante l = sante.findByDemandeContrat(d);
			boolean v=false;

			
			// remarque: pour le jsf on ne vera plus de verification sur les branches de maladies achetees car on n'affichera au user que celles dans sa demande contrat

			 if(rec.getMaladie()==1){                                   //consultations_Visites
				v =VerifMaladie(c.getNumContrat(),l.isConsultations_Visites());

				if(v)
				{

					rec.setTypeReclamation("consultations_Visites");
				}
				else deleteReclamationSinistre(rec.getNumReclamation());
			}
			else  if(rec.getMaladie()==2){                                   //frais_Pharmaceutiques
				v =VerifMaladie(c.getNumContrat(),l.isFrais_Pharmaceutiques());

				if(v)
				{

					rec.setTypeReclamation("frais_Pharmaceutiques");
				}
				else deleteReclamationSinistre(rec.getNumReclamation());


			}
			else  if(rec.getMaladie()==3){                                    //actes_Medicaux_Courants
				v =VerifMaladie(c.getNumContrat(),l.isActes_Medicaux_Courants());

				if(v)
				{

					rec.setTypeReclamation("actes_Medicaux_Courants");				
				}
				else deleteReclamationSinistre(rec.getNumReclamation());

			}
			else if(rec.getMaladie()==4){                                    //hospitalisation
				v =VerifMaladie(c.getNumContrat(),l.isHospitalisation());

				if(v)
				{

					rec.setTypeReclamation("hospitalisation");				
				}
				else deleteReclamationSinistre(rec.getNumReclamation());

			}
			else if(rec.getMaladie()==5){                                    //analyse
				v =VerifMaladie(c.getNumContrat(),l.isAnalyse());

				if(v)
				{

					rec.setTypeReclamation("analyse");				
				}
				else deleteReclamationSinistre(rec.getNumReclamation());

			}
			else if(rec.getMaladie()==6){                                    //radio_Physio
				v =VerifMaladie(c.getNumContrat(),l.isRadio_Physio());

				if(v)
				{

					rec.setTypeReclamation("radio_Physio");				
				}
				else deleteReclamationSinistre(rec.getNumReclamation());

			}
			else if(rec.getMaladie()==7){                                    //optique
				v =VerifMaladie(c.getNumContrat(),l.isOptique());

				if(v)
				{

					rec.setTypeReclamation("optique");				
				}
				else deleteReclamationSinistre(rec.getNumReclamation());

			}
			else if(rec.getMaladie()==8){                                    //frais_Chirugicaux
				v =VerifMaladie(c.getNumContrat(),l.isFrais_Chirurgicaux());

				if(v)
				{

					rec.setTypeReclamation("frais_Chirugicaux");				
				}
				else deleteReclamationSinistre(rec.getNumReclamation());

			}
			else if(rec.getMaladie()==9){                                    //dentaires
				v =VerifMaladie(c.getNumContrat(),l.isDentaires());

				if(v)
				{

					rec.setTypeReclamation("dentaires");				
				}
				else deleteReclamationSinistre(rec.getNumReclamation());

			}
			else if(rec.getMaladie()==10){                                    //maternite
				v =VerifMaladie(c.getNumContrat(),l.isMaternite());

				if(v)
				{

					rec.setTypeReclamation("maternite");				
				}
				else deleteReclamationSinistre(rec.getNumReclamation());

			}
			else if(rec.getMaladie()==11){                                    //autres
				v =VerifMaladie(c.getNumContrat(),l.isAutres());

				if(v)
				{

					rec.setTypeReclamation("autres");				
				}
				else deleteReclamationSinistre(rec.getNumReclamation());

			}

		}

	}

	public String Nomducontrat(int id){
		Contrat c = contrat.findById(id).get();
		String x=c.getDemandeContrat().getNomContrat();
		return x;

	}



	/********************************************************************/
	public List<ReclamationSinistre> afficheRecSinTraite(){
		return reclamationSinistree.findByTraiteReclamation(1);
	}


	
	public List<ReclamationSinistre> afficheRecSinNonTraite(){
		return reclamationSinistree.findByTraiteReclamation(0);

	}
	
	
	public List<ReclamationSinistre> afficheRecSinNonTraiteUser(int iduser){
		User u = user.findById(iduser).get();

		List<ReclamationSinistre> list = new ArrayList<>();
		for(ReclamationSinistre l : reclamationSinistree.IafficheRecSinNonTraiteUser(iduser)){
			if (l.getTraiteReclamation() == 0){
				list.add(l);
			}
		}
		return list ;
	}
	
	public List<ReclamationSinistre> afficheRecSinTraiteUser(int iduser){
		User u = user.findById(iduser).get();
		List<ReclamationSinistre> list = new ArrayList<>();
		for(ReclamationSinistre l : reclamationSinistree.IafficheRecSinTraiteUser(iduser)){
			if (l.getTraiteReclamation() == 1){
				list.add(l);
			}
		}
		return list ;
	}
	
	
	/*****************************************************************/

	public boolean VerifMaladie(int idcon,boolean m){
		Contrat c = contrat.findById(idcon).get();
		String x=Nomducontrat(idcon);
		if(x.equalsIgnoreCase("sante"))
		{ 
			DemandeContrat d=c.getDemandeContrat();
			Type_sante l = sante.findByDemandeContrat(d);
			if(m) //true
			{ return true; }
			else 
			{ return false;}
		}
		else
		{ 
			return false;
		}


	}
	
/******************* JSF ************************************************/
	
	public Type_sante typesante(int idcontrat) 
	{  
		Contrat c= contrat.findById(idcontrat).get();
		 Type_sante t= sante.findByDemandeContrat(c.getDemandeContrat());
		return t;

	}
	
	public User usercon(int idcontrat) 
	{   System.out.println("ruuun1" + idcontrat);
		Contrat c= contrat.findById(idcontrat).get();
		System.out.println("ruuun2"+c.getNumContrat());
		 User u= c.getDemandeContrat().getUsers();
		 System.out.println("ruuun3");
		 System.out.println("user"+u);
		return u;

	}





}