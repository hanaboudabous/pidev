package tn.esprit.spring.services;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entity.User;
import tn.esprit.spring.repository.IUserRepo;
import tn.esprit.spring.entity.Cagnotte;
import tn.esprit.spring.entity.Contrat;
import tn.esprit.spring.entity.Contribution;
import tn.esprit.spring.repository.ICagnotteRepo;
import tn.esprit.spring.repository.IContributionRepo;



@Service
public class ContributionService {
	@Autowired
	IContributionRepo contribution ;

	@Autowired
	ICagnotteRepo cag ;

	@Autowired
	IUserRepo user ;
		
	/*Ajout d'une contribution par un user et affectation de cette contribution au contrat le plus ancien de la cagnotte*/
	public void ajouterContribution(Contribution con , int iduser){
      User u = user.findById(iduser).get();
      float contr=con.getNbtickets()*20;
      con.setMontant(contr); // 1 ticket = 20 dt
      con.setUsers(u);
      con.setPaye(1); // c bon khalast beha
      
      /*Si la cagnotte ne comporte pas de contrats en cours**/	
	  int empty=cag.findAllByEtat("en cours").size();
      if(empty!=0){  //cagnotte non vide
    	  contribution.save(con);
    	  scoring(iduser);
      /*Somme de tous les tickets non paye (paye=0)*/
     
      String state="en cours";
      List<Cagnotte> cagnotte=cag.findByEtatOrderByDateAjout(state);
      Cagnotte first= cagnotte.get(0);// si etat= en cours
      
       
       /*affectation de la contribution à la cagnotte (le contrat le plus ancien)*/
       
       while((first.getMontantactuel()+contr)>=first.getMontantfinal())
      {
    	   float reste= contr-first.getMontantfinal()+first.getMontantactuel();
    	   first.setMontantactuel(first.getMontantfinal());
    	   first.setEtat("pleine");
    	   
    	   cagnotte=cag.findByEtatOrderByDateAjout(state); // si etat= en cours
    	   first= cagnotte.get(0);// si etat = en cours
    	   contr=reste;
    	   cag.save(first);
       }
       
       if((first.getMontantactuel()+contr)<first.getMontantfinal()){
    	   float xx=first.getMontantactuel()+contr;
    	   first.setMontantactuel(xx);
    	   cag.save(first);
    	   
       }
       
       
      } 
       
    
    }
	
	/*AFFICHAGE DES CONTRIBUTIONS*/
	@Transactional
	public List<Contribution> afficherContribution(){
		return contribution.findAll();
	}
	
	
	/*Calcul de la somme de tous les tickets payés par un certain user*/
	public int calculerSommeTickets(int iduser){
		int tot=contribution.ISommeTickets(iduser);
		return tot;
		
		
	}
	
	public void scoring(int iduser){
		User u = user.findById(iduser).get();
		int nbtickets=calculerSommeTickets(iduser);
		int score= nbtickets/5;  // 5tickets = 1 point, 10 tickets= 2 pts....
		u.setScoring(score);
		//email quand le score change
		
	}
		

}
