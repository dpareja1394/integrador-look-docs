package co.edu.usbcali.lookdocs.dataaccess.dao;

import java.util.List;

import co.edu.usbcali.lookdocs.dataaccess.api.Dao;
import co.edu.usbcali.lookdocs.model.Colecciones;
import co.edu.usbcali.lookdocs.model.Usuarios;


/**
* Interface for   ColeccionesDAO.
*
*/
public interface IColeccionesDAO extends Dao<Colecciones, Long> {
	
	public Long getConsecutivo(String sqlNombre) throws Exception ;
	public List<Colecciones> consultarColeccionPorUsuario(Usuarios usuarios) throws Exception;
}
