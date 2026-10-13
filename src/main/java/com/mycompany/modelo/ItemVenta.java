/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.modelo;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ItemVenta {
    private final Celular celular;
    private final int cantidad;
    private final BigDecimal precioUnitario;

    public ItemVenta(Celular celular, int cantidad) {

        if (celular == null) {
            throw new IllegalArgumentException(
                "Debes seleccionar un celular."
            );
        }

        if (cantidad <= 0) {
            throw new IllegalArgumentException(
                "La cantidad debe ser mayor que cero."
            );
        }

        if (cantidad > celular.getStock()) {
            throw new IllegalArgumentException(
                "No hay suficiente stock de " + celular.getModelo()
            );
        }

        this.celular = celular;
        this.cantidad = cantidad;

        this.precioUnitario = celular.getPrecio()
                .setScale(2, RoundingMode.HALF_UP);
    }

    public Celular getCelular() {
        return celular;
    }

    public int getCantidad() {
        return cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public BigDecimal calcularSubtotal() {
        return precioUnitario.multiply(
            BigDecimal.valueOf(cantidad)
        );
    }
}
