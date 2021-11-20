package com.iua.iw3.proyecto.pacha_cano.persistence;

import com.iua.iw3.proyecto.pacha_cano.model.Chofer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChoferRepository extends JpaRepository<Chofer, Long> {

    Optional<Chofer> findByDni (Long dni);
}
