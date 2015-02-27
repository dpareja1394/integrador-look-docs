package co.edu.usbcali.lookdocs.model;
// Generated 27-feb-2015 15:10:55 by Hibernate Tools 4.0.0


import java.util.Date;

/**
 * EventosArticulos generated by hbm2java
 */
public class EventosArticulos  implements java.io.Serializable {


     private Long codigoEveArt;
     private Articulos articulos;
     private Usuarios usuarios;
     private String meGusta;
     private Date fechaMeGusta;
     private String favorito;
     private Date fechaFavorito;

    public EventosArticulos() {
    }

	
    public EventosArticulos(Long codigoEveArt, Articulos articulos, Usuarios usuarios) {
        this.codigoEveArt = codigoEveArt;
        this.articulos = articulos;
        this.usuarios = usuarios;
    }
    public EventosArticulos(Long codigoEveArt, Articulos articulos, Usuarios usuarios, String meGusta, Date fechaMeGusta, String favorito, Date fechaFavorito) {
       this.codigoEveArt = codigoEveArt;
       this.articulos = articulos;
       this.usuarios = usuarios;
       this.meGusta = meGusta;
       this.fechaMeGusta = fechaMeGusta;
       this.favorito = favorito;
       this.fechaFavorito = fechaFavorito;
    }
   
    public Long getCodigoEveArt() {
        return this.codigoEveArt;
    }
    
    public void setCodigoEveArt(Long codigoEveArt) {
        this.codigoEveArt = codigoEveArt;
    }
    public Articulos getArticulos() {
        return this.articulos;
    }
    
    public void setArticulos(Articulos articulos) {
        this.articulos = articulos;
    }
    public Usuarios getUsuarios() {
        return this.usuarios;
    }
    
    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }
    public String getMeGusta() {
        return this.meGusta;
    }
    
    public void setMeGusta(String meGusta) {
        this.meGusta = meGusta;
    }
    public Date getFechaMeGusta() {
        return this.fechaMeGusta;
    }
    
    public void setFechaMeGusta(Date fechaMeGusta) {
        this.fechaMeGusta = fechaMeGusta;
    }
    public String getFavorito() {
        return this.favorito;
    }
    
    public void setFavorito(String favorito) {
        this.favorito = favorito;
    }
    public Date getFechaFavorito() {
        return this.fechaFavorito;
    }
    
    public void setFechaFavorito(Date fechaFavorito) {
        this.fechaFavorito = fechaFavorito;
    }




}


