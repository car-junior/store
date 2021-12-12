package com.virtual.store.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.virtual.store.domain.dto.CredenciaisDTO;
import org.springframework.core.log.LogMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/** quando crio essa classe extendendo UsernamePasswordAuthenticationFilter o
 * spring sabe que essa classe é responsavel de interceptar as requisicoes de autentiacao */
/** o filtro vai interceptar a requisicao ou seja ao acessar a url referente a login ele vai interceptar isso caso esteja certo o usuario     *
 e senha ele retorna o token  */
public class JWTFiltroDeAutenticacao extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private JWTUtil jwtUtil;

    public JWTFiltroDeAutenticacao(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }


    /** Método que tenta autenticar **/
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            /** pegando os dados login e senha da requisicao e convertendo pro tipo CredenciaisDTO **/
            CredenciaisDTO credenciaisDTO = new ObjectMapper().readValue(request.getInputStream(), CredenciaisDTO.class);

            UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(credenciaisDTO.getEmail(), credenciaisDTO.getSenha(), new ArrayList<>());

            /** metodo que verifica se realmente se o usuario e senha sao validos **/
            Authentication autenticacao = authenticationManager.authenticate(userToken);
            Authentication a = autenticacao;
            /** retorna autenticacao sendo valida ou nao para o spring security**/
            return autenticacao;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /** Método que acrescenta token no cabecalho da requisicao caso seja valido a autenticacao */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        /** retorna usuario do springSecurity e faz um casting pro tipo UserSS e depois pega o email do cliente **/
        String userName = ((UserSS) authResult.getPrincipal()).getUsername();
        /** gerando token para o usuario retornado **/
        String token = jwtUtil.gerarToken(userName);
        /** retornando no cabecalho da resposta o token gerado **/
        response.addHeader("Authorization", "Bearer " + token);
    }

    private class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {

        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
                throws IOException, ServletException {
            response.setStatus(401);
            response.setContentType("application/json");
            response.getWriter().append(json());
        }

        private String json() {
            long date = new Date().getTime();
            return "{\"timestamp\": " + date + ", "
                    + "\"status\": 401, "
                    + "\"error\": \"Não autorizado\", "
                    + "\"message\": \"Email ou senha inválidos\", "
                    + "\"path\": \"/login\"}";
        }
    }
}
