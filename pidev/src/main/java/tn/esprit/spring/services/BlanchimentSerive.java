package tn.esprit.spring.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entity.Blanchiment;
import tn.esprit.spring.entity.Contrat;
import tn.esprit.spring.entity.Prime;
import tn.esprit.spring.entity.Sinistre;
import tn.esprit.spring.entity.User;
import tn.esprit.spring.repository.IActifFinancier;
import tn.esprit.spring.repository.IBlanchimentRepo;
import tn.esprit.spring.repository.IContratRepo;
import tn.esprit.spring.repository.IDemandeContratRepo;
import tn.esprit.spring.repository.IReclamationSinistreRepos;
import tn.esprit.spring.repository.ISinistreRepos;

@Service
public class BlanchimentSerive {

	@Autowired
	IActifFinancier actifs ;
	
	@Autowired
	IReclamationSinistreRepos reclamations ;
	@Autowired
	ISinistreRepos sinistre ;
	@Autowired
	IContratRepo contrats ;
	@Autowired
	IDemandeContratRepo demandeContrat ;
	@Autowired
	IBlanchimentRepo blan ;
	/*
	 les cas sont : prime kbira ,  ok
	  montant unique kbir ,  ok
	   barcha des contrat f wa9et 9sir , ok
	   
	  rachat total     ok,
	   barcha marat rachat partiel   ok ,
	    prime par espéce ,    
	 montat kbir w paiement de l etranger ,
	  eli mich ya3ti l prime mouch houa mich ye5ou lflous mb3d    ok,
	   tbadal l eli mich ye5ou l flous me8ir ma ykoun 3aandou 3ale9a bil souscripteur
	   souscription par les memebres de la meme famille
*/
	
	
	/*fama fazet o5rin zeda .. yab3ath flous liquide fil bousta b esm wa7ed mafamech mb3d y9oulou ma weslouch ye5i l boosta yraj3oulou cheque
	 * yda5al flous liquide fil casino mb3d ya5sar chwaya w y5arajhom mb3d yraj3oulou cheque zeda
	 * 
	 * Les contrats d’assurance vie dont la prime annuelle ne dépasse pas 1000€ ou dont la prime unique ne dépasse pas 2500€ (article R. 561-16 1°
	 */
	public void verifBlanchiment(int idUser){
		Blanchiment b = new Blanchiment();
		b.setValide("Verifer ce contrat !");
		b.setNiveau(0);
		List<Contrat> contrat = contrats.findAll();
		for(Contrat c : contrat){
			if(c.getDemandeContrat().getChoixPrime() == Prime.Prime_Unique){
				if ( c.getPrimeCommerciale() > 1000){
					b.setCause("le montant de la prime est un peu grand par rapport au salire " + c.getDemandeContrat().getUsers().getCIN()  );
				}
			}
			if(c.getDemandeContrat().getChoixPrime() == Prime.Prime_Unique){
				if ( c.getPrimeCommerciale() > 2500){
					b.setCause("le montant de la prime est un peu grand par rapport au salire " + c.getDemandeContrat().getUsers().getCIN()  );
				}
			}
			
		}	
	}
	
	public void verifMontantContratBlanchiment(Contrat c){
		Blanchiment b = new Blanchiment();
		b.setValide("Verifer ce contrat !");
		b.setNiveau(0);
	
		if(c.getDemandeContrat().getChoixPrime() == Prime.Prime_Unique){
			if ( c.getPrimeCommerciale() > 1000){
				b.setCause("le montant de la prime est un peu grand par rapport au salire " + c.getDemandeContrat().getUsers().getCIN()  );
			
			}
		}
		if(c.getDemandeContrat().getChoixPrime() == Prime.Prime_Unique){
			if ( c.getPrimeCommerciale() > 2500){
				b.setCause("le montant de la prime est un peu grand par rapport au salire " + c.getDemandeContrat().getUsers().getCIN()  );
			}
		}
	
		blan.save(b);	
	}
	

	public void verifNombreContratBlanchiment(Contrat c){
		Blanchiment b = new Blanchiment();
		b.setValide("Verifer ce contrat !");
		b.setNiveau(0);
		int user = c.getDemandeContrat().getUsers().getUser_ID();	
		List<Contrat> cont =  contrats.LesContratsClient(user);
		System.out.println(" annnneeeeeee   " + cont.get(0).getDate_debut());
		if(cont.get(0).getDate_debut().getYear() == c.getDate_debut().getYear()  || cont.get(1).getDate_debut() == c.getDate_debut()  ){
			b.setCause("Plus que 2 police d assurance dans la meme annee" + c.getDemandeContrat().getUsers().getCIN()  );
		}	
		blan.save(b);	
	}
	public void verifRachatTotalBlanchiment( Sinistre s){
		String rT = "rachat total";
		Blanchiment b = new Blanchiment();
		b.setValide("Verifer ce contrat !");
		b.setNiveau(0);
		if(s.getReclamationSinitre().getTypeReclamation() == rT){
			if (s.getDateReglement().getYear() - s.getReclamationSinitre().getContrats().getDate_debut().getYear() <= 2){
				b.setCause("Rachat totale avant 2 ans .. c est tot mec !"  );

			}
		}
		blan.save(b);	
	}
	
	public void verifRachatPartielBlanchiment( Sinistre s){
		String rP = "rachat partiel";
		Blanchiment b = new Blanchiment();
		int somme = 0 ;
		b.setValide("Verifer ce contrat !");
		b.setNiveau(0);
		int user = s.getReclamationSinitre().getContrats().getDemandeContrat().getUsers().getUser_ID();
		if(s.getReclamationSinitre().getTypeReclamation() == rP){
			List<Sinistre> l = 	sinistre.findAll();
			for(Sinistre sin: l){
				if(sin.getReclamationSinitre().getContrats().getDemandeContrat().getUsers().getUser_ID() == user){
					somme=somme+1;
				}
			}		
				if(somme > 2){
					b.setCause("Rachat partiel plus de 2 fois .. c est beaucoup mec !"  );
				}	
			}
		blan.save(b);	
	}	
	
	public void verifBeneficiareBlanchiment(Contrat c ){
		Blanchiment b = new Blanchiment();
		b.setValide("Verifer ce contrat !");
		b.setNiveau(0);
		int user = c.getDemandeContrat().getUsers().getUser_ID();
		List<Contrat> cont =  contrats.LesContratsClient(user);
		if(cont.get(0).getDemandeContrat().getCinBeneficiaire()!= cont.get(0).getDemandeContrat().getUsers().getCIN()){
			b.setCause("le souscripteur n'est pas le beneficiaire .. vous devez verifer mec !"  );
		}
		blan.save(b);		
	}
	
}
