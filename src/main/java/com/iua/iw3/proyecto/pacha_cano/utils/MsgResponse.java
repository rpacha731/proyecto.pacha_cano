package com.iua.iw3.proyecto.pacha_cano.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@AllArgsConstructor
@Data
@ToString
public class MsgResponse {
    private Integer code;
    private String message = "OK";
}
