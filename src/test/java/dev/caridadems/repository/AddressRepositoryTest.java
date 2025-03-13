package dev.caridadems.repository;

import dev.caridadems.domain.TypeAddress;
import dev.caridadems.model.Address;
import dev.caridadems.model.Donor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class AddressRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private AddressRepository addressRepository;

    @Test
    void save() {
        final var donor = new Donor();
        donor.setEmail("jj@jj");
        donor.setPhone("6782154578");

        final var address = new Address();
        address.setTypeAddress(TypeAddress.HOME);
        address.setStreet("rua da filó");
        address.setNumber("1545");
        address.setComplement("Casa");
        address.setDonor(donor);

        final var addressSave = testEntityManager.persist(address);
        Assertions.assertNotNull(addressSave);

    }
}
