package com.iua.iw3.proyecto.pacha_cano.model.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.iua.iw3.proyecto.pacha_cano.model.Camion;

import java.io.IOException;

public class
CamionJsonSerializer extends StdSerializer<Camion> {

    public CamionJsonSerializer () { this(null); }

    public CamionJsonSerializer (Class<Camion> t) { super(t); }

    @Override
    public void serialize(Camion value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        {
            gen.writeNumberField("id", value.getId());
            gen.writeStringField("patente", value.getPatente());
            gen.writeStringField("descripcion", value.getDescripcion());
            gen.writeNumberField("cisternado", value.getCisternado());
        }
        gen.writeEndObject();
    }
}
