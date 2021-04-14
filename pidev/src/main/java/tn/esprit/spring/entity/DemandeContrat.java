package tn.esprit.spring.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class DemandeContrat implements Serializable {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	 @Column( name = "id")
	private int numDemande; // Clé primaire
	
	


	@Temporal(TemporalType.DATE)
	private Date dateDemande;
	
	
	// Assurance_Credit , Assurance_Epargne , Assurance_Sante , Assurance_Capital_differe
	//  assurance : cas vie : capital différe , rente viagére
	//              cas déces : vie entiére
	//  santé 

	private int nombreAnnee ;
	
	// des question pour distinction du type de contrat
	
	private int deces ;
	
	private int vie ;
	
	private int sante ;
		
	// question pour la tarification
	private float capitalAssure ;
	private int capitalOuRente ;

	
	 @Enumerated(EnumType.STRING)
	 private Prime choixPrime ;
	 private float val_prime;

	 //beneficiaire
	 private String beneficiaire ;

	 private int CinBeneficiaire ;
	 
	 public int getCinBeneficiaire() {
		return CinBeneficiaire;
	}

	public void setCinBeneficiaire(int cinBeneficiaire) {
		CinBeneficiaire = cinBeneficiaire;
	}

	public String getBeneficiaire() {
		return beneficiaire;
	}

	public void setBeneficiaire(String beneficiaire) {
		this.beneficiaire = beneficiaire;
	}

	public Type_sante getType_sante() {
		return type_sante;
	}

	public void setType_sante(Type_sante type_sante) {
		this.type_sante = type_sante;
	}

	// autre question 
	 private int fumeur ; 
	 private int alcool ; // oui ou nn
	 private int sportif ;  // sportif ou non
	 private int maladie; // maladie chronique
	 
	 private int accepte ;  //  demande calculé et accepte ou non ( pour l assureur )
	 private int traite ; // demande traité ou non

	 /// decision type contrat
	 private String nomContrat ;
	 private String typeContrat;
	 
	 // cause de rejet
	 private String cause ;
	 public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	@ManyToOne
	private User users;
	
	// @JsonIgnore			 
	 @OneToOne(cascade = CascadeType.ALL,mappedBy="demandeContrat")
	 private Contrat contrat ;

	 
	 @OneToOne(cascade = CascadeType.ALL,mappedBy="demandeContrat")
	 private Type_sante type_sante ;

	     
	public String getNomContrat() {
		return nomContrat;
	}

	public void setNomContrat(String nomContrat) {
		this.nomContrat = nomContrat;
	}

	public String getTypeContrat() {
		return typeContrat;
	}

	public void setTypeContrat(String typeContrat) {
		this.typeContrat = typeContrat;
	}

	public int getNumDemande() {
		return numDemande;
	}

	public void setNumDemande(int numDemande) {
		this.numDemande = numDemande;
	}

	

	public Date getDateDemande() {
		return dateDemande;
	}

	public void setDateDemande(Date dateDemande) {
		this.dateDemande = dateDemande;
	}

	

	public int getNombreAnnee() {
		return nombreAnnee;
	}

	public void setNombreAnnee(int nombreAnnee) {
		this.nombreAnnee = nombreAnnee;
	}

	public int getDeces() {
		return deces;
	}

	public void setDeces(int deces) {
		this.deces = deces;
	}

	public int getVie() {
		return vie;
	}

	public void setVie(int vie) {
		this.vie = vie;
	}

	public int getCapitalOuRente() {
		return capitalOuRente;
	}

	public void setCapitalOuRente(int capitalOuRente) {
		this.capitalOuRente = capitalOuRente;
	}

	public int getSante() {
		return sante;
	}

	public void setSante(int sante) {
		this.sante = sante;
	}

	public float getCapitalAssure() {
		return capitalAssure;
	}

	public void setCapitalAssure(float capitalAssure) {
		this.capitalAssure = capitalAssure;
	}

	public int getFumeur() {
		return fumeur;
	}

	public void setFumeur(int fumeur) {
		this.fumeur = fumeur;
	}

	public int getAlcool() {
		return alcool;
	}

	public void setAlcool(int alcool) {
		this.alcool = alcool;
	}

	public int getSportif() {
		return sportif;
	}

	public void setSportif(int sportif) {
		this.sportif = sportif;
	}

	public int getMaladie() {
		return maladie;
	}

	public void setMaladie(int maladie) {
		this.maladie = maladie;
	}

	public int getAccepte() {
		return accepte;
	}

	public void setAccepte(int accepte) {
		this.accepte = accepte;
	}

	public int getTraite() {
		return traite;
	}

	public void setTraite(int traite) {
		this.traite = traite;
	}

	public User getUsers() {
		return users;
	}

	public void setUsers(User users) {
		this.users = users;
	}

	public Contrat getContrat() {
		return contrat;
	}

	public void setContrat(Contrat contrat) {
		this.contrat = contrat;
	}

	public DemandeContrat(int numDemande,  Date dateDemande, 
	 int nombreAnnee, int deces, int vie, int capitalOuRente, int sante, float capitalAssure,
			int fumeur, int alcool, int sportif, int maladie, int accepte, int traite, User users, Contrat contrat) {
		super();
		this.numDemande = numDemande;

		this.dateDemande = dateDemande;
	
		this.nombreAnnee = nombreAnnee;
		this.deces = deces;
		this.vie = vie;
		this.capitalOuRente = capitalOuRente;
		this.sante = sante;
		this.capitalAssure = capitalAssure;
		this.fumeur = fumeur;
		this.alcool = alcool;
		this.sportif = sportif;
		this.maladie = maladie;
		this.accepte = accepte;
		this.traite = traite;
		this.users = users;
		this.contrat = contrat;
	}
	public DemandeContrat(  Date dateDemande,
		 int nombreAnnee, int deces, int vie, int capitalOuRente, int sante, float capitalAssure,
			int fumeur, int alcool, int sportif, int maladie, int accepte, int traite, User users) {
		super();
		
	
		this.dateDemande = dateDemande;
	
		this.nombreAnnee = nombreAnnee;
		this.deces = deces;
		this.vie = vie;
		this.capitalOuRente = capitalOuRente;
		this.sante = sante;
		this.capitalAssure = capitalAssure;
		this.fumeur = fumeur;
		this.alcool = alcool;
		this.sportif = sportif;
		this.maladie = maladie;
		this.accepte = accepte;
		this.traite = traite;
		this.users = users;
		
	}
	
	public DemandeContrat( Date dateDemande, 
			 int nombreAnnee, int deces, int vie, int capitalOuRente, int sante, float capitalAssure,
			int fumeur, int alcool, int sportif, int maladie, int accepte, int traite) {
		super();
		
	
		this.dateDemande = dateDemande;

		this.nombreAnnee = nombreAnnee;
		this.deces = deces;
		this.vie = vie;
		this.capitalOuRente = capitalOuRente;
		this.sante = sante;
		this.capitalAssure = capitalAssure;
		this.fumeur = fumeur;
		this.alcool = alcool;
		this.sportif = sportif;
		this.maladie = maladie;
		this.accepte = accepte;
		this.traite = traite;
		
	}

	public DemandeContrat() {
		super();
		// TODO Auto-generated constructor stub
	}



	public Prime getChoixPrime() {
		return choixPrime;
	}

	public void setChoixPrime(Prime choixPrime) {
		this.choixPrime = choixPrime;
	}

	public float getVal_prime() {
		return val_prime;
	}

	public void setVal_prime(float val_prime) {
		this.val_prime = val_prime;
	}

	
	

}
