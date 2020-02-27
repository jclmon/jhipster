package com.lastcrm.gateway.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.lastcrm.gateway.domain.enumeration.Estado;

/**
 * A DTO for the {@link com.lastcrm.gateway.domain.Cita} entity.
 */
public class CitaDTO implements Serializable {

    private Long id;

    @NotNull
    private Instant fecha;

    @Size(max = 3999)
    private String comentario;

    @NotNull
    private Estado estado;


    private Long agenteId;

    private String agenteNombre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFecha() {
        return fecha;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Long getAgenteId() {
        return agenteId;
    }

    public void setAgenteId(Long agenteId) {
        this.agenteId = agenteId;
    }

    public String getAgenteNombre() {
        return agenteNombre;
    }

    public void setAgenteNombre(String agenteNombre) {
        this.agenteNombre = agenteNombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CitaDTO citaDTO = (CitaDTO) o;
        if (citaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), citaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CitaDTO{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", comentario='" + getComentario() + "'" +
            ", estado='" + getEstado() + "'" +
            ", agente=" + getAgenteId() +
            ", agente='" + getAgenteNombre() + "'" +
            "}";
    }
}
