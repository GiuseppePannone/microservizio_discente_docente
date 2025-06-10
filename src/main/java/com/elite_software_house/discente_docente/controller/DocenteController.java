package com.elite_software_house.discente_docente.controller;

import com.elite_software_house.discente_docente.DTO.DocenteDTO;
import com.elite_software_house.discente_docente.service.DocenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/docenti")
public class DocenteController {

    @Autowired
    DocenteService docenteService;


    // LISTA
    @GetMapping("/lista")
    public List<DocenteDTO> findDocenti() {
        return docenteService.findAll();
    }
    // SALVA NUOVO
    @PostMapping
    public ResponseEntity<DocenteDTO> create(@RequestBody DocenteDTO docenteDTO) {
        return new ResponseEntity<>(docenteService.creaDocente(docenteDTO), HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<DocenteDTO> findById(@PathVariable Long id) {
        try{
            DocenteDTO docenteDTO = docenteService.findById(id);
            return ResponseEntity.ok(docenteDTO);
        } catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocenteDTO> update(@PathVariable Long id, @RequestBody DocenteDTO docenteDTO) {
        DocenteDTO docenteSalvato = docenteService.update(id, docenteDTO);
        return ResponseEntity.ok(docenteSalvato);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDocente(@PathVariable Long id) {
        docenteService.delete(id);
        return ResponseEntity.ok("Docente eliminato.");
    }
}