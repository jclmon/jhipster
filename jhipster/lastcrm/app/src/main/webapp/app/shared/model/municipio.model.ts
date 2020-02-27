export interface IMunicipio {
  id?: number;
  nombre?: string;
  codPostal?: string;
  provinciaNombre?: string;
  provinciaId?: number;
}

export class Municipio implements IMunicipio {
  constructor(
    public id?: number,
    public nombre?: string,
    public codPostal?: string,
    public provinciaNombre?: string,
    public provinciaId?: number
  ) {}
}
