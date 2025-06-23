package com.elite_software_house.discente_docente.service;

import com.elite_software_house.discente_docente.DTO.CorsoWithoutAlunnoDTO;
import com.elite_software_house.discente_docente.DTO.DocenteDTO;
import com.elite_software_house.discente_docente.entity.Docente;
import com.elite_software_house.discente_docente.mapper.DocenteMapper;
import com.elite_software_house.discente_docente.repository.DocenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocenteService {


    @Autowired
    private DocenteRepository docenteRepository;

    @Autowired
    private DocenteMapper docenteMapper;

    @Autowired
    private WebClient webClient;

    @Autowired
    private ExternalService externalService;

//    @Autowired
//    private CorsoRepository corsoRepository;

    public List<DocenteDTO> findAll(String token) {
        List<DocenteDTO> docentiDTO = docenteMapper.entityListToDtoList(docenteRepository.findAll(Sort.by(Sort.Direction.ASC, "id")));

        for(DocenteDTO docenteDto : docentiDTO){
            try{
                docenteDto.setCorsi(externalService.getCorsoWithoutAlunnoByDocenteId( token, docenteDto));
               // docenteDto.setCorsiIds(docenteDto.getCorsi().stream().map(CorsoWithoutAlunnoDTO::getId).collect(Collectors.toList()));
               // docenteDto.setCorsiIds(externalService.getCorsoById(docenteDto.getCorsiIds()));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return docentiDTO;
    }

    public DocenteDTO findById(Long id, String token) {
         DocenteDTO docenteDTO = docenteMapper.toDto(docenteRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Docente non trovato")
        ));
         docenteDTO.setCorsi(externalService.getCorsoWithoutAlunnoByDocenteId(token, docenteDTO));
         return docenteDTO;
    }

    public DocenteDTO creaDocente(DocenteDTO docenteDTO) {
        Docente docente = docenteMapper.toEntity(docenteDTO);
        return docenteMapper.toDto(docenteRepository.save(docente));
    }

    public DocenteDTO update(Long id, DocenteDTO docenteDTO) {
        docenteDTO.setId(id);
        Docente oldDocente = docenteRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Docente non trovato")
        );
        Docente docente = docenteMapper.toEntity(docenteDTO);

//        List<Corso> corsi =  docente.getCorsi().stream()
//                .map(corse -> corsoRepository.findById(corse.getId()).orElseThrow())
//                .collect(Collectors.toList());
//
//        for(Corso corso : oldDocente.getCorsi()) {
//            if(!corsi.contains(corso)) {
//                corso = corsoRepository.findById(corso.getId()).orElseThrow();
//                corso.setDocente(docente);
//                corsoRepository.save(corso);
//
//            }
//        }
//
//        for(Corso corso : corsi) {
//            if(!oldDocente.getCorsi().contains(corso)) {
//                corso = corsoRepository.findById(corso.getId()).orElseThrow();
//                corso.setDocente(null);
//                corsoRepository.save(corso);
//            }
//        }
//
//
//        docente.setCorsi(corsi);
        return docenteMapper.toDto(docenteRepository.save(docente));
    }

    public void delete(Long id) {
        Docente docente = docenteRepository.findById(id).orElseThrow();
//        for(Corso corso : docente.getCorsi()) {
//            corso.setDocente(null);
//        }
        docenteRepository.deleteById(id);
    }

}
