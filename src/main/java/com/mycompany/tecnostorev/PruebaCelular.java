/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tecnostorev;

import com.mycompany.modelo.Celular;
import com.mycompany.modelo.Gama;
import java.math.BigDecimal;

public class PruebaCelular {
    public static void main(String[] args) {

        Celular celular = new Celular(
            0,
            "Samsung",
            "Galaxy A15",
            "Android",
            Gama.MEDIA,
            new BigDecimal("650000.00"),
            10
        );

        System.out.println(celular);

        celular.setStock(8);
        System.out.println("Nuevo stock: " + celular.getStock());

        try {
            celular.setPrecio(new BigDecimal("-100"));
        } catch (IllegalArgumentException error) {
            System.out.println("Validación: " + error.getMessage());
        }

        System.out.println("Precio conservado: " + celular.getPrecio());
    }
}
