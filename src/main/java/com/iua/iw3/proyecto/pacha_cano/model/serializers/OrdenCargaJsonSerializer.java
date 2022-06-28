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

    public OrdenCargaJsonSerializer() {this(null);}

    public OrdenCargaJsonSerializer(Class<OrdenCarga> t) { super(t); }

    @Override
    public void serialize(OrdenCarga value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        {
            gen.writeNumberField("id", value.getId());
            gen.writeNumberField("numeroOrden", value.getNumeroOrden());
            gen.writeStringField("codigoExterno", value.getCodigoExterno());
            gen.writeObjectField("camion", value.getCamion());
            gen.writeObjectField("cliente", value.getCliente());
            gen.writeObjectField("chofer", value.getChofer());
            gen.writeObjectField("producto", value.getProducto());
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
            gen.writeObjectField("registroDatosCarga", value.getRegistroDatosCarga());
            gen.writeStringField("masaAcumuladaTotal", value.getMasaAcumuladaTotal() == null ? "null" : value.getMasaAcumuladaTotal().toString());
            gen.writeStringField("temperaturaPromedio", value.getTemperaturaPromedio() == null ? "null" : value.getTemperaturaPromedio().toString());
            gen.writeStringField("densidadPromedio", value.getDensidadPromedio() == null ? "null" : value.getDensidadPromedio().toString());
            gen.writeStringField("caudalPromedio", value.getCaudalPromedio() == null ? "null" : value.getCaudalPromedio().toString());
        }
        gen.writeEndObject();
    }
}
