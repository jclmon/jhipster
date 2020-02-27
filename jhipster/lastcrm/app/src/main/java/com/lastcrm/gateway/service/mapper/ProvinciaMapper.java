package com.lastcrm.gateway.service.mapper;

import com.lastcrm.gateway.domain.*;
import com.lastcrm.gateway.service.dto.ProvinciaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Provincia} and its DTO {@link ProvinciaDTO}.
 */
@Mapper(componentModel = "spring", uses = {PaisMapper.class})
public interface ProvinciaMapper extends EntityMapper<ProvinciaDTO, Provincia> {

    @Mapping(source = "pais.id", target = "paisId")
    @Mapping(source = "pais.nombre", target = "paisNombre")
    ProvinciaDTO toDto(Provincia provincia);

    @Mapping(source = "paisId", target = "pais")
    Provincia toEntity(ProvinciaDTO provinciaDTO);

    default Provincia fromId(Long id) {
        if (id == null) {
            return null;
        }
        Provincia provincia = new Provincia();
        provincia.setId(id);
        return provincia;
    }
}
