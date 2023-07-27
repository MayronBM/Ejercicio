package ni.com.userApi.controlador;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import ni.com.userApi.dto.Token;
import ni.com.userApi.dto.UserDto;
import ni.com.userApi.mapper.UserMapper;
import ni.com.userApi.persistencia.Usuario;
import ni.com.userApi.servicio.servicio.GeneratorJwt;
import ni.com.userApi.servicio.servicio.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private GeneratorJwt generatorJwt;

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

        given(userService.guardar(any(Usuario.class))).willReturn(usuario);

        given(generatorJwt.crearToken(any(Usuario.class))).willReturn(token);

        given(userMapper.convertirGuardar(any(Usuario.class), any(Token.class))).willReturn(userDto);

        ResultActions resultActions = mockMvc.perform(post("/usuario/guardar")
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

        given(userService.obtenerUsuarioPorEmailClave(userDto.getEmail(), userDto.getPassword())).willReturn(Optional.of(usuario));

        given(generatorJwt.crearToken(any(Usuario.class))).willReturn(token);


        ResultActions resultActions = mockMvc.perform(post("/usuario/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)));

        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", is(userDto.getToken())));

    }

    @DisplayName("Login un usuario email null.")
    @Test
    void loginEmailNull() throws Exception {

        Usuario usuario = Usuario.builder()
                .clave("ClaveTemporal123*")
                .build();
        Token token = new Token("1234");
        UserDto userDto = UserDto.builder()
                .name("prueba")
                .password("ClaveTemporal123*")
                .token(token.getToken())
                .build();

        given(userMapper.convertir(any(UserDto.class))).willReturn(usuario);

        given(userService.obtenerUsuarioPorEmailClave(userDto.getEmail(), userDto.getPassword())).willReturn(Optional.of(usuario));

        given(generatorJwt.crearToken(any(Usuario.class))).willReturn(token);


        ResultActions resultActions = mockMvc.perform(post("/usuario/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)));

        resultActions.andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.mensaje", is("Los valores no pueden ser vacíos")));

    }
}
