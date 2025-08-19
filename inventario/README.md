# API REST Segura para Gestión de Productos y Usuarios

### Módulo 6 - Actividad 2 

### Equipo 4: 
- Eduardo Arellano
- Fabiola Díaz
- Felipe Lobos
- Carlos Vasquez

Objetivo: Desarrollar una API REST con Spring Boot y H2-console  que gestione productos, registre y
autentique usuarios, proteja operaciones con JWT y utilice variables de entorno para su configuración.

Recursos (modelos)
• Usuario: Con campos: id, nombre, apellido, rut, correo (único), password (encriptada), active.
• Producto: Con campos: id, nombre, descripción, imagen, stock, precio, active.

Servicios:
• Lógica de negocio para registrar y autenticar usuarios, generando un JWT en el login.
• CRUD para productos con soft delete (no eliminar físicamente, marcar active = false).

Controladores:
• Endpoints públicos para /api/v1/auth/register y /api/v1/auth/login.
• Endpoints protegidos para /api/v1/productos.

Seguridad:
• Configuración de Spring Security para exponer las rutas de autenticación sin token y proteger el resto.
• Implementar UserDetailsService en una clase (por ejemplo, UsuarioDetailsService) para integrar tus
usuarios con Spring Security.
• Configuración de JWT en una clase de utilidad (por ejemplo, JwtUtil), leyendo la clave secreta desde
variables de entorno (por ejemplo, usando un .env y una clase EnvLoader).
Variables de entorno:
• Configurar un archivo .env (en la raíz del proyecto, junto con pom.xml) que contenga las variables
necesarias

### Anotaciones
- Se utiliza applications.properties en vez de el archivo .env
- Y en vez de llamar EnvLoader.java, se usa:

``` java
    public JwtUtil(
            @Value("${jwt.secret}") String secretKey,
            @Value("${jwt.expiration}") long expirationTime) {
        this.SECRET_KEY = secretKey;
        this.EXPIRATION_TIME = expirationTime;
    }
```

- Puerto por defecto: 8080
- La colección de rutas usadas se encuentra en [Ver la documentación](./assets/inventario-app.postman_collection.json)