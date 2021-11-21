package com.iua.iw3.proyecto.pacha_cano.persistence;

import com.iua.iw3.proyecto.pacha_cano.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    Optional<Producto> findByNombre (String nombre);

    Optional<Producto> findByCodigoExterno (String codigoExterno);

}
