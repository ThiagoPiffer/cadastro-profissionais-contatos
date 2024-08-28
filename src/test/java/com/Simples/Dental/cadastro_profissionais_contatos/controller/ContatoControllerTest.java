package com.Simples.Dental.cadastro_profissionais_contatos.controller;

import com.Simples.Dental.cadastro_profissionais_contatos.model.Contato;
import com.Simples.Dental.cadastro_profissionais_contatos.service.iservice.IContatoService;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ContatoController.class)
class ContatoControllerTest {

    @MockBean
    private IContatoService contatoService;

    @InjectMocks
    private ContatoController contatoController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(contatoController).build();
    }

    @Test
    void testBuscarPorId() throws Exception {
        Contato contato = new Contato();
        contato.setId(1L);
        contato.setNome("João da Silva");
        contato.setContato("joao.silva@example.com");

        when(contatoService.buscarPorId(1L)).thenReturn(Optional.of(contato));

        mockMvc.perform(get("/contatos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("João da Silva"))
                .andExpect(jsonPath("$.contato").value("joao.silva@example.com"));

        verify(contatoService, times(1)).buscarPorId(1L);
    }

    @Test
    void testCriarContato() throws Exception {
        Contato contato = new Contato();
        contato.setId(1L);
        contato.setNome("João da Silva");
        contato.setContato("joao.silva@example.com");

        when(contatoService.criar(any(Contato.class))).thenReturn(contato);

        mockMvc.perform(post("/contatos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"João da Silva\",\"contato\":\"joao.silva@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Sucesso: contato com id 1 cadastrado"));

        verify(contatoService, times(1)).criar(any(Contato.class));
    }

    @Test
    void testAtualizarContato() throws Exception {
        mockMvc.perform(put("/contatos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nome\":\"João da Silva\",\"contato\":\"joao.silva@example.com\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Sucesso: contato alterado"));

        verify(contatoService, times(1)).atualizar(eq(1L), any(Contato.class));
    }

    @Test
    void testDeletarContato() throws Exception {
        mockMvc.perform(delete("/contatos/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Sucesso: contato excluído"));

        verify(contatoService, times(1)).deletar(1L);
    }
}
