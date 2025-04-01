package dev.caridadems.service;

import dev.caridadems.dto.ProductDTO;
import dev.caridadems.exception.ObjectAlreadyExistsException;
import dev.caridadems.exception.ObjectDeleteException;
import dev.caridadems.exception.ObjectNotFoundException;
import dev.caridadems.mapper.ProductMapper;
import dev.caridadems.model.Product;
import dev.caridadems.repository.ProductRepository;
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
            throw new ObjectAlreadyExistsException("Já existe um produto com esse nome.");
        }
        return mapper.converterEntityToDto(productRepository.save(mapper.converterDtoToEntity(productDTO)));
    }

        @Transactional
        public ProductDTO editProduct(ProductDTO productDTO) {
            if (!productRepository.existsById(productDTO.getId())) {
                throw new ObjectNotFoundException("Produto com id: " + productDTO.getId() + " não encontrado");
            }
           return mapper.converterEntityToDto(productRepository.save(mapper.converterDtoToEntityUpdate(productDTO))) ;
        }

    @Transactional
    public void deleteProduct(Integer id) {
        final var product = productRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Produto com ID " + id + " não encontrado."));
        try {
            productRepository.delete(product);
        }catch (Exception e) {
                throw new ObjectDeleteException("Não foi possível remover o produto.");
        }
    }

    public Page<ProductDTO> findAll(Pageable pageable, String name) {
        Page<Product> products;
        if (name != null && !name.isBlank()){
            products = productRepository.findByNameContainingIgnoreCase(name, pageable);
        }else {
            products = productRepository.findAll(pageable);
        }
        return products.map(mapper::converterEntityToDto);
    }
}
