package cl.fabydiaz.inventario.dto;

import lombok.Data;

@Data
public class UsuarioRequestDTO {
    private String email;
    private String contrasena;
}
