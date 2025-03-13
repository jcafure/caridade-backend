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

import java.time.LocalDate;
import java.util.List;

@DataJpaTest
 class CampaignRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CampaingRepository campaingRepository;

    @Test
    void save() {
        final var name = "Atendimento";
        final var description = "Atendimento1";
        LocalDate dateInit = LocalDate.now();
        LocalDate dateEnd = LocalDate.of(2025, 3, 15);

        final var charityGroup = new CharityGroup();
        charityGroup.setAddress(List.of(getAddress()));
        charityGroup.setName("Grupo Caridade-MS");
        charityGroup.setPhone("67982154555");
        charityGroup.setEmail("caridadems@gmail.com");
        entityManager.persist(charityGroup);

        final var product = new Product();
        product.setProductCategory(ProductCategory.FOOD);
        product.setName("Arroz");
        product.setUnitOfMeasure(UnitOfMeasure.KG);
        entityManager.persist(product);

        final var donationItem = new DonationItem();
        donationItem.setStatusItem(StatusDonationItemMenuCampaign.FOR_DONATED);
        donationItem.setProduct(product);
        donationItem.setQuantity(15.0);
        entityManager.persist(donationItem);

        final var menuCampaign = new MenuCampaign();
        menuCampaign.setDonationItems(List.of(donationItem));
        menuCampaign.setMealType("arroz carreteiro");
        entityManager.persist(menuCampaign);

        final var campaign = new Campaign();
        campaign.setName(name);
        campaign.setDescription(description);
        campaign.setDateInit(dateInit);
        campaign.setDateEnd(dateEnd);
        campaign.setStatus(StatusCampaign.OPEN);
        campaign.setCharityGroup(charityGroup);
        campaign.setMenuCampaigns(List.of(menuCampaign));

        final var campaignSave = entityManager.persist(campaign);

        Assertions.assertNotNull(campaignSave);
        Assertions.assertTrue(campaignSave.getMenuCampaigns().contains(menuCampaign));
        Assertions.assertEquals(menuCampaign.getDonationItems().get(0), campaignSave.getMenuCampaigns().get(0).getDonationItems().get(0));

    }

    private Address getAddress() {
        final var state = new State();
        state.setName("Mato Grosso do Sul");
        state.setSigla("MS");
        entityManager.persist(state);

        final var city = new City();
        city.setName("Big Field Hell City");
        city.setState(state);
        entityManager.persist(city);

        final var address01 = new Address();
        address01.setCep("79117852");
        address01.setNumber("500");
        address01.setStreet("rua do baron zalini");
        address01.setComplement("Maçonaria");
        address01.setTypeAddress(TypeAddress.PREPARATION_LOCATION);
        address01.setCity(city);
        entityManager.persist(address01);
        return address01;
    }
}
