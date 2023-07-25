package ni.com.userApi.servicio.implementacion;

import ni.com.userApi.support.excepcion.UserException;
import ni.com.userApi.persistencia.Usuario;
import ni.com.userApi.repository.UserRepo;
import ni.com.userApi.servicio.servicio.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserImpl implements UserService {

  private final UserRepo userRepo;

  @Autowired
  public UserImpl(UserRepo userRepo) {
    this.userRepo = userRepo;
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

    usuario = userRepo.save(usuario);
    return usuario;
  }

  @Override
  public Optional<Usuario> obtenerUsuarioPorEmailClave(String email, String clave) {

    return Optional.ofNullable(
        userRepo
            .findByEmailAndAndClave(email, clave)
            .orElseThrow(() -> new UserException("Usuario o Clave incorrecta")));
  }

  @Override
  public Boolean existeEmail(String email) {
    return userRepo.existsByEmail(email);
  }
}
