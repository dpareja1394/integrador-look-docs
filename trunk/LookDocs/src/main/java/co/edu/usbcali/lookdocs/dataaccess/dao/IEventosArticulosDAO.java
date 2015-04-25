package co.edu.usbcali.lookdocs.dataaccess.dao;

import co.edu.usbcali.lookdocs.dataaccess.api.Dao;
import co.edu.usbcali.lookdocs.model.EventosArticulos;


/**
* Interface for   EventosArticulosDAO.
*
*/
public interface IEventosArticulosDAO extends Dao<EventosArticulos, Long> {
	
	public Long getConsecutivo(String sqlName); 
	
	public EventosArticulos consultareEventosPorArticulos(Long codigoArti, Long codigoUsua) throws Exception;
	
	public EventosArticulos consultareEventosArticulos(Long codigoArti) throws Exception;
	
}
