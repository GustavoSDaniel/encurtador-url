package com.gsuatavosdaniel.encurtador_url.links;

import com.gsuatavosdaniel.encurtador_url.Exception.BaseExceptionRunTime;

public class UrlJaExisteException extends BaseExceptionRunTime {

    public UrlJaExisteException() {
    }

    public UrlJaExisteException(String message) {
        super(message);
    }

    public UrlJaExisteException(String message, Throwable cause) {
        super(message, cause);
    }

    public UrlJaExisteException(Throwable cause) {
        super(cause);
    }

    public UrlJaExisteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
