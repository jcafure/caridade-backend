package dev.caridadems.repository;

import dev.caridadems.domain.TypeAddress;
import dev.caridadems.model.Address;
import dev.caridadems.model.CharityGroup;
import dev.caridadems.model.City;
import dev.caridadems.model.State;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import java.util.List;

@DataJpaTest
 class CharityGroupRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private CharityGroupRepository repository;

    @Test
    void testSaveCharityGroup(){
        final var address01 = getAddress();
        final var groupCharity = new CharityGroup();

        groupCharity.setName("Grupo Caridade-MS");
        groupCharity.setPhone("67982154555");
        groupCharity.setEmail("caridadems@gmail.com");
        groupCharity.setAddress(List.of(address01));

        final var groupSave = repository.save(groupCharity);

        Assertions.assertNotNull(groupSave);
        Assertions.assertEquals(groupCharity.getName(), groupSave.getName());
        Assertions.assertEquals(groupCharity.getPhone(), groupSave.getPhone());
        Assertions.assertEquals(groupCharity.getEmail(), groupSave.getEmail());
        Assertions.assertEquals(1, groupSave.getAddress().size());

    }

    private Address getAddress() {
        final var state = new State();
        state.setName("Mato Grosso do Sul");
        state.setSigla("MS");
        testEntityManager.persist(state);

        final var city = new City();
        city.setName("Big Field Hell City");
        city.setState(state);
        testEntityManager.persist(city);


        final var address01 = new Address();
        address01.setCep("79117852");
        address01.setNumber("500");
        address01.setStreet("rua do baron zalini");
        address01.setComplement("Maçonaria");
        address01.setTypeAddress(TypeAddress.PREPARATION_LOCATION);
        address01.setCity(city);
        testEntityManager.persist(address01);
        return address01;
    }
}
