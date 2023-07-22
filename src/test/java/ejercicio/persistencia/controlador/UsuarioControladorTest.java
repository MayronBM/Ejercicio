package ejercicio.persistencia.controlador;

import com.fasterxml.jackson.databind.ObjectMapper;
import ejercicio.persistencia.dto.UserDto;
import ejercicio.persistencia.entidad.Usuario;
import ejercicio.persistencia.mapper.UserMapper;
import ejercicio.persistencia.servicio.servicio.GeneradorJwt;
import ejercicio.persistencia.servicio.servicio.UsuarioServicio;
import ejercicio.web.util.Token;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class UsuarioControladorTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UsuarioServicio usuarioServicio;

    @MockBean
    private GeneradorJwt generadorJwt;

    @MockBean
    private UserMapper userMapper;

    @DisplayName("Guardar un usuario.")
    @Test
    void guardar() throws Exception {

        Usuario usuario = Usuario.builder()
                .nombre("prueba")
                .email("pp@gmail.com")
                .clave("ClaveTemporal123*")
                .build();
        Token token = new Token("1234");
        UserDto userDto = UserDto.builder()
                .name("prueba")
                .email("pp@gmail.com")
                .password("ClaveTemporal123*")
                .token(token.getToken())
                .build();

        given(userMapper.convertir(any(UserDto.class))).willReturn(usuario);

        given(usuarioServicio.guardar(any(Usuario.class))).willReturn(usuario);

        given(generadorJwt.crearToken(any(Usuario.class))).willReturn(token);

        given(userMapper.convertirGuardar(any(Usuario.class), any(Token.class))).willReturn(userDto);

        ResultActions resultActions = mockMvc.perform(post("/api/v1/usuario/guardar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)));

        resultActions.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token", is(userDto.getToken())));

    }

    @DisplayName("Login un usuario.")
    @Test
    void login() throws Exception {

        Usuario usuario = Usuario.builder()
                .email("pp@gmail.com")
                .clave("ClaveTemporal123*")
                .build();
        Token token = new Token("1234");
        UserDto userDto = UserDto.builder()
                .name("prueba")
                .email("pp@gmail.com")
                .password("ClaveTemporal123*")
                .token(token.getToken())
                .build();

        given(userMapper.convertir(any(UserDto.class))).willReturn(usuario);

        given(usuarioServicio.obtenerUsuarioPorEmailClave(userDto.getEmail(), userDto.getPassword())).willReturn(Optional.of(usuario));

        given(generadorJwt.crearToken(any(Usuario.class))).willReturn(token);


        ResultActions resultActions = mockMvc.perform(post("/api/v1/usuario/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)));

        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", is(userDto.getToken())));

    }
}
