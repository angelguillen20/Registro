package com.Registro.Registro.Repository;


import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.Registro.Registro.Model.RegistroMedico;

@Repository
public interface RegistroRepository extends JpaRepository<RegistroMedico, Integer> {


 


}
