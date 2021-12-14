package com.virtual.store.resources;

import com.virtual.store.domain.dto.EmailDTO;
import com.virtual.store.security.JWTUtil;
import com.virtual.store.security.UserSS;
import com.virtual.store.services.AutorizacaoService;
import com.virtual.store.services.UsuarioLogadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/autorizacao")
public class AutorizacaoResource {
    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private AutorizacaoService autorizacaoService;

    @PostMapping(value = "/atualizar_token")
    public ResponseEntity<Void> atualizarToken(HttpServletResponse response) {
        UserSS usuario = UsuarioLogadoService.usuarioAutenticado();
        String token = jwtUtil.gerarToken(usuario.getUsername());
        response.addHeader("Authorization", "Bearer " + token);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/recuperar_senha")
    public ResponseEntity<?> recuperarSenha(@Valid @RequestBody EmailDTO emailDTO){
        autorizacaoService.enviarNovaSenhaCliente(emailDTO.getEmail());
        return ResponseEntity.noContent().build();
    }
}
