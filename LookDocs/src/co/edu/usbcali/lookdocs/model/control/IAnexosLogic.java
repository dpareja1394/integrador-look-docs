package co.edu.usbcali.lookdocs.model.control;

import co.edu.usbcali.lookdocs.model.Anexos;
import co.edu.usbcali.lookdocs.model.dto.AnexosDTO;

import java.math.BigDecimal;

import java.util.*;
import java.util.Date;
import java.util.List;
import java.util.Set;


/**
* @author Zathura Code Generator http://code.google.com/p/zathura
* www.zathuracode.org
*
*/
public interface IAnexosLogic {
    public List<Anexos> getAnexos() throws Exception;

    /**
         * Save an new Anexos entity
         */
    public void saveAnexos(Anexos entity) throws Exception;

    /**
         * Delete an existing Anexos entity
         *
         */
    public void deleteAnexos(Anexos entity) throws Exception;

    /**
        * Update an existing Anexos entity
        *
        */
    public void updateAnexos(Anexos entity) throws Exception;

    /**
         * Load an existing Anexos entity
         *
         */
    public Anexos getAnexos(Long codigoAnexo) throws Exception;

    public List<Anexos> findByCriteria(Object[] variables,
        Object[] variablesBetween, Object[] variablesBetweenDates)
        throws Exception;

    public List<Anexos> findPageAnexos(String sortColumnName,
        boolean sortAscending, int startRow, int maxResults)
        throws Exception;

    public Long findTotalNumberAnexos() throws Exception;

    public List<AnexosDTO> getDataAnexos() throws Exception;
}
