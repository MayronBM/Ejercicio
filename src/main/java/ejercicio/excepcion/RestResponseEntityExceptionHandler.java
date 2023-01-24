package ejercicio.excepcion;

import org.hibernate.TypeMismatchException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;

@ControllerAdvice
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler({AccessDeniedException.class, UsuarioException.class})
    public ResponseEntity<Object> handleAccessDeniedException(
            Exception ex, HttpServletRequest request) {

        ExceptionResponse error = new ExceptionResponse(ex);

        return new ResponseEntity<Object>(error, new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({DataIntegrityViolationException.class, ConstraintViolationException.class})
    public ResponseEntity<Object> handleDataIntegrityViolationException(
            Exception ex, HttpServletRequest request) {

        ExceptionResponse error = new ExceptionResponse(ex);

        return new ResponseEntity<Object>(error, new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({javax.validation.ValidationException.class})
    public ResponseEntity<Object> handleTypeMismatchException(
            Exception ex, HttpServletRequest request) {

        ExceptionResponse error = new ExceptionResponse(ex);

        return new ResponseEntity<Object>(error, new HttpHeaders(), HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleGlobal(
            Exception ex, HttpServletRequest request) {

        ExceptionResponse error = new ExceptionResponse(ex);

        return new ResponseEntity<Object>(error, new HttpHeaders(), HttpStatus.CONFLICT);
    }
}
