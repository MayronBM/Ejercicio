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
import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import java.nio.file.AccessDeniedException;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.Set;

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
            Exception e, HttpServletRequest request) {
        String value = "";
        if (e.getCause() != null && e.getCause().getCause() != null
                && e.getCause().getCause().getMessage().contains("UK_POST_EMAIL")) {
            value = "El correo ya existe.";
        } else {
            value = "El valor agregado ya eiste.";
        }

        ExceptionResponse error = new ExceptionResponse(e);
        error.setMensaje(value);

        return new ResponseEntity<Object>(error, new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<Object> handleTypeMismatchException(
            Exception e, HttpServletRequest request) {
        String value = "";
        Set<ConstraintViolation<?>> constraintViolations
                = ((javax.validation.ConstraintViolationException) e).getConstraintViolations();
        ConstraintViolation<?>[] val = constraintViolations.toArray(new ConstraintViolation[constraintViolations.size()]);

        value = val[0].getMessageTemplate();

        ExceptionResponse error = new ExceptionResponse(e);
        error.setMensaje(value);

        return new ResponseEntity<Object>(error, new HttpHeaders(), HttpStatus.FORBIDDEN);
    }


    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleGlobal(
            Exception ex, HttpServletRequest request) {

        ExceptionResponse error = new ExceptionResponse(ex);

        return new ResponseEntity<Object>(error, new HttpHeaders(), HttpStatus.CONFLICT);
    }
}
