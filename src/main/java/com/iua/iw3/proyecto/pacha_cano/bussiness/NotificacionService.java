package com.iua.iw3.proyecto.pacha_cano.bussiness;

import com.iua.iw3.proyecto.pacha_cano.exceptions.BusinessException;
import com.iua.iw3.proyecto.pacha_cano.model.Notificacion;

import java.util.List;

public interface NotificacionService {

    void nuevaNotificacionUsuario(Long userId, Float temperatura, Long numeroOrden);
    List<Notificacion> notificacionesPorUsuario();
    void leerNotificacion(Long idNotificacion) throws BusinessException;
}
