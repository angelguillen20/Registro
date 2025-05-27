package com.Registro.Registro.Service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Registro.Registro.Model.ExamenDiagnostico;
import com.Registro.Registro.Model.RegistroMedico;
import com.Registro.Registro.Repository.RegistroRepository;

@Service
public class RegistroService {


    @Autowired
    private RegistroRepository registroRepository;

        public List<RegistroMedico> obtenerRegistros() {
        return registroRepository.obtenerRegistros();
    }

    public RegistroMedico buscarPorId(int id) {
        return registroRepository.buscarPorId(id);
    }

    public RegistroMedico agregarRegistro(RegistroMedico registro) {
        return registroRepository.agregarRegistro(registro);
    }

    public void actualizarRegistro(RegistroMedico registro) {
        registroRepository.actualizarRegistro(registro);
    }

    public void eliminarRegistro(int id) {
        registroRepository.eliminarRegistro(id);
    }

    
    public boolean agregarExamen(int registroId, ExamenDiagnostico examen) {
        RegistroMedico registro = registroRepository.buscarPorId(registroId);
        if (registro != null) {
            if (registro.getExamenes() == null) {
                registro.setExamenes(new java.util.ArrayList<>());
            }
            registro.getExamenes().add(examen);
            return true;
        }
        return false;
    }

    public List<ExamenDiagnostico> obtenerExamenes(int registroId) {
        RegistroMedico registro = registroRepository.buscarPorId(registroId);
        if (registro != null && registro.getExamenes() != null) {
            return registro.getExamenes();
        }
        return new java.util.ArrayList<>();
    }

    public boolean eliminarExamen(int registroId, int examenId) {
        RegistroMedico registro = registroRepository.buscarPorId(registroId);
        if (registro != null && registro.getExamenes() != null) {
            List<ExamenDiagnostico> examenes = registro.getExamenes();
            for (int i = 0; i < examenes.size(); i++) {
                if (examenes.get(i).getId() == examenId) {
                    examenes.remove(i);
                return true;
                }
            }
        }
        return false; 
    }
}

