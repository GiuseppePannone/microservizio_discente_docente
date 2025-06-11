package com.elite_software_house.discente_docente.service;

import com.elite_software_house.discente_docente.DTO.CorsoListDTO;
import com.elite_software_house.discente_docente.DTO.CorsoWithoutAlunnoDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.Base64;
import java.util.List;

@Service
public class ExternalService {
    private String credentials = "user:password";
    private String encodedAuth = Base64.getEncoder().encodeToString(credentials.getBytes());
    private final WebClient webClient;

    public ExternalService(WebClient webClient) {
        this.webClient = webClient;
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

    public List<CorsoWithoutAlunnoDTO> getCorsoWithoutAlunno(Long discenteId) {
        if(discenteId != null) {
            return webClient.get()
                    .uri("api/corsi/discente/{discenteId}", discenteId)
                    .header(HttpHeaders.AUTHORIZATION, "Basic " + encodedAuth)
                    .retrieve()
                    .bodyToFlux(CorsoWithoutAlunnoDTO.class)
                    .collectList()
                    .block();
        }
        return List.of();
    }

}
