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
public class UsuariosDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String clave;
    private Long codigoUsua;
    private String email;
    private String estadoRegistro;
    private Date fechaCreacion;
    private Date fechaModifcacion;
    private String nombre;
    private String usuCrea;
    private String usuModifica;
    private Long codigoRol_Roles;

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Long getCodigoUsua() {
        return codigoUsua;
    }

    public void setCodigoUsua(Long codigoUsua) {
        this.codigoUsua = codigoUsua;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Long getCodigoRol_Roles() {
        return codigoRol_Roles;
    }

    public void setCodigoRol_Roles(Long codigoRol_Roles) {
        this.codigoRol_Roles = codigoRol_Roles;
    }
}
