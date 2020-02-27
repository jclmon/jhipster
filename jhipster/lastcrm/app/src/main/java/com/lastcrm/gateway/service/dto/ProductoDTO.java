package com.lastcrm.gateway.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import com.lastcrm.gateway.domain.enumeration.Solicitud;

/**
 * A DTO for the {@link com.lastcrm.gateway.domain.Producto} entity.
 */
public class ProductoDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 500)
    private String direccion;

    @Lob
    private String comentario;

    @NotNull
    private Solicitud destino;

    private Long precio;

    @Lob
    private byte[] image1;

    private String image1ContentType;
    @Lob
    private byte[] image2;

    private String image2ContentType;
    @Lob
    private byte[] image3;

    private String image3ContentType;
    @Lob
    private byte[] image4;

    private String image4ContentType;
    @Lob
    private byte[] image5;

    private String image5ContentType;
    private Long precioAnterior;

    private Integer dormitorios;

    private Integer aseos;

    private Long metros;

    private Integer garage;

    private Integer anioconstruccion;


    private Long municipioId;

    private String municipioNombre;

    private Long tipoProductoId;

    private String tipoProductoNombre;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Solicitud getDestino() {
        return destino;
    }

    public void setDestino(Solicitud destino) {
        this.destino = destino;
    }

    public Long getPrecio() {
        return precio;
    }

    public void setPrecio(Long precio) {
        this.precio = precio;
    }

    public byte[] getImage1() {
        return image1;
    }

    public void setImage1(byte[] image1) {
        this.image1 = image1;
    }

    public String getImage1ContentType() {
        return image1ContentType;
    }

    public void setImage1ContentType(String image1ContentType) {
        this.image1ContentType = image1ContentType;
    }

    public byte[] getImage2() {
        return image2;
    }

    public void setImage2(byte[] image2) {
        this.image2 = image2;
    }

    public String getImage2ContentType() {
        return image2ContentType;
    }

    public void setImage2ContentType(String image2ContentType) {
        this.image2ContentType = image2ContentType;
    }

    public byte[] getImage3() {
        return image3;
    }

    public void setImage3(byte[] image3) {
        this.image3 = image3;
    }

    public String getImage3ContentType() {
        return image3ContentType;
    }

    public void setImage3ContentType(String image3ContentType) {
        this.image3ContentType = image3ContentType;
    }

    public byte[] getImage4() {
        return image4;
    }

    public void setImage4(byte[] image4) {
        this.image4 = image4;
    }

    public String getImage4ContentType() {
        return image4ContentType;
    }

    public void setImage4ContentType(String image4ContentType) {
        this.image4ContentType = image4ContentType;
    }

    public byte[] getImage5() {
        return image5;
    }

    public void setImage5(byte[] image5) {
        this.image5 = image5;
    }

    public String getImage5ContentType() {
        return image5ContentType;
    }

    public void setImage5ContentType(String image5ContentType) {
        this.image5ContentType = image5ContentType;
    }

    public Long getPrecioAnterior() {
        return precioAnterior;
    }

    public void setPrecioAnterior(Long precioAnterior) {
        this.precioAnterior = precioAnterior;
    }

    public Integer getDormitorios() {
        return dormitorios;
    }

    public void setDormitorios(Integer dormitorios) {
        this.dormitorios = dormitorios;
    }

    public Integer getAseos() {
        return aseos;
    }

    public void setAseos(Integer aseos) {
        this.aseos = aseos;
    }

    public Long getMetros() {
        return metros;
    }

    public void setMetros(Long metros) {
        this.metros = metros;
    }

    public Integer getGarage() {
        return garage;
    }

    public void setGarage(Integer garage) {
        this.garage = garage;
    }

    public Integer getAnioconstruccion() {
        return anioconstruccion;
    }

    public void setAnioconstruccion(Integer anioconstruccion) {
        this.anioconstruccion = anioconstruccion;
    }

    public Long getMunicipioId() {
        return municipioId;
    }

    public void setMunicipioId(Long municipioId) {
        this.municipioId = municipioId;
    }

    public String getMunicipioNombre() {
        return municipioNombre;
    }

    public void setMunicipioNombre(String municipioNombre) {
        this.municipioNombre = municipioNombre;
    }

    public Long getTipoProductoId() {
        return tipoProductoId;
    }

    public void setTipoProductoId(Long tipoProductoId) {
        this.tipoProductoId = tipoProductoId;
    }

    public String getTipoProductoNombre() {
        return tipoProductoNombre;
    }

    public void setTipoProductoNombre(String tipoProductoNombre) {
        this.tipoProductoNombre = tipoProductoNombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductoDTO productoDTO = (ProductoDTO) o;
        if (productoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), productoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ProductoDTO{" +
            "id=" + getId() +
            ", direccion='" + getDireccion() + "'" +
            ", comentario='" + getComentario() + "'" +
            ", destino='" + getDestino() + "'" +
            ", precio=" + getPrecio() +
            ", image1='" + getImage1() + "'" +
            ", image2='" + getImage2() + "'" +
            ", image3='" + getImage3() + "'" +
            ", image4='" + getImage4() + "'" +
            ", image5='" + getImage5() + "'" +
            ", precioAnterior=" + getPrecioAnterior() +
            ", dormitorios=" + getDormitorios() +
            ", aseos=" + getAseos() +
            ", metros=" + getMetros() +
            ", garage=" + getGarage() +
            ", anioconstruccion=" + getAnioconstruccion() +
            ", municipio=" + getMunicipioId() +
            ", municipio='" + getMunicipioNombre() + "'" +
            ", tipoProducto=" + getTipoProductoId() +
            ", tipoProducto='" + getTipoProductoNombre() + "'" +
            "}";
    }
}
