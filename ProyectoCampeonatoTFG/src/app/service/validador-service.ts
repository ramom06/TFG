import { Injectable, signal, computed } from '@angular/core';
import {ReglaPassword} from '../interfaces/regla-password';

@Injectable({
  providedIn: 'root'
})

//Esta clase comprueba DNI y Contraseña del user
export class ValidadorService {
  private readonly LETRAS_DNI = 'TRWAGMYFPDXBNJZSQVHLCKE';

  readonly REGLAS_PASSWORD: ReglaPassword[] = [
    { id: 'Longitud',   texto: 'Mínimo 8 caracteres',cumple: p => p.length >= 8 },
    { id: 'Mayúscula', texto: 'Al menos una letra mayúscula',cumple: p => /[A-Z]/.test(p) },
    { id: 'Minúscula', texto: 'Al menos una letra minúscula',cumple: p => /[a-z]/.test(p) },
    { id: 'Número', texto: 'Al menos un número',cumple: p => /\d/.test(p) },
  ];

  validarDNI(dni: string): { valido: boolean; mensaje: string | null } {
    const limpio = dni.trim().toUpperCase();

    if (limpio.length !== 9) {
      return { valido: false, mensaje: 'Debe tener 9 caracteres (8 números + letra)' };
    }

    //Separa letras numeros
    const numeros = limpio.slice(0, 8);
    const letra = limpio.slice(8);

    if (!/^\d{8}$/.test(numeros)) {
      return { valido: false, mensaje: 'Los primeros 8 caracteres deben ser números' };
    }

    //Divide entre 23 y toma la letra
    const letraEsperada = this.LETRAS_DNI[parseInt(numeros, 10) % 23];
    if (letra !== letraEsperada) {
      return { valido: false, mensaje: `Letra incorrecta. Para ${numeros} corresponde la "${letraEsperada}"` };
    }

    return { valido: true, mensaje: null };
  }

  getReglasEstado(password: string) {
    const resultado = [];

    for (const regla of this.REGLAS_PASSWORD) {
      resultado.push({texto: regla.texto, ok: regla.cumple(password)});
    }

    return resultado;
  }

  isPasswordValida(password: string): boolean {return this.REGLAS_PASSWORD.every(r => r.cumple(password));}
}
