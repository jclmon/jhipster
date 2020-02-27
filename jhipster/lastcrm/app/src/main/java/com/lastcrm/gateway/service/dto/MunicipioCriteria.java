package com.lastcrm.gateway.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.lastcrm.gateway.domain.Municipio} entity. This class is used
 * in {@link com.lastcrm.gateway.web.rest.MunicipioResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /municipios?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class MunicipioCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nombre;

    private StringFilter codPostal;

    private LongFilter provinciaId;

    public MunicipioCriteria(){
    }

    public MunicipioCriteria(MunicipioCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.nombre = other.nombre == null ? null : other.nombre.copy();
        this.codPostal = other.codPostal == null ? null : other.codPostal.copy();
        this.provinciaId = other.provinciaId == null ? null : other.provinciaId.copy();
    }

    @Override
    public MunicipioCriteria copy() {
        return new MunicipioCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNombre() {
        return nombre;
    }

    public void setNombre(StringFilter nombre) {
        this.nombre = nombre;
    }

    public StringFilter getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(StringFilter codPostal) {
        this.codPostal = codPostal;
    }

    public LongFilter getProvinciaId() {
        return provinciaId;
    }

    public void setProvinciaId(LongFilter provinciaId) {
        this.provinciaId = provinciaId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final MunicipioCriteria that = (MunicipioCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nombre, that.nombre) &&
            Objects.equals(codPostal, that.codPostal) &&
            Objects.equals(provinciaId, that.provinciaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nombre,
        codPostal,
        provinciaId
        );
    }

    @Override
    public String toString() {
        return "MunicipioCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nombre != null ? "nombre=" + nombre + ", " : "") +
                (codPostal != null ? "codPostal=" + codPostal + ", " : "") +
                (provinciaId != null ? "provinciaId=" + provinciaId + ", " : "") +
            "}";
    }

}
