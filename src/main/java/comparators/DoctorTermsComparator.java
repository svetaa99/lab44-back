package comparators;

import java.util.Comparator;

import backend.models.DoctorTerms;

public class DoctorTermsComparator implements Comparator<DoctorTerms>{

	@Override
	public int compare(DoctorTerms o1, DoctorTerms o2) {
		// TODO Auto-generated method stub
		return o1.getStart().compareTo(o2.getStart());
	}

}
