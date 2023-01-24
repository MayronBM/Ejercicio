package ejercicio.persistencia.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PhoneDto implements Serializable {
    private String number;
    private Integer citycode;
    private Integer contrycode;
}
