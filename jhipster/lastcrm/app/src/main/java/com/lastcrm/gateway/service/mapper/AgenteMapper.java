package com.lastcrm.gateway.service.mapper;

import com.lastcrm.gateway.domain.*;
import com.lastcrm.gateway.service.dto.AgenteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Agente} and its DTO {@link AgenteDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AgenteMapper extends EntityMapper<AgenteDTO, Agente> {



    default Agente fromId(Long id) {
        if (id == null) {
            return null;
        }
        Agente agente = new Agente();
        agente.setId(id);
        return agente;
    }
}
