package tn.esprit.spring.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
public class Reinsurance implements Serializable {


	private static final long serialVersionUID = 1L;
	 
    @Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)	
    
	private int Reinsurance_ID;
	private String mode;
	private String type;
	private float Quota_Share;
	private float Priority;
	private float Rangee;
	private float Aggregate;
	@Temporal (TemporalType.DATE)
	private Date Begin_Date;
	@Temporal (TemporalType.DATE)
	private Date End_Date;
	/*
	@OneToOne
	Contract contract;
	@OneToOne
	Claim claim;*/
	public Reinsurance(int reinsurance_ID, String mode, String type, float quota_Share, float priority,
			float rangee, float aggregate, Date begin_Date, Date end_Date) {
		Reinsurance_ID = reinsurance_ID;
		this.mode = mode;
		this.type = type;
		Quota_Share = quota_Share;
		Priority = priority;
		Rangee = rangee;
		Aggregate = aggregate;
		Begin_Date = begin_Date;
		End_Date = end_Date;
	}
	public int getReinsurance_ID() {
		return Reinsurance_ID;
	}
	public void setReinsurance_ID(int reinsurance_ID) {
		Reinsurance_ID = reinsurance_ID;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public float getQuota_Share() {
		return Quota_Share;
	}
	public void setQuota_Share(float quota_Share) {
		Quota_Share = quota_Share;
	}
	public float getPriority() {
		return Priority;
	}
	public void setPriority(float priority) {
		Priority = priority;
	}
	public float getRangee() {
		return Rangee;
	}
	public void setRangee(float rangee) {
		Rangee = rangee;
	}
	public float getAggregate() {
		return Aggregate;
	}
	public void setAggregate(float aggregate) {
		Aggregate = aggregate;
	}
	public Date getBegin_Date() {
		return Begin_Date;
	}
	public void setBegin_Date(Date begin_Date) {
		Begin_Date = begin_Date;
	}
	public Date getEnd_Date() {
		return End_Date;
	}
	public void setEnd_Date(Date end_Date) {
		End_Date = end_Date;
	}
	@Override
	public String toString() {
		return "Reinsurance_contract [Reinsurance_ID=" + Reinsurance_ID + ", mode=" + mode + ", type=" + type
				+ ", Quota_Share=" + Quota_Share + ", Priority=" + Priority + ", Rangee=" + Rangee + ", Aggregate="
				+ Aggregate + ", Begin_Date=" + Begin_Date + ", End_Date=" + End_Date + "]";
	}


}
