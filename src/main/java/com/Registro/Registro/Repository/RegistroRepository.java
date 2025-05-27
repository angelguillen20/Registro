package com.Registro.Registro.Repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.Registro.Registro.Model.ExamenDiagnostico;
import com.Registro.Registro.Model.RegistroMedico;

@Repository
public class RegistroRepository {

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
