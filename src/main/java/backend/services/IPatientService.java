package backend.services;

import backend.models.Patient;

public interface IPatientService extends IService<Patient> {

	Patient findByEmail(String email);
}
