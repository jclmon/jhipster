package com.lastcrm.gateway.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import com.lastcrm.gateway.domain.enumeration.Solicitud;

/**
 * A Producto.
 */
@Entity
@Table(name = "producto")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 500)
    @Column(name = "direccion", length = 500, nullable = false)
    private String direccion;

    @Lob
    @Column(name = "comentario")
    private String comentario;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "destino", nullable = false)
    private Solicitud destino;

    @Column(name = "precio")
    private Long precio;

    @Lob
    @Column(name = "image_1")
    private byte[] image1;

    @Column(name = "image_1_content_type")
    private String image1ContentType;

    @Lob
    @Column(name = "image_2")
    private byte[] image2;

    @Column(name = "image_2_content_type")
    private String image2ContentType;

    @Lob
    @Column(name = "image_3")
    private byte[] image3;

    @Column(name = "image_3_content_type")
    private String image3ContentType;

    @Lob
    @Column(name = "image_4")
    private byte[] image4;

    @Column(name = "image_4_content_type")
    private String image4ContentType;

    @Lob
    @Column(name = "image_5")
    private byte[] image5;

    @Column(name = "image_5_content_type")
    private String image5ContentType;

    @Column(name = "precio_anterior")
    private Long precioAnterior;

    @Column(name = "dormitorios")
    private Integer dormitorios;

    @Column(name = "aseos")
    private Integer aseos;

    @Column(name = "metros")
    private Long metros;

    @Column(name = "garage")
    private Integer garage;

    @Column(name = "anioconstruccion")
    private Integer anioconstruccion;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("productos")
    private Municipio municipio;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("productos")
    private TipoProducto tipoProducto;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public Producto direccion(String direccion) {
        this.direccion = direccion;
        return this;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getComentario() {
        return comentario;
    }

    public Producto comentario(String comentario) {
        this.comentario = comentario;
        return this;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Solicitud getDestino() {
        return destino;
    }

    public Producto destino(Solicitud destino) {
        this.destino = destino;
        return this;
    }

    public void setDestino(Solicitud destino) {
        this.destino = destino;
    }

    public Long getPrecio() {
        return precio;
    }

    public Producto precio(Long precio) {
        this.precio = precio;
        return this;
    }

    public void setPrecio(Long precio) {
        this.precio = precio;
    }

    public byte[] getImage1() {
        return image1;
    }

    public Producto image1(byte[] image1) {
        this.image1 = image1;
        return this;
    }

    public void setImage1(byte[] image1) {
        this.image1 = image1;
    }

    public String getImage1ContentType() {
        return image1ContentType;
    }

    public Producto image1ContentType(String image1ContentType) {
        this.image1ContentType = image1ContentType;
        return this;
    }

    public void setImage1ContentType(String image1ContentType) {
        this.image1ContentType = image1ContentType;
    }

    public byte[] getImage2() {
        return image2;
    }

    public Producto image2(byte[] image2) {
        this.image2 = image2;
        return this;
    }

    public void setImage2(byte[] image2) {
        this.image2 = image2;
    }

    public String getImage2ContentType() {
        return image2ContentType;
    }

    public Producto image2ContentType(String image2ContentType) {
        this.image2ContentType = image2ContentType;
        return this;
    }

    public void setImage2ContentType(String image2ContentType) {
        this.image2ContentType = image2ContentType;
    }

    public byte[] getImage3() {
        return image3;
    }

    public Producto image3(byte[] image3) {
        this.image3 = image3;
        return this;
    }

    public void setImage3(byte[] image3) {
        this.image3 = image3;
    }

    public String getImage3ContentType() {
        return image3ContentType;
    }

    public Producto image3ContentType(String image3ContentType) {
        this.image3ContentType = image3ContentType;
        return this;
    }

    public void setImage3ContentType(String image3ContentType) {
        this.image3ContentType = image3ContentType;
    }

    public byte[] getImage4() {
        return image4;
    }

    public Producto image4(byte[] image4) {
        this.image4 = image4;
        return this;
    }

    public void setImage4(byte[] image4) {
        this.image4 = image4;
    }

    public String getImage4ContentType() {
        return image4ContentType;
    }

    public Producto image4ContentType(String image4ContentType) {
        this.image4ContentType = image4ContentType;
        return this;
    }

    public void setImage4ContentType(String image4ContentType) {
        this.image4ContentType = image4ContentType;
    }

    public byte[] getImage5() {
        return image5;
    }

    public Producto image5(byte[] image5) {
        this.image5 = image5;
        return this;
    }

    public void setImage5(byte[] image5) {
        this.image5 = image5;
    }

    public String getImage5ContentType() {
        return image5ContentType;
    }

    public Producto image5ContentType(String image5ContentType) {
        this.image5ContentType = image5ContentType;
        return this;
    }

    public void setImage5ContentType(String image5ContentType) {
        this.image5ContentType = image5ContentType;
    }

    public Long getPrecioAnterior() {
        return precioAnterior;
    }

    public Producto precioAnterior(Long precioAnterior) {
        this.precioAnterior = precioAnterior;
        return this;
    }

    public void setPrecioAnterior(Long precioAnterior) {
        this.precioAnterior = precioAnterior;
    }

    public Integer getDormitorios() {
        return dormitorios;
    }

    public Producto dormitorios(Integer dormitorios) {
        this.dormitorios = dormitorios;
        return this;
    }

    public void setDormitorios(Integer dormitorios) {
        this.dormitorios = dormitorios;
    }

    public Integer getAseos() {
        return aseos;
    }

    public Producto aseos(Integer aseos) {
        this.aseos = aseos;
        return this;
    }

    public void setAseos(Integer aseos) {
        this.aseos = aseos;
    }

    public Long getMetros() {
        return metros;
    }

    public Producto metros(Long metros) {
        this.metros = metros;
        return this;
    }

    public void setMetros(Long metros) {
        this.metros = metros;
    }

    public Integer getGarage() {
        return garage;
    }

    public Producto garage(Integer garage) {
        this.garage = garage;
        return this;
    }

    public void setGarage(Integer garage) {
        this.garage = garage;
    }

    public Integer getAnioconstruccion() {
        return anioconstruccion;
    }

    public Producto anioconstruccion(Integer anioconstruccion) {
        this.anioconstruccion = anioconstruccion;
        return this;
    }

    public void setAnioconstruccion(Integer anioconstruccion) {
        this.anioconstruccion = anioconstruccion;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public Producto municipio(Municipio municipio) {
        this.municipio = municipio;
        return this;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public TipoProducto getTipoProducto() {
        return tipoProducto;
    }

    public Producto tipoProducto(TipoProducto tipoProducto) {
        this.tipoProducto = tipoProducto;
        return this;
    }

    public void setTipoProducto(TipoProducto tipoProducto) {
        this.tipoProducto = tipoProducto;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Producto)) {
            return false;
        }
        return id != null && id.equals(((Producto) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Producto{" +
            "id=" + getId() +
            ", direccion='" + getDireccion() + "'" +
            ", comentario='" + getComentario() + "'" +
            ", destino='" + getDestino() + "'" +
            ", precio=" + getPrecio() +
            ", image1='" + getImage1() + "'" +
            ", image1ContentType='" + getImage1ContentType() + "'" +
            ", image2='" + getImage2() + "'" +
            ", image2ContentType='" + getImage2ContentType() + "'" +
            ", image3='" + getImage3() + "'" +
            ", image3ContentType='" + getImage3ContentType() + "'" +
            ", image4='" + getImage4() + "'" +
            ", image4ContentType='" + getImage4ContentType() + "'" +
            ", image5='" + getImage5() + "'" +
            ", image5ContentType='" + getImage5ContentType() + "'" +
            ", precioAnterior=" + getPrecioAnterior() +
            ", dormitorios=" + getDormitorios() +
            ", aseos=" + getAseos() +
            ", metros=" + getMetros() +
            ", garage=" + getGarage() +
            ", anioconstruccion=" + getAnioconstruccion() +
            "}";
    }
}
