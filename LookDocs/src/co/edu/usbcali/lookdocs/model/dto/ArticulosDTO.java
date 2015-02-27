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
public class ArticulosDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String autor;
    private Long codigoArti;
    private String descripcion;
    private String estadoRegistro;
    private Date fechaCreacion;
    private Date fechaModifcacion;
    private String nombre;
    private String usuCrea;
    private String usuModifica;
    private Long codigoUsua_Usuarios;

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Long getCodigoArti() {
        return codigoArti;
    }

    public void setCodigoArti(Long codigoArti) {
        this.codigoArti = codigoArti;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public Long getCodigoUsua_Usuarios() {
        return codigoUsua_Usuarios;
    }

    public void setCodigoUsua_Usuarios(Long codigoUsua_Usuarios) {
        this.codigoUsua_Usuarios = codigoUsua_Usuarios;
    }
}
