package ni.com.userApi.repositorio;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.Optional;
import ni.com.userApi.persistencia.Usuario;
import ni.com.userApi.repository.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UserRepoTest {

    private final UserRepo userRepo;
    private Usuario usuario;

    @Autowired
    public UserRepoTest(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @BeforeEach
    void setup() {
        usuario = Usuario.builder().nombre("prueba").email("pp@gmail.com").clave("ClaveTemporal123*").build();
    }

    @DisplayName("Guardar usuario")
    @Test
    void guardarUsuario() {
        Usuario usuarioGuardado = userRepo.save(usuario);
        assertThat(usuarioGuardado).isNotNull();
    }

    @DisplayName("Buscar por email y clave")
    @Test
    void findByEmailAndAndClave() {
        Optional<Usuario> usuarioBuscado = userRepo.findByEmailAndAndClave("test", "test");
        assertThat(usuarioBuscado).isNotEmpty();
        assertThat(usuarioBuscado.get().getNombre()).isEqualTo("test");
    }

    @DisplayName("Verificar si un email existe")
    @Test
    void existsByEmail() {
        boolean existeEmail = userRepo.existsByEmail("test");
        assertThat(existeEmail).isTrue();
    }
}
