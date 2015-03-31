package co.edu.usbcali.lookdocs.model.dto;

import java.io.Serializable;

import java.sql.*;

import java.util.Date;


/**
*
* @author Zathura Code Generator http://code.google.com/p/zathura
* www.zathuracode.org
*
*/
public class ColeccionesDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long codigoCole;
    private String nombre;
    private Long codigoUsua_Usuarios;

    public Long getCodigoCole() {
        return codigoCole;
    }

    public void setCodigoCole(Long codigoCole) {
        this.codigoCole = codigoCole;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getCodigoUsua_Usuarios() {
        return codigoUsua_Usuarios;
    }

    public void setCodigoUsua_Usuarios(Long codigoUsua_Usuarios) {
        this.codigoUsua_Usuarios = codigoUsua_Usuarios;
    }
}
