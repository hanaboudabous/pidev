package tn.esprit.spring.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "data")
public class Data implements Serializable {

	
	
	@Id                    
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	 @Column( name = "id")
	private int id; // Clé primaire
	 @Column( name = "lx_h")
	private float lxH;
	 @Column( name = "lx_f")
	private float lxF;
	 @Column( name = "dx_h")
	private float dxH;
	 @Column( name = "dx_f")
	private float dxF;
	 @Column( name = "px_h")
	private float pxH;
	 @Column( name = "px_f")
	private float pxF;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public float getLxH() {
		return lxH;
	}
	public void setLxH(float lxH) {
		this.lxH = lxH;
	}
	public float getLxF() {
		return lxF;
	}
	public void setLxF(float lxF) {
		this.lxF = lxF;
	}
	public float getDxH() {
		return dxH;
	}
	public void setDxH(float dxH) {
		this.dxH = dxH;
	}
	public float getDxF() {
		return dxF;
	}
	public void setDxF(float dxF) {
		this.dxF = dxF;
	}
	public float getPxH() {
		return pxH;
	}
	public void setPxH(float pxH) {
		this.pxH = pxH;
	}
	public float getPxF() {
		return pxF;
	}
	public void setPxF(float pxF) {
		this.pxF = pxF;
	}
	public Data(int id, float lxH, float lxF, float dxH, float dxF, float pxH, float pxF) {
		super();
		this.id = id;
		this.lxH = lxH;
		this.lxF = lxF;
		this.dxH = dxH;
		this.dxF = dxF;
		this.pxH = pxH;
		this.pxF = pxF;
	}
	public Data() {
		super();
		// TODO Auto-generated constructor stub
	}
	 
	 
	 
	
}
