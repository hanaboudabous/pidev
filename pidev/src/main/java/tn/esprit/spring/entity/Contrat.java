package Pidev.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;




@Entity
@Table( name = "Contrat")
public class Contrat implements Serializable {

	
	// adresse , num_tel , id_Numcontrat , profession , date ,couverture , salaire , ville ,
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	 @Column( name = "id")
	private int NumContrat; // Clé primaire
	
	@Temporal(TemporalType.DATE)
	private Date date_debut ;
	
	@Temporal(TemporalType.DATE)
	private Date date_fin ;
	
	//ggh
	private float primeCommerciale ;
	
	
	private float primePure ;
	
	private float plafond ;
	
	private String etat ; // resilié ou en cours
	
	private int acceptation ; // contat accepte par le client ou pas encore
	private int scoring ; // bonus malus
	


	// pour la confirmation : si le client n accepte pas le contrat , contrat se supprime de la table
	@JsonIgnore
	@OneToOne
	private DemandeContrat demandeContrat ;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy="contrats")
	private Set<ReclamationSinistre> reclamationSinistres;

	public int getNumContrat() {
		return NumContrat;
	}

	public void setNumContrat(int numContrat) {
		NumContrat = numContrat;
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

	public float getPrimeCommerciale() {
		return primeCommerciale;
	}

	public void setPrimeCommerciale(float primeCommerciale) {
		this.primeCommerciale = primeCommerciale;
	}

	public float getPrimePure() {
		return primePure;
	}

	public void setPrimePure(float primePure) {
		this.primePure = primePure;
	}

	public float getPlafond() {
		return plafond;
	}

	public void setPlafond(float plafond) {
		this.plafond = plafond;
	}

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	public int getScoring() {
		return scoring;
	}

	public void setScoring(int scoring) {
		this.scoring = scoring;
	}

	public DemandeContrat getDemandeContrat() {
		return demandeContrat;
	}

	public void setDemandeContrat(DemandeContrat demandeContrat) {
		this.demandeContrat = demandeContrat;
	}

	public Set<ReclamationSinistre> getReclamationSinistres() {
		return reclamationSinistres;
	}

	public void setReclamationSinistres(Set<ReclamationSinistre> reclamationSinistres) {
		this.reclamationSinistres = reclamationSinistres;
	}

	
	 public int getAcceptation() {
			return acceptation;
		}

		public void setAcceptation(int acceptation) {
			this.acceptation = acceptation;
		}
// getters and setters
	

	

	
	
	
	
	
	
	
	
	
	
}
