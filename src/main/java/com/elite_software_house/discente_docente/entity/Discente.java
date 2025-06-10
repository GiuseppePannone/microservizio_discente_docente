package com.elite_software_house.discente_docente.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
@Entity
@Table(name = "discente_micro")
public class Discente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_discente")
    private Long id;

    @Column(name = "nome_discente", nullable = false)
    private String nome;

    @Column(name = "cognome_discente", nullable = false)
    private String cognome;

    @Column(name = "data_di_nascita", nullable = false)
    private Date dataDiNascita;

    @Column(name = "citta_di_residenza")
    private String cittaDiResidenza;

    @Column(name = "voto", nullable = false)
    private int voto;



}
