package com.lastcrm.gateway.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.lastcrm.gateway.domain.Area} entity.
 */
public class AreaDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 200)
    private String nombre;


    private Long municipioId;

    private String municipioNombre;

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

    public Long getMunicipioId() {
        return municipioId;
    }

    public void setMunicipioId(Long municipioId) {
        this.municipioId = municipioId;
    }

    public String getMunicipioNombre() {
        return municipioNombre;
    }

    public void setMunicipioNombre(String municipioNombre) {
        this.municipioNombre = municipioNombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AreaDTO areaDTO = (AreaDTO) o;
        if (areaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), areaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AreaDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", municipio=" + getMunicipioId() +
            ", municipio='" + getMunicipioNombre() + "'" +
            "}";
    }
}
