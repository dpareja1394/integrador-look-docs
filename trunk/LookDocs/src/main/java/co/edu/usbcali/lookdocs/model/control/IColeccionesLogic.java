package co.edu.usbcali.lookdocs.model.control;

import co.edu.usbcali.lookdocs.model.Colecciones;
import co.edu.usbcali.lookdocs.model.Usuarios;
import co.edu.usbcali.lookdocs.model.dto.ColeccionesDTO;

import java.math.BigDecimal;
import java.util.*;


/**
* @author Zathura Code Generator http://code.google.com/p/zathura
* www.zathuracode.org
*
*/
public interface IColeccionesLogic {
    public List<Colecciones> getColecciones() throws Exception;

    /**
         * Save an new Colecciones entity
         */
    public void saveColecciones(Colecciones entity) throws Exception;

    /**
         * Delete an existing Colecciones entity
         *
         */
    public void deleteColecciones(Colecciones entity) throws Exception;

    /**
        * Update an existing Colecciones entity
        *
        */
    public void updateColecciones(Colecciones entity) throws Exception;

    /**
         * Load an existing Colecciones entity
         *
         */
    public Colecciones getColecciones(Long codigoCole)
        throws Exception;

    public List<Colecciones> findByCriteria(Object[] variables,
        Object[] variablesBetween, Object[] variablesBetweenDates)
        throws Exception;

    public List<Colecciones> findPageColecciones(String sortColumnName,
        boolean sortAscending, int startRow, int maxResults)
        throws Exception;

    public Long findTotalNumberColecciones() throws Exception;

    public List<ColeccionesDTO> getDataColecciones() throws Exception;
    
    public List<Colecciones> consultarColeccionPorUsuario(Usuarios usuarios) throws Exception;
}
