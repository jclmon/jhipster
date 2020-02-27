package com.lastcrm.gateway.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.lastcrm.gateway.domain.enumeration.Solicitud;
import com.lastcrm.gateway.domain.enumeration.Prioridad;

/**
 * A DTO for the {@link com.lastcrm.gateway.domain.FichaCliente} entity.
 */
public class FichaClienteDTO implements Serializable {

    private Long id;

    @NotNull
    private Solicitud solicitud;

    @NotNull
    private Prioridad prioridad;

    @Size(max = 3999)
    private String comentario;


    private Long clienteId;

    private String clienteNombre;

    private Long productoId;

    private String productoDireccion;

    private Set<AreaDTO> areas = new HashSet<>();

    private Set<CitaDTO> citas = new HashSet<>();

    private Set<TipoProductoDTO> tipoProductos = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Solicitud getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
    }

    public Prioridad getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public String getProductoDireccion() {
        return productoDireccion;
    }

    public void setProductoDireccion(String productoDireccion) {
        this.productoDireccion = productoDireccion;
    }

    public Set<AreaDTO> getAreas() {
        return areas;
    }

    public void setAreas(Set<AreaDTO> areas) {
        this.areas = areas;
    }

    public Set<CitaDTO> getCitas() {
        return citas;
    }

    public void setCitas(Set<CitaDTO> citas) {
        this.citas = citas;
    }

    public Set<TipoProductoDTO> getTipoProductos() {
        return tipoProductos;
    }

    public void setTipoProductos(Set<TipoProductoDTO> tipoProductos) {
        this.tipoProductos = tipoProductos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FichaClienteDTO fichaClienteDTO = (FichaClienteDTO) o;
        if (fichaClienteDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fichaClienteDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FichaClienteDTO{" +
            "id=" + getId() +
            ", solicitud='" + getSolicitud() + "'" +
            ", prioridad='" + getPrioridad() + "'" +
            ", comentario='" + getComentario() + "'" +
            ", cliente=" + getClienteId() +
            ", cliente='" + getClienteNombre() + "'" +
            ", producto=" + getProductoId() +
            ", producto='" + getProductoDireccion() + "'" +
            "}";
    }
}
