package dev.caridadems.service;

import dev.caridadems.domain.StatusDonationItemMenuCampaign;
import dev.caridadems.dto.DonationItemDTO;
import dev.caridadems.dto.MenuCampaignDTO;
import dev.caridadems.dto.ProductDTO;
import dev.caridadems.exception.ObjectNotFoundException;
import dev.caridadems.mapper.MenuCampaignMapper;
import dev.caridadems.model.DonationItem;
import dev.caridadems.model.MenuCampaign;
import dev.caridadems.model.Product;
import dev.caridadems.repository.MenuCampaignRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MenuCampaignServiceTest {

    @Mock
    private MenuCampaignRepository menuCampaignRepository;
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

        when(menuCampaignRepository.save(any(MenuCampaign.class))).thenReturn(savedEntity);
        when(menuCampaignMapper.entityToDto(savedEntity)).thenReturn(expectedOutput);
        when(menuCampaignMapper.convertDtoToEntity(inputDto)).thenReturn(savedEntity);

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

    @Test
    void createMenuCampaignFeijoadaCompleta() {
        final var arroz = new Product();
        arroz.setId(1);
        Product feijao = new Product();
        feijao.setId(2);
        Product calabresa = new Product();
        calabresa.setId(3);
        Product couve = new Product();
        couve.setId(4);

        final var arrozDTO = new ProductDTO();
        arrozDTO.setId(1);
        ProductDTO feijaoDTO = new ProductDTO();
        feijaoDTO.setId(2);
        ProductDTO calabresaDTO = new ProductDTO();
        calabresaDTO.setId(3);
        ProductDTO couveDTO = new ProductDTO();
        couveDTO.setId(4);

        final var item1 = new DonationItemDTO();
        item1.setProductDTO(feijaoDTO);
        item1.setQuantity(2.0);
        item1.setStatusItem(StatusDonationItemMenuCampaign.FOR_DONATED.getValue());

        final var item2 = new DonationItemDTO();
        item2.setProductDTO(arrozDTO);
        item2.setQuantity(3.0);
        item2.setStatusItem(StatusDonationItemMenuCampaign.FOR_DONATED.getValue());

        final var item3 = new DonationItemDTO();
        item3.setProductDTO(calabresaDTO);
        item3.setQuantity(1.5);
        item3.setStatusItem(StatusDonationItemMenuCampaign.FOR_DONATED.getValue());

        final var item4 = new DonationItemDTO();
        item4.setProductDTO(couveDTO);
        item4.setQuantity(1.0);
        item4.setStatusItem(StatusDonationItemMenuCampaign.FOR_DONATED.getValue());

        MenuCampaignDTO inputDto = new MenuCampaignDTO();
        inputDto.setName("Feijoada Completa");
        inputDto.setDonationItemDTOList(List.of(item1, item2, item3, item4));

        final var d1 = new DonationItem();
        d1.setProduct(feijao);
        d1.setQuantity(2.0);

        final var d2 = new DonationItem();
        d2.setProduct(arroz);
        d2.setQuantity(3.0);

        final var d3 = new DonationItem();
        d3.setProduct(calabresa);
        d3.setQuantity(1.5);

        final var d4 = new DonationItem();
        d4.setProduct(couve);
        d4.setQuantity(1.0);

        final var savedEntity = new MenuCampaign();
        savedEntity.setMealType("Feijoada Completa");
        savedEntity.setDonationItems(List.of(d1, d2, d3, d4));

        final var expectedOutput = new MenuCampaignDTO();
        expectedOutput.setName("Feijoada Completa");


        when(menuCampaignRepository.save(any(MenuCampaign.class))).thenReturn(savedEntity);
        when(menuCampaignMapper.entityToDto(savedEntity)).thenReturn(expectedOutput);
        when(menuCampaignMapper.convertDtoToEntity(inputDto)).thenReturn(savedEntity);

        final var result = menuCampaignService.createMenuCampaign(inputDto);

        assertNotNull(result);
        assertEquals("Feijoada Completa", result.getName());

        ArgumentCaptor<MenuCampaign> campaignCaptor = ArgumentCaptor.forClass(MenuCampaign.class);
        verify(menuCampaignRepository).save(campaignCaptor.capture());
        MenuCampaign captured = campaignCaptor.getValue();

        assertEquals("Feijoada Completa", captured.getMealType());
        assertEquals(4, captured.getDonationItems().size());
        assertTrue(captured.getDonationItems().stream().anyMatch(i -> i.getProduct().getId().equals(1) && i.getQuantity().equals(3.0)));
        assertTrue(captured.getDonationItems().stream().anyMatch(i -> i.getProduct().getId().equals(2) && i.getQuantity().equals(2.0)));
        assertTrue(captured.getDonationItems().stream().anyMatch(i -> i.getProduct().getId().equals(3) && i.getQuantity().equals(1.5)));
        assertTrue(captured.getDonationItems().stream().anyMatch(i -> i.getProduct().getId().equals(4) && i.getQuantity().equals(1.0)));
    }

    @Test
    void testFindByMealTypeContainingIgnoreCase() {
        final var menuName = "Feijoada";
        final var pageable = PageRequest.of(0,10);
        final var menuCampaignEntity = new MenuCampaign();
        final var menuCampaignDto = new MenuCampaignDTO();
        Page<MenuCampaign> page = new PageImpl<>(List.of(menuCampaignEntity));

        Mockito.when(menuCampaignRepository.findByMealTypeContainingIgnoreCase(menuName, pageable)).thenReturn(page);
        Mockito.when(menuCampaignMapper.entityToDto(menuCampaignEntity)).thenReturn(menuCampaignDto);

        Page<MenuCampaignDTO> pages = menuCampaignService.findAll(menuName, pageable);

        Assertions.assertEquals(1, pages.getTotalElements());
        Assertions.assertEquals(menuCampaignDto, pages.getContent().getFirst());
    }

    @Test
    void testFindAll() {
        final var menuCampaignEntity = new MenuCampaign();
        final var menuCampaignDto = new MenuCampaignDTO();
        final var page = new PageImpl<>(List.of(menuCampaignEntity));
        final var pageable = PageRequest.of(0,10);

        Mockito.when(menuCampaignRepository.findAll(pageable)).thenReturn(page);
        Mockito.when(menuCampaignMapper.entityToDto(menuCampaignEntity)).thenReturn(menuCampaignDto);
        Mockito.when(menuCampaignMapper.entityToDto(menuCampaignEntity)).thenReturn(menuCampaignDto);

        Page<MenuCampaignDTO> pages = menuCampaignService.findAll(null, pageable);

        Assertions.assertEquals(1, pages.getTotalElements());
        Assertions.assertEquals(menuCampaignDto, pages.getContent().getFirst());
    }

    @Test
    void shouldUpdateMenuCampaignSuccessfully() {
        final var dto = new MenuCampaignDTO();
        final var menuEntity = new MenuCampaign();
        dto.setId(1);
        dto.setName("Feijoada Solidária");
        menuEntity.setId(1);
        menuEntity.setMealType("Feijoada Solidária");

        when(menuCampaignRepository.existsById(1)).thenReturn(true);
        when(menuCampaignMapper.convertDtoToEntity(dto)).thenReturn(menuEntity);
        when(menuCampaignRepository.save(menuEntity)).thenReturn(menuEntity);
        when(menuCampaignMapper.entityToDto(menuEntity)).thenReturn(dto);

        final var result = menuCampaignService.updateMenu(dto);

        assertNotNull(result);
        assertEquals(dto.getId(), result.getId());
        assertEquals(dto.getName(), result.getName());

        verify(menuCampaignRepository).existsById(1);
        verify(menuCampaignRepository).save(menuEntity);
        verify(menuCampaignMapper).convertDtoToEntity(dto);
        verify(menuCampaignMapper).entityToDto(menuEntity);
    }

    @Test
    void shouldThrowExceptionWhenMenuNotFound() {
        final var dto = new MenuCampaignDTO();
        dto.setId(999);

        when(menuCampaignRepository.existsById(999)).thenReturn(false);

        assertThrows(ObjectNotFoundException.class, () -> menuCampaignService.updateMenu(dto));

        verify(menuCampaignRepository).existsById(999);
        verifyNoMoreInteractions(menuCampaignRepository);
        verifyNoInteractions(menuCampaignMapper);
    }

    @Test
    void testDeleteMenuSuccessfully() {
        final var menuId = 1;

        menuCampaignService.delete(menuId);

        Mockito.verify(menuCampaignRepository).deleteById(menuId);
    }

    @Test
    void shouldReturnMenuCampaignDTOWhenIdExists() {
        Integer id = 1;
        final var entity = new MenuCampaign();
        entity.setId(id);
        entity.setMealType("carreteiro");

        final var dto = new MenuCampaignDTO();
        dto.setId(id);
        dto.setName("carreteiro");

        when(menuCampaignRepository.findById(id)).thenReturn(Optional.of(entity));
        when(menuCampaignMapper.entityToDto(entity)).thenReturn(dto);

        final var result = menuCampaignService.findById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("carreteiro", result.getName());

        verify(menuCampaignRepository).findById(id);
        verify(menuCampaignMapper).entityToDto(entity);
    }
}