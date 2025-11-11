package com.gsuatavosdaniel.encurtador_url.links;

import com.gsuatavosdaniel.encurtador_url.Exception.BaseExceptionRunTime;

public class LinkNotFoundException extends BaseExceptionRunTime {
    public LinkNotFoundException() {
    }

    public LinkNotFoundException(String message) {
        super(message);
    }

    public LinkNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public LinkNotFoundException(Throwable cause) {
        super(cause);
    }

    public LinkNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
