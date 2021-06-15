package backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.models.DoctorTerms;
import backend.models.Pharmacy;

public interface DoctorTermsRepository extends JpaRepository<DoctorTerms, Long>{

	public List<DoctorTerms> findByDoctorIdEquals(Long id);

//	public List<DoctorTerms> findByOrderByPriceAsc();

//	public List<DoctorTerms> findByOrderByPriceDesc();
	
}
