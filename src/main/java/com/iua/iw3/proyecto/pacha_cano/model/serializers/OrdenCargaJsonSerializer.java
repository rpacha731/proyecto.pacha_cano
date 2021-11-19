package com.iua.iw3.proyecto.pacha_cano.model.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.iua.iw3.proyecto.pacha_cano.model.*;
import com.iua.iw3.proyecto.pacha_cano.utils.JsonUtils;

import java.io.IOException;

public class OrdenCargaJsonSerializer extends StdSerializer<OrdenCarga> {

    public OrdenCargaJsonSerializer(Class<OrdenCarga> t) { super(t); }

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

            gen.writeNumberField("pesoInicial", value.getPesoInicial());
            gen.writeNumberField("pesoFinal", value.getPesoFinal());
            gen.writeNumberField("frecuencia", value.getFrecuencia());
            gen.writeNumberField("password", value.getPassword());
            gen.writeStringField("estado", value.getEstado().toString());
            gen.writeStringField("fechaHoraRecepcion", value.getFechaHoraRecepcion().toString());
            gen.writeStringField("fechaHoraPesoInicial", value.getFechaHoraPesoInicial().toString());
            gen.writeStringField("fechaHoraTurno", value.getFechaHoraTurno().toString());
            gen.writeNumberField("preset", value.getPreset());
            gen.writeStringField("fechaHoraInicioCarga", value.getFechaHoraInicioCarga().toString());
            gen.writeStringField("fechaHoraFinCarga", value.getFechaHoraFinCarga().toString());
            gen.writeStringField("fechaHoraPesoFinal", value.getFechaHoraPesoFinal().toString());

            { // Datos Carga Promedio
                String datosCargaStr = JsonUtils.getObjectMapper(DatosCarga.class, new DatosCargaJsonSerializer(DatosCarga.class), null)
                        .writeValueAsString(value.getDatosCargaProm());
                gen.writeFieldName("datosCargaProm");
                gen.writeRawValue(datosCargaStr);
            }

            { // Datos Carga Historicos
                String datosCargaHistoStr = JsonUtils.getObjectMapper(DatosCarga.class, new DatosCargaJsonSerializer(DatosCarga.class), null)
                        .writeValueAsString(value.getDatosCargaHistorico());
                gen.writeFieldName("datosCargaHistorico");
                gen.writeRawValue(datosCargaHistoStr);
            }

        }
        gen.writeEndObject();
    }
}
