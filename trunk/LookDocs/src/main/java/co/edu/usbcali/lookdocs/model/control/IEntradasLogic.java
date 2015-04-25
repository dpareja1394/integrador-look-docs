package co.edu.usbcali.lookdocs.model.control;

import co.edu.usbcali.lookdocs.model.Colecciones;
import co.edu.usbcali.lookdocs.model.Entradas;
import co.edu.usbcali.lookdocs.model.Rss;
import co.edu.usbcali.lookdocs.model.dto.EntradasDTO;

import java.math.BigDecimal;
import java.util.*;


/**
* @author Zathura Code Generator http://code.google.com/p/zathura
* www.zathuracode.org
*
*/
public interface IEntradasLogic {
    public List<Entradas> getEntradas() throws Exception;

    /**
         * Save an new Entradas entity
         */
    public void saveEntradas(Entradas entity) throws Exception;

    /**
         * Delete an existing Entradas entity
         *
         */
    public void deleteEntradas(Entradas entity) throws Exception;

    /**
        * Update an existing Entradas entity
        *
        */
    public void updateEntradas(Entradas entity) throws Exception;

    /**
         * Load an existing Entradas entity
         *
         */
    public Entradas getEntradas(Long codigoEntra) throws Exception;

    public List<Entradas> findByCriteria(Object[] variables,
        Object[] variablesBetween, Object[] variablesBetweenDates)
        throws Exception;

    public List<Entradas> findPageEntradas(String sortColumnName,
        boolean sortAscending, int startRow, int maxResults)
        throws Exception;

    public Long findTotalNumberEntradas() throws Exception;

    public List<EntradasDTO> getDataEntradas() throws Exception;
    
    public Entradas consultarEntradas(Rss rss);
    
    public Long getConsecutivo(String sqlName) throws Exception;
    
    public Entradas consultarEntradaPorRss(Rss rss);
    
    public List<Entradas> consultarEntradasPorCole(Colecciones coleccion);
}
