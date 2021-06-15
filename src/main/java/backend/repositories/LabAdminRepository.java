package backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.models.LabAdmin;

public interface LabAdminRepository extends JpaRepository<LabAdmin, Long> {
	
	LabAdmin findByEmailEquals(String email);
}
