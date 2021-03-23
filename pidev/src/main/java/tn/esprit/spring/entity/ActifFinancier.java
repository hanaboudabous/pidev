package Pidev.entity;

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
public class ActifFinancier  implements Serializable {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	 @Column( name = "id")
	private int id ; // Clé primaire
	
	
	@Temporal(TemporalType.DATE)
	private Date date_debut ;
	
	@Temporal(TemporalType.DATE)
	private Date date_fin ;
	
	private int maturite ;
	
	private float montant_investi ;
	
	private float montant_cumule ;
	
	private float montant_rachat ;
	
	private int accepte_rachat ;
	
	@Enumerated(EnumType.STRING)
	private Fond nom_fond ;
	
	private String etat ; // resilié ou en cours
	

	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	public int getMaturite() {
		return maturite;
	}




	public void setMaturite(int maturite) {
		this.maturite = maturite;
	}

	public float getMontant_investi() {
		return montant_investi;
	}




	public void setMontant_investi(float montant_investi) {
		this.montant_investi = montant_investi;
	}




	public float getMontant_cumule() {
		return montant_cumule;
	}




	public void setMontant_cumule(float montant_cumule) {
		this.montant_cumule = montant_cumule;
	}




	public float getMontant_rachat() {
		return montant_rachat;
	}




	public void setMontant_rachat(float montant_rachat) {
		this.montant_rachat = montant_rachat;
	}




	public int getAccepte_rachat() {
		return accepte_rachat;
	}




	public void setAccepte_rachat(int accepte_rachat) {
		this.accepte_rachat = accepte_rachat;
	}




	public Fond getNom_fond() {
		return nom_fond;
	}




	public void setNom_fond(Fond nom_fond) {
		this.nom_fond = nom_fond;
	}




	public String getEtat() {
		return etat;
	}




	public void setEtat(String etat) {
		this.etat = etat;
	}




	public User getUsers() {
		return users;
	}




	public void setUsers(User users) {
		this.users = users;
	}




	@ManyToOne
	 private User users;
	
}
