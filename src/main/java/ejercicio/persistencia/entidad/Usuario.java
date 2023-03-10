package ejercicio.persistencia.entidad;

import ejercicio.web.util.validacion.email.Password;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "usuario",
        uniqueConstraints = @UniqueConstraint(
                name = "UK_POST_EMAIL",
                columnNames = "email"
        ))
@Data
public class Usuario {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;
    private String nombre;
    private String email;
    @Password
    private String clave;
    private Date creado;
    private Date modificado;
    private Boolean activo;
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Telefono> telefonos;
}
