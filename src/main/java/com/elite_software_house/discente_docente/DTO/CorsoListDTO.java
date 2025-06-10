package com.elite_software_house.discente_docente.DTO;

import lombok.Data;

import java.util.List;

@Data
public class CorsoListDTO {
    private Long id;
    private String nomeCorso;
    private int oreCorso;
    private int annoAccademico;
    private Long docenteId;

    private List<Long> discenteIds;
}
