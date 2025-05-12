package dev.caridadems.mapper;

import dev.caridadems.dto.DonationItemDTO;
import dev.caridadems.model.DonationItem;
import dev.caridadems.model.MenuCampaign;
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
}