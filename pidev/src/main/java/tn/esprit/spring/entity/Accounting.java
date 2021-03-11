package tn.esprit.spring.entity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity

public class Accounting  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		@Id
		@GeneratedValue (strategy = GenerationType.IDENTITY)
		private int Accounting_ID;
		private float Actifs;
		private float Passifs;
		private float SCR;
		private float BCR;
		public int getAccounting_ID() {
			return Accounting_ID;
		}
		public void setAccounting_ID(int accounting_ID) {
			Accounting_ID = accounting_ID;
		}
		public float getActifs() {
			return Actifs;
		}
		public void setActifs(float actifs) {
			Actifs = actifs;
		}
		public float getPassifs() {
			return Passifs;
		}
		public void setPassifs(float passifs) {
			Passifs = passifs;
		}
		public float getSCR() {
			return SCR;
		}
		public void setSCR(float sCR) {
			SCR = sCR;
		}
		public float getBCR() {
			return BCR;
		}
		public void setBCR(float bCR) {
			BCR = bCR;
		}
		public static long getSerialversionuid() {
			return serialVersionUID;
		}
		public Accounting(int accounting_ID, float actifs, float passifs, float sCR, float bCR) {
			super();
			Accounting_ID = accounting_ID;
			Actifs = actifs;
			Passifs = passifs;
			SCR = sCR;
			BCR = bCR;
		}
		
		
		public User getUser() {
			return User;
		}
		public void setUser(User user) {
			User = user;
		}


		public Accounting(int accounting_ID, float actifs, float passifs, float sCR, float bCR,
				tn.esprit.spring.entity.User user) {
			super();
			Accounting_ID = accounting_ID;
			Actifs = actifs;
			Passifs = passifs;
			SCR = sCR;
			BCR = bCR;
			User = user;
		}


		@OneToOne(mappedBy="Accounting")
		private User User; 
}
