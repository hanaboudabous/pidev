package tn.esprit.spring.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.repository.IUserRepo;
import tn.esprit.spring.entity.DemandeContrat;
import tn.esprit.spring.entity.User;
import tn.esprit.spring.repository.IDemandeContratRepo;
import tn.esprit.spring.repository.*;

@Service
public class DemandeContratService {

	@Autowired
	IDemandeContratRepo demandeContrat;
	
	@Autowired
	IUserRepo userRepo;
	
	
	// ajout demande contrat
	@Transactional
	public void ajoutDemandeContrat(DemandeContrat d , int id , String type , String nom){
		Date date = new Date();
		User u = userRepo.findById(id).get();
		d.setDateDemande(date);
		d.setUsers(u);
		d.setTypeContrat(type);
		d.setNomContrat(nom);   
		demandeContrat.save(d);		
	}
	
	
	// lister les demande Contrat
	@Transactional
	public List<DemandeContrat> afficheDemandeContrat(){
		return demandeContrat.findAll();
	}
	
	// refus a cause des documents
	public void checkDocument(int id , String cause ){
		
		DemandeContrat d = demandeContrat.findById(id).get();
	
		d.setCause(cause);
		d.setTraite(1);
		demandeContrat.save(d);
	}
	// refus a cause des documents
	public void checkDocument(int id , DemandeContrat cause ){
		
		DemandeContrat d = demandeContrat.findById(id).get();
	
		d.setCause(cause.getCause());
		d.setTraite(1);
		demandeContrat.save(d);
	}
		
	
	
	
	// delete demande contrat
	@Transactional
	public void deleteDemandeContrat(int idDemande){
		demandeContrat.deleteById(idDemande);
	}
	
	
	//verifer l etat de la demande contrat ( en cours ... )
	@Transactional
	public void verifeEtatDemandeContrat(int idDemande){
		DemandeContrat d = demandeContrat.findById(idDemande).get();
		if (d.getTraite()== 0) { System.out.println("demande n'est pas traité");}
		else {System.out.println("demande traité");}
	}

	///////// hethom mta3 l employee
	// lister les demande  Contrat en cours ( non traite )
	@Transactional
	public List<DemandeContrat> afficheDemandeContratNonTraite(){
		return demandeContrat.IafficheDemandeContratNonTraite();
	}

	// lister les demande  Contrat  ( traite )
	@Transactional
	public List<DemandeContrat> afficheDemandeContratTraite(){
		return demandeContrat.findByTraite(1);

	}	
	///////// hethom mta3 l user
	// lister les demande  Contrat en cours ( non traite )
	@Transactional
	public List<DemandeContrat> afficheDemandeContratNonTraiteUSER(int idUser){
		User u = userRepo.findById(idUser).get();
		List<DemandeContrat> list = new ArrayList<>();
		for(DemandeContrat l : demandeContrat.findByUsers(u)){
			if (l.getTraite() == 0){
				list.add(l);
			}
		}
		return list ;
	}
	// lister les demande  Contrat  ( traite )
	@Transactional
	public List<DemandeContrat> afficheDemandeContratTraiteUSER(int idUser){

		return demandeContrat.afficheDemandeContratTraiteUSERRepo(idUser) ;
			

	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Transactional
	public void verifieTypeContrat(int id){
		DemandeContrat d = demandeContrat.findById(id).get();
		String s ;
		System.out.println("***************" + d.getSante() != 0 +"-----------------");
		System.out.println("***************" + d.getSante()  +"-----------------");

		System.out.println("***************" + d.getSante() == 0 +"-----------------");

		if(d.getSante() == 0 ){
			
					if(d.getVie()==1 && d.getDeces() == 0){
								if(d.getCapitalOuRente()== 0){
									s =" rente viag";
									System.out.println(s);
								}
								else{
									s = "capital diff";
									System.out.println(s);
								}
					}
					else if (d.getVie()==0 && d.getDeces() == 1){
						s="vie entiere";
						System.out.println();
					}
					else if (d.getVie()==1 && d.getDeces() == 1){
						s="on peut pas vie et deces";
						System.out.println(s);
					}	
					else{
						System.out.println("hello");
						FacesMessage facesMessage =

								new FacesMessage("Login Failed: please check your username/password and try again.");

					}
		}
		else{
			s="sante";
			System.out.println(s);
		}
	
	}
	
	
	
	
}
