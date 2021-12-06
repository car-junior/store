package com.virtual.store.config;

import com.virtual.store.services.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.text.ParseException;


public class TestConfig {
    @Autowired
    private DBService dbService;

    /** CRIANDO ATRIBUTO PARA VALIDAR A CRIACAO DO BANCO SOMENTE QUANDO O DDL FOR CREATE **/
    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlGeracaoBanco;

    @Bean
    public boolean instanciandoBandoDeDados() throws ParseException {
        /** SE FOR DIFERENTE DE CREATE ELE ENTRA E NAO RECRIA O BANCO **/


        return true;
    }

}
