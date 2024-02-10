package com.usg.book.adapter.in.web.token;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberEmailGetter {

    private final JWTParser parser;

    public String getMemberEmail(String authorizationHeader) {
        String token = authorizationHeader.substring(7);

        String email = (String) parser.getToken(token).get("email");

        return email;
    }
}
