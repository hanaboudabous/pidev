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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Data_sante   implements Serializable{
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	 @Column( name = "id")
	private int id ; // Clé primaire
	
	private double age ;  
	private String sexe_Type ;
	private float  consultations_Visites ;
	private float frais_Pharmaceutiques ;
	private float hospitalisation ;
	
	private float actes_Medicaux_Courants ;    
	private float analyse ;
	private float radio_Physio ;
	private float optique ;
	private float frais_Chirurgicaux ;
	private float dentaires ;
	private float maternite ;
	private float autres ;
	public int getId() {
		return id;
	}
	public void setId(int id) {     
		this.id = id;
	}
	public double getAge() {
		return age;
	}
	public void setAge(double age) {
		this.age = age;
	}
	public String getSexe_Type() {
		return sexe_Type;
	}
	public void setSexe_Type(String sexe_Type) {
		this.sexe_Type = sexe_Type;
	}
	public float getConsultations_Visites() {
		return consultations_Visites;
	}
	public void setConsultations_Visites(float consultations_Visites) {
		this.consultations_Visites = consultations_Visites;
	}
	public float getFrais_Pharmaceutiques() {
		return frais_Pharmaceutiques;
	}
	public void setFrais_Pharmaceutiques(float frais_Pharmaceutiques) {
		this.frais_Pharmaceutiques = frais_Pharmaceutiques;
	}
	public float getHospitalisation() {
		return hospitalisation;
	}
	public void setHospitalisation(float hospitalisation) {
		this.hospitalisation = hospitalisation;
	}
	public float getActes_Medicaux_Courants() {
		return actes_Medicaux_Courants;
	}
	public void setActes_Medicaux_Courants(float actes_Medicaux_Courants) {
		this.actes_Medicaux_Courants = actes_Medicaux_Courants;
	}
	public float getAnalyse() {
		return analyse;
	}
	public void setAnalyse(float analyse) {
		this.analyse = analyse;
	}
	public float getRadio_Physio() {
		return radio_Physio;
	}
	public void setRadio_Physio(float radio_Physio) {
		this.radio_Physio = radio_Physio;
	}
	public float getOptique() {
		return optique;
	}
	public void setOptique(float optique) {
		this.optique = optique;
	}
	public float getFrais_Chirurgicaux() {
		return frais_Chirurgicaux;
	}
	public void setFrais_Chirurgicaux(float frais_Chirurgicaux) {
		this.frais_Chirurgicaux = frais_Chirurgicaux;
	}
	public float getDentaires() {
		return dentaires;
	}
	public void setDentaires(float dentaires) {
		this.dentaires = dentaires;
	}
	public float getMaternite() {
		return maternite;
	}
	public void setMaternite(float maternite) {
		this.maternite = maternite;
	}
	public float getAutres() {
		return autres;
	}
	public void setAutres(float autres) {
		this.autres = autres;
	}
	
	
	
	
	
	
	
	
	
	
	

}
