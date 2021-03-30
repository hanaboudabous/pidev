package tn.esprit.spring.entity;

import java.io.Serializable;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Sinistre  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column( name = "id")
	private int idSinitre ; // Clé primaire
	
	private float capitalRembourse ;
	
	@Temporal(TemporalType.DATE)
	private Date dateReglement ;
	
	@JsonIgnore
	@OneToOne
	private ReclamationSinistre reclamationSinitre;


	public int getIdSinitre() {
		return idSinitre;
	}


	public void setIdSinitre(int idSinitre) {
		this.idSinitre = idSinitre;
	}


	public float getCapitalRembourse() {
		return capitalRembourse;
	}


	public void setCapitalRembourse(float capitalRembourse) {
		this.capitalRembourse = capitalRembourse;
	}


	public Date getDateReglement() {
		return dateReglement;
	}


	public void setDateReglement(Date dateReglement) {
		this.dateReglement = dateReglement;
	}


	public ReclamationSinistre getReclamationSinitre() {
		return reclamationSinitre;
	}


	public void setReclamationSinitre(ReclamationSinistre reclamationSinitre) {
		this.reclamationSinitre = reclamationSinitre;
	}
	
	

}
