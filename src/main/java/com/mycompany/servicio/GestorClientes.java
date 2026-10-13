/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.servicio;

import com.mycompany.modelo.Cliente;
import com.mycompany.persistencia.ClienteDAO;
import java.sql.SQLException;
import java.util.List;

public class GestorClientes {
    private final ClienteDAO clienteDAO;

    public GestorClientes(ClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    public void registrar(String nombre, String identificacion,
                          String correo, String telefono)
            throws SQLException {

        nombre = validarTexto(nombre, "Nombre", 100);
        identificacion = validarTexto(
            identificacion, "Identificación", 20
        );
        correo = validarTexto(correo, "Correo", 100);
        telefono = validarTexto(telefono, "Teléfono", 20);

        if (!correo.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            throw new IllegalArgumentException(
                "El correo debe tener un formato como ana@example.com."
            );
        }

        Cliente cliente = new Cliente(
            0, nombre, identificacion, correo, telefono
        );

        clienteDAO.registrar(cliente);
    }

    public List<Cliente> listar() throws SQLException {
        return clienteDAO.listar();
    }

    private String validarTexto(String texto, String campo, int limite) {

        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException(
                campo + " no puede quedar vacío."
            );
        }

        texto = texto.trim();

        if (texto.length() > limite) {
            throw new IllegalArgumentException(
                campo + " permite máximo " + limite + " caracteres."
            );
        }

        return texto;
    }
}
