import { BaseEntity } from 'src/model/base-entity';

export class Municipio implements BaseEntity {
    constructor(
        public id?: number,
        public nombre?: string,
        public codPostal?: string,
        public provinciaNombre?: string,
        public provinciaId?: number,
    ) {
    }
}