package ejercicio.excepcion;

public class UsuarioException extends RuntimeException {
    public UsuarioException(String message) {
        super(message);
    }

    public UsuarioException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsuarioException(Throwable cause) {
        super(cause);
    }

    public UsuarioException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
