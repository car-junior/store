package com.virtual.store.repositories;

import com.virtual.store.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    /** Transactional(readOnly = true) informa ao BD que é apenas uma consulta o que deixa mais rápido esse select **/
    @Transactional(readOnly = true)
    Cliente findByEmail(String email);

    @Transactional(readOnly = true)
    Cliente findByCpfOuCnpj(String cpfOuCnpj);
}
