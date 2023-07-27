package ni.com.userApi.repository;

import java.util.Optional;
import ni.com.userApi.persistencia.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<Usuario, Integer> {
  Optional<Usuario> findByEmailAndAndClave(String email, String clave);

  Boolean existsByEmail(String email);
}
