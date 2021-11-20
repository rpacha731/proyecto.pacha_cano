package com.iua.iw3.proyecto.pacha_cano.bussiness;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

@Component
@Slf4j
public class CreateCSVDatosCarga implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        File file = new File("datosCarga.csv");
        if (!file.exists()) {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
            bufferedWriter.write("numeroOrden,password,masaAcumulada,densidad,temperatura,caudal");
            bufferedWriter.newLine();

            String [] datos = new String[6];
            datos[0] = "154";
            datos[1] = "12345";

            for (int i = 1; i < 10001; i++) {
                datos[2] = String.valueOf(i);
                datos[3] = String.valueOf((1 * i)/i);
                datos[4] = "1";
                datos[5] = String.valueOf(10 + (1 * i)/i);
                bufferedWriter.write(String.join(",",datos));
                bufferedWriter.newLine();
            }

            bufferedWriter.close();

            log.warn("EXITO");

        }
    }
}
