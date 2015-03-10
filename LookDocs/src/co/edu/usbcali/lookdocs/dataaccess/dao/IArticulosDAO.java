
package co.edu.usbcali.lookdocs.dataaccess.dao;

import java.util.List;

import co.edu.usbcali.lookdocs.dataaccess.api.Dao;
import co.edu.usbcali.lookdocs.model.Articulos;


/**
* Interface for   ArticulosDAO.
*
*/
public interface IArticulosDAO extends Dao<Articulos, Long> {
	
	//public List<Articulos> consultarTodosArticulos() throws Exception;
	public List<Articulos> consultarTodosArticulos() throws Exception;
	
}
