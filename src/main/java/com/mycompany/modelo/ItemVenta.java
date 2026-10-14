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
    private final BigDecimal subtotal;

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
        
        this.subtotal = this.precioUnitario.multiply(
      BigDecimal.valueOf(cantidad));
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
    return subtotal;
    }
    
    public ItemVenta(Celular celular, int cantidad, BigDecimal subtotal) {

    if (celular == null || cantidad <= 0
            || subtotal == null || subtotal.signum() <= 0) {
        throw new IllegalArgumentException(
            "El detalle guardado tiene datos incorrectos."
        );
    }

    this.celular = celular;
    this.cantidad = cantidad;
    this.subtotal = subtotal;

    this.precioUnitario = subtotal.divide(
        BigDecimal.valueOf(cantidad),
        2,
        RoundingMode.HALF_UP
    );
}
}
