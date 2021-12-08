package com.virtual.store.config;

import com.virtual.store.services.DBService;
import com.virtual.store.services.MockEmailService;
import com.virtual.store.services.ServicoEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.text.ParseException;

@Configuration
@Profile("test")
public class TestConfig {
    @Autowired
    private DBService dbService;

    @Bean
    public boolean instanciandoBandoDeDados() throws ParseException {
        dbService.instanciandoTesteBancoDeDados();
        return true;
    }

    /** o método @Bean torna o método disponivel como um componente no projeto **/
    @Bean
    public ServicoEmail servicoEmail(){
        return new MockEmailService();
    }

}
