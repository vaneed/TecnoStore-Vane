/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.patron;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Iva19 implements EstrategiaImpuesto {

    @Override
    public BigDecimal calcular(BigDecimal subtotal) {

        return subtotal.multiply(new BigDecimal("0.19"))
                .setScale(2, RoundingMode.HALF_UP);
    }
}
