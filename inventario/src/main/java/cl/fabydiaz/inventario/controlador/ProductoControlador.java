package cl.fabydiaz.inventario.controlador;

import cl.fabydiaz.inventario.dto.ProductoDTO;
import cl.fabydiaz.inventario.modelo.Producto;
import cl.fabydiaz.inventario.servicio.ProductoServicio;
import cl.fabydiaz.inventario.excepcion.RecursoNoEncontradoExcepcion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1") //http://localhost:8080/api/v1)
public class ProductoControlador {

    private static final Logger logger = LoggerFactory.getLogger(ProductoControlador.class);
   
    @Autowired
    private ProductoServicio productoServicio;

    @GetMapping("/productos")
    public ResponseEntity<List<ProductoDTO>> obtenerMisProductos(Authentication authentication) {
        List<ProductoDTO> productos = productoServicio.obtenerProductosDelUsuarioAutenticado(authentication);
        return ResponseEntity.ok(productos);
    }


    @PostMapping("/agregar-producto")
    public ResponseEntity<ProductoDTO> agregarProducto(@RequestBody Producto producto, Authentication authentication) {
        ProductoDTO guardado = productoServicio.guardarProductoParaUsuarioAutenticado(producto, authentication);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    @GetMapping("/producto/{id}")
    public ResponseEntity<ProductoDTO> obtenerProuctoPorId(@PathVariable Integer id){
        ProductoDTO producto = productoServicio.buscarProductoPorId(id);
        return ResponseEntity.ok(producto);
    }

    @PutMapping("/producto/{id}")
    public ResponseEntity<ProductoDTO> actualizarProducto(@PathVariable Integer id, @RequestBody Producto productoRecibido, Authentication authentication){
        Producto producto = productoServicio.actualizarProducto(id, productoRecibido);
        ProductoDTO productoActualizado = this.productoServicio.guardarProductoParaUsuarioAutenticado(producto, authentication);
        return ResponseEntity.ok(productoActualizado);
    }
    @DeleteMapping("/producto/{id}")
    public ResponseEntity<Map<String, Boolean>> eliminarProducto(@PathVariable Integer id,Authentication authentication){
        productoServicio.eliminarProductPorId(id, authentication);
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("producto eliminado", Boolean.TRUE);
        return ResponseEntity.ok(respuesta);
    }

}
