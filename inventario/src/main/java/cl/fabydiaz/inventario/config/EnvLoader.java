package cl.fabydiaz.inventario.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Component;

@Component
public class EnvLoader {

    private final Dotenv dotenv;

    public EnvLoader() {
        this.dotenv = Dotenv.configure().ignoreIfMissing().load();
    }

    public String get(String key) {
        return dotenv.get(key);
    }
}