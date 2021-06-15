package backend.doctorTermsTests;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import backend.models.DoctorTerms;
import backend.repositories.DoctorTermsRepository;
import backend.services.impl.DoctorTermsService;

@SpringBootTest
public class DoctorTermsServiceTest {

	@Mock
	private DoctorTermsRepository doctorTermsRepositoryMock;
	
	@Mock
	private DoctorTerms dtMock;
	
	@InjectMocks
	private DoctorTermsService doctorTermsService;
	
	private DoctorTerms createNewDTObject() {
		return new DoctorTerms(1l, 1l, 1l, LocalDateTime.of(2021, 6, 6, 16, 0, 0), LocalDateTime.of(2021, 6, 6, 17, 0, 0));
	}
	
	private List<DoctorTerms> docIdList(){
		List<DoctorTerms> dts = new ArrayList<DoctorTerms>();
		
		DoctorTerms dt1 = new DoctorTerms(1l, 1l, 1l, LocalDateTime.of(2021, 6, 6, 16, 0, 0), LocalDateTime.of(2021, 6, 6, 17, 0, 0));
		DoctorTerms dt2 = new DoctorTerms(1l, 1l, 3l, LocalDateTime.of(2021, 6, 7, 17, 0, 0), LocalDateTime.of(2021, 6, 7, 17, 30, 0));
		dts.add(dt1);
		dts.add(dt2);
		
		return dts;
	}
	
	private List<DoctorTerms> docFutureList(){
		List<DoctorTerms> dts = new ArrayList<DoctorTerms>();
		
		DoctorTerms dt1 = new DoctorTerms(1l, 1l, 1l, LocalDateTime.of(2021, 5, 6, 16, 0, 0), LocalDateTime.of(2021, 5, 6, 17, 0, 0));
		DoctorTerms dt2 = new DoctorTerms(1l, 1l, 3l, LocalDateTime.of(2021, 12, 7, 17, 0, 0), LocalDateTime.of(2021, 12, 7, 17, 30, 0));
		dts.add(dt1);
		dts.add(dt2);
		
		return dts;
	}
	
	@Test
	public void testFindAll() {
		when(doctorTermsRepositoryMock.findAll()).thenReturn(
				Arrays.asList(createNewDTObject()));
		
		List<DoctorTerms> dts = doctorTermsService.findAll();
		assertThat(dts).hasSize(1);
	}
	
	@Test
	public void testFindOne() {
		when(doctorTermsRepositoryMock.findById(1l)).thenReturn(Optional.of(dtMock));
		
		DoctorTerms dt = doctorTermsService.findById(1l);
		
		assertEquals(dt, dtMock);
	
		verify(doctorTermsRepositoryMock, times(1)).findById(1l);
		verifyNoMoreInteractions(doctorTermsRepositoryMock);
	}
	
	@Test
	public void testByDoctorIdEquals() {
		when(doctorTermsRepositoryMock.findByDoctorIdEquals(1l))
				.thenReturn(docIdList());
		List<DoctorTerms> dts = doctorTermsService.findByDoctorIdEquals(1l);
		
		assertEquals(dts.get(0), createNewDTObject());
	}
	
	@Test
	public void testBadIdDoctorIdEquals() throws Exception {
		when(doctorTermsRepositoryMock.findByDoctorIdEquals(1l))
		.thenReturn(docIdList());
		
		List<DoctorTerms> dts = doctorTermsService.findByDoctorIdEquals(2l);
		
		assertThat(dts).hasSize(0);
	}
	
	@Test
	public void testNullIdDoctorIdEquals() throws Exception {
		when(doctorTermsRepositoryMock.findByDoctorIdEquals(1l))
		.thenReturn(docIdList());
		
		List<DoctorTerms> dts = doctorTermsService.findByDoctorIdEquals(null);
		
		assertThat(dts).hasSize(0);
	}
	
	@Test
	public void testSave() {
		dtMock = createNewDTObject();
		
		when(doctorTermsRepositoryMock.save(dtMock)).thenReturn(dtMock);
        
		DoctorTerms dt = doctorTermsRepositoryMock.save(dtMock);
           
        assertEquals(dt, dtMock);
	}
	
	@Test
	public void testFutureTerms() {
		when(doctorTermsRepositoryMock.findAll()).thenReturn(docFutureList());
		
		List<DoctorTerms> future = doctorTermsService.findAllFutureTerms();
		
		assertThat(future).hasSize(1);
	}
	
}
