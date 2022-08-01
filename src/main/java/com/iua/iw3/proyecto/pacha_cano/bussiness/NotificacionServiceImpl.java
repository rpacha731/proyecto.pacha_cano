package com.iua.iw3.proyecto.pacha_cano.bussiness;

import com.iua.iw3.proyecto.pacha_cano.exceptions.BusinessException;
import com.iua.iw3.proyecto.pacha_cano.exceptions.MailSenderException;
import com.iua.iw3.proyecto.pacha_cano.mailing.dtos.SimpleNotificacionEmail;
import com.iua.iw3.proyecto.pacha_cano.mailing.services.MailService;
import com.iua.iw3.proyecto.pacha_cano.model.Notificacion;
import com.iua.iw3.proyecto.pacha_cano.model.accounts.User;
import com.iua.iw3.proyecto.pacha_cano.model.accounts.UserRepository;
import com.iua.iw3.proyecto.pacha_cano.persistence.NotificacionesRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
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

    private final MailService mailService;


    @Override
    @Async
    public void nuevaNotificacionUsuario(Long userId, Float temperatura, Long numeroOrden) {
        User user = userRepository.findById(userId)
                .orElseThrow();
        List<Notificacion> notificaciones = notificacionRepository.findAllByNumeroOrdenAndLeidaIsFalse(numeroOrden);
        log.info("Notificaciones leidas: {}", notificaciones.size());
        if (notificaciones.isEmpty()) {
            this.messagingTemplate.convertAndSend("/queue/" + user.getUsername() + "/private-notifications",
                    this.notificacionRepository.save(Notificacion.builder()
                            .titulo("Alerta de temperatura")
                            .contenido("La temperatura de carga acaba de superar el umbral, valor actual de " + temperatura + " ºC")
                            .numeroOrden(numeroOrden)
                            .fechaNotificacion(new Date())
                            .userId(userId)
                            .leida(false)
                            .build()));
            try {
                this.enviarMail(numeroOrden, temperatura, user.getEmail());
            } catch (BusinessException e) {
                log.error("Error al enviar email: " + e.getMessage());
            }
        } else
            log.info("No se ha enviado nueva notificación para el usuario " + user.getUsername());
    }

    @Async
    public void enviarMail(Long numeroOrden, Float temperatura, String email) throws BusinessException {
        try {
            this.mailService.sendEmail(SimpleNotificacionEmail.builder()
                    .asunto("Alerta de temperatura")
                    .body("Recibiste una notificación porque la temperatura de la orden " + numeroOrden + " superó el umbral de temperatura.\n" +
                            "La temperatura actual es de " + temperatura + " °C.\n" +
                            "Para ver el detalle de la orden, ingresa a <a href=\"https://cpsoa.mooo.com\" target=\"blank\">https://cpsoa.mooo.com</a>")
                    .destinatario(email)
                    .title("Se ha superado el umbral de temperatura de la orden " + numeroOrden).build());
        } catch (MailSenderException e) {
            throw new BusinessException(e.getMessage());
        }

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
    public Notificacion saveNotificacion(Notificacion notificacion) {
        return this.notificacionRepository.save(notificacion);
    }

}
