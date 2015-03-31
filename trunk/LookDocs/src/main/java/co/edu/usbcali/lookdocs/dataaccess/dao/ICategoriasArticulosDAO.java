package co.edu.usbcali.lookdocs.dataaccess.dao;

import co.edu.usbcali.lookdocs.dataaccess.api.Dao;
import co.edu.usbcali.lookdocs.model.CategoriasArticulos;


/**
* Interface for   CategoriasArticulosDAO.
*
*/
public interface ICategoriasArticulosDAO extends Dao<CategoriasArticulos, Long> {
	
	public Long getConsecutivo(String sqlName) throws Exception;
}
