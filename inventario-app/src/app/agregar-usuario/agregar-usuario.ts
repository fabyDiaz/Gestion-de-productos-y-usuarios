import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Usuario } from '../modelo/usuario';
import { UsuarioServicio } from '../services/usuario-servicio';
import { Router } from '@angular/router';

@Component({
  selector: 'app-agregar-usuario',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './agregar-usuario.html',
  styleUrl: './agregar-usuario.css'
})
export class AgregarUsuario {
  usuario: Usuario = new Usuario();
  contrasena2: string = '';

  private usuarioServicio = inject(UsuarioServicio);
  private enrutador = inject(Router); //Lleva a la página de inicio

  contrasenasNoCoinciden: boolean = false;
  registroExitoso: boolean = false;

onSubmit() {
  if (this.usuario.contrasena !== this.contrasena2) {
    this.contrasenasNoCoinciden = true;
    return;
  }

  this.contrasenasNoCoinciden = false;
  this.guardarUsuario();
}

  guardarUsuario(){
    this.usuarioServicio.agregarUsuario(this.usuario).subscribe({
      next: (datos) => {
        console.log("usuario registrado", datos);
        this.registroExitoso = true;
        // limpiar campos si quieres
        this.usuario = new Usuario();
        this.contrasena2 = '';
        console.log("usuario registrado "+ this.usuario.email) 
      },
      error: (error: any) => {console.log(error)}
    })
  }
}
