package com.lastcrm.gateway.service.mapper;

import com.lastcrm.gateway.domain.*;
import com.lastcrm.gateway.service.dto.TipoProductoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TipoProducto} and its DTO {@link TipoProductoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TipoProductoMapper extends EntityMapper<TipoProductoDTO, TipoProducto> {



    default TipoProducto fromId(Long id) {
        if (id == null) {
            return null;
        }
        TipoProducto tipoProducto = new TipoProducto();
        tipoProducto.setId(id);
        return tipoProducto;
    }
}
