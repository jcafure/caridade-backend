package dev.caridadems.repository;

import dev.caridadems.domain.ProductCategory;
import dev.caridadems.domain.StatusCampaign;
import dev.caridadems.domain.StatusDonationItemMenuCampaign;
import dev.caridadems.domain.TypeAddress;
import dev.caridadems.domain.UnitOfMeasure;
import dev.caridadems.model.Address;
import dev.caridadems.model.Campaign;
import dev.caridadems.model.CharityGroup;
import dev.caridadems.model.City;
import dev.caridadems.model.DonationItem;
import dev.caridadems.model.MenuCampaign;
import dev.caridadems.model.Product;
import dev.caridadems.model.State;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

@DataJpaTest
 class MenuCampaignRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private MenuCampaignRepository menuCampaignRepository;

    @Test
     void save(){
        final var charityGroup = new CharityGroup();
        charityGroup.setAddress(List.of(getAddress()));
        charityGroup.setName("Grupo Caridade-MS");
        charityGroup.setPhone("67982154555");
        charityGroup.setEmail("caridadems@gmail.com");
        testEntityManager.persist(charityGroup);

        final var product = new Product();
        product.setProductCategory(ProductCategory.FOOD);
        product.setName("Arroz");
        product.setUnitOfMeasure(UnitOfMeasure.KG);
        testEntityManager.persist(product);

        final var campaign = new Campaign();
        campaign.setName("atendimento");
        campaign.setDescription("alimentos");
        campaign.setStatus(StatusCampaign.OPEN);
        testEntityManager.persist(campaign);

        final var donationItem = new DonationItem();
        donationItem.setStatusItem(StatusDonationItemMenuCampaign.FOR_DONATED);
        donationItem.setProduct(product);
        donationItem.setQuantity(15.0);
        testEntityManager.persist(donationItem);

        final var menuCampaign = new MenuCampaign();
        menuCampaign.setDonationItems(List.of(donationItem));
        menuCampaign.setMealType("arroz carreteiro");

        final var menuCampaignSave = testEntityManager.persist(menuCampaign);
        Assertions.assertNotNull(menuCampaignSave);
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
