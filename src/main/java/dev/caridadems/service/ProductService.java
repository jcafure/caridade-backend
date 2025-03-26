package dev.caridadems.service;

import dev.caridadems.dto.ProductDTO;
import dev.caridadems.mapper.ProductMapper;
import dev.caridadems.model.Product;
import dev.caridadems.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private final ProductRepository productRepository;
    @Autowired
    private final ProductMapper mapper;

    public ProductService(ProductRepository productRepository, ProductMapper mapper) {
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    @Transactional
    public ProductDTO saveProduct(ProductDTO productDTO) {
        Product product = mapper.converterDTO(productDTO);
        Product productsave =  productRepository.save(product);
        return mapper.converterEntityToDto(productsave);
    }
}
