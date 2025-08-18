import { Component, inject, OnInit } from '@angular/core';
import { Producto } from '../modelo/producto';
import { ProductoServicio } from '../services/producto-servicio';
import { ActivatedRoute, Router } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-editar-producto',
  imports: [FormsModule],
  templateUrl: './editar-producto.html',
  styleUrl: './editar-producto.css'
})
export class EditarProducto implements OnInit {
  producto: Producto = new Producto();
  id!: number;

  private productoServicio = inject(ProductoServicio);
  private ruta = inject(ActivatedRoute);
  private enrutador = inject(Router);

  ngOnInit() {
    this.id = this.ruta.snapshot.params['id'];
    this.cargarProducto();
  }

  cargarProducto() {
    this.productoServicio.obtenerProductoPorId(this.id).subscribe({
      next: (datos) => {
        this.producto = datos;
        // Asegurar que active sea boolean
        if (typeof this.producto.active === 'string') {
          this.producto.active = this.producto.active === 'true';
        }
        console.log('Producto cargado:', this.producto); // Para debug
      },
      error: (errores: any) => {
        console.error('Error al cargar producto:', errores);
      }
    });
  }

  onSubmit() {
    console.log('Enviando producto:', this.producto); // Para debug
    this.guardarProducto();
  }

  guardarProducto() {
    this.productoServicio.editarProducto(this.id, this.producto).subscribe({
      next: (datos) => {
        console.log('Producto actualizado exitosamente');
        this.irProductoLista();
      },
      error: (errores) => {
        console.error('Error al actualizar producto:', errores);
      }
    });
  }

  irProductoLista() {
    this.enrutador.navigate(['/productos']);
  }
}