package tn.esprit.spring.entity;

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
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	 @Column( name = "id")
	private int idSinitre ; // Clé primaire
	
	private float capitalRembourse ;
	
	@Temporal(TemporalType.DATE)
	private Date dateReglement ;
	
/************************************************************************/
	@JsonIgnore
	@OneToOne
	private ReclamationSinistre reclamationSinistre;
/*************************************************************************/

	
	/* Getters & Setters*/
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
		return reclamationSinistre;
	}


	public void setReclamationSinitre(ReclamationSinistre reclamationSinistre) {
		this.reclamationSinistre = reclamationSinistre;
	}

	/* Constructeurs*/
	
	public Sinistre(int idSinitre, float capitalRembourse, Date dateReglement,
			ReclamationSinistre reclamationSinistre) {
		super();
		this.idSinitre = idSinitre;
		this.capitalRembourse = capitalRembourse;
		this.dateReglement = dateReglement;
		this.reclamationSinistre = reclamationSinistre;
	}


	public Sinistre(float capitalRembourse, Date dateReglement, ReclamationSinistre reclamationSinistre) {
		super();
		this.capitalRembourse = capitalRembourse;
		this.dateReglement = dateReglement;
		this.reclamationSinistre = reclamationSinistre;
	}


	public Sinistre() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	

}
