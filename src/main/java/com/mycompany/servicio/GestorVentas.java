/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.servicio;

import com.mycompany.modelo.Celular;
import com.mycompany.modelo.Cliente;
import com.mycompany.modelo.ItemVenta;
import com.mycompany.modelo.Venta;
import com.mycompany.patron.Iva19;
import com.mycompany.persistencia.CelularDAO;
import com.mycompany.persistencia.ClienteDAO;
import com.mycompany.persistencia.VentaDAO;
import java.sql.SQLException;

public class GestorVentas {

    private final ClienteDAO clienteDAO;
    private final CelularDAO celularDAO;
    private final VentaDAO ventaDAO;

    public GestorVentas(ClienteDAO clienteDAO,
                        CelularDAO celularDAO,
                        VentaDAO ventaDAO) {

        this.clienteDAO = clienteDAO;
        this.celularDAO = celularDAO;
        this.ventaDAO = ventaDAO;
    }

    public Venta crearVenta(int idCliente) throws SQLException {

        for (Cliente cliente : clienteDAO.listar()) {

            if (cliente.getId() == idCliente) {
                return new Venta(cliente, new Iva19());
            }
        }

        throw new IllegalArgumentException(
            "No existe un cliente con ese ID."
        );
    }

    public void agregarCelular(Venta venta, int idCelular, int cantidad)
            throws SQLException {

        for (Celular celular : celularDAO.listar()) {

            if (celular.getId() == idCelular) {

                ItemVenta item = new ItemVenta(celular, cantidad);
                venta.agregarItem(item);
                return;
            }
        }

        throw new IllegalArgumentException(
            "No existe un celular con ese ID."
        );
    }

    public void registrar(Venta venta) throws SQLException {

        if (venta.getItems().isEmpty()) {
            throw new IllegalArgumentException(
                "No puedes registrar una venta vacía."
            );
        }

        ventaDAO.guardar(venta);
    }
}