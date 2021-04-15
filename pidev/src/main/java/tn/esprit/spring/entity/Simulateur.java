package tn.esprit.spring.entity;

import java.io.Serializable;

public class Simulateur implements Serializable{
	
	private int annee ;
	private float capital ;
	private float rachat ;
	
	
	
	
	public float getRachat() {
		return rachat;
	}
	public void setRachat(float rachat) {
		this.rachat = rachat;
	}
	public int getAnnee() {
		return annee;
	}
	public void setAnnee(int annee) {
		this.annee = annee;
	}
	public float getCapital() {
		return capital;
	}
	public void setCapital(float capital) {
		this.capital = capital;
	}
	
 
}

