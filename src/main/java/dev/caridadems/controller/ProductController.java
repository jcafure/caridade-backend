package dev.caridadems.controller;

import dev.caridadems.dto.ProductDTO;
import dev.caridadems.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public ResponseEntity<Page<ProductDTO>> getAllProducts(Pageable pageable) {
        Page<ProductDTO> products = productService.findAll(pageable);
        return ResponseEntity.ok(products);
    }

    @PostMapping(value = "/new-product")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.saveProduct(productDTO));
    }

    @PutMapping(value = "/update-product")
    public ResponseEntity<ProductDTO> updateProduct(@RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.editProduct(productDTO));
    }

    @DeleteMapping(value = "/delete-product/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
