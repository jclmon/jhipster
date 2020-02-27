import { Solicitud } from 'app/shared/model/enumerations/solicitud.model';

export interface IProducto {
  id?: number;
  direccion?: string;
  comentario?: any;
  destino?: Solicitud;
  precio?: number;
  image1ContentType?: string;
  image1?: any;
  image2ContentType?: string;
  image2?: any;
  image3ContentType?: string;
  image3?: any;
  image4ContentType?: string;
  image4?: any;
  image5ContentType?: string;
  image5?: any;
  precioAnterior?: number;
  dormitorios?: number;
  aseos?: number;
  metros?: number;
  garage?: number;
  anioconstruccion?: number;
  municipioNombre?: string;
  municipioId?: number;
  tipoProductoNombre?: string;
  tipoProductoId?: number;
}

export class Producto implements IProducto {
  constructor(
    public id?: number,
    public direccion?: string,
    public comentario?: any,
    public destino?: Solicitud,
    public precio?: number,
    public image1ContentType?: string,
    public image1?: any,
    public image2ContentType?: string,
    public image2?: any,
    public image3ContentType?: string,
    public image3?: any,
    public image4ContentType?: string,
    public image4?: any,
    public image5ContentType?: string,
    public image5?: any,
    public precioAnterior?: number,
    public dormitorios?: number,
    public aseos?: number,
    public metros?: number,
    public garage?: number,
    public anioconstruccion?: number,
    public municipioNombre?: string,
    public municipioId?: number,
    public tipoProductoNombre?: string,
    public tipoProductoId?: number
  ) {}
}
