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


public class Leave implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		@Id
		@GeneratedValue (strategy = GenerationType.IDENTITY)
		
		private int Leave_ID;
		@Enumerated(EnumType.STRING)
		Type_Leave Type_Leave;
		
		@Temporal (TemporalType.DATE)
		private Date Start_Date;	
		@Temporal (TemporalType.DATE)
		private Date End_Date;
		private int Leave_Duration;
		@Enumerated(EnumType.STRING)
		Leave_reason Leave_reason;
		public int getLeave_ID() {
			return Leave_ID;
		}
		public void setLeave_ID(int leave_ID) {
			Leave_ID = leave_ID;
		}
		public Type_Leave getType_Leave() {
			return Type_Leave;
		}
		public void setType_Leave(Type_Leave type_Leave) {
			Type_Leave = type_Leave;
		}
		public Date getStart_Date() {
			return Start_Date;
		}
		public void setStart_Date(Date start_Date) {
			Start_Date = start_Date;
		}
		public Date getEnd_Date() {
			return End_Date;
		}
		public void setEnd_Date(Date end_Date) {
			End_Date = end_Date;
		}
		public int getLeave_Duration() {
			return Leave_Duration;
		}
		public void setLeave_Duration(int leave_Duration) {
			Leave_Duration = leave_Duration;
		}
		public Leave_reason getLeave_reason() {
			return Leave_reason;
		}
		public void setLeave_reason(Leave_reason leave_reason) {
			Leave_reason = leave_reason;
		}
		public static long getSerialversionuid() {
			return serialVersionUID;
		}
		public Leave() {
			super();
		}
		
		@Override
		public String toString() {
			return "Leave [Leave_ID=" + Leave_ID + ", Type_Leave=" + Type_Leave + ", Start_Date=" + Start_Date
					+ ", End_Date=" + End_Date + ", Leave_Duration=" + Leave_Duration + ", Leave_reason=" + Leave_reason
					+ "]";
		}
		
		public Leave(int leave_ID, tn.esprit.spring.entity.Type_Leave type_Leave, Date start_Date, Date end_Date,
				int leave_Duration, tn.esprit.spring.entity.Leave_reason leave_reason,
				tn.esprit.spring.entity.User user) {
			super();
			Leave_ID = leave_ID;
			Type_Leave = type_Leave;
			Start_Date = start_Date;
			End_Date = end_Date;
			Leave_Duration = leave_Duration;
			Leave_reason = leave_reason;
			User = user;
		}
		public User getUser() {
			return User;
		}
		public void setUser(User user) {
			User = user;
		}

		@ManyToOne
		User User;

		
		
}
