package backend.dto;

import java.util.List;

import backend.enums.PromotionType;
import backend.models.Medicine;
import backend.models.Promotion;

public class PromotionDTO {
	
	private Long id;
	private Long pharmacyId;
	private List<Medicine> medicines;
	private PromotionType type;
	private int discount;
	private String text;
	private long startDate;
	private long endDate;
	
	public PromotionDTO() {
		
	}

	public PromotionDTO(Long id, Long pharmacyId, List<Medicine> medicines, PromotionType type, int discount, String text,
			long startDate, long endDate) {
		super();
		this.id = id;
		this.pharmacyId = pharmacyId;
		this.medicines = medicines;
		this.type = type;
		this.discount = discount;
		this.text = text;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public PromotionDTO(Promotion p) {
		this(p.getId(), p.getPharmacy().getId(), p.getMedicines(), p.getType(), p.getDiscount(), p.getText(), p.getStartDate(), p.getEndDate());
	}
	
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPharmacyId() {
		return pharmacyId;
	}

	public void setPharmacyId(Long pharmacyId) {
		this.pharmacyId = pharmacyId;
	}

	public List<Medicine> getMedicines() {
		return medicines;
	}

	public void setMedicines(List<Medicine> medicines) {
		this.medicines = medicines;
	}

	public PromotionType getType() {
		return type;
	}

	public void setType(PromotionType type) {
		this.type = type;
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

	
	
	
}
