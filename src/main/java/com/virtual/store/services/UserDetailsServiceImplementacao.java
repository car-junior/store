package com.virtual.store.services;

import com.virtual.store.domain.Cliente;
import com.virtual.store.repositories.ClienteRepository;
import com.virtual.store.security.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/** essa classe permite a busca do usuário por meio do login **/
@Service
public class UserDetailsServiceImplementacao implements UserDetailsService {
    @Autowired
    private ClienteRepository clienteRepository;

    /** método que busca o cliente por meio do email informado **/
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Cliente cliente = clienteRepository.findByEmail(email);
        if (cliente == null) {
            throw new UsernameNotFoundException(email);
        }
        /** instanciando um UserSS a partir de um cliente existente no banco **/
        return new UserSS(cliente.getId(), cliente.getEmail(), cliente.getSenha(), cliente.getPerfis());
    }
}
