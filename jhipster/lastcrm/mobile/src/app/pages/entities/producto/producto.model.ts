import { BaseEntity } from 'src/model/base-entity';

export const enum Solicitud {
    'COMPRA',
    'VENTA',
    'ALQUILER',
    'COMPARTIR',
    'VACACIONAL'
}
export class Producto implements BaseEntity {
    constructor(
        public id?: number,
        public direccion?: string,
        public comentario?: any,
        public destino?: Solicitud,
        public precio?: number,
        public image1ContentType?: string,
        public image1?: any,
        public image2ContentType?: string,
        public image2?: any,
        public image3ContentType?: string,
        public image3?: any,
        public image4ContentType?: string,
        public image4?: any,
        public image5ContentType?: string,
        public image5?: any,
        public municipioNombre?: string,
        public municipioId?: number,
        public tipoProductoNombre?: string,
        public tipoProductoId?: number,
    ) {
    }
}