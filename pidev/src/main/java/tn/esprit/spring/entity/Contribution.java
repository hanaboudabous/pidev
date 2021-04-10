package tn.esprit.spring.entity;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import tn.esprit.spring.entity.User;
@Entity
public class Contribution implements Serializable {
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	 @Column( name = "id")
	private int NumContribution;
	
	/***************************************************************************/
	@JsonIgnore
	 @ManyToOne
	 private User userss;
	 /************************************************************************/
	
	 private int nbtickets;
	 
	 private float montant; // conversion des tickets en montant (*20)
	 
	
	 
	 private int paye; // 0 pour non et 1 pour oui ( payé= ticket utilisé pour faire sortir une cagnotte)

	public int getNumContribution() {
		return NumContribution;
	}

	public void setNumContribution(int numContribution) {
		NumContribution = numContribution;
	}

	public User getUsers() {
		return userss;
	}

	public void setUsers(User userss) {
		this.userss = userss;
	}

	public int getNbtickets() {
		return nbtickets;
	}

	public void setNbtickets(int nbtickets) {
		this.nbtickets = nbtickets;
	}

	public float getMontant() {
		return montant;
	}

	public void setMontant(float montant) {
		this.montant = montant;
	}

	
	public int getPaye() {
		return paye;
	}

	public void setPaye(int paye) {
		this.paye = paye;
	}

	@Override
	public String toString() {
		return "Contribution [NumContribution=" + NumContribution + ", users=" + userss + ", nbtickets=" + nbtickets
				+ ", montant=" + montant + ", paye=" + paye + "]";
	}

	public Contribution() {
		super();
		// TODO Auto-generated constructor stub
	}
	 
	 
	 
	 
	 
	 
	 
	 

}

