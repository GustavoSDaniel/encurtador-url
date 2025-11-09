package com.gsuatavosdaniel.encurtador_url.Exception;

import java.time.LocalDateTime;

public record ErrorResponse(

        String error,
        String message,
        LocalDateTime timesTamp
) {
}
