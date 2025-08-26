package dev.vanqure.thorn;

public class ThornClientException extends IllegalStateException {

    public ThornClientException(final String message) {
        super(message);
    }

    public ThornClientException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
