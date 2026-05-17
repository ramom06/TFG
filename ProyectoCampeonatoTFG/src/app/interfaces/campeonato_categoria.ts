import {Modalidad} from './categoria';

export type Genero = 'M' | 'F';

export interface Campeonato_categoria {
    idCampeonato: number,
    idCategoria: number,
    nombreCampeonato: string,
    nombreCategoria: string,
    fechaInicioCampeonato: Date,
    fechaFinCampeonato: Date,
    modalidad: Modalidad,
    genero: Genero
}

