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
public class AnexosDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long codigoAnexo;
    private String formato;
    private String nombre;
    private String url;
    private Long codigoArti_Articulos;

    public Long getCodigoAnexo() {
        return codigoAnexo;
    }

    public void setCodigoAnexo(Long codigoAnexo) {
        this.codigoAnexo = codigoAnexo;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getCodigoArti_Articulos() {
        return codigoArti_Articulos;
    }

    public void setCodigoArti_Articulos(Long codigoArti_Articulos) {
        this.codigoArti_Articulos = codigoArti_Articulos;
    }
}
