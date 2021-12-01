package com.iua.iw3.proyecto.pacha_cano.model.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.iua.iw3.proyecto.pacha_cano.model.Conciliacion;

import java.io.IOException;

public class ConciliacionJsonSerializer extends StdSerializer<Conciliacion> {

    public ConciliacionJsonSerializer(Class<Conciliacion> t) { super(t); }

    @Override
    public void serialize(Conciliacion value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        {
            gen.writeNumberField("numeroOrden", value.getNumeroOrden());
            gen.writeNumberField("pesoInicial", value.getPesoInicial());
            gen.writeNumberField("pesoFinal", value.getPesoFinal());
            gen.writeNumberField("CantProductoCargado", value.getMasaAcumuladaTotal());
            gen.writeNumberField("netoBalanza", value.getNetoBalanza());
            gen.writeNumberField("diferenciaBalanzaYCaudal", value.getDifBalanzaYCaudal());
            gen.writeNumberField("temperaturaPromedio", value.getTemperaturaPromedio());
            gen.writeNumberField("densidadPromedio", value.getDensidadPromedio());
            gen.writeNumberField("caudalPromedio", value.getCaudalPromedio());
        }
        gen.writeEndObject();
    }
}
