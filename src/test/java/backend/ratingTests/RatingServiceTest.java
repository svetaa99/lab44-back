package backend.ratingTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import backend.models.Ratings;
import backend.repositories.RatingRepository;
import backend.services.impl.RatingService;

@SpringBootTest
public class RatingServiceTest {
	
	@Mock
	private RatingRepository ratingRepositoryMock;
	
	@Mock
	private Ratings ratingsMock;
	
	@InjectMocks
	private RatingService ratingService;
	
	private Ratings createNewRatingsObject() {
		return new Ratings(1l, 1l, 1, 1l, 5);
	}
	
	@Test
	public void testFindAll() {
		when(ratingRepositoryMock.findAll()).thenReturn(Arrays.asList(createNewRatingsObject()));
		List<Ratings> ratings = ratingService.findAll();
		assertThat(ratings).hasSize(1);
	}
	
	@Test
	public void testFindOne() {
		when(ratingRepositoryMock.findById(1l)).thenReturn(Optional.of(ratingsMock));
		Ratings returnRating = ratingService.findById(1l);
		assertEquals(ratingsMock, returnRating);
		verify(ratingRepositoryMock, times(1)).findById(1l);
		verifyNoMoreInteractions(ratingRepositoryMock);
	}
	
	@Test
	public void testSave() {
		Ratings newRating = new Ratings();
		newRating.setMark(4);
		newRating.setObjId(1l);
		newRating.setType(1);
		newRating.setPatientId(2l);
		when(ratingRepositoryMock.findAll()).thenReturn(Arrays.asList(createNewRatingsObject()));
		when(ratingRepositoryMock.save(newRating)).thenReturn(newRating);
		
		int sizeBeforeAdd = ratingService.findAll().size();
		Ratings savedRating = ratingService.save(newRating);
		assertThat(savedRating).isNotNull();
		
		when(ratingRepositoryMock.findAll()).thenReturn(Arrays.asList(createNewRatingsObject(), newRating));
		List<Ratings> ratingsList = ratingService.findAll();
		assertThat(ratingsList).hasSize(sizeBeforeAdd + 1);
		
		savedRating = ratingsList.get(ratingsList.size() - 1);
		assertThat(savedRating.getMark()).isEqualTo(4);
		assertThat(savedRating.getObjId()).isEqualTo(1l);
		assertThat(savedRating.getPatientId()).isEqualTo(2l);
		assertThat(savedRating.getType()).isEqualTo(1);
		
		verify(ratingRepositoryMock, times(2)).findAll();
		verify(ratingRepositoryMock, times(1)).save(newRating);
		verifyNoMoreInteractions(ratingRepositoryMock);
	}
	
	@Test
	public void testFindAllByPatientId() {
		when(ratingRepositoryMock.findByPatientIdEquals(1l)).thenReturn(Arrays.asList(createNewRatingsObject()));
		List<Ratings> ratingsList = ratingService.findAllByPatientId(1l);
		assertEquals(createNewRatingsObject().getPatientId(), ratingsList.get(0).getPatientId());
	}
	
	@Test
	public void testFindAllByOId() {
		when(ratingRepositoryMock.findByObjIdEquals(1l)).thenReturn(Arrays.asList(createNewRatingsObject()));
		List<Ratings> ratingsList = ratingService.findAllByOId(1l);
		assertEquals(createNewRatingsObject().getObjId(), ratingsList.get(0).getObjId());
	}
	
	@Test
	public void testFindAllByType() {
		when(ratingRepositoryMock.findByTypeEquals(1)).thenReturn(Arrays.asList(createNewRatingsObject()));
		List<Ratings> ratingsList = ratingService.findAllByType(1);
		assertEquals(createNewRatingsObject().getType(), ratingsList.get(0).getType());
	}
	
	@Test
	public void testFindByObjAndType() {
		when(ratingRepositoryMock.findByObjIdAndType(1l, 1)).thenReturn(Arrays.asList(createNewRatingsObject()));
		List<Ratings> ratingsList = ratingRepositoryMock.findByObjIdAndType(1l, 1);
		assertEquals(createNewRatingsObject().getId(), ratingsList.get(0).getId());
	}
	
	@Test
	public void testFindByPatientAndObjAndType() {
		when(ratingRepositoryMock.findByPatientIdAndObjIdAndType(1l, 1l, 1)).thenReturn(Arrays.asList(createNewRatingsObject()));
		List<Ratings> ratingsList = ratingRepositoryMock.findByPatientIdAndObjIdAndType(1l, 1l, 1);
		assertEquals(createNewRatingsObject().getId(), ratingsList.get(0).getId());
	}
}
