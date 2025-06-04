package com.Registro.Registro.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Registro.Registro.Model.RegistroMedico;
import com.Registro.Registro.Service.RegistroService;

@RestController
@RequestMapping("/registro-medico")
public class RegistroController {

    private final RegistroService registroService;

    public RegistroController(RegistroService registroService) {
        this.registroService = registroService;
    }

    @GetMapping
    public List<RegistroMedico> listarTodos() {
        return registroService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistroMedico> obtenerPorId(@PathVariable int id) {
        return registroService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RegistroMedico> crear(@RequestBody RegistroMedico registroMedico) {
        RegistroMedico creado = registroService.crearRegistro(registroMedico);
        return ResponseEntity.ok(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RegistroMedico> actualizar(@PathVariable int id, @RequestBody RegistroMedico registroMedico) {
        try {
            RegistroMedico actualizado = registroService.actualizarRegistro(id, registroMedico);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        registroService.eliminarRegistro(id);
        
        return ResponseEntity.noContent().build();
    }
}

