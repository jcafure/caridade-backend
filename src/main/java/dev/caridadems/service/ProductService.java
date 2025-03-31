package dev.caridadems.service;

import dev.caridadems.dto.ProductDTO;
import dev.caridadems.mapper.ProductMapper;
import dev.caridadems.model.Product;
import dev.caridadems.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        boolean exists = productRepository.existsByNameIgnoreCase(productDTO.getName());

        if (exists) {
            throw new RuntimeException("Já existe um produto com esse nome.");
        }
        return mapper.converterEntityToDto(productRepository.save(mapper.converterDtoToEntity(productDTO)));
    }

    @Transactional
    public ProductDTO editProduct(ProductDTO productDTO) {
        if (!productRepository.existsById(productDTO.getId())) {
            throw new EntityNotFoundException("Produto com id: " + productDTO.getId() + " não encontrado");
        }
       return mapper.converterEntityToDto(productRepository.save(mapper.converterDtoToEntity(productDTO))) ;
    }

    @Transactional
    public void deleteProduct(Integer id) {
        final var product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Produto com ID " + id + " não encontrado."));
        productRepository.delete(product);
    }

    public Page<ProductDTO> findAll(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(mapper::converterEntityToDto);
    }
}
