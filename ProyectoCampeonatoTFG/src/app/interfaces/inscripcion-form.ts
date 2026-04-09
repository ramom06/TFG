export interface InscripcionForm {
  idCampeonato: number;
  idCategoria: number;
  idCompetidor: number;
  consentimientoRGPD: boolean;
}

export interface CompetidorLogin {
  dni: string;
  password: string;
}

export interface CompetidorSesion {
  id: number;
  nombre: string;
  apellidos: string;
  email: string;
  rol: string;
}
