package com.lastcrm.gateway.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.lastcrm.gateway.domain.enumeration.Estado;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the {@link com.lastcrm.gateway.domain.Cita} entity. This class is used
 * in {@link com.lastcrm.gateway.web.rest.CitaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /citas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CitaCriteria implements Serializable, Criteria {
    /**
     * Class for filtering Estado
     */
    public static class EstadoFilter extends Filter<Estado> {

        public EstadoFilter() {
        }

        public EstadoFilter(EstadoFilter filter) {
            super(filter);
        }

        @Override
        public EstadoFilter copy() {
            return new EstadoFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private InstantFilter fecha;

    private StringFilter comentario;

    private EstadoFilter estado;

    private LongFilter agenteId;

    public CitaCriteria(){
    }

    public CitaCriteria(CitaCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.fecha = other.fecha == null ? null : other.fecha.copy();
        this.comentario = other.comentario == null ? null : other.comentario.copy();
        this.estado = other.estado == null ? null : other.estado.copy();
        this.agenteId = other.agenteId == null ? null : other.agenteId.copy();
    }

    @Override
    public CitaCriteria copy() {
        return new CitaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public InstantFilter getFecha() {
        return fecha;
    }

    public void setFecha(InstantFilter fecha) {
        this.fecha = fecha;
    }

    public StringFilter getComentario() {
        return comentario;
    }

    public void setComentario(StringFilter comentario) {
        this.comentario = comentario;
    }

    public EstadoFilter getEstado() {
        return estado;
    }

    public void setEstado(EstadoFilter estado) {
        this.estado = estado;
    }

    public LongFilter getAgenteId() {
        return agenteId;
    }

    public void setAgenteId(LongFilter agenteId) {
        this.agenteId = agenteId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CitaCriteria that = (CitaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(fecha, that.fecha) &&
            Objects.equals(comentario, that.comentario) &&
            Objects.equals(estado, that.estado) &&
            Objects.equals(agenteId, that.agenteId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        fecha,
        comentario,
        estado,
        agenteId
        );
    }

    @Override
    public String toString() {
        return "CitaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (fecha != null ? "fecha=" + fecha + ", " : "") +
                (comentario != null ? "comentario=" + comentario + ", " : "") +
                (estado != null ? "estado=" + estado + ", " : "") +
                (agenteId != null ? "agenteId=" + agenteId + ", " : "") +
            "}";
    }

}
