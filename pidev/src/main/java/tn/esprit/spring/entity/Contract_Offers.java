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



@Entity
@Table( name = "ContratOffers")
public class Contract_Offers implements Serializable {
	private static final long serialVersionUID=1L;
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	 @Column( name = "id_Contract_Offers")
	private Long IdContractOffer; 
	
	@Temporal(TemporalType.DATE)
	private Date date_debut ;
	
	@Temporal(TemporalType.DATE)
	private Date date_fin ;
	
	private float Tarification ;
	private float vie_mixte;
	private float dèces_mixte;
	
	@Enumerated(EnumType.STRING)
	State_Offers state_offers;
	
	@ManyToOne
	private User users;
	@ManyToOne
	private Offers offers;
	
	
	//Gettters et Settes

	public long getIdContractOffer() {
		return IdContractOffer;
	}

	public void setIdContractOffer(long idContractOffer) {
		IdContractOffer = idContractOffer;
	}

	public Date getDate_debut() {
		return date_debut;
	}

	public void setDate_debut(Date date_debut) {
		this.date_debut = date_debut;
	}

	public Date getDate_fin() {
		return date_fin;
	}

	public void setDate_fin(Date date_fin) {
		this.date_fin = date_fin;
	}

	public float getTarification() {
		return Tarification;
	}

	public void setTarification(float tarification) {
		Tarification = tarification;
	}

	public State_Offers getState_offers() {
		return state_offers;
	}

	public void setState_offers(State_Offers state_offers) {
		this.state_offers = state_offers;
	}

	@Override
	public String toString() {
		return "Contract_Offers [IdContractOffer=" + IdContractOffer + ", date_debut=" + date_debut + ", date_fin="
				+ date_fin + ", Tarification=" + Tarification + ", vie_mixte=" + vie_mixte + ", dèces_mixte="
				+ dèces_mixte + ", state_offers=" + state_offers + ", users=" + users + ", offers=" + offers + "]";
	}
	
	
	
}
