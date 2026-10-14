/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.servicio;

import com.mycompany.modelo.Celular;
import com.mycompany.persistencia.CelularDAO;
import java.sql.SQLException;
import java.util.List;

public class GestorCelulares {

    private final CelularDAO celularDAO;

    public GestorCelulares(CelularDAO celularDAO) {
        this.celularDAO = celularDAO;
    }

    public void registrar(Celular celular) throws SQLException {
        validar(celular);
        celularDAO.registrar(celular);
    }

    public List<Celular> listar() throws SQLException {
        return celularDAO.listar();
    }

    public boolean actualizar(Celular celular) throws SQLException {

        validar(celular);
        validarId(celular.getId());

        return celularDAO.actualizar(celular);
    }

    public boolean eliminar(int id) throws SQLException {
        validarId(id);
        return celularDAO.eliminar(id);
    }

    private void validar(Celular celular) {

        if (celular == null) {
            throw new IllegalArgumentException(
                "Debes indicar un celular."
            );
        }

        celular.setMarca(
            validarTexto(celular.getMarca(), "Marca", 50)
        );

        celular.setModelo(
            validarTexto(celular.getModelo(), "Modelo", 80)
        );

        celular.setSistemaOperativo(
            validarTexto(
                celular.getSistemaOperativo(),
                "Sistema operativo",
                50
            )
        );

        if (celular.getGama() == null) {
            throw new IllegalArgumentException(
                "Debes seleccionar una gama."
            );
        }
    }

    private void validarId(int id) {

        if (id <= 0) {
            throw new IllegalArgumentException(
                "El ID debe ser mayor que cero."
            );
        }
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