package cl.fabydiaz.inventario.modelo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer IdProducto;
    private String nombre;
    private String descripcion;
    private String imagen;
    private Integer stock;
    private Double precio;
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "usuario_id") // nombre de la columna FK
    private Usuario usuario;
}
