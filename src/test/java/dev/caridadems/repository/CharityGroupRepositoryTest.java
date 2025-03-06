package dev.caridadems.repository;

import dev.caridadems.domain.TypeAddress;
import dev.caridadems.model.Address;
import dev.caridadems.model.CharityGroup;
import dev.caridadems.model.City;
import dev.caridadems.model.State;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
 class CharityGroupRepositoryTest {

    @Mock
    private CharityGroupRepository repository;

    @Test
    void testSaveCharityGroup(){
        final var address01 = getAddress();
        final var groupCharity = new CharityGroup();

        groupCharity.setName("Grupo Caridade-MS");
        groupCharity.setPhone("67982154555");
        groupCharity.setEmail("caridadems@gmail.com");
        groupCharity.setAddress(List.of(address01));

        Mockito.when(repository.save(groupCharity)).thenReturn(groupCharity);
        final var groupSave = repository.save(groupCharity);

        Assertions.assertNotNull(groupSave);
        Assertions.assertEquals(groupCharity.getName(), groupSave.getName());
        Assertions.assertEquals(groupCharity.getPhone(), groupSave.getPhone());
        Assertions.assertEquals(groupCharity.getEmail(), groupSave.getEmail());
        Assertions.assertEquals(1, groupSave.getAddress().size());

    }

    private static Address getAddress() {
        final var state = new State();
        state.setName("Mato Grosso do Sul");
        state.setSigla("MS");

        final var city = new City();
        city.setName("Big Field Hell City");
        city.setState(state);

        final var address01 = new Address();
        address01.setCep("79117852");
        address01.setNumber("500");
        address01.setStreet("rua do baron zalini");
        address01.setComplement("Maçonaria");
        address01.setTypeAddress(TypeAddress.PREPARATION_LOCATION);
        address01.setCity(city);
        return address01;
    }
}
