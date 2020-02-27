package com.lastcrm.gateway.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.lastcrm.gateway.domain.enumeration.Solicitud;
import com.lastcrm.gateway.domain.enumeration.Prioridad;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.lastcrm.gateway.domain.FichaCliente} entity. This class is used
 * in {@link com.lastcrm.gateway.web.rest.FichaClienteResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ficha-clientes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FichaClienteCriteria implements Serializable, Criteria {
    /**
     * Class for filtering Solicitud
     */
    public static class SolicitudFilter extends Filter<Solicitud> {

        public SolicitudFilter() {
        }

        public SolicitudFilter(SolicitudFilter filter) {
            super(filter);
        }

        @Override
        public SolicitudFilter copy() {
            return new SolicitudFilter(this);
        }

    }
    /**
     * Class for filtering Prioridad
     */
    public static class PrioridadFilter extends Filter<Prioridad> {

        public PrioridadFilter() {
        }

        public PrioridadFilter(PrioridadFilter filter) {
            super(filter);
        }

        @Override
        public PrioridadFilter copy() {
            return new PrioridadFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private SolicitudFilter solicitud;

    private PrioridadFilter prioridad;

    private StringFilter comentario;

    private LongFilter clienteId;

    private LongFilter productoId;

    private LongFilter areaId;

    private LongFilter citaId;

    private LongFilter tipoProductoId;

    public FichaClienteCriteria(){
    }

    public FichaClienteCriteria(FichaClienteCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.solicitud = other.solicitud == null ? null : other.solicitud.copy();
        this.prioridad = other.prioridad == null ? null : other.prioridad.copy();
        this.comentario = other.comentario == null ? null : other.comentario.copy();
        this.clienteId = other.clienteId == null ? null : other.clienteId.copy();
        this.productoId = other.productoId == null ? null : other.productoId.copy();
        this.areaId = other.areaId == null ? null : other.areaId.copy();
        this.citaId = other.citaId == null ? null : other.citaId.copy();
        this.tipoProductoId = other.tipoProductoId == null ? null : other.tipoProductoId.copy();
    }

    @Override
    public FichaClienteCriteria copy() {
        return new FichaClienteCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public SolicitudFilter getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(SolicitudFilter solicitud) {
        this.solicitud = solicitud;
    }

    public PrioridadFilter getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(PrioridadFilter prioridad) {
        this.prioridad = prioridad;
    }

    public StringFilter getComentario() {
        return comentario;
    }

    public void setComentario(StringFilter comentario) {
        this.comentario = comentario;
    }

    public LongFilter getClienteId() {
        return clienteId;
    }

    public void setClienteId(LongFilter clienteId) {
        this.clienteId = clienteId;
    }

    public LongFilter getProductoId() {
        return productoId;
    }

    public void setProductoId(LongFilter productoId) {
        this.productoId = productoId;
    }

    public LongFilter getAreaId() {
        return areaId;
    }

    public void setAreaId(LongFilter areaId) {
        this.areaId = areaId;
    }

    public LongFilter getCitaId() {
        return citaId;
    }

    public void setCitaId(LongFilter citaId) {
        this.citaId = citaId;
    }

    public LongFilter getTipoProductoId() {
        return tipoProductoId;
    }

    public void setTipoProductoId(LongFilter tipoProductoId) {
        this.tipoProductoId = tipoProductoId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final FichaClienteCriteria that = (FichaClienteCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(solicitud, that.solicitud) &&
            Objects.equals(prioridad, that.prioridad) &&
            Objects.equals(comentario, that.comentario) &&
            Objects.equals(clienteId, that.clienteId) &&
            Objects.equals(productoId, that.productoId) &&
            Objects.equals(areaId, that.areaId) &&
            Objects.equals(citaId, that.citaId) &&
            Objects.equals(tipoProductoId, that.tipoProductoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        solicitud,
        prioridad,
        comentario,
        clienteId,
        productoId,
        areaId,
        citaId,
        tipoProductoId
        );
    }

    @Override
    public String toString() {
        return "FichaClienteCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (solicitud != null ? "solicitud=" + solicitud + ", " : "") +
                (prioridad != null ? "prioridad=" + prioridad + ", " : "") +
                (comentario != null ? "comentario=" + comentario + ", " : "") +
                (clienteId != null ? "clienteId=" + clienteId + ", " : "") +
                (productoId != null ? "productoId=" + productoId + ", " : "") +
                (areaId != null ? "areaId=" + areaId + ", " : "") +
                (citaId != null ? "citaId=" + citaId + ", " : "") +
                (tipoProductoId != null ? "tipoProductoId=" + tipoProductoId + ", " : "") +
            "}";
    }

}
