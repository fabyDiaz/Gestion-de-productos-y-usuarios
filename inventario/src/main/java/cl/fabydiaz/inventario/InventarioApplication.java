package cl.fabydiaz.inventario;

import cl.fabydiaz.inventario.modelo.Producto;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InventarioApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventarioApplication.class, args);
		//prueba de lombok
		Producto producto = new Producto();
		producto.setDescripcion("Camisa M");
		producto.setPrecio(600.0);
		producto.setStock(30);
	}

}
