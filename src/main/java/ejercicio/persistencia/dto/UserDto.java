package ejercicio.persistencia.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto implements Serializable {
    private UUID id;
    private String name;
    private String email;
    private String password;
    private List<PhoneDto> phones;
    private Date created;
    private Date modified;
    private Boolean isactive;
    private String token;
}
