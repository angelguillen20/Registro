package com.Registro.Registro.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.Registro.Registro.Model.ExamenDiagnostico;
import com.Registro.Registro.Model.RegistroMedico;
import com.Registro.Registro.Repository.RegistroRepository;

@ExtendWith(MockitoExtension.class)
public class RegistroServiceTest {

    @Mock
    private RegistroRepository registroRepository;

    @InjectMocks
    private RegistroService registroService;

    private RegistroMedico registro;
    private ExamenDiagnostico examen;

    @BeforeEach
    void setUp() {
        registro = new RegistroMedico();
        registro.setId(1);
        registro.setFechaExamen(LocalDate.of(2023, 10, 1));
        registro.setDiagnostico("Hipertensión");
        registro.setTratamiento("Dieta y ejercicio");
        registro.setObservaciones("Paciente estable");
        registro.setTipoRegistro("Consulta");

        examen = new ExamenDiagnostico();
        examen.setId(1);
        examen.setNombreExamen("Hemograma");
        examen.setDescripcionExamen("Análisis de sangre completo");
        examen.setPreparacionRequerida("Ayuno de 8 horas");
    }

    @Test
    public void creaRegistroMedico_DeberiaRetornarRegistroGuardado() {
        when(registroRepository.save(any(RegistroMedico.class))).thenReturn(registro);

        RegistroMedico resultado = registroService.creaRegistroMedico(registro);

        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        verify(registroRepository).save(registro);
    }

    @Test
    public void obtenerTodos_DeberiaRetornarListaRegistros() {
        List<RegistroMedico> registros = Arrays.asList(registro, new RegistroMedico());
        when(registroRepository.findAll()).thenReturn(registros);

        List<RegistroMedico> resultado = registroService.obtenerTodos();

        assertEquals(2, resultado.size());
        verify(registroRepository).findAll();
    }

    @Test
    public void obtenerPorId_Existente_DeberiaRetornarRegistro() {
        when(registroRepository.findById(1)).thenReturn(Optional.of(registro));

        Optional<RegistroMedico> resultado = registroService.obtenerPorId(1);

        assertTrue(resultado.isPresent());
        assertEquals("Hipertensión", resultado.get().getDiagnostico());
    }

    @Test
    public void obtenerPorId_NoExistente_DeberiaRetornarVacio() {
        when(registroRepository.findById(1)).thenReturn(Optional.empty());

        Optional<RegistroMedico> resultado = registroService.obtenerPorId(1);

        assertTrue(resultado.isEmpty());
    }

    @Test
    public void crearRegistro_ConExamenes_DeberiaEstablecerRelaciones() {
        registro.setExamenes(Arrays.asList(examen));
        when(registroRepository.save(any(RegistroMedico.class))).thenReturn(registro);

        RegistroMedico resultado = registroService.crearRegistro(registro);

        assertNotNull(resultado);
        assertEquals(1, resultado.getExamenes().size());
        assertEquals(registro, resultado.getExamenes().get(0).getRegistroMedico());
    }

    @Test
    public void crearRegistro_SinExamenes_DeberiaGuardarCorrectamente() {
        registro.setExamenes(null);
        when(registroRepository.save(any(RegistroMedico.class))).thenReturn(registro);

        RegistroMedico resultado = registroService.crearRegistro(registro);

        assertNotNull(resultado);
        assertNull(resultado.getExamenes());
    }

    @Test
    public void actualizarRegistro_Existente_DeberiaActualizarCampos() {
        RegistroMedico actualizado = new RegistroMedico();
        actualizado.setDiagnostico("Hipertensión grado II");
        actualizado.setExamenes(Arrays.asList(examen));

        when(registroRepository.findById(1)).thenReturn(Optional.of(registro));
        when(registroRepository.save(any(RegistroMedico.class))).thenReturn(actualizado);

        RegistroMedico resultado = registroService.actualizarRegistro(1, actualizado);

        assertEquals("Hipertensión grado II", resultado.getDiagnostico());
        assertEquals(1, resultado.getExamenes().size());
    }

    @Test
    public void actualizarRegistro_NoExistente_DeberiaLanzarExcepcion() {
        when(registroRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            registroService.actualizarRegistro(1, registro);
        });
    }

    @Test
    public void eliminarRegistro_DeberiaInvocarDelete() {
        doNothing().when(registroRepository).deleteById(1);

        registroService.eliminarRegistro(1);

        verify(registroRepository).deleteById(1);
    }

    @Test
    public void actualizarRegistro_ConNuevosExamenes_DeberiaActualizarRelaciones() {
        RegistroMedico actualizado = new RegistroMedico();
        actualizado.setExamenes(Arrays.asList(
            new ExamenDiagnostico(),
            new ExamenDiagnostico()
        ));

        when(registroRepository.findById(1)).thenReturn(Optional.of(registro));
        when(registroRepository.save(any(RegistroMedico.class))).thenReturn(actualizado);

        RegistroMedico resultado = registroService.actualizarRegistro(1, actualizado);

        assertEquals(2, resultado.getExamenes().size());
        resultado.getExamenes().forEach(ex -> assertEquals(registro, ex.getRegistroMedico()));
    }
}