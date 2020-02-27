package com.lastcrm.gateway.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.lastcrm.gateway.domain.enumeration.Genero;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.lastcrm.gateway.domain.Cliente} entity. This class is used
 * in {@link com.lastcrm.gateway.web.rest.ClienteResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /clientes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ClienteCriteria implements Serializable, Criteria {
    /**
     * Class for filtering Genero
     */
    public static class GeneroFilter extends Filter<Genero> {

        public GeneroFilter() {
        }

        public GeneroFilter(GeneroFilter filter) {
            super(filter);
        }

        @Override
        public GeneroFilter copy() {
            return new GeneroFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nombre;

    private StringFilter apellido1;

    private StringFilter apellido2;

    private StringFilter nif;

    private GeneroFilter genero;

    private StringFilter tlfMovil;

    private StringFilter tlfMovil2;

    private StringFilter tlfFijo;

    private StringFilter fax;

    private StringFilter email1;

    private StringFilter email2;

    private LongFilter fuenteId;

    public ClienteCriteria(){
    }

    public ClienteCriteria(ClienteCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.nombre = other.nombre == null ? null : other.nombre.copy();
        this.apellido1 = other.apellido1 == null ? null : other.apellido1.copy();
        this.apellido2 = other.apellido2 == null ? null : other.apellido2.copy();
        this.nif = other.nif == null ? null : other.nif.copy();
        this.genero = other.genero == null ? null : other.genero.copy();
        this.tlfMovil = other.tlfMovil == null ? null : other.tlfMovil.copy();
        this.tlfMovil2 = other.tlfMovil2 == null ? null : other.tlfMovil2.copy();
        this.tlfFijo = other.tlfFijo == null ? null : other.tlfFijo.copy();
        this.fax = other.fax == null ? null : other.fax.copy();
        this.email1 = other.email1 == null ? null : other.email1.copy();
        this.email2 = other.email2 == null ? null : other.email2.copy();
        this.fuenteId = other.fuenteId == null ? null : other.fuenteId.copy();
    }

    @Override
    public ClienteCriteria copy() {
        return new ClienteCriteria(this);
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

    public StringFilter getNif() {
        return nif;
    }

    public void setNif(StringFilter nif) {
        this.nif = nif;
    }

    public GeneroFilter getGenero() {
        return genero;
    }

    public void setGenero(GeneroFilter genero) {
        this.genero = genero;
    }

    public StringFilter getTlfMovil() {
        return tlfMovil;
    }

    public void setTlfMovil(StringFilter tlfMovil) {
        this.tlfMovil = tlfMovil;
    }

    public StringFilter getTlfMovil2() {
        return tlfMovil2;
    }

    public void setTlfMovil2(StringFilter tlfMovil2) {
        this.tlfMovil2 = tlfMovil2;
    }

    public StringFilter getTlfFijo() {
        return tlfFijo;
    }

    public void setTlfFijo(StringFilter tlfFijo) {
        this.tlfFijo = tlfFijo;
    }

    public StringFilter getFax() {
        return fax;
    }

    public void setFax(StringFilter fax) {
        this.fax = fax;
    }

    public StringFilter getEmail1() {
        return email1;
    }

    public void setEmail1(StringFilter email1) {
        this.email1 = email1;
    }

    public StringFilter getEmail2() {
        return email2;
    }

    public void setEmail2(StringFilter email2) {
        this.email2 = email2;
    }

    public LongFilter getFuenteId() {
        return fuenteId;
    }

    public void setFuenteId(LongFilter fuenteId) {
        this.fuenteId = fuenteId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ClienteCriteria that = (ClienteCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nombre, that.nombre) &&
            Objects.equals(apellido1, that.apellido1) &&
            Objects.equals(apellido2, that.apellido2) &&
            Objects.equals(nif, that.nif) &&
            Objects.equals(genero, that.genero) &&
            Objects.equals(tlfMovil, that.tlfMovil) &&
            Objects.equals(tlfMovil2, that.tlfMovil2) &&
            Objects.equals(tlfFijo, that.tlfFijo) &&
            Objects.equals(fax, that.fax) &&
            Objects.equals(email1, that.email1) &&
            Objects.equals(email2, that.email2) &&
            Objects.equals(fuenteId, that.fuenteId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nombre,
        apellido1,
        apellido2,
        nif,
        genero,
        tlfMovil,
        tlfMovil2,
        tlfFijo,
        fax,
        email1,
        email2,
        fuenteId
        );
    }

    @Override
    public String toString() {
        return "ClienteCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nombre != null ? "nombre=" + nombre + ", " : "") +
                (apellido1 != null ? "apellido1=" + apellido1 + ", " : "") +
                (apellido2 != null ? "apellido2=" + apellido2 + ", " : "") +
                (nif != null ? "nif=" + nif + ", " : "") +
                (genero != null ? "genero=" + genero + ", " : "") +
                (tlfMovil != null ? "tlfMovil=" + tlfMovil + ", " : "") +
                (tlfMovil2 != null ? "tlfMovil2=" + tlfMovil2 + ", " : "") +
                (tlfFijo != null ? "tlfFijo=" + tlfFijo + ", " : "") +
                (fax != null ? "fax=" + fax + ", " : "") +
                (email1 != null ? "email1=" + email1 + ", " : "") +
                (email2 != null ? "email2=" + email2 + ", " : "") +
                (fuenteId != null ? "fuenteId=" + fuenteId + ", " : "") +
            "}";
    }

}
