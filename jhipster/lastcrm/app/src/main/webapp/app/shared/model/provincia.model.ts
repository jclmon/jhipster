export interface IProvincia {
  id?: number;
  nombre?: string;
  paisNombre?: string;
  paisId?: number;
}

export class Provincia implements IProvincia {
  constructor(public id?: number, public nombre?: string, public paisNombre?: string, public paisId?: number) {}
}
