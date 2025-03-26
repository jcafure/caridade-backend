package dev.caridadems.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UnitOfMeasureTest {

    @Test
    void shouldReturnEnumWhenValueIsValid() {
        assertEquals(UnitOfMeasure.KG, UnitOfMeasure.toEnum("Kilogramas"));
        assertEquals(UnitOfMeasure.LITROS, UnitOfMeasure.toEnum("Litros"));
        assertEquals(UnitOfMeasure.UN, UnitOfMeasure.toEnum("Unidade"));
        assertEquals(UnitOfMeasure.GR, UnitOfMeasure.toEnum("Gramas"));
    }

    @Test
    void shouldReturnNullWhenValueIsInvalid() {
        assertNull(UnitOfMeasure.toEnum("Invalid"));
    }

    @Test
    void shouldReturnNullWhenValueIsNull() {
        assertNull(UnitOfMeasure.toEnum(null));
    }
}