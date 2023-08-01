package ni.com.userApi.support.excepcion;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.sql.SQLException;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionResponse implements Serializable {
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
  private LocalDateTime fecha;

  private String mensaje;

  public ExceptionResponse() {}

  public ExceptionResponse(String mensaje) {
    this.mensaje = mensaje;
    fecha = LocalDateTime.now();
  }

  public ExceptionResponse(Exception ex) {
    mensaje = ex.getMessage();
    fecha = LocalDateTime.now();
  }

  public ExceptionResponse(SQLException ex) {
    mensaje = mensajeSqlException(ex);
    fecha = LocalDateTime.now();
  }

  private String mensajeSqlException(SQLException ex) {
    String msg = "Error code: %d, Cause: %s";
    if (ex.getErrorCode() == 23505 && ex.getMessage().contains("UK_POST_EMAIL")) {
      return "El email se encuentra asignado a otro usuario.";
    } else {
      return String.format(msg, ex.getErrorCode(), ex.getMessage());
    }
  }
}
