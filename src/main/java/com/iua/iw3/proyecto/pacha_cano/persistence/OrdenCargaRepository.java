package com.iua.iw3.proyecto.pacha_cano.persistence;

import com.iua.iw3.proyecto.pacha_cano.model.Estados;
import com.iua.iw3.proyecto.pacha_cano.model.OrdenCarga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrdenCargaRepository extends JpaRepository<OrdenCarga, Long> {

    Optional<OrdenCarga> findByNumeroOrden(Long numeroOrden);

    List<OrdenCarga> findAllByEstado (Estados estados);

}
