package com.lastcrm.gateway.service.mapper;

import com.lastcrm.gateway.domain.*;
import com.lastcrm.gateway.service.dto.ProductoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Producto} and its DTO {@link ProductoDTO}.
 */
@Mapper(componentModel = "spring", uses = {MunicipioMapper.class, TipoProductoMapper.class})
public interface ProductoMapper extends EntityMapper<ProductoDTO, Producto> {

    @Mapping(source = "municipio.id", target = "municipioId")
    @Mapping(source = "municipio.nombre", target = "municipioNombre")
    @Mapping(source = "tipoProducto.id", target = "tipoProductoId")
    @Mapping(source = "tipoProducto.nombre", target = "tipoProductoNombre")
    ProductoDTO toDto(Producto producto);

    @Mapping(source = "municipioId", target = "municipio")
    @Mapping(source = "tipoProductoId", target = "tipoProducto")
    Producto toEntity(ProductoDTO productoDTO);

    default Producto fromId(Long id) {
        if (id == null) {
            return null;
        }
        Producto producto = new Producto();
        producto.setId(id);
        return producto;
    }
}
