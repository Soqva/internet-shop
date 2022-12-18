package com.s0qva.application.advice;

import com.s0qva.application.dto.AuthDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.lang.reflect.Type;

@ControllerAdvice
@RequiredArgsConstructor
@SuppressWarnings("NullableProblems")
public class ClientPasswordEncoderAdvice extends RequestBodyAdviceAdapter {
    private final PasswordEncoder passwordEncoder;

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter,
                                Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {

        var authDto = (AuthDto) body;

        encodePassword(authDto);
        return authDto;
    }

    private void encodePassword(AuthDto authDto) {
        var encodedPassword = passwordEncoder.encode(authDto.getPassword());

        authDto.setPassword(encodedPassword);
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType,
                            Class<? extends HttpMessageConverter<?>> converterType) {

        return ((Type) AuthDto.class).equals(targetType);
    }
}
