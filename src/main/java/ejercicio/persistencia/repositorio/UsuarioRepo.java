package ejercicio.persistencia.repositorio;

import ejercicio.persistencia.entidad.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepo extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByEmailAndAndClave(String email, String clave);

    Boolean existsByEmail(String email);
}
