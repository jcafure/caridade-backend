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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @Test
    void testGetAllProductsReturnsPageOfProductDTO() throws Exception {
        final var dto = new ProductDTO();
        dto.setId(1);
        dto.setName("Arroz");
        dto.setUnitOfMeasure("KG");
        dto.setCategoryProduct("FOOD");

        Page<ProductDTO> page = new PageImpl<>(List.of(dto));

        // 👇 Ajuste aqui: agora é necessário passar o "name" também no mock
        Mockito.when(productService.findAll(Mockito.any(Pageable.class), Mockito.anyString()))
                .thenReturn(page);

        mockMvc.perform(get("/products/all")
                        .param("name", "") // nome em branco para simular listagem geral
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].name").value("Arroz"))
                .andExpect(jsonPath("$.content[0].unitOfMeasure").value("KG"))
                .andExpect(jsonPath("$.content[0].categoryProduct").value("FOOD"));
    }


    @Test
    void testUpdateProductSuccessfully() throws Exception {
        final var dto = new ProductDTO();
        dto.setId(1);
        dto.setName("Arroz Atualizado");
        dto.setCategoryProduct("FOOD");
        dto.setUnitOfMeasure("KG");

        Mockito.when(productService.editProduct(Mockito.any())).thenReturn(dto);

        mockMvc.perform(MockMvcRequestBuilders.put("/products/update-product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Arroz Atualizado"))
                .andExpect(jsonPath("$.categoryProduct").value("FOOD"))
                .andExpect(jsonPath("$.unitOfMeasure").value("KG"));
    }

    @Test
    void testDeleteProductSuccessfully() throws Exception {
        final var productId = 1;
        mockMvc.perform(delete("/products/delete-product/{id}", productId))
                .andExpect(status().isNoContent());

        verify(productService).deleteProduct(productId);
    }
}