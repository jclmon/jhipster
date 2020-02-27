package com.lastcrm.gateway.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

import com.lastcrm.gateway.domain.enumeration.Estado;

/**
 * A Cita.
 */
@Entity
@Table(name = "cita")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Cita implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private Instant fecha;

    @Size(max = 3999)
    @Column(name = "comentario", length = 3999)
    private String comentario;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private Estado estado;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("citas")
    private Agente agente;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFecha() {
        return fecha;
    }

    public Cita fecha(Instant fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(Instant fecha) {
        this.fecha = fecha;
    }

    public String getComentario() {
        return comentario;
    }

    public Cita comentario(String comentario) {
        this.comentario = comentario;
        return this;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Estado getEstado() {
        return estado;
    }

    public Cita estado(Estado estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Agente getAgente() {
        return agente;
    }

    public Cita agente(Agente agente) {
        this.agente = agente;
        return this;
    }

    public void setAgente(Agente agente) {
        this.agente = agente;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cita)) {
            return false;
        }
        return id != null && id.equals(((Cita) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Cita{" +
            "id=" + getId() +
            ", fecha='" + getFecha() + "'" +
            ", comentario='" + getComentario() + "'" +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
