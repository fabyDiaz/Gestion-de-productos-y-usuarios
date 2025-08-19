package cl.fabydiaz.inventario.servicio;

import cl.fabydiaz.inventario.dto.ProductoDTO;
import cl.fabydiaz.inventario.excepcion.RecursoNoEncontradoExcepcion;
import cl.fabydiaz.inventario.modelo.Producto;
import cl.fabydiaz.inventario.modelo.Usuario;
import cl.fabydiaz.inventario.repositorio.ProductoRespositorio;
import cl.fabydiaz.inventario.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoServicio implements IProductoServicio{

    @Autowired
    ProductoRespositorio productoRepositorio;
    @Autowired
    UsuarioRepositorio usuarioRepositorio;

    @Override
    public List<Producto> listarProductos() {
        return this.productoRepositorio.findAll();
    }

    public List<ProductoDTO> obtenerProductosDelUsuarioAutenticado(Authentication authentication) {
        String username = authentication.getName(); // nombre desde el token
        Usuario usuario = usuarioRepositorio.findByEmailAndActiveTrue(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        //List<Producto> productos = productoRepositorio.findByUsuarioIdUsuario(usuario.getIdUsuario());
        List<Producto> productos = productoRepositorio.findByUsuarioIdUsuarioAndActiveTrue(usuario.getIdUsuario());

        return productos.stream().map(producto ->
                productobuild(producto)).collect(Collectors.toList());
    }


    @Override
    public ProductoDTO buscarProductoPorId(Integer idProducto) {
        Producto productoEncontrado = this.productoRepositorio.findById(idProducto).
                orElseThrow(() -> new RecursoNoEncontradoExcepcion("Producto no encontrado con ID: " + idProducto));

        return productobuild(productoEncontrado);
    }

    @Override
    public Producto guardarProducto(Producto producto) {
        return this.productoRepositorio.save(producto);
    }
    @Override
    public ProductoDTO guardarProductoParaUsuarioAutenticado(Producto producto, Authentication authentication) {
        // 1. Obtener nombre de usuario desde el token
        String username = authentication.getName();

        // 2. Buscar el usuario en la base de datos
        Usuario usuario = usuarioRepositorio.findByEmailAndActiveTrue(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        // 3. Asociar el producto con el usuario
        producto.setUsuario(usuario);
        producto.setActive(true);

        // 4. Guardar producto
        Producto productoGuardado =  productoRepositorio.save(producto);

        return productobuild(productoGuardado);
    }


    @Override
    public void eliminarProductPorId(Integer idProducto, Authentication authentication) {
        String nombreUsuario = authentication.getName();

        Usuario usuario = usuarioRepositorio.findByEmailAndActiveTrue(nombreUsuario)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        Producto producto = productoRepositorio.findById(idProducto)
                .orElseThrow(() -> new RecursoNoEncontradoExcepcion("Producto no encontrado con ID: " + idProducto));

        // Validar que el producto pertenezca al usuario autenticado
        if (!producto.getUsuario().getIdUsuario().equals(usuario.getIdUsuario())) {
            throw new AccessDeniedException("No tienes permiso para eliminar este producto");
        }

        //this.productoRepositorio.deleteById(producto.getIdProducto());
        producto.setActive(false);
        productoRepositorio.save(producto);
    }

    @Override
    public Producto actualizarProducto(Integer idProducto, Producto productoRecibido){
        Producto producto = this.productoRepositorio.findById(idProducto).
                orElseThrow(() -> new RecursoNoEncontradoExcepcion("Producto no encontrado con ID: " + idProducto));
        producto.setNombre((productoRecibido.getNombre()));
        producto.setDescripcion(productoRecibido.getDescripcion());
        producto.setImagen(productoRecibido.getImagen());
        producto.setStock(productoRecibido.getStock());
        producto.setPrecio(productoRecibido.getPrecio());
        producto.setActive(productoRecibido.isActive());

        return producto;
    }

    private ProductoDTO productobuild(Producto producto){
        return ProductoDTO.builder()
                .IdProducto(producto.getIdProducto())
                .nombre(producto.getNombre())
                .descripcion(producto.getDescripcion())
                .imagen(producto.getImagen())
                .stock(producto.getStock())
                .precio(producto.getPrecio())
                .active(producto.isActive())
                .usuarioId(producto.getUsuario().getIdUsuario())
                .build();
    }
}
