package com.iua.iw3.proyecto.pacha_cano.mailing.services;

import com.iua.iw3.proyecto.pacha_cano.exceptions.MailSenderException;
import com.iua.iw3.proyecto.pacha_cano.mailing.dtos.NotificacionEmail;
import com.iua.iw3.proyecto.pacha_cano.mailing.dtos.SimpleNotificacionEmail;
import dev.ditsche.mailo.MailAddress;
import dev.ditsche.mailo.factory.MailBuilder;
import dev.ditsche.mailo.provider.MailProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class MailServiceImpl implements MailService {

    private final MailProvider mailProvider;
    private String mailFrom;


    @Override
    @Async
    public void sendEmail(NotificacionEmail notificationEmail) throws MailSenderException {
        SimpleNotificacionEmail notificacionEmail = (SimpleNotificacionEmail) notificationEmail;

        MailBuilder mailBuilder = MailBuilder.mjml()
                .subject(notificacionEmail.getAsunto())
                .to(new MailAddress(notificacionEmail.getDestinatario()))
                .from(new MailAddress(mailFrom))
                .param("title", notificacionEmail.getTitle())
                .param("body", notificacionEmail.getBody())
                .loadTemplate("mailTemplate");

        try {
            this.mailProvider.send(mailBuilder);
            log.info("Email enviado!");
        } catch (Exception e) {
            throw new MailSenderException("Excepción al enviar email: " + e.getMessage());
        }
    }
}
