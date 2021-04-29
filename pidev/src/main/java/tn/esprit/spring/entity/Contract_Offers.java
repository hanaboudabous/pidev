package tn.esprit.spring.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
@Table( name = "ContratOffers")
public class Contract_Offers implements Serializable {
	private static final long serialVersionUID=1L;
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	 @Column( name = "id_Contract_Offers")
	private int IdContractOffer; 
	

	private String date_debut ;
	

	private String date_fin ;
	
	
	private double Tarification ;
	private float vie_mixte;
	private float dèces_mixte;
	private int duree;
	
	@Enumerated(EnumType.STRING)
	private State_Offers state_offers;
	@JsonIgnore
	@ManyToOne
	private User users;
	@JsonIgnore 
	@ManyToOne
	private Offers offers;
	
	
	//Gettters et Settes

	public int getDuree() {
		return duree;
	}

	public void setDuree(int duree) {
		this.duree = duree;
	}

	public int getIdContractOffer() {
		return IdContractOffer;
	}

	public void setIdContractOffer(int idContractOffer) {
		IdContractOffer = idContractOffer;
	}

	public String getDate_debut() {
		return date_debut;
	}

	public void setDate_debut(String date_debut) {
		this.date_debut = date_debut;
	}

	public String getDate_fin() {
		return date_fin;
	}

	public void setDate_fin(String date_fin) {
		this.date_fin = date_fin;
	}

	public double getTarification() {
		return Tarification;
	}

	public void setTarification(double tarification) {
		Tarification = tarification;
	}

	public State_Offers getState_offers() {
		return state_offers;
	}

	public void setState_offers(State_Offers state_offers) {
		this.state_offers = state_offers;
	}
    
	public User getUsers() {
		return users;
	}

	public void setUsers(User users) {
		this.users = users;
	}

	public Offers getOffers() {
		return offers;
	}

	public void setOffers(Offers offers) {
		this.offers = offers;
	}
	

	public float getVie_mixte() {
		return vie_mixte;
	}

	public void setVie_mixte(float vie_mixte) {
		this.vie_mixte = vie_mixte;
	}

	public float getDèces_mixte() {
		return dèces_mixte;
	}

	public void setDèces_mixte(float dèces_mixte) {
		this.dèces_mixte = dèces_mixte;
	}

	

	@Override
	public String toString() {
		return "Contract_Offers [IdContractOffer=" + IdContractOffer + ", date_debut=" + date_debut + ", date_fin="
				+ date_fin + ", Tarification=" + Tarification + ", vie_mixte=" + vie_mixte + ", dèces_mixte="
				+ dèces_mixte + ", state_offers=" + state_offers + ", users=" + users + ", offers=" + offers + "]";
	}

	
	public Contract_Offers() {
		super();
	}
	
	
	
}
