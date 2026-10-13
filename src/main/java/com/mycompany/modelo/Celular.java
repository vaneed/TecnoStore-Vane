/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.modelo;

import java.math.BigDecimal;

public class Celular {
    private int id;
    private String marca;
    private String modelo;
    private String sistemaOperativo;
    private Gama gama;
    private BigDecimal precio;
    private int stock;

    public Celular(
            int id,
            String marca,
            String modelo,
            String sistemaOperativo,
            Gama gama,
            BigDecimal precio,
            int stock) {

        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.sistemaOperativo = sistemaOperativo;
        this.gama = gama;

        setPrecio(precio);
        setStock(stock);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getSistemaOperativo() {
        return sistemaOperativo;
    }

    public void setSistemaOperativo(String sistemaOperativo) {
        this.sistemaOperativo = sistemaOperativo;
    }

    public Gama getGama() {
        return gama;
    }

    public void setGama(Gama gama) {
        this.gama = gama;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        if (precio == null || precio.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException(
                "El precio debe ser mayor que cero."
            );
        }

        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException(
                "El stock no puede ser negativo."
            );
        }

        this.stock = stock;
    }

    @Override
    public String toString() {
        return id + " | " + marca + " " + modelo
                + " | Sistema: " + sistemaOperativo
                + " | Gama: " + gama
                + " | Precio: $" + precio
                + " | Stock: " + stock;
    }
}
