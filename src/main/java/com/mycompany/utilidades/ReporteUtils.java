/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.utilidades;

import com.mycompany.modelo.Celular;
import java.util.List;
import java.util.stream.Collectors;
import com.mycompany.modelo.ItemVenta;
import com.mycompany.modelo.Venta;
import java.math.BigDecimal;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.TreeMap;

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
    
    public static String generarTopTres(List<Venta> ventas) {

    List<ItemVenta> detalles = ventas.stream()
            .flatMap(venta -> venta.getItems().stream())
            .collect(Collectors.toList());

    Map<Integer, Long> cantidades = detalles.stream()
            .collect(Collectors.groupingBy(
                item -> item.getCelular().getId(),
                Collectors.summingLong(ItemVenta::getCantidad)
            ));

    Map<Integer, String> nombres = detalles.stream()
            .collect(Collectors.toMap(
                item -> item.getCelular().getId(),
                item -> item.getCelular().getMarca()
                    + " " + item.getCelular().getModelo(),
                (nombreAnterior, nombreNuevo) -> nombreAnterior
            ));

    StringBuilder reporte = new StringBuilder();
    reporte.append("TOP 3 DE CELULARES MÁS VENDIDOS\n");

    if (cantidades.isEmpty()) {
        reporte.append("Todavía no hay celulares vendidos.\n");
    }

    cantidades.entrySet().stream()
            .sorted((primero, segundo) -> {

                int comparacion = Long.compare(
                    segundo.getValue(), primero.getValue()
                );

                if (comparacion == 0) {
                    return Integer.compare(
                        primero.getKey(), segundo.getKey()
                    );
                }

                return comparacion;
            })
            .limit(3)
            .forEach(elemento -> reporte
                .append("ID: ").append(elemento.getKey())
                .append(" | ").append(nombres.get(elemento.getKey()))
                .append(" | Unidades: ").append(elemento.getValue())
                .append("\n")
            );

    return reporte.toString();
}

public static String generarVentasPorMes(List<Venta> ventas) {

    Map<YearMonth, BigDecimal> totales = ventas.stream()
            .collect(Collectors.groupingBy(
                venta -> YearMonth.from(venta.getFecha()),
                TreeMap::new,
                Collectors.reducing(
                    new BigDecimal("0.00"),
                    Venta::calcularTotal,
                    BigDecimal::add
                )
            ));

    StringBuilder reporte = new StringBuilder();
    reporte.append("VENTAS POR MES — INCLUYEN IVA\n");

    if (totales.isEmpty()) {
        reporte.append("Todavía no hay ventas.\n");
    }

    totales.forEach((mes, total) -> reporte
            .append(mes)
            .append(" | Total: $").append(total)
            .append("\n")
    );

    return reporte.toString();
}

public static String generarResumenVentas(List<Venta> ventas) {

    StringBuilder reporte = new StringBuilder();
    reporte.append("RESUMEN DE TODAS LAS VENTAS\n");

    DateTimeFormatter formato =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    if (ventas.isEmpty()) {
        reporte.append("Todavía no hay ventas.\n");
    }

    for (Venta venta : ventas) {

        reporte.append("\nVenta: ").append(venta.getId())
                .append(" | Cliente: ")
                .append(venta.getCliente().getNombre())
                .append(" | Fecha: ")
                .append(venta.getFecha().format(formato))
                .append("\n");

        for (ItemVenta item : venta.getItems()) {

            reporte.append("  ")
                    .append(item.getCelular().getMarca())
                    .append(" ")
                    .append(item.getCelular().getModelo())
                    .append(" | Cantidad: ").append(item.getCantidad())
                    .append(" | Subtotal: $")
                    .append(item.calcularSubtotal())
                    .append("\n");
        }

        reporte.append("Subtotal: $").append(venta.calcularSubtotal())
                .append(" | IVA: $").append(venta.calcularIva())
                .append(" | Total: $").append(venta.calcularTotal())
                .append("\n");
    }

    return reporte.toString();
    }
}