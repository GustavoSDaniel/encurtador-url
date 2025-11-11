package com.gsuatavosdaniel.encurtador_url.links;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public record LinkRequest(

         @NotBlank(message = "Tem que ser em um formato de Url valida")
         @URL(message = "Formato de URL invalido")
         String longUrl
) {
}
