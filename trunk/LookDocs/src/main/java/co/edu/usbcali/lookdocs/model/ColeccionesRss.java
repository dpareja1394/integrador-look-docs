package co.edu.usbcali.lookdocs.model;
// Generated 27-feb-2015 15:10:55 by Hibernate Tools 4.0.0



/**
 * ColeccionesRss generated by hbm2java
 */
public class ColeccionesRss  implements java.io.Serializable {


     private Long codigoColRss;
     private Rss rss;
     private Colecciones colecciones;

    public ColeccionesRss() {
    }

    public ColeccionesRss(Long codigoColRss, Rss rss, Colecciones colecciones) {
       this.codigoColRss = codigoColRss;
       this.rss = rss;
       this.colecciones = colecciones;
    }
   
    public Long getCodigoColRss() {
        return this.codigoColRss;
    }
    
    public void setCodigoColRss(Long codigoColRss) {
        this.codigoColRss = codigoColRss;
    }
    public Rss getRss() {
        return this.rss;
    }
    
    public void setRss(Rss rss) {
        this.rss = rss;
    }
    public Colecciones getColecciones() {
        return this.colecciones;
    }
    
    public void setColecciones(Colecciones colecciones) {
        this.colecciones = colecciones;
    }




}

