package com.Registro.Registro.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.Registro.Registro.Model.ExamenDiagnostico;
import com.Registro.Registro.Model.RegistroMedico;

@Repository
public class RegistroRepository {

    public void RegistroMedicoRepository() {
        // Crear exámenes para el primer registro
        ExamenDiagnostico examen1 = new ExamenDiagnostico(1, "Hemograma", "Análisis de sangre completo", "Ayuno 8 horas", null);
        ExamenDiagnostico examen2 = new ExamenDiagnostico(2, "Rayos X", "Radiografía de tórax", "No requiere preparación", null);

        
        listaExamenDiagnosticos.add(examen1);
        listaExamenDiagnosticos.add(examen2);

        RegistroMedico registro1 = new RegistroMedico(
            1,
            LocalDate.of(2023, 5, 15),
            "Infección respiratoria",
            "Antibióticos por 7 días",
            "Paciente responde bien al tratamiento",
            "Consulta general",
            listaExamenDiagnosticos
        );

        // Asociar el registro médico a cada examen
        examen1.setRegistroMedico(registro1);
        examen2.setRegistroMedico(registro1);

        // Crear otro registro sin exámenes
        RegistroMedico registro2 = new RegistroMedico(
            2,
            LocalDate.of(2023, 6, 10),
            "Dolor lumbar",
            "Fisioterapia y analgésicos",
            "Requiere seguimiento",
            "Consulta especializada",
            new ArrayList<>()
        );

        listaRegistro.add(registro1);
        listaRegistro.add(registro2);
    }

    private List<ExamenDiagnostico> listaExamenDiagnosticos = new ArrayList<>();
    private List<RegistroMedico> listaRegistro = new ArrayList<>();




    public List<RegistroMedico> obtenerRegistros(){
        return listaRegistro;
    }

    public RegistroMedico buscarPorId(int id){
        for (RegistroMedico registro : listaRegistro) {
            if (registro.getId()== id) {
                return registro;
            }
        }
        return null;
    }
    public RegistroMedico agregarRegistro(RegistroMedico registroMedico){
        listaRegistro.add(registroMedico);
        return registroMedico;
    }

    public void actualizarRegistro(RegistroMedico registroActualizadoMedico){
        for (int i = 0; i < listaRegistro.size(); i++) {
            if(listaRegistro.get(i).getId()== registroActualizadoMedico.getId()){
                listaRegistro.set(i, registroActualizadoMedico);
                return;
            }
        }
    }

    public void eliminarRegistro(int id){
        RegistroMedico registroEliminar = buscarPorId(id);
        if(registroEliminar != null){
            listaRegistro.remove(registroEliminar);
        }
    }

    public List<ExamenDiagnostico> obtenerExamenes() {
        return listaExamenDiagnosticos;
    }

    public ExamenDiagnostico buscarExamenDiaporID(int id){
        for (ExamenDiagnostico examen : listaExamenDiagnosticos) {
            if (examen.getId()==id) {
                return examen;
            }
        }
        return null;
    }

    public void actualizarExamen(ExamenDiagnostico examenActualizado){
        for (int i = 0; i < listaExamenDiagnosticos.size(); i++) {
            if (listaExamenDiagnosticos.get(i).getId()== examenActualizado.getId()) {
                listaExamenDiagnosticos.set(i, examenActualizado);
                return;
            }
        }
    }

    public void eliminarExamen(int id){
        ExamenDiagnostico examenEliminar = buscarExamenDiaporID(id);
        if (examenEliminar != null) {
            listaExamenDiagnosticos.remove(examenEliminar);
        }
    }


}
