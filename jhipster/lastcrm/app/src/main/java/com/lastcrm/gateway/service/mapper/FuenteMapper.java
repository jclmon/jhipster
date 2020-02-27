package com.lastcrm.gateway.service.mapper;

import com.lastcrm.gateway.domain.*;
import com.lastcrm.gateway.service.dto.FuenteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Fuente} and its DTO {@link FuenteDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FuenteMapper extends EntityMapper<FuenteDTO, Fuente> {



    default Fuente fromId(Long id) {
        if (id == null) {
            return null;
        }
        Fuente fuente = new Fuente();
        fuente.setId(id);
        return fuente;
    }
}
