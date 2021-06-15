package backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import backend.models.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
