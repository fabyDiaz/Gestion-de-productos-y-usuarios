package cl.fabydiaz.inventario.dto;

import cl.fabydiaz.inventario.modelo.Usuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoDTO {
    private Integer IdProducto;
    private String nombre;
    private String descripcion;
    private String imagen;
    private Integer stock;
    private Double precio;
    private boolean active;
    private Integer usuarioId;
}
