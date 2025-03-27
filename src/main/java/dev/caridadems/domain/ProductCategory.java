package dev.caridadems.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductCategory {
    FOOD(0, "Alimento"),
    DRINK(1, "Bebida/Suco/Refrigerante"),
    CLEANING_MATERIAL(2, "Material Limpeza"),
    DISPOSABLE_PRODUCTS(4,"Produtos Descartáveis");

    private final Integer id;
    private final String valor;

    public static ProductCategory toEnum (String value) {
        if(value == null ){
            return null;
        }
        for (ProductCategory productCategory : ProductCategory.values()) {
            if (value.equals(productCategory.getValor())){
                return productCategory;
            }
        }
        return null;
    }
}
