package com.lastcrm.gateway.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.lastcrm.gateway.domain.enumeration.Solicitud;

import com.lastcrm.gateway.domain.enumeration.Prioridad;

/**
 * A FichaCliente.
 */
@Entity
@Table(name = "ficha_cliente")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FichaCliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "solicitud", nullable = false)
    private Solicitud solicitud;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "prioridad", nullable = false)
    private Prioridad prioridad;

    @Size(max = 3999)
    @Column(name = "comentario", length = 3999)
    private String comentario;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("fichaClientes")
    private Cliente cliente;

    @ManyToOne
    @JsonIgnoreProperties("fichaClientes")
    private Producto producto;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @NotNull
    @JoinTable(name = "ficha_cliente_area",
               joinColumns = @JoinColumn(name = "ficha_cliente_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "area_id", referencedColumnName = "id"))
    private Set<Area> areas = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @NotNull
    @JoinTable(name = "ficha_cliente_cita",
               joinColumns = @JoinColumn(name = "ficha_cliente_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "cita_id", referencedColumnName = "id"))
    private Set<Cita> citas = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @NotNull
    @JoinTable(name = "ficha_cliente_tipo_producto",
               joinColumns = @JoinColumn(name = "ficha_cliente_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "tipo_producto_id", referencedColumnName = "id"))
    private Set<TipoProducto> tipoProductos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Solicitud getSolicitud() {
        return solicitud;
    }

    public FichaCliente solicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
        return this;
    }

    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
    }

    public Prioridad getPrioridad() {
        return prioridad;
    }

    public FichaCliente prioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
        return this;
    }

    public void setPrioridad(Prioridad prioridad) {
        this.prioridad = prioridad;
    }

    public String getComentario() {
        return comentario;
    }

    public FichaCliente comentario(String comentario) {
        this.comentario = comentario;
        return this;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public FichaCliente cliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Producto getProducto() {
        return producto;
    }

    public FichaCliente producto(Producto producto) {
        this.producto = producto;
        return this;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Set<Area> getAreas() {
        return areas;
    }

    public FichaCliente areas(Set<Area> areas) {
        this.areas = areas;
        return this;
    }

    public FichaCliente addArea(Area area) {
        this.areas.add(area);
        return this;
    }

    public FichaCliente removeArea(Area area) {
        this.areas.remove(area);
        return this;
    }

    public void setAreas(Set<Area> areas) {
        this.areas = areas;
    }

    public Set<Cita> getCitas() {
        return citas;
    }

    public FichaCliente citas(Set<Cita> citas) {
        this.citas = citas;
        return this;
    }

    public FichaCliente addCita(Cita cita) {
        this.citas.add(cita);
        return this;
    }

    public FichaCliente removeCita(Cita cita) {
        this.citas.remove(cita);
        return this;
    }

    public void setCitas(Set<Cita> citas) {
        this.citas = citas;
    }

    public Set<TipoProducto> getTipoProductos() {
        return tipoProductos;
    }

    public FichaCliente tipoProductos(Set<TipoProducto> tipoProductos) {
        this.tipoProductos = tipoProductos;
        return this;
    }

    public FichaCliente addTipoProducto(TipoProducto tipoProducto) {
        this.tipoProductos.add(tipoProducto);
        return this;
    }

    public FichaCliente removeTipoProducto(TipoProducto tipoProducto) {
        this.tipoProductos.remove(tipoProducto);
        return this;
    }

    public void setTipoProductos(Set<TipoProducto> tipoProductos) {
        this.tipoProductos = tipoProductos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FichaCliente)) {
            return false;
        }
        return id != null && id.equals(((FichaCliente) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "FichaCliente{" +
            "id=" + getId() +
            ", solicitud='" + getSolicitud() + "'" +
            ", prioridad='" + getPrioridad() + "'" +
            ", comentario='" + getComentario() + "'" +
            "}";
    }
}
