import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AutenticacionService} from '../service/autenticacion-service';

/**
 * Guard que protege /admin/campeonatos.
 * Si no hay sesión de admin redirige a /admin/login.
 */
export const guards: CanActivateFn = () => {
  const auth   = inject(AutenticacionService);
  const router = inject(Router);

  if (auth.isAdmin()) return true;

  router.navigate(['/admin/login']);
  return false;
};
