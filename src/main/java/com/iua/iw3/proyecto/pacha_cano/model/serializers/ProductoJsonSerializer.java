package com.iua.iw3.proyecto.pacha_cano.model.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.iua.iw3.proyecto.pacha_cano.model.Camion;
import com.iua.iw3.proyecto.pacha_cano.model.Producto;

import java.io.IOException;

public class ProductoJsonSerializer extends StdSerializer<Producto> {

    public ProductoJsonSerializer(Class<Producto> t) { super(t); }

    @Override
    public void serialize(Producto value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        {
            gen.writeNumberField("id", value.getId());
            gen.writeStringField("nombre", value.getNombre());
            gen.writeStringField("descripcion", value.getDescripcion());
        }
        gen.writeEndObject();
    }
}
