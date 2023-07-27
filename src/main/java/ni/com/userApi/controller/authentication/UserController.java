package ni.com.userApi.controller.authentication;

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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("usuario")
@Getter
@Setter
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

  @PostMapping("/guardar")
  public ResponseEntity<?> postUser(@RequestBody UserDto userDto) {
    if (!EmailValidator.getInstance().isValid(userDto.getEmail())) {
      throw new UserException("Debe agregar un correo válido.");
    }

    Usuario usuario = userMapper.convertir(userDto);
    usuario = userService.guardar(usuario);
    Token token = generatorJwt.crearToken(usuario);

    return new ResponseEntity<>(userMapper.convertirGuardar(usuario, token), HttpStatus.CREATED);
  }

  @PostMapping("/login")
  public ResponseEntity<?> loginUser(@RequestBody UserDto userDto) throws Exception {
    Usuario usuario = userMapper.convertir(userDto);

    if (StringUtils.isEmpty(usuario.getEmail()) || StringUtils.isEmpty(usuario.getClave())) {
      throw new UserException("Los valores no pueden ser vacíos");
    }

    return userService
        .obtenerUsuarioPorEmailClave(usuario.getEmail(), usuario.getClave())
        .map(usuarioBd -> new ResponseEntity<>(generatorJwt.crearToken(usuario), HttpStatus.OK))
        .orElse(new ResponseEntity<>(HttpStatus.CONFLICT));
  }
}
