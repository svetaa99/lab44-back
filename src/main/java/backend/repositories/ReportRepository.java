package backend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.models.Report;

public interface ReportRepository extends JpaRepository<Report, Long>{

	public List<Report> findByVisitIdEquals(Long id);
	
//	public List<Report> findByPatientIdEquals(Long id);
//	
//	public List<Report> findByDoctorIdEquals(Long id);
	
}
