/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.modelo;

import com.mycompany.patron.EstrategiaImpuesto;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.mycompany.patron.Iva19;

public class Venta {
    private int id;
    private final Cliente cliente;
    private final LocalDateTime fecha;
    private final List<ItemVenta> items;
    private final EstrategiaImpuesto estrategiaImpuesto;
    private BigDecimal totalGuardado;

    public Venta(Cliente cliente, EstrategiaImpuesto estrategiaImpuesto) {

        if (cliente == null) {
            throw new IllegalArgumentException(
                "Debes seleccionar un cliente."
            );
        }

        if (estrategiaImpuesto == null) {
            throw new IllegalArgumentException(
                "Debes indicar cómo calcular el impuesto."
            );
        }

        this.cliente = cliente;
        this.estrategiaImpuesto = estrategiaImpuesto;
        this.fecha = LocalDateTime.now();
        this.items = new ArrayList<>();
    }
    
    public Venta(int id, Cliente cliente,
             LocalDateTime fecha, BigDecimal totalGuardado) {

    this.id = id;
    this.cliente = cliente;
    this.fecha = fecha;
    this.totalGuardado = totalGuardado;
    this.items = new ArrayList<>();
    this.estrategiaImpuesto = new Iva19();
    }
    

    public void agregarItem(ItemVenta item) {

        if (item == null) {
            throw new IllegalArgumentException(
                "El detalle de venta no puede estar vacío."
            );
        }

        for (ItemVenta existente : items) {
            if (existente.getCelular().getId()
                    == item.getCelular().getId()) {

                throw new IllegalArgumentException(
                    "Ese celular ya está en la venta. "
                    + "Ingresa su cantidad completa en un solo detalle."
                );
            }
        }

        items.add(item);
    }

    public BigDecimal calcularSubtotal() {

        BigDecimal subtotal = new BigDecimal("0.00");

        for (ItemVenta item : items) {
            subtotal = subtotal.add(item.calcularSubtotal());
        }

        return subtotal;
    }

    public BigDecimal calcularIva() {

    if (totalGuardado != null) {
        return totalGuardado.subtract(calcularSubtotal());
    }

    return estrategiaImpuesto.calcular(calcularSubtotal());
    }

    public BigDecimal calcularTotal() {

    if (totalGuardado != null) {
        return totalGuardado;
    }

    return calcularSubtotal().add(calcularIva());
}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public List<ItemVenta> getItems() {
        return Collections.unmodifiableList(items);
    }
}
