package exception;

public class VehiculoException extends Exception {
    public VehiculoException(String message){
        super(message);
    }
    public VehiculoException(String message, Throwable cause){
        super(message, cause);
    }
    public VehiculoException(Throwable cause){
        super(cause);
    }
    public VehiculoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace){
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
