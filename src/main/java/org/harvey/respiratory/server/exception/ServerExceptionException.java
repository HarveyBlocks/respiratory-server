package org.harvey.respiratory.server.exception;

/**
 * TODO  ServerException的异常
 *
 * @author <a href="mailto:harvey.blocks@outlook.com">Harvey Blocks</a>
 * @version 1.0
 * @date 2025-05-15 23:38
 */
public class ServerExceptionException extends RuntimeException {
    public ServerExceptionException() {
        super();
    }

    public ServerExceptionException(String message) {
        super(message);
    }

    public ServerExceptionException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServerExceptionException(Throwable cause) {
        super(cause);
    }

    protected ServerExceptionException(
            String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
