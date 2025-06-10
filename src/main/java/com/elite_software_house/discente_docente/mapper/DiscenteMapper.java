package com.elite_software_house.discente_docente.mapper;

import com.elite_software_house.discente_docente.DTO.DiscenteDTO;
import com.elite_software_house.discente_docente.entity.Discente;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DiscenteMapper {

    Discente toEntity(DiscenteDTO discenteDTO);

    DiscenteDTO toDto(Discente discente);

    List<DiscenteDTO> entityListToDtoList(List<Discente> discenteList);

    List<Discente> dtoListToEntityList(List<DiscenteDTO> discenteDTOList);

//    DiscenteCorsoListDTO toDtoCorsoList(Discente discente);
//
//    List<DiscenteCorsoListDTO> toDtoCorsoList(List<Discente> discente);

}
