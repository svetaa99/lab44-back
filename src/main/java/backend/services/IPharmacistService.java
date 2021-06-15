package backend.services;

import java.util.List;

import backend.models.Pharmacist;
import backend.models.Pharmacy;

public interface IPharmacistService extends IService<Pharmacist> {

	List<Pharmacist> findAllByPharmacy(Long pharmacistId);
	
	List<Pharmacist> sortByRating(String type, Long pharmacistId);
	
	List<Pharmacist> findAllByNameOrSurname(String name, String surname);

}
