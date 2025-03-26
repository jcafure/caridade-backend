package dev.caridadems.service;

import dev.caridadems.dto.ProductDTO;
import dev.caridadems.mapper.ProductMapper;
import dev.caridadems.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper mapper;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductMapper mapper) {
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    @Transactional
    public ProductDTO saveProduct(ProductDTO productDTO) {
        return mapper.converterEntityToDto(productRepository.save(mapper.converterDtoToEntity(productDTO)));
    }
}
