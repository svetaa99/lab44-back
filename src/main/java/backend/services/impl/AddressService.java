package backend.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.models.Address;
import backend.repositories.AddressRepository;
import backend.services.IAddressService;

@Service
public class AddressService implements IAddressService {

	@Autowired
	private AddressRepository addressRepository;
	
	@Override
	public List<Address> findAll() {
		return addressRepository.findAll();
	}

	@Override
	public Address findById(Long id) {
		return addressRepository.findById(id).orElse(null);
	}

	@Override
	public Address save(Address obj) {
		return addressRepository.save(obj);
	}

	@Override
	public void delete(Address obj) {
		addressRepository.delete(obj);
	}

}
