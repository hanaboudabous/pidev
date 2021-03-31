package tn.esprit.spring.entity;

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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;

import tn.esprit.spring.entity.Payment;


import tn.esprit.spring.entity.Status;
import tn.esprit.spring.entity.Geographical_area;


@Entity
public class User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	
	
	private int User_ID;
	private String First_name;
	private String Last_name;
	@Column(name="number",unique=true,length=8)
	private int Number;
	@Column(name="cin",unique=true,length=8)
	private long CIN;
	@Enumerated(EnumType.STRING)
	Geographical_area Geographical_area;
	private int Motorisation;
	@Temporal (TemporalType.DATE)
	@Column(name="Birth_date")
	private Date Birth_date;
	private String Address;
	@Column(name="Postal_code",length=4)
	private int Postal_code;
	private String Job;
	@Enumerated(EnumType.STRING)
	Status Status;
	private int shifting;
	@Column(name="email",unique=true)
	private String Email;
	private String Password;
	private String Confirm_password;
	//@Value("${User.random:random.ints(999999, 111111, 999999)}")

	@Column(columnDefinition = "integer default 0")
	private int Verified_account;
	private int random;

	@Temporal (TemporalType.DATE)
	private Date Hiring_date;
	@Enumerated(EnumType.STRING)
	Role_User Role_User;
	
	
	public int getUser_ID() {
		return User_ID;
	}
	public void setUser_ID(int user_ID) {
		User_ID = user_ID;
	}
	public String getFirst_name() {
		return First_name;
	}
	public void setFirst_name(String first_name) {
		First_name = first_name;
	}
	public String getLast_name() {
		return Last_name;
	}
	public void setLast_name(String last_name) {
		Last_name = last_name;
	}
	public int getNumber() {
		return Number;
	}
	public void setNumber(int number) {
		Number = number;
	}
	public long getCIN() {
		return CIN;
	}
	public void setCIN(long cIN) {
		CIN = cIN;
	}
	public Geographical_area getGeographical_area() {
		return Geographical_area;
	}
	public void setGeographical_area(Geographical_area geographical_area) {
		Geographical_area = geographical_area;
	}
	public int getMotorisation() {
		return Motorisation;
	}
	public void setMotorisation(int motorisation) {
		Motorisation = motorisation;
	}
	public Date getBirth_date() {
		return Birth_date;
	}
	public void setBirth_date(Date birth_date) {
		Birth_date = birth_date;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public int getPostal_code() {
		return Postal_code;
	}
	public void setPostal_code(int postal_code) {
		Postal_code = postal_code;
	}
	public String getJob() {
		return Job;
	}
	public void setJob(String job) {
		Job = job;
	}
	public Status getStatus() {
		return Status;
	}
	public void setStatus(Status status) {
		Status = status;
	}
	public int getShifting() {
		return shifting;
	}
	public void setShifting(int shifting) {
		this.shifting = shifting;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public String getConfirm_password() {
		return Confirm_password;
	}
	public void setConfirm_password(String confirm_password) {
		Confirm_password = confirm_password;
	}
	public int getVerified_account() {
		return Verified_account;
	}
	public void setVerified_account(int verified_account) {
		Verified_account = verified_account;
	}
	public int getRandom() {
		return random;
	}
	public void setRandom(int random) {
		this.random = random;
	}
	public Date getHiring_date() {
		return Hiring_date;
	}
	public void setHiring_date(Date hiring_date) {
		Hiring_date = hiring_date;
	}
	public Role_User getRole_User() {
		return Role_User;
	}
	public void setRole_User(Role_User role_User) {
		Role_User = role_User;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "User [User_ID=" + User_ID + ", First_name=" + First_name + ", Last_name=" + Last_name + ", Number="
				+ Number + ", CIN=" + CIN + ", Geographical_area=" + Geographical_area + ", Motorisation="
				+ Motorisation + ", Birth_date=" + Birth_date + ", Address=" + Address + ", Postal_code=" + Postal_code
				+ ", Job=" + Job + ", Status=" + Status + ", shifting=" + shifting + ", Email=" + Email + ", Password="
				+ Password + ", Confirm_password=" + Confirm_password + ", Verified_account=" + Verified_account
				+ ", random=" + random + ", Hiring_date=" + Hiring_date + ", Role_User=" + Role_User + "]";
	}
	public User(int user_ID, String first_name, String last_name, int number, long cIN,
			tn.esprit.spring.entity.Geographical_area geographical_area, int motorisation, Date birth_date,
			String address, int postal_code, String job,tn.esprit.spring.entity.Status status, int shifting,
			String email, String password, String confirm_password, int verified_account, int random, Date hiring_date,
			tn.esprit.spring.entity.Role_User role_User) {
		super();
		User_ID = user_ID;
		First_name = first_name;
		Last_name = last_name;
		Number = number;
		CIN = cIN;
		Geographical_area = geographical_area;
		Motorisation = motorisation;
		Birth_date = birth_date;
		Address = address;
		Postal_code = postal_code;
		Job = job;
		Status = status;
		this.shifting = shifting;
		Email = email;
		Password = password;
		Confirm_password = confirm_password;
		Verified_account = verified_account;
		this.random = random;
		Hiring_date = hiring_date;
		Role_User = role_User;
	}
	public User(String first_name, String last_name, int number, long cIN,
			tn.esprit.spring.entity.Geographical_area geographical_area, int motorisation, Date birth_date,
			String address, int postal_code, String job,tn.esprit.spring.entity.Status status, int shifting,
			String email, String password, String confirm_password, int verified_account, int random, Date hiring_date,
			tn.esprit.spring.entity.Role_User role_User) {
		super();
		First_name = first_name;
		Last_name = last_name;
		Number = number;
		CIN = cIN;
		Geographical_area = geographical_area;
		Motorisation = motorisation;
		Birth_date = birth_date;
		Address = address;
		Postal_code = postal_code;
		Job = job;
		Status = status;
		this.shifting = shifting;
		Email = email;
		Password = password;
		Confirm_password = confirm_password;
		Verified_account = verified_account;
		this.random = random;
		Hiring_date = hiring_date;
		Role_User = role_User;
	}
	public User()
	{}
	@OneToMany(cascade = CascadeType.ALL, mappedBy="User")
	private Set<Payment> Payment;
	public User(int user_ID, String first_name, String last_name, int number, long cIN,
			tn.esprit.spring.entity.Geographical_area geographical_area, int motorisation, Date birth_date,
			String address, int postal_code, String job, tn.esprit.spring.entity.Status status, int shifting,
			String email, String password, String confirm_password, int verified_account, int random, Date hiring_date,
			tn.esprit.spring.entity.Role_User role_User, Set<tn.esprit.spring.entity.Payment> payment) {
		super();
		User_ID = user_ID;
		First_name = first_name;
		Last_name = last_name;
		Number = number;
		CIN = cIN;
		Geographical_area = geographical_area;
		Motorisation = motorisation;
		Birth_date = birth_date;
		Address = address;
		Postal_code = postal_code;
		Job = job;
		Status = status;
		this.shifting = shifting;
		Email = email;
		Password = password;
		Confirm_password = confirm_password;
		Verified_account = verified_account;
		this.random = random;
		Hiring_date = hiring_date;
		Role_User = role_User;
		Payment = payment;
	}
	public Set<Payment> getPayment() {
		return Payment;
	}
	public void setPayment(Set<Payment> payment) {
		Payment = payment;
	}


}
