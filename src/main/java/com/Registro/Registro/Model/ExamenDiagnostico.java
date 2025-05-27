package com.Registro.Registro.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "examen Diagnostico")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamenDiagnostico {

    @Id
    private int id;
    private String NombreExamen;
    private String DescripcionExamen;
    private String PreparacionRequerida;


    @ManyToOne
    @JoinColumn(name = "registro_medico_id")
    @JsonBackReference
    private RegistroMedico registroMedico;
}
