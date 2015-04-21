package co.edu.usbcali.lookdocs.dataaccess.dao;

import java.util.List;

import co.edu.usbcali.lookdocs.dataaccess.api.Dao;
import co.edu.usbcali.lookdocs.model.Colecciones;
import co.edu.usbcali.lookdocs.model.Rss;


/**
* Interface for   RssDAO.
*
*/
public interface IRssDAO extends Dao<Rss, Long> {
	
	public Long getConsecutivo(String sqlName);
	
	public List<Rss> getRssDadoIdColeccion(Long codigoCole);
	
	public List<Colecciones> consultarColeccionesPorURL(String url);
	
	public Rss consultarRssPorURl(String url);
	
	public Rss consultarRssPorUrlCole(String rss, Colecciones coleccion);
	
	public List<Object> consultarRssPorCole(Colecciones coleccion);
	
	public List<Colecciones> consultarColePorURL(String url);
	
	public List<Rss> consultarRssPorURlList(String url);
}
