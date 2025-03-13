package dev.caridadems.repository;

import dev.caridadems.domain.ProductCategory;
import dev.caridadems.domain.UnitOfMeasure;
import dev.caridadems.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
 class ProductRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ProductRepository productRepository;

    @Test
     void saveProduct() {
        final var product = new Product();
        product.setProductCategory(ProductCategory.FOOD);
        product.setName("Arroz");
        product.setUnitOfMeasure(UnitOfMeasure.KG);

        final var productSave = testEntityManager.persist(product);

        Assertions.assertNotNull(productSave);
        Assertions.assertEquals(productSave.getProductCategory(), product.getProductCategory());
        Assertions.assertEquals(productSave.getName(), product.getName());
        Assertions.assertEquals(productSave.getUnitOfMeasure(), product.getUnitOfMeasure());
    }
}
