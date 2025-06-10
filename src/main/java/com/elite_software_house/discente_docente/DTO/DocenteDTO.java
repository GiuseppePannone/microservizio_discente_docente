package com.elite_software_house.discente_docente.DTO;

import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class DocenteDTO {
    private Long id;
    private String nome;
    private String cognome;
    private Date dataDiNascita;
    private List<Long> corsiIds;
    private List<CorsoListDTO> corsoList;
}
