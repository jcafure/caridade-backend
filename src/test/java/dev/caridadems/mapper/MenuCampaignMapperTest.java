package dev.caridadems.mapper;

import dev.caridadems.dto.DonationItemDTO;
import dev.caridadems.dto.MenuCampaignDTO;
import dev.caridadems.dto.ProductDTO;
import dev.caridadems.model.DonationItem;
import dev.caridadems.model.MenuCampaign;
import dev.caridadems.model.Product;
import dev.caridadems.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

class MenuCampaignMapperTest {

    @InjectMocks
    private MenuCampaignMapper menuCampaignMapper;

    @Mock
    private DonationItemMapper donationItemMapper;

    @Mock
    private ProductService productService;

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

    @Test
    void convertDtoToEntity_shouldMapCorrectly() {
        final var productDTO = new ProductDTO();
        productDTO.setId(1);
        final var itemDTO = new DonationItemDTO();
        itemDTO.setProductDTO(productDTO);
        itemDTO.setQuantity(2.0);
        itemDTO.setStatusItem("Para doar");
        final var dto = new MenuCampaignDTO();
        dto.setName("Almoço");
        dto.setDonationItemDTOList(List.of(itemDTO));
        final var productEntity = new Product();
        productEntity.setId(productDTO.getId());
        final var mockedItem = new DonationItem();
        mockedItem.setQuantity(2.0);
        mockedItem.setProduct(productEntity);

        when(donationItemMapper.convertDtoListToEntity(anyList()))
                .thenReturn(List.of(mockedItem));

        final var result = menuCampaignMapper.convertDtoToEntity(dto);

        assertNotNull(result);
        assertEquals("Almoço", result.getMealType());
        assertEquals(1, result.getDonationItems().size());

    }

    @Test
    void test_applyDtoToEntity() {
        final var menuUpdate = new MenuCampaign();
        menuUpdate.setId(1);
        menuUpdate.setMealType("Antigo");
        menuUpdate.setDonationItems(new ArrayList<>());

        final var existing10 = new DonationItem();
        existing10.setId(10);
        existing10.setMenuCampaign(menuUpdate);
        existing10.setQuantity(1.0);
        menuUpdate.getDonationItems().add(existing10);

        final var existing20 = new DonationItem();
        existing20.setId(20);
        existing20.setMenuCampaign(menuUpdate);
        existing20.setQuantity(2.0);
        menuUpdate.getDonationItems().add(existing20);

        final var prod1 = new Product();
        prod1.setId(100);
        final var prod2 = new Product();
        prod2.setId(200);

        final var dto = new MenuCampaignDTO();
        dto.setId(1);
        dto.setName("Feijoada Solidária");

        final var itemExistenteDto = new DonationItemDTO();
        itemExistenteDto.setId(10);
        itemExistenteDto.setQuantity(5.0);
        final var productDto1 = new ProductDTO();
        productDto1.setId(100);
        itemExistenteDto.setProductDTO(productDto1);

        final var itemNovoDto = new DonationItemDTO();
        itemNovoDto.setId(null);
        itemNovoDto.setQuantity(7.0);
        final var productDto2 = new ProductDTO();
        productDto2.setId(200);
        itemNovoDto.setProductDTO(productDto2);

        dto.setDonationItemDTOList(List.of(itemExistenteDto, itemNovoDto));

        when(productService.findById(100)).thenReturn(prod1);
        when(productService.findById(200)).thenReturn(prod2);

        menuCampaignMapper.applyDtoToEntity(dto, menuUpdate);

        assertEquals("Feijoada Solidária", menuUpdate.getMealType());
        var items = menuUpdate.getDonationItems();
        assertEquals(2, items.size());

        DonationItem kept10 = items.stream().filter(i -> Integer.valueOf(10).equals(i.getId())).findFirst().orElse(null);
        DonationItem created = items.stream().filter(i -> i.getId() == null).findFirst().orElse(null);
        assertNotNull(kept10);
        assertNotNull(created);

    }
}