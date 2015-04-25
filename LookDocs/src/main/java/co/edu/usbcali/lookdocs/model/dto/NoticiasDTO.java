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
public class NoticiasDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String titulo;
    private String link;
    private String descripcion;
    
       
    

	public NoticiasDTO() {
		super();
	}

	public NoticiasDTO(String titulo, String link, String descripcion) {
		super();
		this.titulo = titulo;
		this.link = link;
		this.descripcion = descripcion;
	}

	public String getTitulo() {
		return titulo;
	}
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	    
    

}
