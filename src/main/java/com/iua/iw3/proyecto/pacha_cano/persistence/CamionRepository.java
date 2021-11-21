package com.iua.iw3.proyecto.pacha_cano.persistence;

import com.iua.iw3.proyecto.pacha_cano.model.Camion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CamionRepository extends JpaRepository<Camion, Long> {

    Optional<Camion> findByPatente (String patente);

    Optional<Camion> findByCodigoExterno (String codigoEsterno);

}
