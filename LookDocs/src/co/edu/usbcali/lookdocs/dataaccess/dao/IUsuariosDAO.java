package co.edu.usbcali.lookdocs.dataaccess.dao;

import co.edu.usbcali.lookdocs.dataaccess.api.Dao;
import co.edu.usbcali.lookdocs.model.Usuarios;


/**
* Interface for   UsuariosDAO.
*
*/
public interface IUsuariosDAO extends Dao<Usuarios, Long> {
	public Usuarios obtenerPorMail(String email);

	public void registrarUsuarioLector(Usuarios usuarios);
	
	public Long getConsecutivo(String sqlName);
}
