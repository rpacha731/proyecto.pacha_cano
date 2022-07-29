package com.iua.iw3.proyecto.pacha_cano.bussiness;

import com.iua.iw3.proyecto.pacha_cano.model.Notificacion;

import java.util.List;

public interface NotificacionService {

    void nuevaNotificacionUsuario(String nombreEvento, Long userId);

    List<Notificacion> notificacionesPorUsuario();

    Notificacion leerNotificacion(Long idNotificacion);

    void borrarNotificacion(Long idNotificacion);

    Notificacion saveNotificacion(Notificacion notificacion);

}
