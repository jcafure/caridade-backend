package dev.caridadems.service;

import dev.caridadems.domain.ProductCategory;
import dev.caridadems.domain.UnitOfMeasure;
import dev.caridadems.dto.ProductDTO;
import dev.caridadems.exception.ObjectAlreadyExistsException;
import dev.caridadems.exception.ObjectNotFoundException;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

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

        Mockito.when(productRepository.existsByNameIgnoreCase("Arroz")).thenReturn(false);
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

    @Test
    void testSaveProductAlreadyExistsThrowsException() {
        final var dto = new ProductDTO();
        dto.setName("Arroz");

        Mockito.when(productRepository.existsByNameIgnoreCase("Arroz")).thenReturn(true);

        Assertions.assertThrows(ObjectAlreadyExistsException.class, () -> {
            productService.saveProduct(dto);
        });
    }
    @Test
    void testEditProductSuccessfully() {
        final var dto = new ProductDTO();
        dto.setId(1);
        dto.setName("Arroz Atualizado");
        dto.setCategoryProduct("FOOD");
        dto.setUnitOfMeasure("KG");

        final var productEntity = new Product();
        productEntity.setId(1);
        productEntity.setName("Arroz Atualizado");
        productEntity.setProductCategory(ProductCategory.FOOD);
        productEntity.setUnitOfMeasure(UnitOfMeasure.KG);

        Mockito.when(productRepository.existsById(1)).thenReturn(true);
        Mockito.when(mapper.converterDtoToEntityUpdate(dto)).thenReturn(productEntity);
        Mockito.when(productRepository.save(productEntity)).thenReturn(productEntity);
        Mockito.when(mapper.converterEntityToDto(productEntity)).thenReturn(dto);

        ProductDTO result = productService.editProduct(dto);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(dto.getId(), result.getId());
        Assertions.assertEquals(dto.getName(), result.getName());
        Assertions.assertEquals(dto.getCategoryProduct(), result.getCategoryProduct());
        Assertions.assertEquals(dto.getUnitOfMeasure(), result.getUnitOfMeasure());
    }

    @Test
    void testEditProductThrowsObjectNotFoundException() {
        final var dto = new ProductDTO();
        dto.setId(99);
        dto.setName("Produto Inexistente");

        Mockito.when(productRepository.existsById(99)).thenReturn(false);

        ObjectNotFoundException thrown = Assertions.assertThrows(
                ObjectNotFoundException.class,
                () -> productService.editProduct(dto)
        );

        Assertions.assertEquals("Produto com id: 99 não encontrado", thrown.getMessage());
    }

    @Test
    void testDeleteProductSuccessfully() {
        final var productId = 1;
        final var product = new Product();
        product.setId(productId);
        product.setName("Produto Teste");

        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Assertions.assertDoesNotThrow(() -> productService.deleteProduct(productId));
        Mockito.verify(productRepository).delete(product);
    }

    @Test
    void testDeleteProductThrowsObjectNotFoundException() {
        final var productId = 99;
        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.empty());

        ObjectNotFoundException thrown = Assertions.assertThrows(
                ObjectNotFoundException.class,
                () -> productService.deleteProduct(productId)
        );

        Assertions.assertEquals("Produto com ID 99 não encontrado.", thrown.getMessage());
    }

    @Test
    void testFindAllReturnsPageOfProductDTO() {
        final var pageable = PageRequest.of(0, 10);
        final var product = new Product();
        final var dto = new ProductDTO();

        product.setId(1);
        product.setName("Arroz");
        product.setUnitOfMeasure(UnitOfMeasure.KG);
        product.setProductCategory(ProductCategory.FOOD);

        dto.setId(1);
        dto.setName("Arroz");
        dto.setUnitOfMeasure("KG");
        dto.setCategoryProduct("FOOD");

        Page<Product> productPage = new PageImpl<>(List.of(product));

        Mockito.when(productRepository.findAll(pageable)).thenReturn(productPage);
        Mockito.when(mapper.converterEntityToDto(product)).thenReturn(dto);

        Page<ProductDTO> result = productService.findAll(pageable, null);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.getTotalElements());
        Assertions.assertEquals("Arroz", result.getContent().getFirst().getName());
    }

    @Test
    void testFindAllWithNameFilter() {
        final var pageable = PageRequest.of(0, 10);
        final var nameFilter = "arroz";
        final var product = new Product();
        final var dto = new ProductDTO();
        product.setName("Arroz");
        dto.setName("Arroz");
        Page<Product> productPage = new PageImpl<>(List.of(product));

        Mockito.when(productRepository.findByNameContainingIgnoreCase(nameFilter, pageable))
                .thenReturn(productPage);
        Mockito.when(mapper.converterEntityToDto(product)).thenReturn(dto);

        Page<ProductDTO> result = productService.findAll(pageable, nameFilter);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.getTotalElements());
        Assertions.assertEquals("Arroz", result.getContent().getFirst().getName());
    }

    @Test
    void testFindAllWithoutNameFilter() {
        final var pageable = PageRequest.of(0, 10);
        final var nameFilter = "";
        final var dto = new ProductDTO();
        final var product = new Product();

        product.setName("Feijão");
        dto.setName("Feijão");
        Page<Product> productPage = new PageImpl<>(List.of(product));

        Mockito.when(productRepository.findAll(pageable)).thenReturn(productPage);
        Mockito.when(mapper.converterEntityToDto(product)).thenReturn(dto);

        Page<ProductDTO> result = productService.findAll(pageable, nameFilter);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.getTotalElements());
        Assertions.assertEquals("Feijão", result.getContent().getFirst().getName());
    }
}