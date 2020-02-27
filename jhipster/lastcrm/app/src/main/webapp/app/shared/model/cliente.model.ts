import { Genero } from 'app/shared/model/enumerations/genero.model';

export interface ICliente {
  id?: number;
  nombre?: string;
  apellido1?: string;
  apellido2?: string;
  nif?: string;
  genero?: Genero;
  tlfMovil?: string;
  tlfMovil2?: string;
  tlfFijo?: string;
  fax?: string;
  email1?: string;
  email2?: string;
  fuenteNombre?: string;
  fuenteId?: number;
}

export class Cliente implements ICliente {
  constructor(
    public id?: number,
    public nombre?: string,
    public apellido1?: string,
    public apellido2?: string,
    public nif?: string,
    public genero?: Genero,
    public tlfMovil?: string,
    public tlfMovil2?: string,
    public tlfFijo?: string,
    public fax?: string,
    public email1?: string,
    public email2?: string,
    public fuenteNombre?: string,
    public fuenteId?: number
  ) {}
}
