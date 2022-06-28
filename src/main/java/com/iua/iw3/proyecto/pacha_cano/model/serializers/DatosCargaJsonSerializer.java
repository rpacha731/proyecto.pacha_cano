package com.iua.iw3.proyecto.pacha_cano.model.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.iua.iw3.proyecto.pacha_cano.model.DatosCarga;

import java.io.IOException;

public class DatosCargaJsonSerializer extends StdSerializer<DatosCarga> {

    public DatosCargaJsonSerializer() { this(null); }

    public DatosCargaJsonSerializer(Class<DatosCarga> t) { super(t); }

    @Override
    public void serialize(DatosCarga value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        {
            gen.writeNumberField("id", value.getId());
            gen.writeNumberField("masaAcumulada", value.getMasaAcumulada());
            gen.writeNumberField("temperatura", value.getTemperatura());
            gen.writeNumberField("densidad", value.getDensidad());
            gen.writeNumberField("caudal", value.getCaudal());
            gen.writeStringField("estampaTiempo", value.getEstampaTiempo() != null ? value.getEstampaTiempo().toString() : "");
        }
        gen.writeEndObject();
    }
}
