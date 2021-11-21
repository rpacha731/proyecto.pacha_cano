package com.iua.iw3.proyecto.pacha_cano.bussiness;

import lombok.extern.slf4j.Slf4j;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Slf4j
public class CreateCSVDatosCarga {

    public static String generateCSV (Long numOrden) throws IOException {
        File file = new File("datosCarga-" + numOrden.intValue() + ".csv");
        if (!file.exists()) {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write("masaAcumulada,densidad,temperatura,caudal");
            bufferedWriter.newLine();

            String [] datos = new String[4];

            for (int i = 1; i < 10001; i++) {
                datos[0] = String.valueOf(i);
                datos[1] = String.valueOf((1 * i)/i);
                datos[2] = "1";
                datos[3] = String.valueOf(10 + (1 * i)/i);
                bufferedWriter.write(String.join(",",datos));
                bufferedWriter.newLine();
            }

            bufferedWriter.close();

            log.warn("EXITO");

            return file.getName();
        }
        return null;
    }

}
