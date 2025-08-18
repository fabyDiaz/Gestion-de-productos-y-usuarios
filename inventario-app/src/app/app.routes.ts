import { Routes } from '@angular/router';
import { ProductoLista } from './producto-lista/producto-lista';
import { AgregarProducto } from './agregar-producto/agregar-producto';
import { AgregarUsuario } from './agregar-usuario/agregar-usuario';
import { IniciarSesion } from './iniciar-sesion/iniciar-sesion';
import { authGuard } from './auth-guard';
import { EditarProducto } from './editar-producto/editar-producto';

//http://localhost:4200/productos
export const routes: Routes = [
    { path: '', redirectTo: 'productos', pathMatch: 'full' },
    {path:'productos', component: ProductoLista, canActivate: [authGuard]},
    {path: 'agregar-producto', component: AgregarProducto, canActivate: [authGuard]},
    {path: 'agregar-usuario', component: AgregarUsuario },
    {path: 'iniciar-sesion', component: IniciarSesion},
    {path: 'editar-producto/:id', component: EditarProducto,  canActivate: [authGuard]},
    { path: '**', redirectTo: 'productos' }
];
