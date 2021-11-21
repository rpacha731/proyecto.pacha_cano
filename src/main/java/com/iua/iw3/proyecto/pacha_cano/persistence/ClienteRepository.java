package com.iua.iw3.proyecto.pacha_cano.persistence;

import com.iua.iw3.proyecto.pacha_cano.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByRazonSocial (String razonSocial);

    Optional<Cliente> findByCodigoExterno (String codigoExterno);
}
