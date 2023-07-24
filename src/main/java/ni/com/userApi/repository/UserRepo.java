package ni.com.userApi.repository;

import ni.com.userApi.persistencia.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByEmailAndAndClave(String email, String clave);

    Boolean existsByEmail(String email);
}
