/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tecnostorev;

import com.mycompany.modelo.Celular;
import com.mycompany.modelo.Gama;
import com.mycompany.persistencia.CelularDAO;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class MenuCelulares {
    public static void ejecutar(Connection conexion, Scanner teclado) {

        CelularDAO celularDAO = new CelularDAO(conexion);
        String opcion;

        do {
            System.out.println("--- TECNOSTORE ---");
            System.out.println("1. Registrar celular");
            System.out.println("2. Listar celulares");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");

            opcion = teclado.nextLine();

            try {
                switch (opcion) {
                    case "1":
                        Celular celular = leerCelular(teclado);
                        celularDAO.registrar(celular);
                        System.out.println("Celular registrado.");
                        break;

                    case "2":
                        List<Celular> celulares = celularDAO.listar();

                        if (celulares.isEmpty()) {
                            System.out.println("No hay celulares registrados.");
                        } else {
                            for (Celular elemento : celulares) {
                                System.out.println(elemento);
                            }
                        }
                        break;

                    case "0":
                        System.out.println("Hasta pronto.");
                        break;

                    default:
                        System.out.println("Opción no válida.");
                }

            } catch (IllegalArgumentException error) {
                System.out.println("Dato incorrecto: " + error.getMessage());

            } catch (SQLException error) {
                System.out.println(
                    "Error de base de datos: " + error.getMessage()
                );
            }

        } while (!opcion.equals("0"));
    }

    private static Celular leerCelular(Scanner teclado) {

        System.out.print("Marca: ");
        String marca = leerTexto(teclado);

        System.out.print("Modelo: ");
        String modelo = leerTexto(teclado);

        System.out.print("Sistema operativo: ");
        String sistemaOperativo = leerTexto(teclado);

        System.out.print("Gama (ALTA, MEDIA o BAJA): ");
        Gama gama = Gama.valueOf(
            leerTexto(teclado).toUpperCase(Locale.ROOT)
        );

        System.out.print("Precio: ");
        BigDecimal precio = new BigDecimal(leerTexto(teclado));

        System.out.print("Stock: ");
        int stock = Integer.parseInt(leerTexto(teclado));

        return new Celular(
            0,
            marca,
            modelo,
            sistemaOperativo,
            gama,
            precio,
            stock
        );
    }

    private static String leerTexto(Scanner teclado) {

        String texto = teclado.nextLine().trim();

        if (texto.isEmpty()) {
            throw new IllegalArgumentException(
                "Los campos no pueden quedar vacíos."
            );
        }

        return texto;
    }
}
