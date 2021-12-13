package com.virtual.store.security;

import com.virtual.store.domain.enums.PerfilUsuario;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class UserSS implements UserDetails {
    private Integer id;
    private String email;
    private String senha;
    private Collection<? extends GrantedAuthority> authorities;

    public Integer getId()  {
        return id;
    }

    public UserSS() {}

    public UserSS(Integer id, String email, String senha, Set<PerfilUsuario> perfilUsuarios) {
        this.id = id;
        this.email = email;
        this.senha = senha;
        /** pegando a lista de perfis e convertendo cada elemento para o tipo GrantedAuthority e salvando na lista de authorities **/
        this.authorities = perfilUsuarios.stream().map(perfil -> new SimpleGrantedAuthority(perfil.getDescricao())).collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    /** retorna expressao verdadeira caso exista o perfil do usuario informado **/
    public boolean existePerfil(PerfilUsuario perfilUsuario) {
        return getAuthorities().contains(new SimpleGrantedAuthority(perfilUsuario.getDescricao()));
    }
}
