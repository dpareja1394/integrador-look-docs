package co.edu.usbcali.lookdocs.model.control;

import co.edu.usbcali.lookdocs.model.Usuarios;
import co.edu.usbcali.lookdocs.model.dto.UsuariosDTO;

import java.math.BigDecimal;
import java.util.*;


/**
* @author Zathura Code Generator http://code.google.com/p/zathura
* www.zathuracode.org
*
*/
public interface IUsuariosLogic {
    public List<Usuarios> getUsuarios() throws Exception;

    /**
         * Save an new Usuarios entity
         */
    public void saveUsuarios(Usuarios entity) throws Exception;

    /**
         * Delete an existing Usuarios entity
         *
         */
    public void deleteUsuarios(Usuarios entity) throws Exception;

    /**
        * Update an existing Usuarios entity
        *
        */
    public void updateUsuarios(Usuarios entity) throws Exception;

    /**
         * Load an existing Usuarios entity
         *
         */
    public Usuarios getUsuarios(Long codigoUsua) throws Exception;

    public List<Usuarios> findByCriteria(Object[] variables,
        Object[] variablesBetween, Object[] variablesBetweenDates)
        throws Exception;

    public List<Usuarios> findPageUsuarios(String sortColumnName,
        boolean sortAscending, int startRow, int maxResults)
        throws Exception;

    public Long findTotalNumberUsuarios() throws Exception;

    public List<UsuariosDTO> getDataUsuarios() throws Exception;

    public boolean validateEmailAddress(String sEmail) throws Exception;

    public String iniciarSesionLector(String email, String password) throws Exception;
    
    public String iniciarSesionAdministrador(String email, String password) throws Exception;
    
    public void registrarUsuarioLector(Usuarios usuarios) throws Exception;
    
    public Long getConsecutivo(String sqlName) throws Exception;
    
    public void enviarMensaje(Usuarios usuarios)throws Exception;

    public Usuarios obtenerPorMail(String email)throws Exception;
}
