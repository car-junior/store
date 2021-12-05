package com.virtual.store.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class URL {

    /** METODO PARA FAZER ENCODE DE NOME DE DESFAZER ENCODE **/
    public static String decodeParam(String nome) {
        try {
            return URLDecoder.decode(nome, "UTF-8");
        }
        catch (UnsupportedEncodingException e){
            return "";
        }

    }

    /** METODO PARA TRANSFORMAR A LISTA DE IDS DE CATEGORIAS NO FORMATO STRING PARA LISTA DE INTEGER **/
    public static List<Integer> decodeIdsList(String idsString){
        /*        List<Integer> ids = new ArrayList<>();
                String[] vetorIds = idsString.split(",");

                for (String s : vetorIds){
                    ids.add(Integer.parseInt(s));
                }
                return ids;*/
        /** SEGUNDA OPCAO DE CONVERSAO E SPLIT **/
        return Arrays.asList(idsString.split(",")).stream()
                .map(elemento -> Integer.parseInt(elemento)).collect(Collectors.toList());

    }
}
