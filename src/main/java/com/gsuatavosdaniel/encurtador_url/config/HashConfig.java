package com.gsuatavosdaniel.encurtador_url.config;

import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class HashConfig {

    @Value("${MY_SALT}")
    private String salt;

    @Bean
    public Hashids hashids() {

        String Base62 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

        return new Hashids(salt, 8, Base62);
    }
}
