/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author pc
 */
public class documentos {
    private int id;
    private String tipo_documento;
    private String nombre_documento;

    public documentos() {
    }

    public documentos(int id, String tipo_documento, String nombre_documento) {
        this.id = id;
        this.tipo_documento = tipo_documento;
        this.nombre_documento = nombre_documento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo_documento() {
        return tipo_documento;
    }

    public void setTipo_documento(String tipo_documento) {
        this.tipo_documento = tipo_documento;
    }

    public String getNombre_documento() {
        return nombre_documento;
    }

    public void setNombre_documento(String nombre_documento) {
        this.nombre_documento = nombre_documento;
    }
}
