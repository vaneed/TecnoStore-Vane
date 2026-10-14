/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.utilidades;

import com.mycompany.modelo.Celular;
import java.util.List;
import java.util.stream.Collectors;

public class ReporteUtils {

    public static String generarStockBajo(List<Celular> celulares) {

        List<Celular> stockBajo = celulares.stream()
                .filter(celular -> celular.getStock() < 5)
                .collect(Collectors.toList());

        StringBuilder reporte = new StringBuilder();

        reporte.append("CELULARES CON STOCK BAJO\n");

        if (stockBajo.isEmpty()) {
            reporte.append("No hay celulares con menos de 5 unidades.\n");
        } else {

            for (Celular celular : stockBajo) {
                reporte.append("ID: ").append(celular.getId())
                        .append(" | ").append(celular.getMarca())
                        .append(" ").append(celular.getModelo())
                        .append(" | Stock: ").append(celular.getStock())
                        .append("\n");
            }
        }

        return reporte.toString();
    }
}