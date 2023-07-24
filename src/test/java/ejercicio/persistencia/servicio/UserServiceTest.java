package ejercicio.persistencia.servicio;

import ni.com.userApi.persistencia.Telefono;
import ni.com.userApi.persistencia.Usuario;
import ni.com.userApi.repository.UserRepo;
import ni.com.userApi.servicio.implementacion.UserImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserImpl usuarioServicio;
    private Usuario usuario;

    @BeforeEach
    void setup() {
        usuario = Usuario.builder().nombre("prueba").email("pp@gmail.com").clave("ClaveTemporal123*").build();
        Telefono telefono = Telefono.builder().codigoCiudad(123).codigoPais(123).numero("12345678").build();
        List<Telefono> telefonoList = new ArrayList<>();
        telefonoList.add(telefono);

        usuario.setTelefonos(telefonoList);
    }

    @DisplayName("Prueba guardar un usuario.")
    @Test
    void guardar() {
        given(userRepo.save(usuario)).willReturn(usuario);
        Usuario usuarioGuardado = usuarioServicio.guardar(usuario);
        assertThat(usuarioGuardado).isNotNull();
    }

    @DisplayName("Obtener usuario por email y clave")
    @Test
    void obtenerUsuarioPorEmailClave() {
        given(userRepo.findByEmailAndAndClave("pp@gmail.com", "ClaveTemporal123*"))
                .willReturn(Optional.ofNullable(usuario));
        Optional<Usuario> usuarioEncontrado = usuarioServicio
                .obtenerUsuarioPorEmailClave("pp@gmail.com", "ClaveTemporal123*");
        assertThat(usuarioEncontrado).isPresent();
    }

    @DisplayName("Comprobar si existe email")
    @Test
    void existeEmail() {
        given(userRepo.existsByEmail("ClaveTemporal123*")).willReturn(true);
        assertThat(usuarioServicio.existeEmail("ClaveTemporal123*")).isTrue();
    }
}
