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
public class EventosArticulosDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long codigoEveArt;
    private String favorito;
    private Date fechaFavorito;
    private Date fechaMeGusta;
    private String meGusta;
    private Long codigoArti_Articulos;
    private Long codigoUsua_Usuarios;

    public Long getCodigoEveArt() {
        return codigoEveArt;
    }

    public void setCodigoEveArt(Long codigoEveArt) {
        this.codigoEveArt = codigoEveArt;
    }

    public String getFavorito() {
        return favorito;
    }

    public void setFavorito(String favorito) {
        this.favorito = favorito;
    }

    public Date getFechaFavorito() {
        return fechaFavorito;
    }

    public void setFechaFavorito(Date fechaFavorito) {
        this.fechaFavorito = fechaFavorito;
    }

    public Date getFechaMeGusta() {
        return fechaMeGusta;
    }

    public void setFechaMeGusta(Date fechaMeGusta) {
        this.fechaMeGusta = fechaMeGusta;
    }

    public String getMeGusta() {
        return meGusta;
    }

    public void setMeGusta(String meGusta) {
        this.meGusta = meGusta;
    }

    public Long getCodigoArti_Articulos() {
        return codigoArti_Articulos;
    }

    public void setCodigoArti_Articulos(Long codigoArti_Articulos) {
        this.codigoArti_Articulos = codigoArti_Articulos;
    }

    public Long getCodigoUsua_Usuarios() {
        return codigoUsua_Usuarios;
    }

    public void setCodigoUsua_Usuarios(Long codigoUsua_Usuarios) {
        this.codigoUsua_Usuarios = codigoUsua_Usuarios;
    }
}
