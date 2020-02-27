import { BaseEntity } from 'src/model/base-entity';

export class Provincia implements BaseEntity {
    constructor(
        public id?: number,
        public nombre?: string,
        public paisNombre?: string,
        public paisId?: number,
    ) {
    }
}