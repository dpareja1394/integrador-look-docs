package co.edu.usbcali.lookdocs.dataaccess.dao;

import java.util.List;

import co.edu.usbcali.lookdocs.dataaccess.api.Dao;
import co.edu.usbcali.lookdocs.model.Colecciones;
import co.edu.usbcali.lookdocs.model.Entradas;
import co.edu.usbcali.lookdocs.model.Rss;


/**
* Interface for   EntradasDAO.
*
*/
public interface IEntradasDAO extends Dao<Entradas, Long> {
	
	public Entradas consultarEntradas(Rss rss);
	
	public Long getConsecutivo(String sqlName);
	
	public Entradas consultarEntradaPorRss(Rss rss);
	
	public List<Entradas> consultarEntradasPorCole(Colecciones coleccion);
}
