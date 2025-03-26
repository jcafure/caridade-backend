package dev.caridadems.mapper;

import dev.caridadems.domain.ProductCategory;
import dev.caridadems.domain.UnitOfMeasure;
import dev.caridadems.dto.ProductDTO;
import dev.caridadems.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product converterDTO(ProductDTO productDTO) {
        final var product = new Product();
        product.setName(productDTO.getName());
        product.setUnitOfMeasure(UnitOfMeasure.toEnum(productDTO.getUnitOfMeasure()));
        product.setProductCategory(ProductCategory.toEnum((productDTO.getCategoryProduct())));
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
