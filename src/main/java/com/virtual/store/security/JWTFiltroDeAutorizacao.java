package com.virtual.store.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTFiltroDeAutorizacao extends BasicAuthenticationFilter {

    private static final String AUTHORIZATION = "Authorization";

    private JWTUtil jwtUtil;
    private UserDetailsService userDetailsService;

    public JWTFiltroDeAutorizacao(AuthenticationManager authenticationManager, JWTUtil jwtUtil, UserDetailsService userDetailsService) {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService; /** fazer busca de usuario pelo email **/
    }

    /** método que intercepta a requisicao e verifica se o usuario está autorizado **/
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        /** pegando o br token do cabeçalho **/
        String bearerToken = request.getHeader(AUTHORIZATION);
        /** validando se existe o token e que começa com o formato padrao **/
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            UsernamePasswordAuthenticationToken usuarioTokenAutenticao = getAutenticacao(bearerToken.substring(7));
            /** se for != null o token está certo **/
            if (usuarioTokenAutenticao != null) {
                /** token estando certo essa funcao libera o acesso ao endpoint solicitado **/
                SecurityContextHolder.getContext().setAuthentication(usuarioTokenAutenticao);
            }
        }
        /** continua fazendo a requisicao normalmente **/
        chain.doFilter(request, response);
    }

    /** mandando valor do token e ele vai retornar um objeto do tipo (UsernamePasswordAuthenticationToken) somente se o token for valido se nao for retorna null **/
    private UsernamePasswordAuthenticationToken getAutenticacao(String bearerToken) {
        if (jwtUtil.tokenValido(bearerToken)) {
            /** retornando usuario por meio do token **/
            String userName = jwtUtil.getUserName(bearerToken);

            /** instanciando usuario por meio do username **/
            UserDetails usuario = userDetailsService.loadUserByUsername(userName);

            /** passando usuario e sua credenciais sendo passada por perfis **/
            return new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
        }

        return  null;
    }

}
