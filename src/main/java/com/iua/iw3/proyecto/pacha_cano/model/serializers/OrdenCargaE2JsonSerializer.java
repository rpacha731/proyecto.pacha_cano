package com.iua.iw3.proyecto.pacha_cano.model.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.iua.iw3.proyecto.pacha_cano.model.*;
import com.iua.iw3.proyecto.pacha_cano.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class OrdenCargaE2JsonSerializer extends StdSerializer<OrdenCarga> {

    public OrdenCargaE2JsonSerializer(Class<OrdenCarga> t) { super(t); }

    @Override
    public void serialize(OrdenCarga value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        {
            gen.writeNumberField("id", value.getId());
            gen.writeNumberField("numeroOrden", value.getNumeroOrden());

            { //Camion
                String camionStr = JsonUtils.getObjectMapper(Camion.class, new CamionJsonSerializer(Camion.class), null)
                        .writeValueAsString(value.getCamion());
                gen.writeFieldName("camion");
                gen.writeRawValue(camionStr);
            }

            { //Cliente
                String clienteStr = JsonUtils.getObjectMapper(Cliente.class, new ClienteJsonSerializer(Cliente.class), null)
                        .writeValueAsString(value.getCliente());
                gen.writeFieldName("cliente");
                gen.writeRawValue(clienteStr);
            }

            { //Chofer
                String choferStr = JsonUtils.getObjectMapper(Chofer.class, new ChoferJsonSerializer(Chofer.class), null)
                        .writeValueAsString(value.getChofer());
                gen.writeFieldName("chofer");
                gen.writeRawValue(choferStr);
            }

            { //Producto
                String productoStr = JsonUtils.getObjectMapper(Producto.class, new ProductoJsonSerializer(Producto.class), null)
                        .writeValueAsString(value.getProducto());
                gen.writeFieldName("producto");
                gen.writeRawValue(productoStr);
            }

            gen.writeNumberField("frecuencia", value.getFrecuencia());
            gen.writeStringField("estado", value.getEstado().toString());
            gen.writeStringField("fechaHoraTurno", value.getFechaHoraTurno().toString());
            gen.writeNumberField("preset", value.getPreset());
            gen.writeNumberField("pesoInicial", value.getPesoInicial());
            gen.writeNumberField("password", value.getPassword());
            gen.writeStringField("fechaHoraPesoInicial", value.getFechaHoraPesoInicial().toString());


        }
        gen.writeEndObject();
    }
}
