import { CanActivateFn } from '@angular/router';
import { inject } from '@angular/core';
import { UsuarioServicio } from './services/usuario-servicio';
import { Router } from '@angular/router';

export const authGuard: CanActivateFn = () => {
  const usuarioServicio = inject(UsuarioServicio);
  const router = inject(Router);

  if (usuarioServicio.estaAutenticado()) {
    return true;
  } else {
    router.navigate(['/iniciar-sesion']);
    return false;
  }
};
