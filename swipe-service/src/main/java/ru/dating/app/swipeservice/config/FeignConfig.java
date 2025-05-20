package ru.dating.app.swipeservice.config;

import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Configuration;
import ru.dating.app.swipeservice.external.decoder.CustomErrorDecoder;

@Configuration
public class FeignConfig {

    ErrorDecoder errorDecoder() {
        return new CustomErrorDecoder();
    }
}