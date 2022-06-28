package com.iua.iw3.proyecto.pacha_cano.model.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.iua.iw3.proyecto.pacha_cano.model.Camion;
import com.iua.iw3.proyecto.pacha_cano.utils.requests.MsgResponse;

import java.io.IOException;

public class
MsgResponseJsonSerializer extends StdSerializer<MsgResponse> {

    public MsgResponseJsonSerializer() { this(null); }

    public MsgResponseJsonSerializer(Class<MsgResponse> t) { super(t); }

    @Override
    public void serialize(MsgResponse value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        {
            gen.writeNumberField("code", value.getCode());
            gen.writeStringField("message", value.getMessage());
        }
        gen.writeEndObject();
    }
}
