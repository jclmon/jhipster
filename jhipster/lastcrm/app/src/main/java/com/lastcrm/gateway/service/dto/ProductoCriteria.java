package com.lastcrm.gateway.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import com.lastcrm.gateway.domain.enumeration.Solicitud;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.lastcrm.gateway.domain.Producto} entity. This class is used
 * in {@link com.lastcrm.gateway.web.rest.ProductoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /productos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ProductoCriteria implements Serializable, Criteria {
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

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter direccion;

    private SolicitudFilter destino;

    private LongFilter precio;

    private LongFilter precioAnterior;

    private IntegerFilter dormitorios;

    private IntegerFilter aseos;

    private LongFilter metros;

    private IntegerFilter garage;

    private IntegerFilter anioconstruccion;

    private LongFilter municipioId;

    private LongFilter tipoProductoId;

    public ProductoCriteria(){
    }

    public ProductoCriteria(ProductoCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.direccion = other.direccion == null ? null : other.direccion.copy();
        this.destino = other.destino == null ? null : other.destino.copy();
        this.precio = other.precio == null ? null : other.precio.copy();
        this.precioAnterior = other.precioAnterior == null ? null : other.precioAnterior.copy();
        this.dormitorios = other.dormitorios == null ? null : other.dormitorios.copy();
        this.aseos = other.aseos == null ? null : other.aseos.copy();
        this.metros = other.metros == null ? null : other.metros.copy();
        this.garage = other.garage == null ? null : other.garage.copy();
        this.anioconstruccion = other.anioconstruccion == null ? null : other.anioconstruccion.copy();
        this.municipioId = other.municipioId == null ? null : other.municipioId.copy();
        this.tipoProductoId = other.tipoProductoId == null ? null : other.tipoProductoId.copy();
    }

    @Override
    public ProductoCriteria copy() {
        return new ProductoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDireccion() {
        return direccion;
    }

    public void setDireccion(StringFilter direccion) {
        this.direccion = direccion;
    }

    public SolicitudFilter getDestino() {
        return destino;
    }

    public void setDestino(SolicitudFilter destino) {
        this.destino = destino;
    }

    public LongFilter getPrecio() {
        return precio;
    }

    public void setPrecio(LongFilter precio) {
        this.precio = precio;
    }

    public LongFilter getPrecioAnterior() {
        return precioAnterior;
    }

    public void setPrecioAnterior(LongFilter precioAnterior) {
        this.precioAnterior = precioAnterior;
    }

    public IntegerFilter getDormitorios() {
        return dormitorios;
    }

    public void setDormitorios(IntegerFilter dormitorios) {
        this.dormitorios = dormitorios;
    }

    public IntegerFilter getAseos() {
        return aseos;
    }

    public void setAseos(IntegerFilter aseos) {
        this.aseos = aseos;
    }

    public LongFilter getMetros() {
        return metros;
    }

    public void setMetros(LongFilter metros) {
        this.metros = metros;
    }

    public IntegerFilter getGarage() {
        return garage;
    }

    public void setGarage(IntegerFilter garage) {
        this.garage = garage;
    }

    public IntegerFilter getAnioconstruccion() {
        return anioconstruccion;
    }

    public void setAnioconstruccion(IntegerFilter anioconstruccion) {
        this.anioconstruccion = anioconstruccion;
    }

    public LongFilter getMunicipioId() {
        return municipioId;
    }

    public void setMunicipioId(LongFilter municipioId) {
        this.municipioId = municipioId;
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
        final ProductoCriteria that = (ProductoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(direccion, that.direccion) &&
            Objects.equals(destino, that.destino) &&
            Objects.equals(precio, that.precio) &&
            Objects.equals(precioAnterior, that.precioAnterior) &&
            Objects.equals(dormitorios, that.dormitorios) &&
            Objects.equals(aseos, that.aseos) &&
            Objects.equals(metros, that.metros) &&
            Objects.equals(garage, that.garage) &&
            Objects.equals(anioconstruccion, that.anioconstruccion) &&
            Objects.equals(municipioId, that.municipioId) &&
            Objects.equals(tipoProductoId, that.tipoProductoId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        direccion,
        destino,
        precio,
        precioAnterior,
        dormitorios,
        aseos,
        metros,
        garage,
        anioconstruccion,
        municipioId,
        tipoProductoId
        );
    }

    @Override
    public String toString() {
        return "ProductoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (direccion != null ? "direccion=" + direccion + ", " : "") +
                (destino != null ? "destino=" + destino + ", " : "") +
                (precio != null ? "precio=" + precio + ", " : "") +
                (precioAnterior != null ? "precioAnterior=" + precioAnterior + ", " : "") +
                (dormitorios != null ? "dormitorios=" + dormitorios + ", " : "") +
                (aseos != null ? "aseos=" + aseos + ", " : "") +
                (metros != null ? "metros=" + metros + ", " : "") +
                (garage != null ? "garage=" + garage + ", " : "") +
                (anioconstruccion != null ? "anioconstruccion=" + anioconstruccion + ", " : "") +
                (municipioId != null ? "municipioId=" + municipioId + ", " : "") +
                (tipoProductoId != null ? "tipoProductoId=" + tipoProductoId + ", " : "") +
            "}";
    }

}
