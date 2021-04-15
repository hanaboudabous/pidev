package tn.esprit.spring.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import tn.esprit.spring.entity.Contrat;

@Entity
public class Reinsurance_contract {
	private static final long serialVersionUID = 1L;
	 
    @Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)	
    
    //Quote-part = 70%
	private int Reinsurance_ID;
	private float primeCommerciale;
	private float PrimePure;
	private float Remboursement;
	@OneToOne
	private Contrat Contrat ;
	@OneToOne
	private User User ;
	
	public User getUser() {
		return User;
	}
	public void setUser(User user) {
		User = user;
	}
	public int getReinsurance_ID() {
		return Reinsurance_ID;
	}
	public void setReinsurance_ID(int reinsurance_ID) {
		Reinsurance_ID = reinsurance_ID;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public float getPrimeCommerciale() {
		return primeCommerciale;
	}
	public void setPrimeCommerciale(float primeCommerciale) {
		this.primeCommerciale = primeCommerciale;
	}
	public float getPrimePure() {
		return PrimePure;
	}
	public void setPrimePure(float primePure) {
		PrimePure = primePure;
	}
	public Contrat getContrat() {
		return Contrat;
	}
	public void setContrat(Contrat contrat) {
		Contrat = contrat;
	}
	
	public float getRemboursement() {
		return Remboursement;
	}
	public void setRemboursement(float remboursement) {
		Remboursement = remboursement;
	}

    @Override
	public String toString() {
		return "Reinsurance_contract [Reinsurance_ID=" + Reinsurance_ID + ", primeCommerciale=" + primeCommerciale
				+ ", PrimePure=" + PrimePure + ", Remboursement=" + Remboursement + ", Contrat=" + Contrat + ", User="
				+ User + "]";
	}
    	
	public Reinsurance_contract(int reinsurance_ID, float primeCommerciale, float primePure, float remboursement,
			tn.esprit.spring.entity.Contrat contrat, tn.esprit.spring.entity.User user) {
		super();
		Reinsurance_ID = reinsurance_ID;
		this.primeCommerciale = primeCommerciale;
		PrimePure = primePure;
		Remboursement = remboursement;
		Contrat = contrat;
		User = user;
	}
	
	public Reinsurance_contract(float primeCommerciale, float primePure, float remboursement,
			tn.esprit.spring.entity.Contrat contrat, tn.esprit.spring.entity.User user) {
		super();
		this.primeCommerciale = primeCommerciale;
		PrimePure = primePure;
		Remboursement = remboursement;
		Contrat = contrat;
		User = user;
	}
	public Reinsurance_contract() {
	}


	
}

