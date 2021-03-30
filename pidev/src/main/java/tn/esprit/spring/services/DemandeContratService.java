package Pidev.service;

import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Pidev.entity.DemandeContrat;
import Pidev.entity.User;
import Pidev.repository.IDemandeContratRepo;
import Pidev.repository.IUserRepo;

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
	
	// lister les demande  Contrat en cours ( non traite )
		@Transactional
		public List<DemandeContrat> afficheDemandeContratNonTraite(){
			return demandeContrat.findByTraite(0);
		}
	
		// lister les demande  Contrat  ( traite )
				@Transactional
				public List<DemandeContrat> afficheDemandeContratTraite(){
					return demandeContrat.findByTraite(1);
				
				}
			
	
	
	
	
	
	

	
	
}
