package cl.fabydiaz.inventario.controlador;

import cl.fabydiaz.inventario.config.JwtUtil;
import cl.fabydiaz.inventario.dto.UsuarioResponseDTO;
import cl.fabydiaz.inventario.dto.UsuarioRequestDTO;
import cl.fabydiaz.inventario.modelo.Usuario;
import cl.fabydiaz.inventario.servicio.UsuarioDetailsService;
import cl.fabydiaz.inventario.servicio.UsuarioServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("api/v1/auth")
public class UsuarioController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioDetailsService userDetailsService;
    @Autowired
    private UsuarioServicio usuarioServicio;

    @Autowired
    private JwtUtil jwtUtil;

    private static final Logger logger = LoggerFactory.getLogger(ProductoControlador.class);

    @PostMapping("/register")
    public ResponseEntity<UsuarioResponseDTO> agregarUsuario(@RequestBody Usuario usuario){
        logger.info("Porducto a agregar: "+ usuario);
        UsuarioResponseDTO nuevo = usuarioServicio.guardarUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UsuarioRequestDTO request) {
        try {
            // 1. Autenticar credenciales
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getContrasena())
            );

            // 2. Cargar detalles del usuario
            UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());

            // 3. Generar el token
            String jwt = jwtUtil.generarToken(userDetails);

            // 4. Retornar el token al frontend
            return ResponseEntity.ok(Collections.singletonMap("token", jwt));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("error", "Credenciales inválidas"));
        }
    }



}
