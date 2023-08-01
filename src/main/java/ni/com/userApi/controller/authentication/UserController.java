package ni.com.userApi.controller.authentication;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.Setter;
import ni.com.userApi.dto.Token;
import ni.com.userApi.dto.UserDto;
import ni.com.userApi.mapper.UserMapper;
import ni.com.userApi.persistencia.Usuario;
import ni.com.userApi.servicio.servicio.GeneratorJwt;
import ni.com.userApi.servicio.servicio.UserService;
import ni.com.userApi.support.excepcion.UserException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("usuarios")
@Getter
@Setter
@Tag(name = "Usuarios", description = "Registro y Login de usuarios")
public class UserController {

  private UserService userService;
  private GeneratorJwt generatorJwt;
  private UserMapper userMapper;

  @Autowired
  public UserController(UserService userService, GeneratorJwt generatorJwt, UserMapper userMapper) {
    this.userService = userService;
    this.generatorJwt = generatorJwt;
    this.userMapper = userMapper;
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public UserDto postUser(@RequestBody UserDto userDto) {
    if (!EmailValidator.getInstance().isValid(userDto.getEmail())) {
      throw new UserException("Debe agregar un correo válido.");
    }

    Usuario usuario = userMapper.convertir(userDto);
    usuario = userService.guardar(usuario);
    Token token = generatorJwt.crearToken(usuario);

    return userMapper.convertirGuardar(usuario, token);
  }

  @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public Token loginUser(@RequestBody UserDto userDto) {
    Usuario usuario = userMapper.convertir(userDto);

    if (StringUtils.isEmpty(usuario.getEmail()) || StringUtils.isEmpty(usuario.getClave())) {
      throw new UserException("Los valores no pueden ser vacíos");
    }

    return userService
        .obtenerUsuarioPorEmailClave(usuario.getEmail(), usuario.getClave())
        .map(usuarioBd -> generatorJwt.crearToken(usuario))
        .orElseThrow();
  }
}
