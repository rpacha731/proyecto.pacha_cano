package com.iua.iw3.proyecto.pacha_cano.persistence;

import com.iua.iw3.proyecto.pacha_cano.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
