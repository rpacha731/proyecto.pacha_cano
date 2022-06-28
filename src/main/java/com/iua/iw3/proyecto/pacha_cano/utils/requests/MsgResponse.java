package com.iua.iw3.proyecto.pacha_cano.utils.requests;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.iua.iw3.proyecto.pacha_cano.model.serializers.MsgResponseJsonSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
@Data
@JsonSerialize(using = MsgResponseJsonSerializer.class)
public class MsgResponse implements Serializable {
    private Integer code;
    private String message;
}
