package com.virtual.store.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String fraseSecret;

    @Value("${jwt.expiration}")
    private Long tempoExpiracao;

    public String gerarToken(String usuario) {
        Map<String, Object> claims = new HashMap<>();
        /** método para gerar token **/
        return Jwts.builder()
                .setSubject(usuario) /** usuario **/
                .setExpiration(new Date(System.currentTimeMillis() + tempoExpiracao)) /** tempo de duracao token **/
                .signWith(SignatureAlgorithm.HS512, fraseSecret.getBytes(StandardCharsets.UTF_8)) /** forma de assinatura do token , **/
                .compact(); /** compactando tudo e retornando o token **/
    }

}
