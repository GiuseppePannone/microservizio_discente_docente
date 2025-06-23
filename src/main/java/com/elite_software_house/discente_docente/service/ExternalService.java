package com.elite_software_house.discente_docente.service;

import com.elite_software_house.discente_docente.DTO.CorsoListDTO;
import com.elite_software_house.discente_docente.DTO.CorsoWithoutAlunnoDTO;
import com.elite_software_house.discente_docente.DTO.DocenteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.Base64;
import java.util.List;

@Service
public class ExternalService {
//    private String credentials = "user:password";
//    private String encodedAuth = Base64.getEncoder().encodeToString(credentials.getBytes());
    @Autowired
    private WebClient webClient;

    private WebClient webClientWithToken(String token) {
        return webClient.mutate()
                .defaultHeader(HttpHeaders.AUTHORIZATION, token)
                .build();
    }


    public Flux<CorsoListDTO> getCorsoById(List<Long> ids) {
        if(ids != null) {
            return Flux.fromIterable(ids)
                    .flatMap(id -> webClient
                            .get()
                            .uri("/api/corsi/{id}", id)
                            .retrieve()
                            .bodyToMono(CorsoListDTO.class));
        } else {
            return Flux.empty();
        }
    }

    public List<CorsoWithoutAlunnoDTO> getCorsoWithoutAlunno(Long discenteId, String token) {
        if(discenteId != null) {
            return webClientWithToken(token).get()
                    .uri("api/corsi/discente/{discenteId}", discenteId)
                   // .header(HttpHeaders.AUTHORIZATION, "Basic " + encodedAuth)
                    .retrieve()
                    .bodyToFlux(CorsoWithoutAlunnoDTO.class)
                    .collectList()
                    .block();
        }
        return List.of();
    }
    public List<CorsoWithoutAlunnoDTO> getCorsoWithoutAlunnoByDocenteId(String token, DocenteDTO docenteDto) {
        if(docenteDto.getId() != null) {
            return webClientWithToken(token).get()
                    .uri("api/corsi/{docenteId}/getByDocenteId", docenteDto.getId())
                    // .header(HttpHeaders.AUTHORIZATION, "Basic " + encodedAuth)
                    .retrieve()
                    .bodyToFlux(CorsoWithoutAlunnoDTO.class)
                    .collectList()
                    .block();
        }
        return List.of();
    }



}
