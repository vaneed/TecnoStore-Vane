/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tecnostorev;

import com.mycompany.modelo.Celular;
import com.mycompany.modelo.Cliente;
import com.mycompany.modelo.Venta;
import com.mycompany.persistencia.CelularDAO;
import com.mycompany.persistencia.ClienteDAO;
import com.mycompany.persistencia.VentaDAO;
import com.mycompany.servicio.GestorVentas;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MenuVentas {

    public static void ejecutar(Connection conexion, Scanner teclado) {

        ClienteDAO clienteDAO = new ClienteDAO(conexion);
        CelularDAO celularDAO = new CelularDAO(conexion);
        VentaDAO ventaDAO = new VentaDAO(conexion);

        GestorVentas gestor = new GestorVentas(
            clienteDAO, celularDAO, ventaDAO
        );

        try {
            List<Cliente> clientes = clienteDAO.listar();
            List<Celular> celulares = celularDAO.listar();

            if (clientes.isEmpty() || celulares.isEmpty()) {
                System.out.println(
                    "Primero debes registrar clientes y celulares."
                );
                return;
            }

            System.out.println("\n--- NUEVA VENTA ---");
            System.out.println("Clientes:");

            for (Cliente cliente : clientes) {
                System.out.println(cliente);
            }

            System.out.print("ID del cliente: ");
            int idCliente = Integer.parseInt(
                teclado.nextLine().trim()
            );

            Venta venta = gestor.crearVenta(idCliente);

            System.out.println("\nCelulares:");

            for (Celular celular : celulares) {
                System.out.println(celular);
            }

            while (true) {

                System.out.print(
                    "ID del celular (0 para terminar la selección): "
                );

                int idCelular = Integer.parseInt(
                    teclado.nextLine().trim()
                );

                if (idCelular == 0) {
                    break;
                }

                System.out.print("Cantidad: ");
                int cantidad = Integer.parseInt(
                    teclado.nextLine().trim()
                );

                gestor.agregarCelular(
                    venta, idCelular, cantidad
                );

                System.out.println("Celular agregado.");
            }

            if (venta.getItems().isEmpty()) {
                System.out.println(
                    "Venta cancelada: no seleccionaste celulares."
                );
                return;
            }

            System.out.println(
                "\nSubtotal: $" + venta.calcularSubtotal()
            );
            System.out.println(
                "IVA del 19%: $" + venta.calcularIva()
            );
            System.out.println(
                "Total: $" + venta.calcularTotal()
            );

            System.out.print("Escribe SI para confirmar la venta: ");
            String confirmacion = teclado.nextLine().trim();

            if (!confirmacion.equalsIgnoreCase("SI")) {
                System.out.println("Venta cancelada.");
                return;
            }

            gestor.registrar(venta);

            System.out.println(
                "Venta registrada. ID: " + venta.getId()
            );

        } catch (IllegalArgumentException error) {

            System.out.println(
                "No se registró la venta: " + error.getMessage()
            );

        } catch (SQLException error) {

            System.out.println(
                "Error al procesar la venta: " + error.getMessage()
            );
        }
    }
}
