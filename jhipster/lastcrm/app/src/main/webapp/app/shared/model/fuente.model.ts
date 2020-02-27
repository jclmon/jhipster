export interface IFuente {
  id?: number;
  nombre?: string;
}

export class Fuente implements IFuente {
  constructor(public id?: number, public nombre?: string) {}
}
