package com.iua.iw3.proyecto.pacha_cano.mailing.services;

import com.iua.iw3.proyecto.pacha_cano.exceptions.MailSenderException;
import com.iua.iw3.proyecto.pacha_cano.mailing.dtos.NotificacionEmail;

public interface MailService {

    void sendEmail(NotificacionEmail notificationEmail) throws MailSenderException;
}
