package com.codeuai.idvalidator.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.codeuai.idvalidator.model.ValidationError;
import com.codeuai.idvalidator.service.CNPJService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;


class CNPJControllerTest {

    private MockMvc mvc;

    @Mock
    private CNPJService service;

    private CNPJController controller;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        controller = new CNPJController(service);
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("POST /cnpj retorna válido quando o serviço diz verdadeiro")
    void postReturnsValid() throws ValidationError, Exception {
        String sample = "12345678000195";
        when(service.isValid(sample)).thenReturn(true);

        mvc.perform(post("/cnpj")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"document\":\"" + sample + "\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.valid").value(true));
    }

    @Test
    @DisplayName("POST /cnpj retorna inválido quando o serviço diz falso")
    void postReturnsInvalid() throws ValidationError, Exception {
        String sample = "00000000000000";
        when(service.isValid(sample)).thenReturn(false);

        mvc.perform(post("/cnpj")
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"document\":\"" + sample + "\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.valid").value(false));
    }
}