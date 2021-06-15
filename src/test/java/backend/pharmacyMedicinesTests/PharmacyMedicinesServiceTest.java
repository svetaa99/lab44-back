package backend.pharmacyMedicinesTests;

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


import backend.enums.MedicineTypes;
import backend.models.Address;
import backend.models.Medicine;
import backend.models.Pharmacy;
import backend.models.PharmacyMedicines;
import backend.repositories.PharmacyMedicinesRepository;
import backend.services.impl.PharmacyMedicinesService;

@SpringBootTest
public class PharmacyMedicinesServiceTest {
	
	@Mock
	private PharmacyMedicinesRepository pmRepositoryMock;
	
	@Mock
	private PharmacyMedicines pmMock;
	
	@InjectMocks
	private PharmacyMedicinesService pmService;
	
	private PharmacyMedicines createNewPMObject() {
		return new PharmacyMedicines(1l, new Pharmacy(1l, "Nova apoteka", new Address(3l, "JNA", 78, "Ruma", "Srbija", 41.03, 42.04), "Desc", 7.3, 100), 
				new Medicine(3l, "testmed", MedicineTypes.ANESTHETIC, "speeec"), 100, 7, 6l, 7l);
	}
	
	@Test
	public void testFindAll() {
		when(pmRepositoryMock.findAll()).thenReturn(Arrays.asList(createNewPMObject()));
		
		List<PharmacyMedicines> pms = pmService.findAll();
		
		assertThat(pms).hasSize(1);

	}
	
	@Test
	public void testFindOne() {
		when(pmRepositoryMock.findById(1l)).thenReturn(Optional.of(pmMock));
		
		// 2. Akcija
		PharmacyMedicines returnPM = pmService.findById(1l);
		
		// 3. Verifikacija: asertacije i/ili verifikacija interakcije sa mock objektima
		assertEquals(pmMock, returnPM);
		
        verify(pmRepositoryMock, times(1)).findById(1l);
        verifyNoMoreInteractions(pmRepositoryMock);
	}
	
	@Test
	public void testSave() {
		PharmacyMedicines newPM = new PharmacyMedicines();
		newPM.setPharmacy(new Pharmacy(3l, "NewPharm", new Address(3l, "Street", 12, "SomeCity", "SomeCountry", 41.01, 42.03), "someDesc", 5.6, 123));
		newPM.setMedicine(new Medicine(3l, "newmed", MedicineTypes.ANESTHETIC, "someSpec"));
		newPM.setPrice(123);
		newPM.setQuantity(5);
		newPM.setStartDate(1l);
		newPM.setStartDate(2l);
		
		when(pmRepositoryMock.findAll()).thenReturn(Arrays.asList(createNewPMObject()));
		when(pmRepositoryMock.save(newPM)).thenReturn(newPM);
		
		int sizeBeforeAdd = pmService.findAll().size();
		
		PharmacyMedicines savedPm = pmService.save(newPM);
		
		when(pmRepositoryMock.findAll()).thenReturn(Arrays.asList(createNewPMObject(), newPM));
		
		assertThat(savedPm).isNotNull();
		
		List<PharmacyMedicines> pmList = pmService.findAll();
		assertThat(pmList).hasSize(sizeBeforeAdd + 1);
		
		savedPm = pmList.get(pmList.size() - 1);
		
		assertThat(savedPm.getQuantity()).isEqualTo(5);
		assertThat(savedPm.getPrice()).isEqualTo(123);
		
		verify(pmRepositoryMock, times(2)).findAll();
		verify(pmRepositoryMock, times(1)).save(newPM);
		verifyNoMoreInteractions(pmRepositoryMock);
	}
	
	@Test
	public void testFindAllMedicinesInPharmacy() {
		
		when(pmRepositoryMock.findByPharmacyIdEquals(1l)).thenReturn(Arrays.asList(createNewPMObject()));
		
		List<PharmacyMedicines> pmList = pmService.findAllByPharmacyId(1l);
		
		assertEquals(createNewPMObject().getMedicine(), pmList.get(0).getMedicine());
	}
	
	@Test
	public void testFindAllPharmaciesWithMedicine() {
		
		when(pmRepositoryMock.findByMedicineIdEquals(3l)).thenReturn(Arrays.asList(createNewPMObject()));
		
		List<PharmacyMedicines> pmList = pmService.findAllByMedicineId(3l);
		
		assertEquals(createNewPMObject().getPharmacy(), pmList.get(0).getPharmacy());
	}
	
	
	@Test
	public void testFindPharmacyMedicinesByIds() {
		
		when(pmRepositoryMock.findByPharmacyIdAndMedicineId(1l, 3l)).thenReturn(createNewPMObject());
		
		PharmacyMedicines pm = pmService.findPharmacyMedicinesByIds(1l, 3l);
		
		assertEquals(createNewPMObject().getId(), pm.getId());
	}
	
	@Test
	public void testFindAllByPharmacyId() {
		
		when(pmRepositoryMock.findByPharmacyIdEquals(1l)).thenReturn(Arrays.asList(createNewPMObject()));
		
		List<PharmacyMedicines> pmList = pmRepositoryMock.findByPharmacyIdEquals(1l);
		
		assertEquals(createNewPMObject().getId(), pmList.get(0).getId());
		
	}
	
	@Test
	public void testFindAllByMedicineId() {
		
		when(pmRepositoryMock.findByMedicineIdEquals(3l)).thenReturn(Arrays.asList(createNewPMObject()));
		
		List<PharmacyMedicines> pmList = pmRepositoryMock.findByMedicineIdEquals(3l);
		
		assertEquals(createNewPMObject().getId(), pmList.get(0).getId());
		
	}
	
	@Test
	public void testFindAllByMedicineName() {
		
		when(pmRepositoryMock.findByMedicineNameContainingIgnoreCase("testmed")).thenReturn(Arrays.asList(createNewPMObject()));
		
		List<PharmacyMedicines> pmList = pmRepositoryMock.findByMedicineNameContainingIgnoreCase("testmed");
		
		assertEquals(createNewPMObject().getId(), pmList.get(0).getId());
		
	}

}
