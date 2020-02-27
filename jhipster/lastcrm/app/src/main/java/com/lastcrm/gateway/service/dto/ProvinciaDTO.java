package com.lastcrm.gateway.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.lastcrm.gateway.domain.Provincia} entity.
 */
public class ProvinciaDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 200)
    private String nombre;


    private Long paisId;

    private String paisNombre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getPaisId() {
        return paisId;
    }

    public void setPaisId(Long paisId) {
        this.paisId = paisId;
    }

    public String getPaisNombre() {
        return paisNombre;
    }

    public void setPaisNombre(String paisNombre) {
        this.paisNombre = paisNombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProvinciaDTO provinciaDTO = (ProvinciaDTO) o;
        if (provinciaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), provinciaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProvinciaDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", pais=" + getPaisId() +
            ", pais='" + getPaisNombre() + "'" +
            "}";
    }
}
