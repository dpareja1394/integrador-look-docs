package co.edu.usbcali.lookdocs.dataaccess.dao;

import co.edu.usbcali.lookdocs.dataaccess.api.Dao;
import co.edu.usbcali.lookdocs.model.Anexos;


/**
* Interface for   AnexosDAO.
*
*/
public interface IAnexosDAO extends Dao<Anexos, Long> {
	
	public Long getConsecutivo(String sqlName) throws Exception;
}
