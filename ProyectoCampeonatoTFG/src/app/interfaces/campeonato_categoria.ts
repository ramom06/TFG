import {Modalidad} from './categoria';

export interface Campeonato_categoria {
    id_campeonato: number,
    id_categoria:number,
    nombreCampeonato: string,
    nombreCategoria: string,
    fechaInicioCampeonato:Date,
    fechaFinCampeonato: Date,
    modalidad: Modalidad,
    genero: Genero
}

export type Genero = 'M' | 'F';

