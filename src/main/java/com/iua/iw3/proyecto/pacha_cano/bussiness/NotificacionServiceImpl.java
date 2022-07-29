package com.iua.iw3.proyecto.pacha_cano.bussiness;

import com.iua.iw3.proyecto.pacha_cano.exceptions.BusinessException;
import com.iua.iw3.proyecto.pacha_cano.model.Notificacion;
import com.iua.iw3.proyecto.pacha_cano.model.accounts.User;
import com.iua.iw3.proyecto.pacha_cano.model.accounts.UserRepository;
import com.iua.iw3.proyecto.pacha_cano.persistence.NotificacionesRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class NotificacionServiceImpl implements NotificacionService {

    private final NotificacionesRepository notificacionRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final SimpMessagingTemplate messagingTemplate;


    @Override
    public void nuevaNotificacionUsuario(String nombreEvento, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow();
        this.messagingTemplate.convertAndSend("/queue/" + user.getUsername() + "/private-notifications",
                this.notificacionRepository.save(Notificacion.builder()
                        .titulo("Notificacion")
                        .contenido("contenido notificacion")
                        .actionUrl("/user")
                        .evento("Mirá el detalle aquí!")
                        .fechaNotificacion(new Date())
                        .userId(userId)
                        .build()));
    }


    @Override
    public List<Notificacion> notificacionesPorUsuario() {
        User userActual = this.authService.getUserActual();
        return this.notificacionRepository.findAllByUserId(userActual.getId());
    }


    @Override
    public Notificacion leerNotificacion(Long idNotificacion) {
        Notificacion aux = this.notificacionRepository.findById(idNotificacion)
                .orElseThrow();
        aux.setLeida(true);
        return this.saveNotificacion(aux);
    }

    @Override
    public void borrarNotificacion(Long idNotificacion) {
        this.notificacionRepository.deleteById(idNotificacion);
    }

    @Override
    public Notificacion saveNotificacion(Notificacion notificacion) {
        return this.notificacionRepository.save(notificacion);
    }

}
