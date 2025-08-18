package cl.fabydiaz.inventario.servicio;

import cl.fabydiaz.inventario.dto.UsuarioResponseDTO;
import cl.fabydiaz.inventario.modelo.Usuario;

public interface IUsuarioServicio {

    UsuarioResponseDTO guardarUsuario(Usuario usuario);
}
