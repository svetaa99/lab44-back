package backend.dto;

import java.util.List;

public class DermatologistFilterObject {
	
	private List<DermatologistDTO> dermatologistsList;
	private FilterObject filterObject;

	public DermatologistFilterObject() {
		
	}

	public DermatologistFilterObject(List<DermatologistDTO> dermatologistsList, FilterObject filterObject) {
		super();
		this.dermatologistsList = dermatologistsList;
		this.filterObject = filterObject;
	}

	public List<DermatologistDTO> getDermatologistsList() {
		return dermatologistsList;
	}

	public void setDermatologistsList(List<DermatologistDTO> dermatologistsList) {
		this.dermatologistsList = dermatologistsList;
	}

	public FilterObject getFilterObject() {
		return filterObject;
	}

	public void setFilterObject(FilterObject filterObject) {
		this.filterObject = filterObject;
	}
	
}
