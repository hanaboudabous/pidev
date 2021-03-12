package Pidev.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class ReclamationSinistre implements Serializable  {
	
	@Id // id rec
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	 @Column( name = "id")
	private int numReclamation ; // Clé primaire
	
	private String descriptionReclamation ;
	
	@Temporal(TemporalType.DATE)
	private Date dateOccurence ;
	
	@Temporal(TemporalType.DATE)
	private Date dateReclamation ;
	
	private String typeReclamation ;
	
	private String document ;
	
	private float frais ; // flous eli 5alashom lil clinique
	
	 
	 private int traiteReclamation ;
	
	@ManyToOne
	private Contrat contrats;
				 
	@OneToOne(cascade = CascadeType.ALL,mappedBy="reclamationSinitre")
	private Sinistre sinistre ;

	public int getNumReclamation() {
		return numReclamation;
	}

	public void setNumReclamation(int numReclamation) {
		this.numReclamation = numReclamation;
	}

	public String getDescriptionReclamation() {
		return descriptionReclamation;
	}

	public void setDescriptionReclamation(String descriptionReclamation) {
		this.descriptionReclamation = descriptionReclamation;
	}

	public Date getDateOccurence() {
		return dateOccurence;
	}

	public void setDateOccurence(Date dateOccurence) {
		this.dateOccurence = dateOccurence;
	}

	public Date getDateReclamation() {
		return dateReclamation;
	}

	public void setDateReclamation(Date dateReclamation) {
		this.dateReclamation = dateReclamation;
	}

	public String getTypeReclamation() {
		return typeReclamation;
	}

	public void setTypeReclamation(String typeReclamation) {
		this.typeReclamation = typeReclamation;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public float getFrais() {
		return frais;
	}

	public void setFrais(float frais) {
		this.frais = frais;
	}

	public int getTraiteReclamation() {
		return traiteReclamation;
	}

	public void setTraiteReclamation(int traiteReclamation) {
		this.traiteReclamation = traiteReclamation;
	}

	public Contrat getContrats() {
		return contrats;
	}

	public void setContrats(Contrat contrats) {
		this.contrats = contrats;
	}

	public Sinistre getSinistre() {
		return sinistre;
	}

	public void setSinistre(Sinistre sinistre) {
		this.sinistre = sinistre;
	}

}
