/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.tecnostorev;

import com.mycompany.utilidades.ReporteUtils;
import com.mycompany.modelo.Celular;
import com.mycompany.modelo.Gama;
import com.mycompany.persistencia.CelularDAO;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import com.mycompany.modelo.Venta;
import com.mycompany.persistencia.VentaDAO;
import com.mycompany.utilidades.ArchivoUtils;
import java.io.IOException;
import java.nio.file.Path;
import com.mycompany.servicio.GestorCelulares;

public class MenuCelulares {
    public static void ejecutar(Connection conexion, Scanner teclado) {

        CelularDAO celularDAO = new CelularDAO(conexion);
            GestorCelulares gestorCelulares = new GestorCelulares(celularDAO);
        String opcion;

        do {
            System.out.println("\n--- TECNOSTORE ---");
            System.out.println("1. Registrar celular");
            System.out.println("2. Listar celulares");
            System.out.println("3. Actualizar celular");
            System.out.println("4. Eliminar celular");
            System.out.println("5. Gestionar clientes");
            System.out.println("6. Registrar venta");
            System.out.println("7. Ver reportes y generar archivo");
            System.out.println("0. Salir");
            System.out.print("Elige una opción: ");

            opcion = teclado.nextLine();

            try {
                switch (opcion) {
                    case "1":
                        Celular celular = leerCelular(teclado);
                        gestorCelulares.registrar(celular);
                        System.out.println("Celular registrado.");
                        break;

                    case "2":
                        List<Celular> celulares = gestorCelulares.listar();

                        if (celulares.isEmpty()) {
                            System.out.println("No hay celulares registrados.");
                        } else {
                            for (Celular elemento : celulares) {
                                System.out.println(elemento);
                            }
                        }
                        break;
case "3": {
    System.out.print("ID del celular que deseas actualizar: ");
    int idActualizar = Integer.parseInt(leerTexto(teclado));    

    System.out.println("Escribe todos los datos del celular:");

    Celular celularActualizado = leerCelular(teclado);
    celularActualizado.setId(idActualizar);

    boolean actualizado = gestorCelulares.actualizar(celularActualizado);

    if (actualizado) {
        System.out.println("Celular actualizado.");
    } else {
        System.out.println(
            "No se actualizó ninguna fila. Revisa que el ID exista."
        );
    }

    break;
}

case "4": {
    System.out.print("ID del celular que deseas eliminar: ");
    int idEliminar = Integer.parseInt(leerTexto(teclado));

    System.out.print("Escribe SI para confirmar: ");
    String confirmacion = teclado.nextLine().trim();

    if (confirmacion.equalsIgnoreCase("SI")) {

        boolean eliminado = gestorCelulares.eliminar(idEliminar);

        if (eliminado) {
            System.out.println("Celular eliminado.");
        } else {
            System.out.println("No existe un celular con ese ID.");
        }

    } else {
        System.out.println("Eliminación cancelada.");
    }

    break;
}
    
case "5": {
    MenuClientes.ejecutar(conexion, teclado);
    }
    break;

    case "6":{
    MenuVentas.ejecutar(conexion, teclado);
    break;
    }
    
    case "7": {

    VentaDAO ventaDAO = new VentaDAO(conexion);
    List<Venta> ventas = ventaDAO.listar();

    String reporte =
            ReporteUtils.generarStockBajo(gestorCelulares.listar())
            + "\n" + ReporteUtils.generarTopTres(ventas)
            + "\n" + ReporteUtils.generarVentasPorMes(ventas)
            + "\n" + ReporteUtils.generarResumenVentas(ventas);

    System.out.println(reporte);

    try {
        Path ruta = ArchivoUtils.guardarReporte(reporte);
        System.out.println("Archivo guardado en: " + ruta);

    } catch (IOException error) {
        System.out.println(
            "El reporte se mostró, pero no se pudo guardar el archivo: "
            + error.getMessage()
        );
    }

    break;
    }
    
                    case "0":
                        System.out.println("Hasta pronto.");
                        break;

                    default:
                        System.out.println("Opción no válida.");
                }

            } catch (IllegalArgumentException error) {
                System.out.println("Dato incorrecto: " + error.getMessage());

            } catch (SQLException error) {

    if (error.getErrorCode() == 1451) {
        System.out.println(
            "No puedes eliminar este celular porque tiene ventas asociadas."
        );
    } else {
        System.out.println(
            "Error de base de datos: " + error.getMessage()
        );
    }
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
