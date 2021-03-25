package tn.esprit.spring.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity

public class DataFondEURO implements Serializable {
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int id; 
				
				private String libelle ;
				private String typBonde ;
				private float montantSouscription ;
				private Date dateSouscription; 
				private Date dateEcheance ;
				private int maturite ; 
				private float tauxRendement ;
				private float tauxCoupon ;
				private String typeTaux ;
				private float nominal ;	
				private float prixAchat ;
				private float remunerationPrincipale ;
				
				public int getId() {
					return id;
				}
				public void setId(int id) {
					this.id = id;
				}
				public String getLibelle() {
					return libelle;
				}
				public void setLibelle(String libelle) {
					this.libelle = libelle;
				}
				public String getTypBonde() {
					return typBonde;
				}
				public void setTypBonde(String typBonde) {
					this.typBonde = typBonde;
				}
				public float getMontantSouscription() {
					return montantSouscription;
				}
				public void setMontantSouscription(float montantSouscription) {
					this.montantSouscription = montantSouscription;
				}
				public Date getDateSouscription() {
					return dateSouscription;
				}
				public void setDateSouscription(Date dateSouscription) {
					this.dateSouscription = dateSouscription;
				}
				public Date getDateEcheance() {
					return dateEcheance;
				}
				public void setDateEcheance(Date dateEcheance) {
					this.dateEcheance = dateEcheance;
				}
				public int getMaturite() {
					return maturite;
				}
				public void setMaturite(int maturite) {
					this.maturite = maturite;
				}
				public float getTauxRendement() {
					return tauxRendement;
				}
				public void setTauxRendement(float tauxRendement) {
					this.tauxRendement = tauxRendement;
				}
				public float getTauxCoupon() {
					return tauxCoupon;
				}
				public void setTauxCoupon(float tauxCoupon) {
					this.tauxCoupon = tauxCoupon;
				}
				public String getTypeTaux() {
					return typeTaux;
				}
				public void setTypeTaux(String typeTaux) {
					this.typeTaux = typeTaux;
				}
				public float getNominal() {
					return nominal;
				}
				public void setNominal(float nominal) {
					this.nominal = nominal;
				}
				public float getPrixAchat() {
					return prixAchat;
				}
				public void setPrixAchat(float prixAchat) {
					this.prixAchat = prixAchat;
				}
				public float getRemunerationPrincipale() {
					return remunerationPrincipale;
				}
				public void setRemunerationPrincipale(float remunerationPrincipale) {
					this.remunerationPrincipale = remunerationPrincipale;
				}
				
				
	
				

}
