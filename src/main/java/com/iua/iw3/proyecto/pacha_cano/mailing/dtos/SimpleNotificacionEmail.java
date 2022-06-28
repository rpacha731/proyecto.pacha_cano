package com.iua.iw3.proyecto.pacha_cano.mailing.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SimpleNotificacionEmail implements NotificacionEmail {

    private String asunto;
    private String destinatario;
    private String body;
    private String redirectUrl;
    private String title;

    @Override
    public String getAsunto() { return this.asunto; }

    @Override
    public String getDestinatario() { return this.destinatario; }

    @Override
    public Object getBody() {
        return this.body;
    }

    @Override
    public String getRedirectUrl() {
        return this.redirectUrl;
    }
}
