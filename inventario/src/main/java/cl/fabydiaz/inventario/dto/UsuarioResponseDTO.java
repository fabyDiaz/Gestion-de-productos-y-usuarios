package cl.fabydiaz.inventario.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioResponseDTO {
    private Integer idUsuario;
    private String nombre;
    private String apellido;
    private String rut;
    private String email;
    private boolean active;
}
