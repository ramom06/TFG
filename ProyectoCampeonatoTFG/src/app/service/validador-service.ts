import { Injectable, signal, computed } from '@angular/core';

export interface ReglaPassword {
  id: string;
  texto: string;
  cumple: (p: string) => boolean;
}

@Injectable({
  providedIn: 'root'
})
export class ValidadorService {
  private readonly LETRAS_DNI = 'TRWAGMYFPDXBNJZSQVHLCKE';

  readonly REGLAS_PASSWORD: ReglaPassword[] = [
    { id: 'len',   texto: 'Mínimo 8 caracteres',           cumple: p => p.length >= 8 },
    { id: 'upper', texto: 'Al menos una letra mayúscula',  cumple: p => /[A-Z]/.test(p) },
    { id: 'lower', texto: 'Al menos una letra minúscula',  cumple: p => /[a-z]/.test(p) },
    { id: 'digit', texto: 'Al menos un número',            cumple: p => /\d/.test(p) },
  ];

  validarDNI(dni: string): { valido: boolean; mensaje: string | null } {
    const limpio = dni.trim().toUpperCase();

    if (limpio.length !== 9) {
      return { valido: false, mensaje: 'Debe tener 9 caracteres (8 números + letra)' };
    }

    const numeros = limpio.slice(0, 8);
    const letra = limpio.slice(8);

    if (!/^\d{8}$/.test(numeros)) {
      return { valido: false, mensaje: 'Los primeros 8 caracteres deben ser números' };
    }

    const letraEsperada = this.LETRAS_DNI[parseInt(numeros, 10) % 23];
    if (letra !== letraEsperada) {
      return { valido: false, mensaje: `Letra incorrecta. Para ${numeros} corresponde la "${letraEsperada}"` };
    }

    return { valido: true, mensaje: null };
  }

  getReglasEstado(password: string) {
    return this.REGLAS_PASSWORD.map(r => ({
      ...r,
      ok: r.cumple(password)
    }));
  }

  isPasswordValida(password: string): boolean {
    return this.REGLAS_PASSWORD.every(r => r.cumple(password));
  }
}
