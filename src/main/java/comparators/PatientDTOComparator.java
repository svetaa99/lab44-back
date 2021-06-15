package comparators;

import java.util.Comparator;

import backend.dto.PatientDTO;

public class PatientDTOComparator implements Comparator<PatientDTO>{
	
	private int order;
	
	public PatientDTOComparator() {
		
	}
	
	public PatientDTOComparator(int order) {
		this.order = order;
	}
	
	public void setOrder(int order) {
		if(order == 0)
			this.order = -1;
		else 
			this.order = 1;
	}
	
	public int getOrder() {
		return this.order;
	}

	
	@Override
	public int compare(PatientDTO o1, PatientDTO o2) {
		int retVal;
		if(o1.getDate() == null && o2.getDate() == null)
			retVal = 0;
		else if(o1.getDate() == null && o2.getDate() != null)
			retVal = -1;
		else if(o1.getDate() != null && o2.getDate() == null)
			retVal = 1;
		else
			retVal = o1.getDate().compareTo(o2.getDate());
		
		return retVal * order;
	}

}
