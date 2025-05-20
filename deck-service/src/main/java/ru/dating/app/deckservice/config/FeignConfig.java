package ru.dating.app.deckservice.config;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Configuration;
import ru.dating.app.deckservice.external.decoder.CustomErrorDecoder;

@Configuration
public class FeignConfig {

    ErrorDecoder errorDecoder() {
        return new CustomErrorDecoder();
    }
}
