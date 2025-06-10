package com.elite_software_house.discente_docente.mapper;

import com.elite_software_house.discente_docente.DTO.DocenteDTO;
import com.elite_software_house.discente_docente.entity.Docente;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DocenteMapper {

    Docente toEntity(DocenteDTO docenteDTO);

    DocenteDTO toDto(Docente docente);

    List<DocenteDTO> entityListToDtoList(List<Docente> docenti);

    List<Docente> dtoListToEntityList(List<DocenteDTO> docenteDTO);

//    DocenteCorsoListDTO toCorsoListDTO(Docente docente);
}

