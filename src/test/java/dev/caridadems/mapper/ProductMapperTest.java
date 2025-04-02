package dev.caridadems.mapper;

import dev.caridadems.domain.ProductCategory;
import dev.caridadems.domain.UnitOfMeasure;
import dev.caridadems.dto.ProductDTO;
import dev.caridadems.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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

        assertEquals(product.getName(), dto.getName());
        assertEquals(product.getUnitOfMeasure().name(), dto.getUnitOfMeasure());
        assertEquals(product.getProductCategory().name(), dto.getCategoryProduct());
    }

    @Test
    void converterEntityToDto() {
        final var productEntity = new Product();
        productEntity.setId(1);
        productEntity.setName("Arroz");
        productEntity.setUnitOfMeasure(UnitOfMeasure.KG);
        productEntity.setProductCategory(ProductCategory.FOOD);

        final var productDtoResponse = mapper.converterEntityToDto(productEntity);

        assertEquals(productDtoResponse.getName(), productEntity.getName());
        assertEquals(productDtoResponse.getUnitOfMeasure(), productEntity.getUnitOfMeasure().getValue());
        assertEquals(productDtoResponse.getCategoryProduct(), productEntity.getProductCategory().getValor());
    }

    @Test
    void testConverterDtoToEntityUpdate() {
        final var dto = new ProductDTO();
        dto.setId(1);
        dto.setName("Arroz");
        dto.setUnitOfMeasure("Kilogramas");
        dto.setCategoryProduct("Alimento");

        Product entity = mapper.converterDtoToEntityUpdate(dto);
        assertNotNull(entity);
        assertEquals(1, entity.getId());
        assertEquals("Arroz", entity.getName());
        assertEquals(UnitOfMeasure.KG, entity.getUnitOfMeasure());
        assertEquals(ProductCategory.FOOD, entity.getProductCategory());
    }
    @Test
    void testConverterDtoToEntityUpdateNoId() {
        final var dto = new ProductDTO();
        dto.setName("Arroz");
        dto.setUnitOfMeasure("Kilogramas");
        dto.setCategoryProduct("Alimento");

        Product entity = mapper.converterDtoToEntityUpdate(dto);
        assertNotNull(entity);
        assertEquals("Arroz", entity.getName());
        assertEquals(UnitOfMeasure.KG, entity.getUnitOfMeasure());
        assertEquals(ProductCategory.FOOD, entity.getProductCategory());
    }

    @Test
    void testConverterDtoToEntity() {
        final var dto = new ProductDTO();
        dto.setId(1);
        dto.setName("Arroz");
        dto.setUnitOfMeasure("kg");
        dto.setCategoryProduct("food");

        Product entity = mapper.converterDtoToEntity(dto);
        assertNotNull(entity);
        assertEquals(1, entity.getId());
        assertEquals("Arroz", entity.getName());
        assertEquals(UnitOfMeasure.KG, entity.getUnitOfMeasure());
        assertEquals(ProductCategory.FOOD, entity.getProductCategory());
    }
}