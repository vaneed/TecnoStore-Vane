/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tecnostorev;

import com.mycompany.modelo.Cliente;
import com.mycompany.persistencia.ClienteDAO;
import com.mycompany.servicio.GestorClientes;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MenuClientes {
    public static void ejecutar(Connection conexion, Scanner teclado) {

        ClienteDAO clienteDAO = new ClienteDAO(conexion);
        GestorClientes gestor = new GestorClientes(clienteDAO);

        String opcion;

        do {
            System.out.println("\n--- CLIENTES ---");
            System.out.println("1. Registrar cliente");
            System.out.println("2. Listar clientes");
            System.out.println("0. Volver");
            System.out.print("Elige una opción: ");

            opcion = teclado.nextLine();

            try {
                switch (opcion) {

                    case "1": {
                        System.out.print("Nombre: ");
                        String nombre = teclado.nextLine();

                        System.out.print("Identificación: ");
                        String identificacion = teclado.nextLine();

                        System.out.print("Correo: ");
                        String correo = teclado.nextLine();

                        System.out.print("Teléfono: ");
                        String telefono = teclado.nextLine();

                        gestor.registrar(
                            nombre, identificacion, correo, telefono
                        );

                        System.out.println("Cliente registrado.");
                        break;
                    }

                    case "2": {
                        List<Cliente> clientes = gestor.listar();

                        if (clientes.isEmpty()) {
                            System.out.println("No hay clientes registrados.");
                        } else {
                            for (Cliente cliente : clientes) {
                                System.out.println(cliente);
                            }
                        }

                        break;
                    }

                    case "0":
                        System.out.println("Volviendo al menú...");
                        break;

                    default:
                        System.out.println("Opción no válida.");
                }

            } catch (IllegalArgumentException error) {
                System.out.println(
                    "Dato incorrecto: " + error.getMessage()
                );

            } catch (SQLException error) {

                if (error.getErrorCode() == 1062) {
                    System.out.println(
                        "Ya existe un cliente con esa identificación."
                    );
                } else {
                    System.out.println(
                        "Error de base de datos: " + error.getMessage()
                    );
                }
            }

        } while (!opcion.equals("0"));
    }
}
