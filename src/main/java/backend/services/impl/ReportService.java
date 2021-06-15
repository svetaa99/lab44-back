package backend.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.models.Report;
import backend.repositories.ReportRepository;
import backend.services.IService;

@Service
public class ReportService implements IService<Report>{

	@Autowired
	private ReportRepository reportRepository;
	
	@Override
	public List<Report> findAll() {
		return reportRepository.findAll();
	}

	@Override
	public Report findById(Long id) {
		return reportRepository.findById(id).orElseGet(null);
	}

	@Override
	public Report save(Report obj) {
		return reportRepository.save(obj);
	}

	@Override
	public void delete(Report obj) {
		reportRepository.delete(obj);
	}
	
}
