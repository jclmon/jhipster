export interface IArea {
  id?: number;
  nombre?: string;
  municipioNombre?: string;
  municipioId?: number;
}

export class Area implements IArea {
  constructor(public id?: number, public nombre?: string, public municipioNombre?: string, public municipioId?: number) {}
}
