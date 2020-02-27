import { Moment } from 'moment';
import { Estado } from 'app/shared/model/enumerations/estado.model';

export interface ICita {
  id?: number;
  fecha?: Moment;
  comentario?: string;
  estado?: Estado;
  agenteNombre?: string;
  agenteId?: number;
}

export class Cita implements ICita {
  constructor(
    public id?: number,
    public fecha?: Moment,
    public comentario?: string,
    public estado?: Estado,
    public agenteNombre?: string,
    public agenteId?: number
  ) {}
}
