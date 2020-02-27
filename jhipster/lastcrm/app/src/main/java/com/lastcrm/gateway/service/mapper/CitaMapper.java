package com.lastcrm.gateway.service.mapper;

import com.lastcrm.gateway.domain.*;
import com.lastcrm.gateway.service.dto.CitaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Cita} and its DTO {@link CitaDTO}.
 */
@Mapper(componentModel = "spring", uses = {AgenteMapper.class})
public interface CitaMapper extends EntityMapper<CitaDTO, Cita> {

    @Mapping(source = "agente.id", target = "agenteId")
    @Mapping(source = "agente.nombre", target = "agenteNombre")
    CitaDTO toDto(Cita cita);

    @Mapping(source = "agenteId", target = "agente")
    Cita toEntity(CitaDTO citaDTO);

    default Cita fromId(Long id) {
        if (id == null) {
            return null;
        }
        Cita cita = new Cita();
        cita.setId(id);
        return cita;
    }
}
