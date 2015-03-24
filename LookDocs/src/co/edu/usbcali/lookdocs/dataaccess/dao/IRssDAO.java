package co.edu.usbcali.lookdocs.dataaccess.dao;

import java.util.List;

import co.edu.usbcali.lookdocs.dataaccess.api.Dao;
import co.edu.usbcali.lookdocs.model.Rss;


/**
* Interface for   RssDAO.
*
*/
public interface IRssDAO extends Dao<Rss, Long> {
	
	public Long getConsecutivo(String sqlName);
	
	public List<Rss> getRssDadoIdColeccion(Long codigoCole);
	
}
