package backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.models.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long>{

}
