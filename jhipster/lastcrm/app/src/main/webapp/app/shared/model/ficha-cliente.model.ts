import { IArea } from 'app/shared/model/area.model';
import { ICita } from 'app/shared/model/cita.model';
import { ITipoProducto } from 'app/shared/model/tipo-producto.model';
import { Solicitud } from 'app/shared/model/enumerations/solicitud.model';
import { Prioridad } from 'app/shared/model/enumerations/prioridad.model';

export interface IFichaCliente {
  id?: number;
  solicitud?: Solicitud;
  prioridad?: Prioridad;
  comentario?: string;
  clienteNombre?: string;
  clienteId?: number;
  productoDireccion?: string;
  productoId?: number;
  areas?: IArea[];
  citas?: ICita[];
  tipoProductos?: ITipoProducto[];
}

export class FichaCliente implements IFichaCliente {
  constructor(
    public id?: number,
    public solicitud?: Solicitud,
    public prioridad?: Prioridad,
    public comentario?: string,
    public clienteNombre?: string,
    public clienteId?: number,
    public productoDireccion?: string,
    public productoId?: number,
    public areas?: IArea[],
    public citas?: ICita[],
    public tipoProductos?: ITipoProducto[]
  ) {}
}
