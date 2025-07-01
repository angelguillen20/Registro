package com.Registro.Registro;

import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.Registro.Registro.Model.ExamenDiagnostico;
import com.Registro.Registro.Model.RegistroMedico;
import com.Registro.Registro.Repository.RegistroRepository;

import com.github.javafaker.Faker;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {
    
    @Autowired
    private RegistroRepository registroRepository;
    
    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();

        // Generar registros médicos
        for (int i = 0; i < 5; i++) {
            RegistroMedico registro = new RegistroMedico();
          
            registro.setFechaExamen(LocalDate.now().minusDays(faker.number().numberBetween(1, 30)));
            registro.setDiagnostico(faker.lorem().sentence(3));
            registro.setTratamiento(faker.lorem().sentence(5));
            registro.setObservaciones(faker.lorem().paragraph(1));
            registro.setTipoRegistro(faker.options().option("Consulta", "Emergencia", "Control", "Hospitalización"));
            
            // Crear exámenes asociados
            ExamenDiagnostico examen1 = new ExamenDiagnostico();
            examen1.setNombreExamen(faker.lorem().word() + " Test");
            examen1.setDescripcionExamen(faker.lorem().sentence());
            examen1.setPreparacionRequerida(faker.options().option(
                "Ayuno de 8 horas", 
                "Sin preparación",
                "Tomar agua",
                "No requerida"
            ));
            
            ExamenDiagnostico examen2 = new ExamenDiagnostico();
            examen2.setNombreExamen(faker.lorem().word() + " Scan");
            examen2.setDescripcionExamen(faker.lorem().sentence());
            examen2.setPreparacionRequerida(faker.options().option(
                "Ayuno de 8 horas", 
                "Sin preparación",
                "Tomar agua",
                "No requerida"
            ));
            
            registro.setExamenes(Arrays.asList(examen1, examen2));
            
            registroRepository.save(registro);
        }
    }
}
