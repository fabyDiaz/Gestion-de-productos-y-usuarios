import { Component, inject } from '@angular/core';
import { RouterModule,  Router} from '@angular/router';
import { UsuarioServicio } from './services/usuario-servicio';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App {
  protected title = 'inventario-app';
   usuarioServicio = inject(UsuarioServicio);
    router = inject(Router);

    get autenticado(): boolean {
    return this.usuarioServicio.estaAutenticado();
  }

  cerrarSesion(): void {
    this.usuarioServicio.cerrarSesion();
    this.router.navigate(['/iniciar-sesion']); // o donde quieras redirigir
  }
}
