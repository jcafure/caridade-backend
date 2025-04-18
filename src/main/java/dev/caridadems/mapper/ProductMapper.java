package dev.caridadems.mapper;

import dev.caridadems.domain.ProductCategory;
import dev.caridadems.domain.UnitOfMeasure;
import dev.caridadems.dto.ProductDTO;
import dev.caridadems.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product converterDtoToEntity(ProductDTO productDTO) {
        final var product = new Product();
        if (productDTO.getId() != null) {
            product.setId(productDTO.getId());
        }
        product.setName(productDTO.getName());
        product.setUnitOfMeasure(UnitOfMeasure.valueOf(productDTO.getUnitOfMeasure().trim().toUpperCase()));
        product.setProductCategory(ProductCategory.valueOf(productDTO.getCategoryProduct().trim().toUpperCase()));
        return product;
    }

    public Product converterDtoToEntityUpdate(ProductDTO productDTO) {
        final var product = new Product();
        if (productDTO.getId() != null) {
            product.setId(productDTO.getId());
        }
        product.setName(productDTO.getName());
        product.setUnitOfMeasure(UnitOfMeasure.toEnum(productDTO.getUnitOfMeasure()));
        product.setProductCategory(ProductCategory.toEnum(productDTO.getCategoryProduct()));
        return product;
    }

    public ProductDTO converterEntityToDto(Product product) {
        final var productDto = new ProductDTO();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setUnitOfMeasure(product.getUnitOfMeasure().getValue());
        productDto.setCategoryProduct(product.getProductCategory().getValor());
        return productDto;
    }
}
