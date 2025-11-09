package com.gsuatavosdaniel.encurtador_url.links;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.URL;

public record LinkRequest(

         @NotBlank(message = "Tem que ser em um formato de Url valida")
         @URL(message = "Formato de URL invalido")
         @Size(max = 2048, message = "URL muito longa  maximo 2048 caracteres")
         String longUrl
) {
}
