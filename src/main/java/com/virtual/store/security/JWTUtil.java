package com.virtual.store.security;

import io.jsonwebtoken.Claims;
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
        /** método para gerar token **/
        return Jwts.builder()
                .setSubject(usuario) /** usuario **/
                .setExpiration(new Date(System.currentTimeMillis() + tempoExpiracao)) /** tempo de duracao token **/
                .signWith(SignatureAlgorithm.HS512, fraseSecret.getBytes(StandardCharsets.UTF_8)) /** forma de assinatura do token , **/
                .compact(); /** compactando tudo e retornando o token **/
    }

    public boolean tokenValido(String bearerToken) {
        /** Claims armazena as renvidicacoes do token nesse caso é usuario e tempo de expiracao **/
        /** isso sempre aconteceu quando requistar algo aos os endpoints **/
        Claims claims = getClaims(bearerToken);

        if (claims != null) {
            String nomeUsuario = claims.getSubject();
            Date expiracaoToken = claims.getExpiration();
            Date horaAtual = new Date(System.currentTimeMillis());

            /** retorna true caso usuario, tempo de expiracao do token e a hora atual seja menor ou igual ao tempo de expiracao **/
            if (nomeUsuario != null && expiracaoToken != null && horaAtual.before(expiracaoToken)) {
                return true;
            }
        }
        return false;
    }

    public String getUserName(String bearerToken) {
        Claims claims = getClaims(bearerToken);
        if (claims != null) {
            return claims.getSubject();
        }
        return null;
    }

    /** obtendo os clains a partir de um token caso seja invalido retorna null **/
    private Claims getClaims(String bearerToken) {
        try {
            return Jwts.parser().setSigningKey(fraseSecret.getBytes())
                    .parseClaimsJws(bearerToken).getBody();
        }
        catch (Exception e) {
            return null;
        }
    }
}
