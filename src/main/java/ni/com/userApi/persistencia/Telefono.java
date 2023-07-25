package ni.com.userApi.persistencia;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "telefono")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Telefono {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String numero;
  private Integer codigoCiudad;
  private Integer codigoPais;

  @ManyToOne
  @JoinColumn(name = "usuario")
  private Usuario usuario;
}
