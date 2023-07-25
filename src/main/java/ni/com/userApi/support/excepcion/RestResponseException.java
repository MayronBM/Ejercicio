package ni.com.userApi.support.excepcion;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import java.sql.SQLException;
import java.util.Set;

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
