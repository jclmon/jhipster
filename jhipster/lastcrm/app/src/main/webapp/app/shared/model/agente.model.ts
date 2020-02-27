export interface IAgente {
  id?: number;
  nombre?: string;
  apellido1?: string;
  apellido2?: string;
  telefono?: string;
}

export class Agente implements IAgente {
  constructor(public id?: number, public nombre?: string, public apellido1?: string, public apellido2?: string, public telefono?: string) {}
}
