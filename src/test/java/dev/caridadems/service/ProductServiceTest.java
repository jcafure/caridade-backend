package dev.caridadems.service;

import dev.caridadems.domain.ProductCategory;
import dev.caridadems.domain.UnitOfMeasure;
import dev.caridadems.dto.ProductDTO;
import dev.caridadems.mapper.ProductMapper;
import dev.caridadems.model.Product;
import dev.caridadems.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductMapper mapper;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveProduct() {
        final var dto = new ProductDTO();
        dto.setId(1);
        dto.setName("Arroz");
        dto.setCategoryProduct("Alimento");
        dto.setUnitOfMeasure("Kilogramas");

        final var productEntity = new Product();
        productEntity.setId(1);
        productEntity.setName("Arroz");
        productEntity.setUnitOfMeasure(UnitOfMeasure.KG);
        productEntity.setProductCategory(ProductCategory.FOOD);

        Mockito.when(mapper.converterDtoToEntity(dto)).thenReturn(productEntity);
        Mockito.when(productRepository.save(productEntity)).thenReturn(productEntity);
        Mockito.when(mapper.converterEntityToDto(productEntity)).thenReturn(dto);

        final var dtoResponse = productService.saveProduct(dto);
        Assertions.assertNotNull(dtoResponse);
        Assertions.assertEquals(dtoResponse.getName(), productEntity.getName());
        Assertions.assertEquals(dtoResponse.getId(), productEntity.getId());
        Assertions.assertEquals(dtoResponse.getCategoryProduct(), productEntity.getProductCategory().getValor());
        Assertions.assertEquals(dtoResponse.getUnitOfMeasure(), productEntity.getUnitOfMeasure().getValue());

    }
}