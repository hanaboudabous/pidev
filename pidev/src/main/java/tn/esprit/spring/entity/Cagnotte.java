package tn.esprit.spring.entity;

import java.io.Serializable;
import java.util.Date;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Cagnotte implements Serializable{
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	 @Column( name = "id")
	private int NumCagnotte;
	
	@Temporal(TemporalType.DATE)
	private Date dateAjout ;
	
	@Temporal(TemporalType.DATE)
	private Date dateEcheance ;
	
	private float montantfinal;
	
	private float montantactuel;
	
    private String etat; 
	/********************************************************************************/
	@JsonIgnore
	 @OneToOne
	 private Contrat contrat ;
/************************************************************************************/

	public int getNumCagnotte() {
		return NumCagnotte;
	}


	public void setNumCagnotte(int numCagnotte) {
		NumCagnotte = numCagnotte;
	}


	


	public float getMontantfinal() {
		return montantfinal;
	}


	public void setMontantfinal(float montantfinal) {
		this.montantfinal = montantfinal;
	}


	public float getMontantactuel() {
		return montantactuel;
	}


	public void setMontantactuel(float montantactuel) {
		this.montantactuel = montantactuel;
	}


	public Contrat getContrat() {
		return contrat;
	}


	public void setContrat(Contrat contrat) {
		this.contrat = contrat;
	}
	
	


	
	
	


	public String getEtat() {
		return etat;
	}


	public void setEtat(String etat) {
		this.etat = etat;
	}


	


	public Cagnotte(int numCagnotte, Date dateAjout, Date dateEcheance, float montantfinal, float montantactuel,
			String etat, Contrat contrat) {
		super();
		NumCagnotte = numCagnotte;
		this.dateAjout = dateAjout;
		this.dateEcheance = dateEcheance;
		this.montantfinal = montantfinal;
		this.montantactuel = montantactuel;
		this.etat = etat;
		this.contrat = contrat;
	}


	public Date getDateAjout() {
		return dateAjout;
	}


	public void setDateAjout(Date dateAjout) {
		this.dateAjout = dateAjout;
	}


	public Date getDateEcheance() {
		return dateEcheance;
	}


	public void setDateEcheance(Date dateEcheance) {
		this.dateEcheance = dateEcheance;
	}


	public Cagnotte() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	 
	 
	 

}
