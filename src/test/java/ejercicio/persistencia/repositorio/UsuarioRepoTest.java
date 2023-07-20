package ejercicio.persistencia.repositorio;

import ejercicio.persistencia.entidad.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UsuarioRepoTest {

    private final UsuarioRepo usuarioRepo;
    private Usuario usuario;

    @Autowired
    public UsuarioRepoTest(UsuarioRepo usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    @BeforeEach
    void setup() {
        usuario = Usuario.builder().nombre("prueba").email("pp@gmail.com").clave("ClaveTemporal123*").build();
    }

    @DisplayName("Guardar usuario")
    @Test
    void guardarUsuario() {
        Usuario usuarioGuardado = usuarioRepo.save(usuario);
        assertThat(usuarioGuardado).isNotNull();
    }

    @DisplayName("Buscar por email y clave")
    @Test
    void findByEmailAndAndClave() {
        Optional<Usuario> usuarioBuscado = usuarioRepo.findByEmailAndAndClave("test", "test");
        assertThat(usuarioBuscado).isNotEmpty();
        assertThat(usuarioBuscado.get().getNombre()).isEqualTo("test");
    }

    @DisplayName("Verificar si un email existe")
    @Test
    void existsByEmail() {
        boolean existeEmail = usuarioRepo.existsByEmail("test");
        assertThat(existeEmail).isTrue();
    }
}
