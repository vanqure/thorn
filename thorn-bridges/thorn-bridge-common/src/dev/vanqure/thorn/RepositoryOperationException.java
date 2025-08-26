package dev.vanqure.thorn;

public final class RepositoryOperationException extends RuntimeException {

    RepositoryOperationException(final String message) {
        super(message);
    }

    RepositoryOperationException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
