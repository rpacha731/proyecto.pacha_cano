package com.iua.iw3.proyecto.pacha_cano.model.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.iua.iw3.proyecto.pacha_cano.model.Chofer;

import java.io.IOException;

public class ChoferJsonSerializer extends StdSerializer<Chofer> {

    public ChoferJsonSerializer(Class<Chofer> t) { super(t); }

    @Override
    public void serialize(Chofer value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        {
            gen.writeNumberField("id", value.getId());
            gen.writeStringField("nombre", value.getNombre());
            gen.writeStringField("apellido", value.getApellido());
            gen.writeNumberField("dni", value.getDni());
        }
        gen.writeEndObject();
    }
}
