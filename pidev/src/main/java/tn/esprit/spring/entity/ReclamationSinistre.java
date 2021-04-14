package tn.esprit.spring.entity;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ReclamationSinistre implements Serializable  {
	
	@Id
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
	
	 // 0 ou 1 
	private int rachattotal;
	

	private int rachatpartiel;
	private int deces;
	
	private int maladie;
	
	 
	 private int traiteReclamation ;
	/*******************************************************************/
	@JsonIgnore
	@ManyToOne
	private Contrat contrats;
	
	@JsonIgnore
	@OneToOne(cascade = CascadeType.REMOVE,mappedBy="reclamationSinistre")
	private Sinistre sinistre ;
/***********************************************************************/
	
	/* Getters & Setters*/
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
	//@JsonBackReference
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
	public int getRachattotal() {
		return rachattotal;
	}

	public void setRachattotal(int rachattotal) {
		this.rachattotal = rachattotal;
	}

	public int getRachatpartiel() {
		return rachatpartiel;
	}

	public void setRachatpartiel(int rachatpartiel) {
		this.rachatpartiel = rachatpartiel;
	}

	public int getDeces() {
		return deces;
	}

	public void setDeces(int deces) {
		this.deces = deces;
	}

	public int getMaladie() {
		return maladie;
	}

	public void setMaladie(int maladie) {
		this.maladie = maladie;
	}

	
	/* Constructeurs*/
	
	public ReclamationSinistre(int numReclamation, String descriptionReclamation, Date dateOccurence,
			Date dateReclamation, String typeReclamation, String document, float frais, int rachattotal,
			int rachatpartiel, int deces, int maladie, int traiteReclamation, Contrat contrats, Sinistre sinistre) {
		super();
		this.numReclamation = numReclamation;
		this.descriptionReclamation = descriptionReclamation;
		this.dateOccurence = dateOccurence;
		this.dateReclamation = dateReclamation;
		this.typeReclamation = typeReclamation;
		this.document = document;
		this.frais = frais;
		this.rachattotal = rachattotal;
		this.rachatpartiel = rachatpartiel;
		this.deces = deces;
		this.maladie = maladie;
		this.traiteReclamation = traiteReclamation;
		this.contrats = contrats;
		this.sinistre = sinistre;
	}

	public ReclamationSinistre(String descriptionReclamation, Date dateOccurence, Date dateReclamation,
			String typeReclamation, String document, float frais, int rachattotal, int rachatpartiel, int deces,
			int maladie, int traiteReclamation, Contrat contrats, Sinistre sinistre) {
		super();
		this.descriptionReclamation = descriptionReclamation;
		this.dateOccurence = dateOccurence;
		this.dateReclamation = dateReclamation;
		this.typeReclamation = typeReclamation;
		this.document = document;
		this.frais = frais;
		this.rachattotal = rachattotal;
		this.rachatpartiel = rachatpartiel;
		this.deces = deces;
		this.maladie = maladie;
		this.traiteReclamation = traiteReclamation;
		this.contrats = contrats;
		this.sinistre = sinistre;
	}

	public ReclamationSinistre() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	

}
