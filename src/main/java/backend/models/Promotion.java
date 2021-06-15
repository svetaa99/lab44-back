package backend.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import backend.dto.PromotionDTO;
import backend.enums.PromotionType;

@Entity
public class Promotion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Pharmacy pharmacy;
	
	@ManyToMany
	@JoinTable(name="promotion_medicines", joinColumns = @JoinColumn(name = "promotion_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "medicine_id", referencedColumnName = "id"))
	private List<Medicine> medicines = new ArrayList<Medicine>();
	
	@Column
	private PromotionType type;
	
	@Column
	private int discount;
	
	@Column
	private String text;
	
	@Column
	private long startDate;
	
	@Column
	private long endDate;
	
	public Promotion() {
		
	}
	
	public Promotion(Pharmacy pharmacy, List<Medicine> medicines, PromotionType type, int discount, String text, long startDate, long endDate) {
		super();
		this.pharmacy = pharmacy;
		this.medicines = medicines;
		this.type = type;
		this.discount = discount;
		this.text = text;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Promotion(Long id, Pharmacy pharmacy, List<Medicine> medicines, PromotionType type, int discount, String text, long startDate, long endDate) {
		super();
		this.id = id;
		this.pharmacy = pharmacy;
		this.medicines = medicines;
		this.type = type;
		this.discount = discount;
		this.text = text;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Pharmacy getPharmacy() {
		return pharmacy;
	}

	public void setPharmacy(Pharmacy pharmacy) {
		this.pharmacy = pharmacy;
	}

	public List<Medicine> getMedicines() {
		return medicines;
	}

	public void setMedicines(List<Medicine> medicines) {
		this.medicines = medicines;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public long getStartDate() {
		return startDate;
	}

	public void setStartDate(long startDate) {
		this.startDate = startDate;
	}

	public long getEndDate() {
		return endDate;
	}

	public void setEndDate(long endDate) {
		this.endDate = endDate;
	}

	public PromotionType getType() {
		return type;
	}

	public void setType(PromotionType type) {
		this.type = type;
	}
	
	
}
