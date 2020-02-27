package com.lastcrm.gateway.service.mapper;

import com.lastcrm.gateway.domain.*;
import com.lastcrm.gateway.service.dto.FichaClienteDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link FichaCliente} and its DTO {@link FichaClienteDTO}.
 */
@Mapper(componentModel = "spring", uses = {ClienteMapper.class, ProductoMapper.class, AreaMapper.class, CitaMapper.class, TipoProductoMapper.class})
public interface FichaClienteMapper extends EntityMapper<FichaClienteDTO, FichaCliente> {

    @Mapping(source = "cliente.id", target = "clienteId")
    @Mapping(source = "cliente.nombre", target = "clienteNombre")
    @Mapping(source = "producto.id", target = "productoId")
    @Mapping(source = "producto.direccion", target = "productoDireccion")
    FichaClienteDTO toDto(FichaCliente fichaCliente);

    @Mapping(source = "clienteId", target = "cliente")
    @Mapping(source = "productoId", target = "producto")
    @Mapping(target = "removeArea", ignore = true)
    @Mapping(target = "removeCita", ignore = true)
    @Mapping(target = "removeTipoProducto", ignore = true)
    FichaCliente toEntity(FichaClienteDTO fichaClienteDTO);

    default FichaCliente fromId(Long id) {
        if (id == null) {
            return null;
        }
        FichaCliente fichaCliente = new FichaCliente();
        fichaCliente.setId(id);
        return fichaCliente;
    }
}
