import { Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Producto } from '../modelo/producto';
import { ProductoServicio } from '../services/producto-servicio';
import { Router } from '@angular/router';

@Component({
  selector: 'app-agregar-producto',
  imports: [FormsModule],
  templateUrl: './agregar-producto.html',
  styleUrl: './agregar-producto.css'
})
export class AgregarProducto {
  producto: Producto = new Producto();

  private productoServicio = inject(ProductoServicio);
  private enrutador = inject(Router);
  

  onSubmit(){
    if (!this.producto.descripcion || this.producto.precio <= 0) {
      alert("Debes completar los campos correctamente");
      return;
    }
    this.guardarProducto();
  }

  guardarProducto(){
    this.productoServicio.agregarProducto(this.producto).subscribe({
      next:(datos) =>{
        this.irListaProductos();
      },
      error: (error: any)=> {console.log(error)}
    })
  }

  irListaProductos(){
    this.enrutador.navigate(['/productos']);
  }
}
