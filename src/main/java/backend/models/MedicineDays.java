package backend.models;

import javax.persistence.Embeddable;

@Embeddable
public class MedicineDays {

	private Long medicine;
	
	private int days;
	
	public MedicineDays() {
		
	}

	public MedicineDays(Long medicine, int days) {
		super();
		this.medicine = medicine;
		this.days = days;
	}

	public Long getmedicine() {
		return medicine;
	}

	public void setmedicine(Long medicine) {
		this.medicine = medicine;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	@Override
	public String toString() {
		return "MedicineDays [medicine=" + medicine + ", days=" + days + "]";
	}
	
	
}
