package com.iua.iw3.proyecto.pacha_cano.model.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.iua.iw3.proyecto.pacha_cano.model.*;
import com.iua.iw3.proyecto.pacha_cano.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
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

            gen.writeNumberField("pesoInicial", value.getPesoInicial() == null ? -1 : value.getPesoInicial());
            gen.writeNumberField("pesoFinal", value.getPesoFinal() == null ? -1 : value.getPesoFinal());
            gen.writeNumberField("frecuencia", value.getFrecuencia());
            gen.writeNumberField("password", value.getPassword() == null ? -1 : value.getPassword());
            gen.writeStringField("estado", value.getEstado().toString());
            gen.writeStringField("fechaHoraRecepcion", value.getFechaHoraRecepcion() == null ? "null" : value.getFechaHoraRecepcion().toString());
            gen.writeStringField("fechaHoraPesoInicial", value.getFechaHoraPesoInicial() == null ? "null" : value.getFechaHoraPesoInicial().toString());
            gen.writeStringField("fechaHoraTurno", value.getFechaHoraTurno() == null ? "null" : value.getFechaHoraTurno().toString());
            gen.writeNumberField("preset", value.getPreset() == null ? -1 : value.getPreset());
            gen.writeStringField("fechaHoraInicioCarga", value.getFechaHoraInicioCarga() == null ? "null" : value.getFechaHoraInicioCarga().toString());
            gen.writeStringField("fechaHoraFinCarga", value.getFechaHoraFinCarga() == null ? "null" : value.getFechaHoraFinCarga().toString());
            gen.writeStringField("fechaHoraPesoFinal", value.getFechaHoraPesoFinal() == null ? "null" : value.getFechaHoraPesoFinal().toString());

            { // Datos Carga Registro
                String datosCargaHistoStr = JsonUtils.getObjectMapper(DatosCarga.class, new DatosCargaJsonSerializer(DatosCarga.class), null)
                        .writeValueAsString(value.getRegistroDatosCarga());
                gen.writeFieldName("registroDatosCarga");
                gen.writeRawValue(datosCargaHistoStr);
            }

            // Promedios y masa acumulada final
            gen.writeStringField("masaAcumuladaTotal", value.getMasaAcumuladaTotal() == null ? "null" : value.getMasaAcumuladaTotal().toString());
            gen.writeStringField("temperaturaPromedio", value.getTemperaturaPromedio() == null ? "null" : value.getTemperaturaPromedio().toString());
            gen.writeStringField("densidadPromedio", value.getDensidadPromedio() == null ? "null" : value.getDensidadPromedio().toString());
            gen.writeStringField("caudalPromedio", value.getCaudalPromedio() == null ? "null" : value.getCaudalPromedio().toString());

        }
        gen.writeEndObject();
    }
}
