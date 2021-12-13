package com.virtual.store.services;

import com.virtual.store.security.UserSS;
import org.springframework.security.core.context.SecurityContextHolder;

public class UsuarioLogadoService {

    public static UserSS usuarioAutenticado() {
        try {
            /** retornando usuário logado **/
            return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        catch (Exception e) {
            return null;
        }
    }
}
