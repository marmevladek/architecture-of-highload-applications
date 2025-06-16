package ru.dating.app.deckservice.external.decoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Request;
import feign.Request.HttpMethod;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
import lombok.extern.log4j.Log4j2;
import ru.dating.app.deckservice.exception.CustomException;
import ru.dating.app.deckservice.payload.ErrorResponse;

import java.io.IOException;

@Log4j2
public class CustomErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Exception decode(String methodKey, Response response) {
        log.info("Feign request URL: {}", response.request().url());
        log.info("Feign request headers: {}", response.request().headers());

        try {
            ErrorResponse errorResponse = objectMapper.readValue(
                    response.body().asInputStream(), ErrorResponse.class);

            int status = response.status();

            if (status == 502 || status == 503 || status == 504) {
                // retryAfter — можно указать null или время в миллисекундах, если знаешь
                return new RetryableException(
                        status,
                        errorResponse.getErrorMessage(),
                        response.request().httpMethod(),
                        (Throwable) null,          // Throwable cause - нет
                        (Long) null,          // retryAfter - не указываем задержку, будет дефолт
                        response.request()
                );
            }

            return new CustomException(
                    errorResponse.getErrorMessage(),
                    errorResponse.getErrorCode(),
                    status
            );

        } catch (IOException e) {
            return new RetryableException(
                    500,
                    "IOException during error decode",
                    response.request().httpMethod(),
                    (Throwable) e,
                    (Long) null,
                    response.request()
            );
        }
    }
}