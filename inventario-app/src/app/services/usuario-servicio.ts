import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Usuario } from '../modelo/usuario';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UsuarioServicio {

  private urlBase = "http://localhost:8080/api/v1/auth";
  private clienteHttp = inject(HttpClient);
  
  constructor() { }

  agregarUsuario(usuario: Usuario):Observable<Object>{
      //return this.clienteHttp.post(this.urlBase, usuario);
      return this.clienteHttp.post(`${this.urlBase}/register`, usuario);
  }

  login(email: string, contrasena: string): Observable<any> {
    const body = { email, contrasena };
    return this.clienteHttp.post<{ token: string }>(`${this.urlBase}/login`, body, {
      headers: { 'Content-Type': 'application/json' }
    });
  }

  guardarToken(token: string): void {
    localStorage.setItem('token', token);
  }

  obtenerToken(): string | null {
    return localStorage.getItem('token');
  }

  cerrarSesion(): void {
    localStorage.removeItem('token');
  }

  estaAutenticado(): boolean {
    return !!this.obtenerToken();
  }

}
