package co.edu.usbcali.lookdocs.dataaccess.dao;

import co.edu.usbcali.lookdocs.dataaccess.api.Dao;
import co.edu.usbcali.lookdocs.model.ColeccionesRss;


/**
* Interface for   ColeccionesRssDAO.
*
*/
public interface IColeccionesRssDAO extends Dao<ColeccionesRss, Long> {
	
	public Long getConsecutivo(String sqlName);
	
	public ColeccionesRss consultarSiExisteRssEnLaColeccion(Long idColeccion, String urlRss);
}
