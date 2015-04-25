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
public class CategoriasArticulosDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long codigoCateArti;
    private String estadoRegistro;
    private Date fechaCreacion;
    private Date fechaModifcacion;
    private String usuCrea;
    private String usuModifica;
    private Long codigoArti_Articulos;
    private Long codigoCate_Categorias;

    public Long getCodigoCateArti() {
        return codigoCateArti;
    }

    public void setCodigoCateArti(Long codigoCateArti) {
        this.codigoCateArti = codigoCateArti;
    }

    public String getEstadoRegistro() {
        return estadoRegistro;
    }

    public void setEstadoRegistro(String estadoRegistro) {
        this.estadoRegistro = estadoRegistro;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaModifcacion() {
        return fechaModifcacion;
    }

    public void setFechaModifcacion(Date fechaModifcacion) {
        this.fechaModifcacion = fechaModifcacion;
    }

    public String getUsuCrea() {
        return usuCrea;
    }

    public void setUsuCrea(String usuCrea) {
        this.usuCrea = usuCrea;
    }

    public String getUsuModifica() {
        return usuModifica;
    }

    public void setUsuModifica(String usuModifica) {
        this.usuModifica = usuModifica;
    }

    public Long getCodigoArti_Articulos() {
        return codigoArti_Articulos;
    }

    public void setCodigoArti_Articulos(Long codigoArti_Articulos) {
        this.codigoArti_Articulos = codigoArti_Articulos;
    }

    public Long getCodigoCate_Categorias() {
        return codigoCate_Categorias;
    }

    public void setCodigoCate_Categorias(Long codigoCate_Categorias) {
        this.codigoCate_Categorias = codigoCate_Categorias;
    }
}
