package dev.caridadems.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TypeAddressTest {

    @Test
    void shouldReturnEnumWhenValidLabel() {
        assertEquals(TypeAddress.HOME, TypeAddress.toEnum("Residência"));
        assertEquals(TypeAddress.LOCAL_JOB, TypeAddress.toEnum("Local de trabalho"));
        assertEquals(TypeAddress.PREPARATION_LOCATION, TypeAddress.toEnum("Local de preparo"));
    }

    @Test
    void shouldIgnoreCaseWhenMatchingLabel() {
        assertEquals(TypeAddress.HOME, TypeAddress.toEnum("residência"));
        assertEquals(TypeAddress.LOCAL_JOB, TypeAddress.toEnum("LOCAL DE TRABALHO"));
    }

    @Test
    void shouldThrowExceptionWhenInvalidLabel() {
        String invalidLabel = "Endereço inválido";
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            TypeAddress.toEnum(invalidLabel);
        });

        assertEquals("Tipo de endereço inválido: " + invalidLabel, exception.getMessage());
    }
}