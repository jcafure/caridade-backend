package dev.caridadems.mapper;

import dev.caridadems.domain.ProductCategory;
import dev.caridadems.domain.UnitOfMeasure;
import dev.caridadems.dto.ProductDTO;
import dev.caridadems.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

class ProductMapperTest {

    @InjectMocks
    private ProductMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testConvertDtoToEntity() {
        final var dto = new ProductDTO();
        dto.setName("Arroz");
        dto.setCategoryProduct(ProductCategory.FOOD.name());
        dto.setUnitOfMeasure(UnitOfMeasure.KG.name());

        final var product = mapper.converterDtoToEntity(dto);

        Assertions.assertEquals(product.getName(), dto.getName());
        Assertions.assertEquals(product.getUnitOfMeasure().name(), dto.getUnitOfMeasure());
        Assertions.assertEquals(product.getProductCategory().name(), dto.getCategoryProduct());
    }

    @Test
    void converterEntityToDto() {
        final var productEntity = new Product();
        productEntity.setId(1);
        productEntity.setName("Arroz");
        productEntity.setUnitOfMeasure(UnitOfMeasure.KG);
        productEntity.setProductCategory(ProductCategory.FOOD);

        final var productDtoResponse = mapper.converterEntityToDto(productEntity);

        Assertions.assertEquals(productDtoResponse.getName(), productEntity.getName());
        Assertions.assertEquals(productDtoResponse.getUnitOfMeasure(), productEntity.getUnitOfMeasure().getValue());
        Assertions.assertEquals(productDtoResponse.getCategoryProduct(), productEntity.getProductCategory().getValor());
    }
}