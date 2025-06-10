package com.elite_software_house.discente_docente.service;


import com.elite_software_house.discente_docente.DTO.CorsoDiscenteDTO;
import com.elite_software_house.discente_docente.DTO.CorsoListDTO;
import com.elite_software_house.discente_docente.DTO.DiscenteDTO;
import com.elite_software_house.discente_docente.entity.Discente;
import com.elite_software_house.discente_docente.mapper.DiscenteMapper;
import com.elite_software_house.discente_docente.repository.DiscenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiscenteService {
    @Autowired
    private DiscenteRepository discenteRepository;

    @Autowired
    private DiscenteMapper discenteMapper;

    @Autowired
    private WebClient webClient;

    @Autowired
    private ExternalService externalService;

    public List<DiscenteDTO> findAll() {
        List<DiscenteDTO> discentiDTO = discenteMapper.entityListToDtoList(discenteRepository.findAll());

        for(DiscenteDTO discenteDto : discentiDTO){
            try{
                discenteDto.setCorsi(externalService.getCorsoWithoutAlunno(discenteDto.getId()));
            } catch (Exception e){
                System.out.println(e.getMessage());
            }

        }
        return discentiDTO;
    }



    public List<Long> getDiscentiIdsByCorsoId(Long corsoId) {
        return webClient.get()
                .uri("/api/corsi/{corsoId}/discenti", corsoId)
                .retrieve()
                .bodyToFlux(Long.class)
                .collectList()
                .block();
    }

    public List<CorsoListDTO> getCorsiByIds(List<Long> corsoIds) {
        return corsoIds.stream()
                .map(this::getCorsoById)
                .collect(Collectors.toList());
    }

    public CorsoListDTO getCorsoById(Long id) {
        return new CorsoListDTO();
    }


    public List<CorsoDiscenteDTO> getAllCorsoDiscenti(){
        return webClient.get()
                .uri("/corso-discente")
                .retrieve()
                .bodyToFlux(CorsoDiscenteDTO.class)
                .collectList()
                .block();
    }


    public DiscenteDTO findById(Long id) {
        return discenteMapper.toDto(discenteRepository.findById(id).orElse(null));
    }

   public DiscenteDTO creaDiscente(DiscenteDTO discenteDTO) {
        Discente discente = discenteMapper.toEntity(discenteDTO);
        return discenteMapper.toDto(discenteRepository.save(discente));
    }

    public CorsoDiscenteDTO associaCorsoDiscente(CorsoDiscenteDTO corsoDiscenteDTO) {
        return webClient.post()
                .uri("/corso-discente")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(corsoDiscenteDTO)
                .retrieve()
                .bodyToMono(CorsoDiscenteDTO.class)
                .block();
    }

    public DiscenteDTO update(Long id, DiscenteDTO discenteDTO) {
        this.discenteRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Discente non trovato")
        );
        discenteDTO.setId(id);
        Discente discente = discenteMapper.toEntity(discenteDTO);
        return discenteMapper.toDto(discenteRepository.save(discente));
    }

    public void delete(Long id) {
        Discente discente = discenteRepository.findById(id).orElseThrow();
        discenteRepository.delete(discente);
    }
}
