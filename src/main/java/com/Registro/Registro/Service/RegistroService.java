package com.Registro.Registro.Service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Registro.Registro.Model.RegistroMedico;
import com.Registro.Registro.Repository.RegistroRepository;

@Service
public class RegistroService {
    @Autowired
    private RegistroRepository registroRepository;

    @Transactional
    public RegistroMedico creaRegistroMedico(RegistroMedico registro) {
        return registroRepository.save(registro);
    }
    

    public List<RegistroMedico> obtenerTodos() {
        return registroRepository.findAll();
    }

    public Optional<RegistroMedico> obtenerPorId(int id) {
        return registroRepository.findById(id);
    }

    @Transactional
    public RegistroMedico crearRegistro(RegistroMedico registro) {
        // Aseguramos que los examenes referencien correctamente el registro
        if (registro.getExamenes() != null) {
            registro.getExamenes().forEach(ex -> ex.setRegistroMedico(registro));
        }
        return registroRepository.save(registro);
    }

    @Transactional
    public RegistroMedico actualizarRegistro(int id, RegistroMedico nuevoRegistro) {
        return registroRepository.findById(id).map(registroExistente -> {
            registroExistente.setDate(nuevoRegistro.getDate());
            registroExistente.setDiagnostico(nuevoRegistro.getDiagnostico());
            registroExistente.setTratamiento(nuevoRegistro.getTratamiento());
            registroExistente.setObservaciones(nuevoRegistro.getObservaciones());
            registroExistente.setTipoRegistro(nuevoRegistro.getTipoRegistro());

            // Manejo de examenes
            registroExistente.getExamenes().clear();
            if (nuevoRegistro.getExamenes() != null) {
                nuevoRegistro.getExamenes().forEach(ex -> {
                    ex.setRegistroMedico(registroExistente);
                    registroExistente.getExamenes().add(ex);
                });
            }

            return registroRepository.save(registroExistente);
        }).orElseThrow(() -> new RuntimeException("Registro médico no encontrado con id " + id));
    }

    @Transactional
    public void eliminarRegistro(int id) {
        registroRepository.deleteById(id);
    }
}
