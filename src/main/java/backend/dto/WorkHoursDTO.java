package backend.dto;

import backend.models.WorkHours;

public class WorkHoursDTO {
	
	private String startTime;
	private String finishTime;
	
	public WorkHoursDTO() {
		
	}
	
	public WorkHoursDTO(WorkHours wh) {
		this(wh.getStartTime().toString(), wh.getFinishTime().toString());
	}

	public WorkHoursDTO(String startTime, String finishTime) {
		super();
		this.startTime = startTime;
		this.finishTime = finishTime;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}
	
}
