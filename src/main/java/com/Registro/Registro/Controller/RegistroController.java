package com.Registro.Registro.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Registro.Registro.Model.ExamenDiagnostico;
import com.Registro.Registro.Model.RegistroMedico;
import com.Registro.Registro.Service.RegistroService;

@RestController
@RequestMapping("/Registro")
public class RegistroController {


    @Autowired
    private RegistroService registroService;

    @GetMapping
    public List<RegistroMedico> obtenerRegistros(){
        return registroService.obtenerRegistros();
    }
    @GetMapping("/{id}")
    public RegistroMedico buscarporId(@PathVariable int id){
        return registroService.buscarPorId(id);
    }

   
    @PutMapping("/{id}")
    public void actualizarRegistro(@PathVariable int id){
        registroService.eliminarRegistro(id);
    }
    @DeleteMapping("/{id}")
    public void eliminarRegistro(@PathVariable int id){
        registroService.eliminarRegistro(id);
    }
    @GetMapping("/{id}/examenes")
    public List<ExamenDiagnostico> obtenerExamenes(@PathVariable int id){
        return registroService.obtenerExamenes(id);
    }
    @PostMapping("/{id}/examenes")
    public boolean agregarExamen(@PathVariable int id,@RequestBody ExamenDiagnostico examen){
        return registroService.agregarExamen(id, examen);
    }
    @DeleteMapping("/{id}/examenes/{examenId}")
    public boolean eliminarExamen(@PathVariable int id,@PathVariable int examenId){
        return registroService.eliminarExamen(id, examenId);
    }
        @PostMapping
    public ResponseEntity<RegistroMedico> crearHabitacion(@RequestBody RegistroMedico registroMedico) {
        // Asocia cada cama a la habitación
        for ( ExamenDiagnostico exa: registroMedico.getExamenes()) {
            exa.setRegistroMedico(registroMedico);
        }

        // Guarda la habitación y sus camas en cascada
        RegistroMedico guardada = registroService.agregarRegistro(registroMedico);
        return ResponseEntity.ok(guardada);
    }
}
