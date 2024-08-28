package com.Simples.Dental.cadastro_profissionais_contatos.controller;

import com.Simples.Dental.cadastro_profissionais_contatos.model.Profissional;
import com.Simples.Dental.cadastro_profissionais_contatos.service.iservice.IProfissionalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProfissionalController.class)
class ProfissionalControllerTest {

    @MockBean
    private IProfissionalService profissionalService;

    @InjectMocks
    private ProfissionalController profissionalController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(profissionalController).build();
    }

    @Test
    void testBuscarPorId() throws Exception {
        Profissional profissional = new Profissional();
        profissional.setId(1L);
        profissional.setNome("Maria Oliveira");

        when(profissionalService.buscarPorId(1L)).thenReturn(Optional.of(profissional));

        mockMvc.perform(get("/profissionais/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Maria Oliveira"));

        verify(profissionalService, times(1)).buscarPorId(1L);
    }

    @Test
    void testCriarProfissional() throws Exception {
        Profissional profissional = new Profissional();
        profissional.setId(1L);
        profissional.setNome("Maria Oliveira");

        when(profissionalService.criar(any(Profissional.class))).thenReturn(profissional);

        mockMvc.perform(post("/profissionais")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"Maria Oliveira\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Sucesso: profissional com id 1 cadastrado"));

        verify(profissionalService, times(1)).criar(any(Profissional.class));
    }

    @Test
    void testAtualizarProfissional() throws Exception {
        mockMvc.perform(put("/profissionais/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"Maria Oliveira\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Sucesso: profissional alterado"));

        verify(profissionalService, times(1)).atualizar(eq(1L), any(Profissional.class));
    }

    @Test
    void testDeletarProfissional() throws Exception {
        mockMvc.perform(delete("/profissionais/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Sucesso: profissional excluído"));

        verify(profissionalService, times(1)).deletar(1L);
    }
}
