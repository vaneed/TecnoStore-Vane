/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.utilidades;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class ArchivoUtils {

    public static Path guardarReporte(String contenido)
            throws IOException {

        Path ruta = Path.of("reporte_ventas.txt").toAbsolutePath();

        try (BufferedWriter escritor = Files.newBufferedWriter(
                ruta, StandardCharsets.UTF_8)) {

            escritor.write(contenido);
        }

        return ruta;
    }
}
