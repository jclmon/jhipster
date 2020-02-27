package com.lastcrm.gateway.service.mapper;

import com.lastcrm.gateway.domain.*;
import com.lastcrm.gateway.service.dto.MunicipioDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Municipio} and its DTO {@link MunicipioDTO}.
 */
@Mapper(componentModel = "spring", uses = {ProvinciaMapper.class})
public interface MunicipioMapper extends EntityMapper<MunicipioDTO, Municipio> {

    @Mapping(source = "provincia.id", target = "provinciaId")
    @Mapping(source = "provincia.nombre", target = "provinciaNombre")
    MunicipioDTO toDto(Municipio municipio);

    @Mapping(source = "provinciaId", target = "provincia")
    Municipio toEntity(MunicipioDTO municipioDTO);

    default Municipio fromId(Long id) {
        if (id == null) {
            return null;
        }
        Municipio municipio = new Municipio();
        municipio.setId(id);
        return municipio;
    }
}
