package com.iua.iw3.proyecto.pacha_cano.model.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.iua.iw3.proyecto.pacha_cano.model.Cliente;

import java.io.IOException;

public class ClienteJsonSerializer extends StdSerializer<Cliente> {

    public ClienteJsonSerializer() { this(null); }

    public ClienteJsonSerializer(Class<Cliente> t) { super(t); }

    @Override
    public void serialize(Cliente value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        {
            gen.writeNumberField("id", value.getId());
            gen.writeStringField("razonSocial", value.getRazonSocial());
            gen.writeStringField("contacto", value.getContacto());
            gen.writeStringField("codigoExterno", value.getCodigoExterno());
        }
        gen.writeEndObject();
    }
}
