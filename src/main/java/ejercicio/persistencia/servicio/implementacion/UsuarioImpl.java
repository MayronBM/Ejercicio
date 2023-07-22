package ejercicio.persistencia.servicio.implementacion;

import ejercicio.excepcion.UsuarioException;
import ejercicio.persistencia.entidad.Usuario;
import ejercicio.persistencia.repositorio.UsuarioRepo;
import ejercicio.persistencia.servicio.servicio.UsuarioServicio;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Service
public class UsuarioImpl implements UsuarioServicio {

    private final UsuarioRepo usuarioRepo;

    @Autowired
    public UsuarioImpl(UsuarioRepo usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }

    @Override
    public Usuario guardar(Usuario usuario) {

        if (usuario.getId() == null) {
            usuario.setCreado(LocalDateTime.now());
        } else {
            usuario.setModificado(LocalDateTime.now());
        }
        usuario.setActivo(true);
        Usuario finalUsuario = usuario;
        if (usuario.getTelefonos() != null) {
            usuario.getTelefonos().forEach(telefono -> telefono.setUsuario(finalUsuario));
        }

        usuario = usuarioRepo.save(usuario);
        return usuario;
    }

    @Override
    public Optional<Usuario> obtenerUsuarioPorEmailClave(String email, String clave) {

        return Optional.ofNullable(usuarioRepo.findByEmailAndAndClave(email, clave)
                .orElseThrow(() -> new UsuarioException("Usuario o Clave incorrecta")));
    }

    @Override
    public Boolean existeEmail(String email) {
        return usuarioRepo.existsByEmail(email);
    }
}
