package backend.models;

import java.time.LocalDate;
import java.time.LocalTime;

public class SearchDateTime {

	private Long visit;
	
	private LocalDate searchDate;
	
	private LocalTime searchTime;

	public LocalDate getSearchDate() {
		return searchDate;
	}

	public void setSearchDate(LocalDate searchDate) {
		this.searchDate = searchDate;
	}

	public LocalTime getSearchTime() {
		return searchTime;
	}

	public void setSearchTime(LocalTime searchTime) {
		this.searchTime = searchTime;
	}

	public Long getVisit() {
		return visit;
	}

	public void setVisitId(Long visit) {
		this.visit = visit;
	}
	
	
	
}
