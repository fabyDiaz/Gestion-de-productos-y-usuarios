package cl.fabydiaz.inventario.servicio;

import cl.fabydiaz.inventario.dto.UsuarioResponseDTO;
import cl.fabydiaz.inventario.modelo.Usuario;
import cl.fabydiaz.inventario.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServicio implements IUsuarioServicio{
    @Autowired
    UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UsuarioResponseDTO guardarUsuario(Usuario usuario) {
        // Verificar si el email ya existe
        if (usuarioRepositorio.findByEmail(usuario.getEmail()).isPresent()) {
            throw new IllegalArgumentException("El email ya está registrado");
        }

        String contrasenaEncriptada = passwordEncoder.encode(usuario.getContrasena());
        usuario.setContrasena(contrasenaEncriptada);
        usuario.setActive(true);
        Usuario usuarioGuardado =  usuarioRepositorio.save(usuario);

        return UsuarioResponseDTO.builder()
                .idUsuario(usuarioGuardado.getIdUsuario())
                .nombre(usuarioGuardado.getNombre())
                .apellido(usuarioGuardado.getApellido())
                .rut(usuarioGuardado.getRut())
                .email(usuarioGuardado.getEmail())
                .active(usuarioGuardado.isActive())
                .build();
    }
}
