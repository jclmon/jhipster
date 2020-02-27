package com.lastcrm.gateway.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.lastcrm.gateway.domain.enumeration.Genero;

/**
 * A DTO for the {@link com.lastcrm.gateway.domain.Cliente} entity.
 */
public class ClienteDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 200)
    private String nombre;

    @NotNull
    @Size(max = 200)
    private String apellido1;

    @Size(max = 200)
    private String apellido2;

    @Size(max = 15)
    private String nif;

    @NotNull
    private Genero genero;

    @NotNull
    @Size(max = 15)
    private String tlfMovil;

    @Size(max = 15)
    private String tlfMovil2;

    @Size(max = 15)
    private String tlfFijo;

    @Size(max = 15)
    private String fax;

    @NotNull
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    private String email1;

    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    private String email2;


    private Long fuenteId;

    private String fuenteNombre;

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

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public String getTlfMovil() {
        return tlfMovil;
    }

    public void setTlfMovil(String tlfMovil) {
        this.tlfMovil = tlfMovil;
    }

    public String getTlfMovil2() {
        return tlfMovil2;
    }

    public void setTlfMovil2(String tlfMovil2) {
        this.tlfMovil2 = tlfMovil2;
    }

    public String getTlfFijo() {
        return tlfFijo;
    }

    public void setTlfFijo(String tlfFijo) {
        this.tlfFijo = tlfFijo;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail1() {
        return email1;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public Long getFuenteId() {
        return fuenteId;
    }

    public void setFuenteId(Long fuenteId) {
        this.fuenteId = fuenteId;
    }

    public String getFuenteNombre() {
        return fuenteNombre;
    }

    public void setFuenteNombre(String fuenteNombre) {
        this.fuenteNombre = fuenteNombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClienteDTO clienteDTO = (ClienteDTO) o;
        if (clienteDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), clienteDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClienteDTO{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", apellido1='" + getApellido1() + "'" +
            ", apellido2='" + getApellido2() + "'" +
            ", nif='" + getNif() + "'" +
            ", genero='" + getGenero() + "'" +
            ", tlfMovil='" + getTlfMovil() + "'" +
            ", tlfMovil2='" + getTlfMovil2() + "'" +
            ", tlfFijo='" + getTlfFijo() + "'" +
            ", fax='" + getFax() + "'" +
            ", email1='" + getEmail1() + "'" +
            ", email2='" + getEmail2() + "'" +
            ", fuente=" + getFuenteId() +
            ", fuente='" + getFuenteNombre() + "'" +
            "}";
    }
}
