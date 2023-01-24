package ejercicio.persistencia.entidad;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "telefono")
@Data
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
