export interface ReglaPassword {
  id: string;
  texto: string;
  cumple: (p: string) => boolean;
}
