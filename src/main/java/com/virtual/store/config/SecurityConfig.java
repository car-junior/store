package com.virtual.store.config;

import com.virtual.store.security.JWTFiltroDeAutenticacao;
import com.virtual.store.security.JWTFiltroDeAutorizacao;
import com.virtual.store.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private Environment environment;

    @Autowired
    private JWTUtil jwtUtil;

    /** injeto a interface pois o spring busca no projeto alguma instanciacao
    dessa interface que no caso é UserDetailsServiceImplementacao e injeta ela **/
    @Autowired
    private UserDetailsService userDetailsService;

    private static final String[] URLS = { "/h2-console/**" };

    private static final String[] URLS_OPEN_GET = { "/produtos/**", "/categorias/**" };

    private static final String[] URLS_OPEN_POST = { "/clientes/**", "/autorizacao/recuperar_senha/**" };

    /** sobrepondo metodo config da classe WebSecurityConfigurerAdapter **/
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        if (Arrays.asList(environment.getActiveProfiles()).contains("test")){
            http.headers().frameOptions().disable();
        }

        /** chamando metodo que configurou o cors e desabilitando o csrf **/
        http.cors().and().csrf().disable();
        http.authorizeRequests()
                .antMatchers(HttpMethod.POST, URLS_OPEN_POST).permitAll() /** PERMITE ACESSO APENAS AOS METODOS POST DAS URLS INFORMADAS **/
                .antMatchers(HttpMethod.GET, URLS_OPEN_GET).permitAll() /** PERMITE ACESSO APENAS AOS METODOS GETS DAS URLS INFORMADAS **/
                .antMatchers(URLS).permitAll() /** PERMITE ACESSO PARA AS URLS SEM SER NECESSARIO AUTENTICACAO **/
                .anyRequest().authenticated(); /** E PARA TODO O RESTO É NECESSÁRIO AUTENTICACAO **/

        /** adicionando filtro de autenticao do jwt **/
        http.addFilter(new JWTFiltroDeAutenticacao(authenticationManager(), jwtUtil));

        /** adicionando filtro de autoricao que ira verificar se o usuario tem permissao para acessar o sistema **/
        http.addFilter(new JWTFiltroDeAutorizacao(authenticationManager(), jwtUtil, userDetailsService));

        /** garantindo que o back end não irá criar sessão de usuário **/
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    /** mecanismo de autenticao som passando userService e encoder **/
    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        /** liberando cors para toda urls desde a origem **/
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
