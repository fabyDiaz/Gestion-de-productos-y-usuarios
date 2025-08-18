import { Component, inject } from '@angular/core';
import { Producto } from '../modelo/producto';
import { ProductoServicio } from '../services/producto-servicio';
import { Router } from '@angular/router';

@Component({
  selector: 'app-producto-lista',
  imports: [],
  templateUrl: './producto-lista.html',
})
export class ProductoLista {
  productos: Producto[];

  private productoServicio = inject(ProductoServicio);
  private enrutador = inject(Router)


  ngOnInit(){
    //cargar los productos 
    this.obtenerProductos();
  }
  private obtenerProductos(): void{
    this.productoServicio.obtenerMisProductos().subscribe(
      {
        next: (datos) => {
          this.productos = datos;
        },
        error: (error) =>{
          console.error("Error al obtener los prodcutos ",error);
        }
      }
    );
  }

  editarProducto(id: number){
    this.enrutador.navigate(['editar-producto', id]);
  }

  eliminarProducto(id:number){
    this.productoServicio.eliminarProducto(id).subscribe({
      next: (datos) => this.obtenerProductos(),
      error: (errores) => console.log(errores)
    });
  }
}
