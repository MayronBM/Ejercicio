package ejercicio.persistencia.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class PhoneDto implements Serializable {
    private String number;
    private Integer citycode;
    private Integer contrycode;
}
