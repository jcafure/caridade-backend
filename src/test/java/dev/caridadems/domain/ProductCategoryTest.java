package dev.caridadems.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductCategoryTest {

    @Test
    void shouldReturnNullWhenValueIsNull() {
        assertNull(ProductCategory.toEnum(null));
    }

    @Test
    void toEnum() {
        assertEquals(ProductCategory.FOOD, ProductCategory.toEnum("Alimento"));
        assertEquals(ProductCategory.DRINK, ProductCategory.toEnum("Bebida/Suco/Refrigerante"));
        assertEquals(ProductCategory.CLEANING_MATERIAL, ProductCategory.toEnum("Material Limpeza"));
        assertEquals(ProductCategory.DISPOSABLE_PRODUCTS, ProductCategory.toEnum("Produtos Descartáveis"));
    }

    @Test
    void shouldReturnNullWhenValueIsInvalid() {
        assertNull(ProductCategory.toEnum("Não existe"));
    }
}