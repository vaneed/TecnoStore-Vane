/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.modelo;

public class Cliente extends Persona {

    private final int id;
    private final String correo;
    private final String telefono;

    public Cliente(int id, String nombre, String identificacion,
                   String correo, String telefono) {

        super(nombre, identificacion);

        this.id = id;
        this.correo = correo;
        this.telefono = telefono;
    }

    public int getId() {
        return id;
    }

    public String getCorreo() {
        return correo;
    }

    public String getTelefono() {
        return telefono;
    }

    @Override
    public String toString() {
        return id + " | " + getNombre()
                + " | Identificación: " + getIdentificacion()
                + " | Correo: " + correo
                + " | Teléfono: " + telefono;
    }
}
