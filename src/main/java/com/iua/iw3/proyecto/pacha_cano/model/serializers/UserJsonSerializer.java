package com.iua.iw3.proyecto.pacha_cano.model.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.iua.iw3.proyecto.pacha_cano.model.Camion;
import com.iua.iw3.proyecto.pacha_cano.model.accounts.User;

import java.io.IOException;

public class
UserJsonSerializer extends StdSerializer<User> {

    public UserJsonSerializer() { this(null); }

    public UserJsonSerializer(Class<User> t) { super(t); }

    @Override
    public void serialize(User value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        {
            gen.writeNumberField("id", value.getId());
            gen.writeStringField("nombre", value.getNombre());
            gen.writeStringField("apellido", value.getApellido());
            gen.writeStringField("email", value.getEmail());
            gen.writeBooleanField("enabled", value.isEnabled());
            gen.writeObjectField("roles", value.getRoles());
        }
        gen.writeEndObject();
    }
}
