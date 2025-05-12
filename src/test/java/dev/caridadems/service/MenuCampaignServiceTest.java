package dev.caridadems.service;

import dev.caridadems.domain.StatusDonationItemMenuCampaign;
import dev.caridadems.dto.DonationItemDTO;
import dev.caridadems.dto.MenuCampaignDTO;
import dev.caridadems.dto.ProductDTO;
import dev.caridadems.mapper.DonationItemMapper;
import dev.caridadems.mapper.MenuCampaignMapper;
import dev.caridadems.model.DonationItem;
import dev.caridadems.model.MenuCampaign;
import dev.caridadems.model.Product;
import dev.caridadems.repository.MenuCampaignRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MenuCampaignServiceTest {

    @Mock
    private ProductService productService;
    @Mock
    private MenuCampaignRepository menuCampaignRepository;
    @Mock
    private DonationItemMapper donationItemMapper;
    @Mock
    private MenuCampaignMapper menuCampaignMapper;
    @InjectMocks
    private MenuCampaignService menuCampaignService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createMenuCampaign() {
        final var product = new Product();
        product.setId(1);

        final var itemDTO = new DonationItemDTO();
        final var productDTO = new ProductDTO();
        productDTO.setId(1);
        itemDTO.setProductDTO(productDTO);
        itemDTO.setQuantity(5.0);
        itemDTO.setStatusItem(StatusDonationItemMenuCampaign.FOR_DONATED.getValue());

        final var inputDto = new MenuCampaignDTO();
        inputDto.setName("Almoço");
        inputDto.setDonationItemDTOList(List.of(itemDTO));

        final var donationItem = new DonationItem();
        donationItem.setProduct(product);
        donationItem.setQuantity(5.0);

        final var savedEntity = new MenuCampaign();
        savedEntity.setMealType("Almoço");
        savedEntity.setDonationItems(List.of(donationItem));
        MenuCampaignDTO expectedOutput = new MenuCampaignDTO();
        expectedOutput.setName("Almoço");

        when(productService.findById(1)).thenReturn(product);
        when(donationItemMapper.dtoToEntity(itemDTO, product)).thenReturn(donationItem);
        when(menuCampaignRepository.save(any(MenuCampaign.class))).thenReturn(savedEntity);
        when(menuCampaignMapper.entityToDto(savedEntity)).thenReturn(expectedOutput);

        MenuCampaignDTO result = menuCampaignService.createMenuCampaign(inputDto);

        assertNotNull(result);
        assertEquals("Almoço", result.getName());
        ArgumentCaptor<MenuCampaign> campaignCaptor = ArgumentCaptor.forClass(MenuCampaign.class);
        verify(menuCampaignRepository).save(campaignCaptor.capture());
        MenuCampaign captured = campaignCaptor.getValue();

        assertEquals("Almoço", captured.getMealType());
        assertEquals(1, captured.getDonationItems().size());
        assertEquals(5.0, captured.getDonationItems().getFirst().getQuantity());


    }
}