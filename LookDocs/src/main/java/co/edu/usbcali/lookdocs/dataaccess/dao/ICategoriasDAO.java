package co.edu.usbcali.lookdocs.dataaccess.dao;

import java.util.List;

import co.edu.usbcali.lookdocs.dataaccess.api.Dao;
import co.edu.usbcali.lookdocs.model.Articulos;
import co.edu.usbcali.lookdocs.model.Categorias;


/**
* Interface for   CategoriasDAO.
*
*/
public interface ICategoriasDAO extends Dao<Categorias, Long> {
	
	public Long getConsecutivo(String sqlName) throws Exception;
	public List<Articulos> consultaArticulosPorCategoria(Long idCate) throws Exception;
}
