package com.iua.iw3.proyecto.pacha_cano.persistence;

import com.iua.iw3.proyecto.pacha_cano.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacionesRepository extends JpaRepository<Notificacion, Long> {
    List<Notificacion> findAllByUserId (Long userId);

    List<Notificacion> findAllByNumeroOrdenAndLeidaIsFalse(Long numeroOrden);
}
