package backend.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.models.Ratings;
import backend.repositories.RatingRepository;
import backend.services.IRatingService;

@Service
public class RatingService implements IRatingService {

	@Autowired
	private RatingRepository ratingRepository;
	
	@Override
	public List<Ratings> findAll() {
		return ratingRepository.findAll();
	}

	@Override
	public Ratings findById(Long id) {
		return ratingRepository.findById(id).orElse(null);
	}

	@Override
	public Ratings save(Ratings obj) {
		return ratingRepository.save(obj);
	}

	@Override
	public void delete(Ratings obj) {
		ratingRepository.delete(obj);
	}

	@Override
	public List<Ratings> findAllByPatientId(Long patientId) {
		return ratingRepository.findByPatientIdEquals(patientId);
	}

	@Override
	public List<Ratings> findAllByOId(Long objId) {
		return ratingRepository.findByObjIdEquals(objId);
	}

	@Override
	public List<Ratings> findAllByType(int type) {
		return ratingRepository.findByTypeEquals(type);
	}

	@Override
	public List<Ratings> findByObjIdAndType(Long objId, int type) {
		return ratingRepository.findByObjIdAndType(objId, type);
	}

	@Override
	public List<Ratings> findByPatientAndObjAndType(Long patientId, Long objId, int type) {
		return ratingRepository.findByPatientIdAndObjIdAndType(patientId, objId, type);
	}

}
