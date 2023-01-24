package ejercicio.web.autenticacion;

import ejercicio.excepcion.UsuarioException;
import ejercicio.persistencia.dto.UserDto;
import ejercicio.persistencia.entidad.Usuario;
import ejercicio.persistencia.mapper.UserMapper;
import ejercicio.persistencia.servicio.servicio.GeneradorJwt;
import ejercicio.persistencia.servicio.servicio.UsuarioServicio;
import ejercicio.web.util.Token;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/usuario")
@Data
public class UsuarioControlador {

    private UsuarioServicio usuarioServicio;
    private GeneradorJwt generadorJwt;
    private UserMapper userMapper;

    @Autowired
    public UsuarioControlador(UsuarioServicio usuarioServicio, GeneradorJwt generadorJwt,
                              UserMapper userMapper) {
        this.usuarioServicio = usuarioServicio;
        this.generadorJwt = generadorJwt;
        this.userMapper = userMapper;
    }

    @PostMapping("/guardar")
    public ResponseEntity<?> postUser(@RequestBody UserDto userDto) throws Exception {
        try {

            if (!EmailValidator.getInstance()
                    .isValid(userDto.getEmail())) {
                throw new UsuarioException("Debe agregar un correo válido.");
            }

            Usuario usuario = userMapper.convertir(userDto);
            usuario = usuarioServicio.guardar(usuario);
            Token token = generadorJwt.crearToken(usuario);

            return new ResponseEntity<>(userMapper.convertirGuardar(usuario, token), HttpStatus.CREATED);
        } catch (UsuarioException e) {
            throw new UsuarioException(e.getMessage());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDto userDto) throws Exception {
        try {
            Usuario usuario = userMapper.convertir(userDto);
            if (StringUtils.isEmpty(usuario.getEmail()) || StringUtils.isEmpty(usuario.getClave())) {
                throw new UsuarioException("Los valores no pueden ser vacíos");
            }

            return usuarioServicio.obtenerUsuarioPorEmailClave(usuario.getEmail(), usuario.getClave())
                    .map(usuarioBd -> new ResponseEntity<>(generadorJwt.crearToken(usuario), HttpStatus.OK))
                    .orElse(new ResponseEntity<>(HttpStatus.CONFLICT));

        } catch (UsuarioException e) {
            throw new UsuarioException(e.getMessage());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
