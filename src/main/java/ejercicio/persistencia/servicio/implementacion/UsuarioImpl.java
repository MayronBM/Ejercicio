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
        try {
            if (usuario.getId() == null) {
                usuario.setCreado(new Date());
            } else {
                usuario.setModificado(new Date());
            }
            usuario.setActivo(true);
            Usuario finalUsuario = usuario;
            usuario.getTelefonos().forEach(telefono -> telefono.setUsuario(finalUsuario));

            usuario = usuarioRepo.save(usuario);
        } catch (DataIntegrityViolationException | ConstraintViolationException e) {
            if (e.getCause() != null && e.getCause().getCause() != null
                    && e.getCause().getCause().getMessage().contains("UK_POST_EMAIL")) {
                throw new DataIntegrityViolationException(MessageFormat
                        .format("El correo {0} ya existe.", usuario.getEmail()));
            }
            throw new DataIntegrityViolationException("error de duplicado");
        } catch (ValidationException e) {
            Set<ConstraintViolation<?>> constraintViolations
                    = ((javax.validation.ConstraintViolationException) e).getConstraintViolations();
            ConstraintViolation<?>[] val = constraintViolations.toArray(new ConstraintViolation[constraintViolations.size()]);
            throw new DataIntegrityViolationException(val[0].getMessageTemplate());
        } catch (Exception e) {
            throw new UsuarioException("Error inesperado.");
        }
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
