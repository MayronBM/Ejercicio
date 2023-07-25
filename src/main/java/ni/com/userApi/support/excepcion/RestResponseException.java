package ni.com.userApi.support.excepcion;

import java.sql.SQLException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class RestResponseException {

  @ResponseBody
  @ExceptionHandler({UserException.class})
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ExceptionResponse handleAccessDeniedException(UserException ex) {
    return new ExceptionResponse(ex);
  }

  @ResponseBody
  @ExceptionHandler({SQLException.class})
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ExceptionResponse sqlException(SQLException ex) {
    return new ExceptionResponse(ex);
  }
}
