package ejercicio.excepcion;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ExceptionResponse implements Serializable {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime fecha;
    private String mensaje;

    public ExceptionResponse() {
    }

    public ExceptionResponse(String mensaje) {
        this.mensaje = mensaje;
        fecha = LocalDateTime.now();
    }

    public ExceptionResponse(Exception ex) {
        mensaje = ex.getMessage();
        fecha = LocalDateTime.now();
    }
}
