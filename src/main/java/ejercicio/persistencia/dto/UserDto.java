package ejercicio.persistencia.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto implements Serializable {
    private UUID id;
    private String name;
    private String email;
    private String password;
    private List<PhoneDto> phones;
    private LocalDateTime created;
    private LocalDateTime modified;
    private Boolean isactive;
    private String token;
}
