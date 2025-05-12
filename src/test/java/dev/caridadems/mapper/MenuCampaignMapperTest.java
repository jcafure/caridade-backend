package dev.caridadems.mapper;

import dev.caridadems.dto.DonationItemDTO;
import dev.caridadems.dto.ProductDTO;
import dev.caridadems.model.DonationItem;
import dev.caridadems.model.MenuCampaign;
import dev.caridadems.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class MenuCampaignMapperTest {

    @InjectMocks
    private MenuCampaignMapper menuCampaignMapper;

    @Mock
    private DonationItemMapper donationItemMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void entityToDto() {
        var campaign = new MenuCampaign();
        campaign.setId(1);
        campaign.setMealType("Almoço");

        var item = new DonationItem();
        item.setQuantity(10.0);
        campaign.setDonationItems(List.of(item));

        var itemDTO = new DonationItemDTO();
        itemDTO.setQuantity(10.0);

        when(donationItemMapper.entityToDto(campaign.getDonationItems())).thenReturn(List.of(itemDTO));

        var dto = menuCampaignMapper.entityToDto(campaign);

        assertEquals(1, dto.getId());
        assertEquals("Almoço", dto.getName());
        assertNotNull(dto.getDonationItemDTOList());
        assertEquals(1, dto.getDonationItemDTOList().size());
        assertEquals(10.0, dto.getDonationItemDTOList().getFirst().getQuantity());

    }

    @Test
    void entityToDto_withMultipleDifferentProducts_shouldMapAllCorrectly() {
        var product1 = new Product();
        product1.setId(1);
        product1.setName("Arroz");

        var product2 = new Product();
        product2.setId(2);
        product2.setName("Feijão");

        var item1 = new DonationItem();
        item1.setQuantity(10.0);
        item1.setProduct(product1);

        var item2 = new DonationItem();
        item2.setQuantity(5.0);
        item2.setProduct(product2);

        var campaign = new MenuCampaign();
        campaign.setId(1);
        campaign.setMealType("Almoço");
        campaign.setDonationItems(List.of(item1, item2));

        var itemDTO1 = new DonationItemDTO();
        itemDTO1.setQuantity(10.0);
        var productDTO1 = new ProductDTO();
        productDTO1.setId(1);
        productDTO1.setName("Arroz");
        itemDTO1.setProductDTO(productDTO1);

        var itemDTO2 = new DonationItemDTO();
        itemDTO2.setQuantity(5.0);
        var productDTO2 = new ProductDTO();
        productDTO2.setId(2);
        productDTO2.setName("Feijão");
        itemDTO2.setProductDTO(productDTO2);

        when(donationItemMapper.entityToDto(campaign.getDonationItems()))
                .thenReturn(List.of(itemDTO1, itemDTO2));

        var dto = menuCampaignMapper.entityToDto(campaign);

        assertEquals(1, dto.getId());
        assertEquals("Almoço", dto.getName());
        assertNotNull(dto.getDonationItemDTOList());
        assertEquals(2, dto.getDonationItemDTOList().size());

        var dto1 = dto.getDonationItemDTOList().get(0);
        var dto2 = dto.getDonationItemDTOList().get(1);

        assertEquals("Arroz", dto1.getProductDTO().getName());
        assertEquals(10.0, dto1.getQuantity());

        assertEquals("Feijão", dto2.getProductDTO().getName());
        assertEquals(5.0, dto2.getQuantity());
    }

}