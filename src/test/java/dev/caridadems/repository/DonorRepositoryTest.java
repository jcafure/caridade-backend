package dev.caridadems.repository;

import dev.caridadems.domain.TypeAddress;
import dev.caridadems.model.Address;
import dev.caridadems.model.City;
import dev.caridadems.model.Donor;
import dev.caridadems.model.State;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
class DonorRepositoryTest {

    @Mock
    private DonorRepository donorRepository;

    @Test
    void testSaveDonor() {
        final var state = new State();
        state.setName("Mato Grosso do Sul");
        state.setSigla("MS");

        final var city = new City();
        city.setName("Big Field Hell City");
        city.setState(state);

        final var address01 = new Address();
        address01.setCep("79117852");
        address01.setNumber("1245");
        address01.setStreet("rua do baron");
        address01.setComplement("Casa");
        address01.setTypeAddress(TypeAddress.HOME);
        address01.setCity(city);

        final var address02 = new Address();
        address02.setCep("79117763");
        address02.setStreet("Rua da briza");
        address02.setNumber("100");
        address02.setComplement("Trabalho");
        address02.setTypeAddress(TypeAddress.LOCAL_JOB);
        address02.setCity(city);

        final var donor = new Donor();
        donor.setName("Jaime");
        donor.setEmail("jaiminho@teste");
        donor.setPhone("67982153447");
        donor.setAddress(Arrays.asList(address01, address02));

        Mockito.when(donorRepository.save(donor)).thenReturn(donor);

        final var donorSave = donorRepository.save(donor);

        Assertions.assertNotNull(donorSave);
        Assertions.assertEquals(donor.getName(), donorSave.getName());
        Assertions.assertEquals(donor.getEmail(), donorSave.getEmail());
        Assertions.assertEquals(donor.getPhone(), donorSave.getPhone());
        Assertions.assertEquals(2, donorSave.getAddress().size());

        final var addressSave = donorSave.getAddress().getFirst();
        Assertions.assertEquals(address01.getCep(), addressSave.getCep());
        Assertions.assertEquals(address01.getNumber(), addressSave.getNumber());
        Assertions.assertEquals(address01.getStreet(), addressSave.getStreet());
        Assertions.assertEquals(address01.getComplement(), addressSave.getComplement());
        Assertions.assertEquals(address01.getTypeAddress(), addressSave.getTypeAddress());

        final var addressSave02 = donorSave.getAddress().get(1);
        Assertions.assertEquals(address02.getCep(), addressSave02.getCep());
        Assertions.assertEquals(address02.getNumber(), addressSave02.getNumber());
        Assertions.assertEquals(address02.getStreet(), addressSave02.getStreet());
        Assertions.assertEquals(address02.getComplement(), addressSave02.getComplement());
        Assertions.assertEquals(address02.getTypeAddress(), addressSave02.getTypeAddress());

    }
}
