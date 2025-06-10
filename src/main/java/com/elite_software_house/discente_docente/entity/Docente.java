package com.elite_software_house.discente_docente.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Data
@Entity
@Table(name = "docente_micro")
public class Docente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_docente")
    private Long id;

    @Column(name = "nome_docente", nullable = false)
    private String nome;

    @Column(name = "cognome_docente", nullable = false)
    private String cognome;

    @Column(name = "data_di_nascita", nullable = false, unique = true)
    private Date dataDiNascita;

}