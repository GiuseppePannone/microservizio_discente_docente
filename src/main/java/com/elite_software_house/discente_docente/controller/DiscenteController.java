package com.elite_software_house.discente_docente.controller;

import com.elite_software_house.discente_docente.DTO.CorsoDiscenteDTO;
import com.elite_software_house.discente_docente.DTO.CorsoListDTO;
import com.elite_software_house.discente_docente.DTO.DiscenteDTO;
import com.elite_software_house.discente_docente.service.DiscenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/discenti")
public class DiscenteController {

    @Autowired
    DiscenteService discenteService;

    @GetMapping("/lista")
    public List<DiscenteDTO> findDiscenti(){
        return discenteService.findAll();
    }

    @GetMapping("/corso-discente")
    public ResponseEntity<List<CorsoDiscenteDTO>> getAllCorsoDiscenti() {
        return ResponseEntity.ok(discenteService.getAllCorsoDiscenti());
    }

    @PostMapping("/by-corsi")
    public ResponseEntity<List<CorsoListDTO>> getCorsiByIds(@RequestBody List<Long> corsoIds) {
        List<CorsoListDTO> corsiDto = discenteService.getCorsiByIds(corsoIds);

        for(CorsoListDTO corsoDto : corsiDto){
            List<Long> discenteIds = discenteService.getDiscentiIdsByCorsoId(corsoDto.getId());
            corsoDto.setDiscenteIds(discenteIds);
        }

        return ResponseEntity.ok(corsiDto);
    }


    @PostMapping
    public ResponseEntity<DiscenteDTO> create(@RequestBody DiscenteDTO discenteDTO){
        return new ResponseEntity<>(discenteService.creaDiscente(discenteDTO), HttpStatus.CREATED);
    }

    @PostMapping("/corso-discente")
    public ResponseEntity<CorsoDiscenteDTO> associaCorsoDiscente(@RequestBody CorsoDiscenteDTO corsoDiscenteDTO){
        CorsoDiscenteDTO creaCorsoDiscenteDto = discenteService.associaCorsoDiscente(corsoDiscenteDTO);
        return new ResponseEntity<>(creaCorsoDiscenteDto, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<DiscenteDTO> findById(@PathVariable Long id){
        try {
            DiscenteDTO discenteDTO = discenteService.findById(id);
            return ResponseEntity.ok(discenteDTO);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiscenteDTO> update(@PathVariable Long id, @RequestBody DiscenteDTO discenteDTO) {
        DiscenteDTO discenteSalvato = discenteService.update(id, discenteDTO);
        return ResponseEntity.ok(discenteSalvato);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDiscente(@PathVariable Long id){
        discenteService.delete(id);
        return ResponseEntity.ok("Discente eliminato");
    }
}
