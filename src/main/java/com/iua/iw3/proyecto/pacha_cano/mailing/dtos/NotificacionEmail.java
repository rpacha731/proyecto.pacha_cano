package com.iua.iw3.proyecto.pacha_cano.mailing.dtos;

public interface NotificacionEmail {

    String getAsunto();

    String getDestinatario();

    Object getBody();

    String getRedirectUrl();
}
