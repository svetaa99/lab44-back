package backend.dto;

import java.time.LocalDateTime;

import backend.enums.VacationType;
import backend.models.Vacation;


public class EventDTO {

	private Long id;
	
	private String name;
	
	private LocalDateTime start;
	
	private LocalDateTime end;
	
	private String color;
	
	private boolean timed;
	
	public EventDTO() {
		
	}

	public EventDTO(VisitDTO visit) {
		this.id = visit.getId();
		this.name = "Appointment\n" + visit.getpatient().getName() + " " + visit.getpatient().getSurname();
		this.start = visit.getStart();
		this.end = visit.getFinish();
		this.color = "#03C6FC";
		this.timed = true;
	}
	
	public EventDTO(Vacation vacation) {
		this.id = -1l;
		this.name = vacation.getType().equals(VacationType.ABSENCE) ? "Vacation" : "Absence";
		this.start = vacation.getStart().atStartOfDay();
		this.end = vacation.getFinish().atStartOfDay();
		this.color = this.name.equals("Vacation") ? "#24FC03" : "#FC5A03"; 
		this.timed = false;
	}
	
	public EventDTO(String name, LocalDateTime start, LocalDateTime end, String color, boolean timed) {
		this.name = name;
		this.start = start;
		this.end = end;
		this.color = color;
		this.timed = timed;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public void setStart(LocalDateTime start) {
		this.start = start;
	}

	public LocalDateTime getEnd() {
		return end;
	}

	public void setEnd(LocalDateTime end) {
		this.end = end;
	}
	
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	public boolean isTimed() {
		return timed;
	}

	public void setTimed(boolean timed) {
		this.timed = timed;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "EventDTO [name=" + name + ", start=" + start + ", end=" + end + "]";
	}

}
