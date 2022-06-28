package com.iua.iw3.proyecto.pacha_cano.model.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.iua.iw3.proyecto.pacha_cano.model.accounts.Rol;
import com.iua.iw3.proyecto.pacha_cano.model.accounts.User;

import java.io.IOException;

public class
RolJsonSerializer extends StdSerializer<Rol> {

    public RolJsonSerializer() { this(null); }

    public RolJsonSerializer(Class<Rol> t) { super(t); }

    @Override
    public void serialize(Rol value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        {
            gen.writeNumberField("id", value.getId());
            gen.writeStringField("nombre", value.getNombre());
            gen.writeStringField("descripcion", value.getDescripcion());
        }
        gen.writeEndObject();
    }
}
