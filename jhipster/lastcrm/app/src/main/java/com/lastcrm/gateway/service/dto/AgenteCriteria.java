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
 * Criteria class for the {@link com.lastcrm.gateway.domain.Agente} entity. This class is used
 * in {@link com.lastcrm.gateway.web.rest.AgenteResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /agentes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AgenteCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nombre;

    private StringFilter apellido1;

    private StringFilter apellido2;

    private StringFilter telefono;

    public AgenteCriteria(){
    }

    public AgenteCriteria(AgenteCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.nombre = other.nombre == null ? null : other.nombre.copy();
        this.apellido1 = other.apellido1 == null ? null : other.apellido1.copy();
        this.apellido2 = other.apellido2 == null ? null : other.apellido2.copy();
        this.telefono = other.telefono == null ? null : other.telefono.copy();
    }

    @Override
    public AgenteCriteria copy() {
        return new AgenteCriteria(this);
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

    public StringFilter getApellido1() {
        return apellido1;
    }

    public void setApellido1(StringFilter apellido1) {
        this.apellido1 = apellido1;
    }

    public StringFilter getApellido2() {
        return apellido2;
    }

    public void setApellido2(StringFilter apellido2) {
        this.apellido2 = apellido2;
    }

    public StringFilter getTelefono() {
        return telefono;
    }

    public void setTelefono(StringFilter telefono) {
        this.telefono = telefono;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AgenteCriteria that = (AgenteCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nombre, that.nombre) &&
            Objects.equals(apellido1, that.apellido1) &&
            Objects.equals(apellido2, that.apellido2) &&
            Objects.equals(telefono, that.telefono);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nombre,
        apellido1,
        apellido2,
        telefono
        );
    }

    @Override
    public String toString() {
        return "AgenteCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nombre != null ? "nombre=" + nombre + ", " : "") +
                (apellido1 != null ? "apellido1=" + apellido1 + ", " : "") +
                (apellido2 != null ? "apellido2=" + apellido2 + ", " : "") +
                (telefono != null ? "telefono=" + telefono + ", " : "") +
            "}";
    }

}
