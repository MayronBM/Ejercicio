package ejercicio.persistencia.servicio;

import ejercicio.persistencia.entidad.Telefono;
import ejercicio.persistencia.entidad.Usuario;
import ejercicio.persistencia.repositorio.UsuarioRepo;
import ejercicio.persistencia.servicio.implementacion.UsuarioImpl;
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
public class UsuarioServicioTest {
    @Mock
    private UsuarioRepo usuarioRepo;

    @InjectMocks
    private UsuarioImpl usuarioServicio;
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
        given(usuarioRepo.save(usuario)).willReturn(usuario);
        Usuario usuarioGuardado = usuarioServicio.guardar(usuario);
        assertThat(usuarioGuardado).isNotNull();
    }

    @DisplayName("Obtener usuario por email y clave")
    @Test
    void obtenerUsuarioPorEmailClave() {
        given(usuarioRepo.findByEmailAndAndClave("pp@gmail.com", "ClaveTemporal123*"))
                .willReturn(Optional.ofNullable(usuario));
        Optional<Usuario> usuarioEncontrado = usuarioServicio
                .obtenerUsuarioPorEmailClave("pp@gmail.com", "ClaveTemporal123*");
        assertThat(usuarioEncontrado).isPresent();
    }

    @DisplayName("Comprobar si existe email")
    @Test
    void existeEmail() {
        given(usuarioRepo.existsByEmail("ClaveTemporal123*")).willReturn(true);
        assertThat(usuarioServicio.existeEmail("ClaveTemporal123*")).isTrue();
    }
}
