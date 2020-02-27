package com.lastcrm.gateway.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.lastcrm.gateway.domain.enumeration.Genero;

/**
 * A Cliente.
 */
@Entity
@Table(name = "cliente")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 200)
    @Column(name = "nombre", length = 200, nullable = false)
    private String nombre;

    @NotNull
    @Size(max = 200)
    @Column(name = "apellido_1", length = 200, nullable = false)
    private String apellido1;

    @Size(max = 200)
    @Column(name = "apellido_2", length = 200)
    private String apellido2;

    @Size(max = 15)
    @Column(name = "nif", length = 15)
    private String nif;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "genero", nullable = false)
    private Genero genero;

    @NotNull
    @Size(max = 15)
    @Column(name = "tlf_movil", length = 15, nullable = false)
    private String tlfMovil;

    @Size(max = 15)
    @Column(name = "tlf_movil_2", length = 15)
    private String tlfMovil2;

    @Size(max = 15)
    @Column(name = "tlf_fijo", length = 15)
    private String tlfFijo;

    @Size(max = 15)
    @Column(name = "fax", length = 15)
    private String fax;

    @NotNull
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    @Column(name = "email_1", nullable = false)
    private String email1;

    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    @Column(name = "email_2")
    private String email2;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("clientes")
    private Fuente fuente;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public Cliente nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public Cliente apellido1(String apellido1) {
        this.apellido1 = apellido1;
        return this;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public Cliente apellido2(String apellido2) {
        this.apellido2 = apellido2;
        return this;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getNif() {
        return nif;
    }

    public Cliente nif(String nif) {
        this.nif = nif;
        return this;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public Genero getGenero() {
        return genero;
    }

    public Cliente genero(Genero genero) {
        this.genero = genero;
        return this;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public String getTlfMovil() {
        return tlfMovil;
    }

    public Cliente tlfMovil(String tlfMovil) {
        this.tlfMovil = tlfMovil;
        return this;
    }

    public void setTlfMovil(String tlfMovil) {
        this.tlfMovil = tlfMovil;
    }

    public String getTlfMovil2() {
        return tlfMovil2;
    }

    public Cliente tlfMovil2(String tlfMovil2) {
        this.tlfMovil2 = tlfMovil2;
        return this;
    }

    public void setTlfMovil2(String tlfMovil2) {
        this.tlfMovil2 = tlfMovil2;
    }

    public String getTlfFijo() {
        return tlfFijo;
    }

    public Cliente tlfFijo(String tlfFijo) {
        this.tlfFijo = tlfFijo;
        return this;
    }

    public void setTlfFijo(String tlfFijo) {
        this.tlfFijo = tlfFijo;
    }

    public String getFax() {
        return fax;
    }

    public Cliente fax(String fax) {
        this.fax = fax;
        return this;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail1() {
        return email1;
    }

    public Cliente email1(String email1) {
        this.email1 = email1;
        return this;
    }

    public void setEmail1(String email1) {
        this.email1 = email1;
    }

    public String getEmail2() {
        return email2;
    }

    public Cliente email2(String email2) {
        this.email2 = email2;
        return this;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public Fuente getFuente() {
        return fuente;
    }

    public Cliente fuente(Fuente fuente) {
        this.fuente = fuente;
        return this;
    }

    public void setFuente(Fuente fuente) {
        this.fuente = fuente;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cliente)) {
            return false;
        }
        return id != null && id.equals(((Cliente) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Cliente{" +
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
            "}";
    }
}
