package backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.enums.MedicineTypes;
import backend.models.Medicine;

public interface MedicineRepository extends JpaRepository<Medicine, Long> {

	List<Medicine> findAllByNameContainingIgnoreCase(String name);

	List<Medicine> findByType(MedicineTypes type);

}
