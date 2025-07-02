package dev.caridadems.mapper;

import dev.caridadems.domain.ProductCategory;
import dev.caridadems.domain.StatusDonationItemMenuCampaign;
import dev.caridadems.domain.UnitOfMeasure;
import dev.caridadems.dto.DonationItemDTO;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class DonationItemMapperTest {

    @InjectMocks
    private DonationItemMapper donationItemMapper;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void dtoToEntity() {
        final var productDTO = new ProductDTO();
        productDTO.setName("Arroz");
        productDTO.setCategoryProduct(ProductCategory.FOOD.getValor());
        productDTO.setUnitOfMeasure(UnitOfMeasure.KG.getValue());

        final var product = new Product();
        product.setName("Arroz");
        product.setProductCategory(ProductCategory.FOOD);
        product.setUnitOfMeasure(UnitOfMeasure.KG);

        final var donationItemDTO = new DonationItemDTO();
        donationItemDTO.setStatusItem(StatusDonationItemMenuCampaign.FOR_DONATED.getValue());
        donationItemDTO.setQuantity(15.0);
        donationItemDTO.setProductDTO(productDTO);

        var result = donationItemMapper.dtoToEntity(donationItemDTO, product);

        assertNotNull(result);
        assertEquals(result.getQuantity(), donationItemDTO.getQuantity());
        assertEquals(result.getProduct().getName(), donationItemDTO.getProductDTO().getName());
        assertEquals(result.getProduct().getProductCategory().getValor(), donationItemDTO.getProductDTO().getCategoryProduct());
        assertEquals(result.getProduct().getUnitOfMeasure().getValue(), donationItemDTO.getProductDTO().getUnitOfMeasure());
    }

    @Test
    void entityToDto() {
        final var product = new Product();
        product.setName("Arroz");
        product.setProductCategory(ProductCategory.FOOD);
        product.setUnitOfMeasure(UnitOfMeasure.KG);

        final var productDTO = new ProductDTO();
        productDTO.setName("Arroz");
        productDTO.setCategoryProduct(ProductCategory.FOOD.getValor());
        productDTO.setUnitOfMeasure(UnitOfMeasure.KG.getValue());

        final var item = new DonationItem();
        item.setId(1);
        item.setProduct(product);
        item.setQuantity(5.0);
        item.setStatusItem(StatusDonationItemMenuCampaign.PARTIALLY_DONATED);

        when(productMapper.converterEntityToDto(product)).thenReturn(productDTO);

        List<DonationItemDTO> result = donationItemMapper.entityToDto(List.of(item));

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(productDTO.getName(), result.getFirst().getProductDTO().getName());
        assertEquals(productDTO.getName(), result.getFirst().getProductDTO().getName());
        assertEquals("Kilogramas", result.getFirst().getProductDTO().getUnitOfMeasure());
        assertEquals("Alimento", result.getFirst().getProductDTO().getCategoryProduct());
        assertEquals(result.getFirst().getStatusItem(), StatusDonationItemMenuCampaign.PARTIALLY_DONATED.getValue());
        assertEquals(result.getFirst().getQuantity(), item.getQuantity());
        assertNotNull(result.getFirst().getId());
    }

    @Test
    void testConvertDtoListToEntity_shouldReturnDonationItemList() {
        final var productDTO = new ProductDTO();
        final var donationItemDTO = new DonationItemDTO();
        final var menuCampaign = new MenuCampaign();
        final var mockProduct = new Product();

        mockProduct.setId(1);
        mockProduct.setName("Arroz");

        productDTO.setId(1);
        donationItemDTO.setProductDTO(productDTO);
        donationItemDTO.setQuantity(10.0);
        donationItemDTO.setStatusItem("Para doar");
        List<DonationItemDTO> dtoList = List.of(donationItemDTO);
        menuCampaign.setId(100);

        when(productService.findById(1)).thenReturn(mockProduct);

        List<DonationItem> result = donationItemMapper.convertDtoListToEntity(dtoList, menuCampaign);

        assertEquals(1, result.size());
        DonationItem item = result.getFirst();

        assertEquals(10.0, item.getQuantity());
        assertEquals(menuCampaign, item.getMenuCampaign());
        assertEquals(mockProduct, item.getProduct());
    }
}