package com.lastcrm.gateway.service.mapper;

import com.lastcrm.gateway.domain.*;
import com.lastcrm.gateway.service.dto.AreaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Area} and its DTO {@link AreaDTO}.
 */
@Mapper(componentModel = "spring", uses = {MunicipioMapper.class})
public interface AreaMapper extends EntityMapper<AreaDTO, Area> {

    @Mapping(source = "municipio.id", target = "municipioId")
    @Mapping(source = "municipio.nombre", target = "municipioNombre")
    AreaDTO toDto(Area area);

    @Mapping(source = "municipioId", target = "municipio")
    Area toEntity(AreaDTO areaDTO);

    default Area fromId(Long id) {
        if (id == null) {
            return null;
        }
        Area area = new Area();
        area.setId(id);
        return area;
    }
}
