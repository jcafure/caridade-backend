package dev.caridadems.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.caridadems.dto.ProductDTO;
import dev.caridadems.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateProductSuccessfully() throws Exception {
        final var requestDto = new ProductDTO();
        requestDto.setName("Arroz");
        requestDto.setCategoryProduct("Alimento");
        requestDto.setUnitOfMeasure("Kilogramas");

        final var responseDto = new ProductDTO();
        responseDto.setId(1);
        responseDto.setName("Arroz");
        responseDto.setCategoryProduct("Alimento");
        responseDto.setUnitOfMeasure("Kilogramas");

        Mockito.when(productService.saveProduct(Mockito.any(ProductDTO.class)))
                .thenReturn(responseDto);

        mockMvc.perform(post("/products/new-product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Arroz"))
                .andExpect(jsonPath("$.categoryProduct").value("Alimento"))
                .andExpect(jsonPath("$.unitOfMeasure").value("Kilogramas"));
    }
}