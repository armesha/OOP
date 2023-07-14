package cz.upce.fei.boop.pujcovna.spravce;

public class OvladaniException extends Exception {
    public OvladaniException(Exception message) {
        super(message);
    }

    public OvladaniException(String message, Throwable cause) {
        super(message, cause);
    }
}
