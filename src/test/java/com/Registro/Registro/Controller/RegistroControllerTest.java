package com.Registro.Registro.Controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.Registro.Registro.Model.ExamenDiagnostico;
import com.Registro.Registro.Model.RegistroMedico;
import com.Registro.Registro.Service.RegistroService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(RegistroController.class)
public class RegistroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RegistroService registroService;

    @Autowired
    private ObjectMapper objectMapper;

    private RegistroMedico registro;
    private ExamenDiagnostico examen;

    @BeforeEach
    void setUp() {
        registro = new RegistroMedico();
        registro.setId(1);
        registro.setFechaExamen(LocalDate.of(2023, 1, 15));
        registro.setDiagnostico("Hipertensión");
        registro.setTratamiento("Dieta y ejercicio");
        registro.setObservaciones("Paciente estable");
        registro.setTipoRegistro("Consulta");

        examen = new ExamenDiagnostico();
        examen.setId(1);
        examen.setNombreExamen("Hemograma");
        examen.setDescripcionExamen("Análisis de sangre completo");
        examen.setPreparacionRequerida("Ayuno de 8 horas");

        registro.setExamenes(List.of(examen));
    }

    @Test
    public void testGetAllRegistros() throws Exception {
        when(registroService.obtenerTodos()).thenReturn(List.of(registro));

        mockMvc.perform(get("/registro-medico"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].diagnostico").value("Hipertensión"));
    }

    @Test
    public void testGetRegistroById() throws Exception {
        when(registroService.obtenerPorId(1)).thenReturn(Optional.of(registro));

        mockMvc.perform(get("/registro-medico/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.diagnostico").value("Hipertensión"));
    }

    @Test
    public void testCreateRegistro() throws Exception {
        when(registroService.crearRegistro(any(RegistroMedico.class))).thenReturn(registro);

        mockMvc.perform(post("/registro-medico")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registro)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.tratamiento").value("Dieta y ejercicio"));
    }

    @Test
    public void testUpdateRegistro() throws Exception {
        when(registroService.actualizarRegistro(eq(1), any(RegistroMedico.class))).thenReturn(registro);

        mockMvc.perform(put("/registro-medico/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registro)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.observaciones").value("Paciente estable"));
    }

    @Test
    public void testDeleteRegistro() throws Exception {
        doNothing().when(registroService).eliminarRegistro(1);

        mockMvc.perform(delete("/registro-medico/1"))
                .andExpect(status().isNoContent());
        
        verify(registroService, times(1)).eliminarRegistro(1);
    }

    @Test
    public void testGetAllRegistrosEmpty() throws Exception {
        when(registroService.obtenerTodos()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/registro-medico"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetRegistroByIdNotFound() throws Exception {
        when(registroService.obtenerPorId(1)).thenReturn(Optional.empty());

        mockMvc.perform(get("/registro-medico/1"))
                .andExpect(status().isNotFound());
    }
}