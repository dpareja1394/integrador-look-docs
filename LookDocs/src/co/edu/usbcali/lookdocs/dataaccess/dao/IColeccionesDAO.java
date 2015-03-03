package co.edu.usbcali.lookdocs.dataaccess.dao;

import co.edu.usbcali.lookdocs.dataaccess.api.Dao;
import co.edu.usbcali.lookdocs.model.Colecciones;


/**
* Interface for   ColeccionesDAO.
*
*/
public interface IColeccionesDAO extends Dao<Colecciones, Long> {
	
	public Long getConsecutivo(String sqlNombre) throws Exception ;
}
