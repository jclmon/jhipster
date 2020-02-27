import { BaseEntity } from 'src/model/base-entity';

export class TipoProducto implements BaseEntity {
    constructor(
        public id?: number,
        public nombre?: string,
    ) {
    }
}