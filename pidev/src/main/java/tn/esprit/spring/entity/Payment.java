package tn.esprit.spring.entity;
import java.io.Serializable;
import java.util.Date;
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


public class Payment implements Serializable {

		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		@Id
		@GeneratedValue (strategy = GenerationType.IDENTITY)
		
		private int Payment_ID;
		@Enumerated(EnumType.STRING)
		Type_Payment Type_Payment;
		
		@Temporal (TemporalType.DATE)
		private Date Date_Payment;
		private float Payment_Amount;
		public int getPayment_ID() {
			return Payment_ID;
		}
		public void setPayment_ID(int payment_ID) {
			Payment_ID = payment_ID;
		}
		public Type_Payment getType() {
			return Type_Payment;
		}
		public void setType(Type_Payment Type_Payment) {
			Type_Payment = Type_Payment;
		}
		public Date getDate_Payment() {
			return Date_Payment;
		}
		public void setDate_Payment(Date date_Payment) {
			Date_Payment = date_Payment;
		}
		public float getPayment_Amount() {
			return Payment_Amount;
		}
		public void setPayment_Amount(float payment_Amount) {
			Payment_Amount = payment_Amount;
		}
		public static long getSerialversionuid() {
			return serialVersionUID;
		}
		@Override
		public String toString() {
			return "Payment [Payment_ID=" + Payment_ID + ", Type_Payment=" + Type_Payment + ", Date_Payment=" + Date_Payment
					+ ", Payment_Amount=" + Payment_Amount + "]";
		}
		public Payment() {
			super();
		}
		public Payment(int payment_ID, tn.esprit.spring.entity.Type_Payment type, Date date_Payment, float payment_Amount) {
			super();
			Payment_ID = payment_ID;
			Type_Payment = Type_Payment;
			Date_Payment = date_Payment;
			Payment_Amount = payment_Amount;
		}
		
		public Type_Payment getType_Payment() {
			return Type_Payment;
		}
		public void setType_Payment(Type_Payment type_Payment) {
			Type_Payment = type_Payment;
		}
		public User getUser() {
			return User;
		}
		public void setUser(User user) {
			User = user;
		}
		
		
		public Payment(int payment_ID, tn.esprit.spring.entity.Type_Payment type_Payment, Date date_Payment,
				float payment_Amount, tn.esprit.spring.entity.User user) {
			super();
			Payment_ID = payment_ID;
			Type_Payment = type_Payment;
			Date_Payment = date_Payment;
			Payment_Amount = payment_Amount;
			User = user;
		}


	


		





		public Payment(tn.esprit.spring.entity.Type_Payment type_Payment, float payment_Amount) {
			super();
			Type_Payment = type_Payment;
			Payment_Amount = payment_Amount;
		}











		public Payment(float payment_Amount) {
			super();
			Payment_Amount = payment_Amount;
		}











		@ManyToOne
		User User;

		
		
	}


