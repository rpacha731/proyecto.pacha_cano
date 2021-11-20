package com.iua.iw3.proyecto.pacha_cano.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class MsgResponse {
    private Integer code;
    private String message = "OK";

    @Override
    public String toString() {
        return "{ código = " + code +", mensaje = '" + message + "' }";
    }
}
