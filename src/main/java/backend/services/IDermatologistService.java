package backend.services;

import java.util.List;

import backend.models.Dermatologist;

public interface IDermatologistService extends IService<Dermatologist> {

	List<Dermatologist> findAllByNameOrSurname(String name, String surname);
	
	List<Dermatologist> findAllByPharmacy(Long pharmacyId);
}
