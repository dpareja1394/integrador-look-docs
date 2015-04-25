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
public class EntradasDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long codigoEntra;
    private String favorito;
    private Date fechaFavorito;
    private Date fechaLeido;
    private String leido;
    private Long codigoRss_Rss;

    public Long getCodigoEntra() {
        return codigoEntra;
    }

    public void setCodigoEntra(Long codigoEntra) {
        this.codigoEntra = codigoEntra;
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

    public Date getFechaLeido() {
        return fechaLeido;
    }

    public void setFechaLeido(Date fechaLeido) {
        this.fechaLeido = fechaLeido;
    }

    public String getLeido() {
        return leido;
    }

    public void setLeido(String leido) {
        this.leido = leido;
    }

    public Long getCodigoRss_Rss() {
        return codigoRss_Rss;
    }

    public void setCodigoRss_Rss(Long codigoRss_Rss) {
        this.codigoRss_Rss = codigoRss_Rss;
    }
}
