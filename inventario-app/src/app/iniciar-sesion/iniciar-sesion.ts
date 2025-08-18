import { Component, inject } from '@angular/core';
import { UsuarioServicio } from '../services/usuario-servicio';
import { Usuario } from '../modelo/usuario';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-iniciar-sesion',
  imports: [FormsModule],
  standalone: true,
  templateUrl: './iniciar-sesion.html',
  styleUrl: './iniciar-sesion.css'
})
export class IniciarSesion {
  email = '';
  contrasena = '';
  error = '';

  private usuarioServicio = inject(UsuarioServicio);
  private router = inject(Router); //Lleva a la página de inicio

  constructor() {}

  login(): void{
    this.usuarioServicio.login(this.email, this.contrasena).subscribe({
      next: (response) => {
        this.usuarioServicio.guardarToken(response.token);
        this.router.navigate(['/productos']);
      },
      error:() =>{
        this.error = 'Credenciales inválidas';
      }
    });
  }
}
