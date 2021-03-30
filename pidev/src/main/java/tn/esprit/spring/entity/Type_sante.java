package Pidev.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Type_sante implements Serializable {
	

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column( name = "id")
	private int id; // Clé primaire
	
	private boolean  consultations_Visites ;
	private boolean frais_Pharmaceutiques ;
	private boolean hospitalisation ;
	
	private boolean actes_Medicaux_Courants ;    
	private boolean analyse ;
	private boolean radio_Physio ;
	private boolean optique ;
	private boolean frais_Chirurgicaux ;
	private boolean dentaires ;
	private boolean maternite ;
	private boolean autres ;
	
	private boolean ajoutEnf ;   
	private boolean ajouconjoint ;
	
	public int nombrefille ;
	public int nombreenfant ;
	
	 public int getNombrefille() {
		return nombrefille;
	}

	public void setNombrefille(int nombrefille) {
		this.nombrefille = nombrefille;
	}

	public int getNombreenfant() {
		return nombreenfant;
	}

	public void setNombreenfant(int nombreenfant) {
		this.nombreenfant = nombreenfant;
	}

	public boolean isAjoutEnf() {
		return ajoutEnf;
	}

	public void setAjoutEnf(boolean ajoutEnf) {
		this.ajoutEnf = ajoutEnf;
	}

	public boolean isAjouconjoint() {
		return ajouconjoint;
	}

	public void setAjouconjoint(boolean ajouconjoint) {
		this.ajouconjoint = ajouconjoint;
	}
	

	@OneToOne()
	 private DemandeContrat demandeContrat ;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isConsultations_Visites() {
		return consultations_Visites;
	}

	public void setConsultations_Visites(boolean consultations_Visites) {
		this.consultations_Visites = consultations_Visites;
	}

	public boolean isFrais_Pharmaceutiques() {
		return frais_Pharmaceutiques;
	}

	public void setFrais_Pharmaceutiques(boolean frais_Pharmaceutiques) {
		this.frais_Pharmaceutiques = frais_Pharmaceutiques;
	}

	public boolean isHospitalisation() {
		return hospitalisation;
	}

	public void setHospitalisation(boolean hospitalisation) {
		this.hospitalisation = hospitalisation;
	}

	public boolean isActes_Medicaux_Courants() {
		return actes_Medicaux_Courants;
	}

	public void setActes_Medicaux_Courants(boolean actes_Medicaux_Courants) {
		this.actes_Medicaux_Courants = actes_Medicaux_Courants;
	}

	public boolean isAnalyse() {
		return analyse;
	}

	public void setAnalyse(boolean analyse) {
		this.analyse = analyse;
	}

	public boolean isRadio_Physio() {
		return radio_Physio;
	}

	public void setRadio_Physio(boolean radio_Physio) {
		this.radio_Physio = radio_Physio;
	}

	public boolean isOptique() {
		return optique;
	}

	public void setOptique(boolean optique) {
		this.optique = optique;
	}

	public boolean isFrais_Chirurgicaux() {
		return frais_Chirurgicaux;
	}

	public void setFrais_Chirurgicaux(boolean frais_Chirurgicaux) {
		this.frais_Chirurgicaux = frais_Chirurgicaux;
	}

	public boolean isDentaires() {
		return dentaires;
	}

	public void setDentaires(boolean dentaires) {
		this.dentaires = dentaires;
	}

	public boolean isMaternite() {
		return maternite;
	}

	public void setMaternite(boolean maternite) {
		this.maternite = maternite;
	}

	public boolean isAutres() {
		return autres;
	}

	public void setAutres(boolean autres) {
		this.autres = autres;
	}

	public DemandeContrat getDemandeContrat() {
		return demandeContrat;
	}

	public void setDemandeContrat(DemandeContrat demandeContrat) {
		this.demandeContrat = demandeContrat;
	}

	
	
}
