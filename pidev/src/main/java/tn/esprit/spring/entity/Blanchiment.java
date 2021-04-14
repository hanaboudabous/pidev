package tn.esprit.spring.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Blanchiment implements Serializable{
	
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	 @Column( name = "id")
	private int id ; // Clé primaire
	
	private String cause ;
	private int niveau ;
	private String valide ;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCause() {
		return cause;
	}
	public void setCause(String cause) {
		this.cause = cause;
	}
	public int getNiveau() {
		return niveau;
	}
	public void setNiveau(int niveau) {
		this.niveau = niveau;
	}
	public String getValide() {
		return valide;
	}
	public void setValide(String valide) {
		this.valide = valide;
	}
	
	// les cas sont : prime kbira , montant unique kbir , rachat total , barcha marat rachat partiel , prime par espéce , barcha des contrat f wa9et 9sir ,
						// montat kbir w paiement de l etranger , eli mich ya3ti l prime mouch houa mich ye5ou lflous mb3d , tbadal l eli mich ye5ou l flous me8ir ma ykoun 3aandou 3ale9a bil souscripteur

	
}
