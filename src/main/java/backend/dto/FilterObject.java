package backend.dto;

import java.util.List;
import java.util.Map;

public class FilterObject {
	
	private Map<String, String> filterMap;
	
	public FilterObject() {
		
	}
	
	

	public FilterObject(Map<String, String> filterMap) {
		super();
		this.filterMap = filterMap;
	}


	public Map<String, String> getFilterMap() {
		return filterMap;
	}

	public void setFilterMap(Map<String, String> filterMap) {
		this.filterMap = filterMap;
	}

}
