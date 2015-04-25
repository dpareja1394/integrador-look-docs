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
public class ColeccionesRssDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long codigoColRss;
    private Long codigoCole_Colecciones;
    private Long codigoRss_Rss;

    public Long getCodigoColRss() {
        return codigoColRss;
    }

    public void setCodigoColRss(Long codigoColRss) {
        this.codigoColRss = codigoColRss;
    }

    public Long getCodigoCole_Colecciones() {
        return codigoCole_Colecciones;
    }

    public void setCodigoCole_Colecciones(Long codigoCole_Colecciones) {
        this.codigoCole_Colecciones = codigoCole_Colecciones;
    }

    public Long getCodigoRss_Rss() {
        return codigoRss_Rss;
    }

    public void setCodigoRss_Rss(Long codigoRss_Rss) {
        this.codigoRss_Rss = codigoRss_Rss;
    }
}
