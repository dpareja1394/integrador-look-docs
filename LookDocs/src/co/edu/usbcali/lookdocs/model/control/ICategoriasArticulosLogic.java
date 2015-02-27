package co.edu.usbcali.lookdocs.model.control;

import co.edu.usbcali.lookdocs.model.CategoriasArticulos;
import co.edu.usbcali.lookdocs.model.dto.CategoriasArticulosDTO;

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
public interface ICategoriasArticulosLogic {
    public List<CategoriasArticulos> getCategoriasArticulos()
        throws Exception;

    /**
         * Save an new CategoriasArticulos entity
         */
    public void saveCategoriasArticulos(CategoriasArticulos entity)
        throws Exception;

    /**
         * Delete an existing CategoriasArticulos entity
         *
         */
    public void deleteCategoriasArticulos(CategoriasArticulos entity)
        throws Exception;

    /**
        * Update an existing CategoriasArticulos entity
        *
        */
    public void updateCategoriasArticulos(CategoriasArticulos entity)
        throws Exception;

    /**
         * Load an existing CategoriasArticulos entity
         *
         */
    public CategoriasArticulos getCategoriasArticulos(Long codigoCateArti)
        throws Exception;

    public List<CategoriasArticulos> findByCriteria(Object[] variables,
        Object[] variablesBetween, Object[] variablesBetweenDates)
        throws Exception;

    public List<CategoriasArticulos> findPageCategoriasArticulos(
        String sortColumnName, boolean sortAscending, int startRow,
        int maxResults) throws Exception;

    public Long findTotalNumberCategoriasArticulos() throws Exception;

    public List<CategoriasArticulosDTO> getDataCategoriasArticulos()
        throws Exception;
}
