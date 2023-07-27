package ni.com.userApi.persistencia;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.*;
import ni.com.userApi.support.annotation.password.Password;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(
    name = "usuario",
    uniqueConstraints = @UniqueConstraint(name = "UK_POST_EMAIL", columnNames = "email"))
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Usuario {
  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  private UUID id;

  private String nombre;
  private String email;
  @Password private String clave;
  private LocalDateTime creado;
  private LocalDateTime modificado;
  private Boolean activo;

  @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
  private List<Telefono> telefonos;
}
