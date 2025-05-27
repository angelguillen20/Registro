package com.Registro.Registro.Model;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "registro_medico")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistroMedico {

    @Id
    private int id;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private String Diagnostico;
    private String Tratamiento;
    private String Observaciones;
    private String TipoRegistro;


    @OneToMany(mappedBy = "registroMedico", cascade = CascadeType.ALL, orphanRemoval = true )
    @JsonManagedReference
    private List<ExamenDiagnostico> examenes;
}
