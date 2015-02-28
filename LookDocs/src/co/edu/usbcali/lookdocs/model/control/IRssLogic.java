package co.edu.usbcali.lookdocs.model.control;

import co.edu.usbcali.lookdocs.model.Rss;
import co.edu.usbcali.lookdocs.model.dto.RssDTO;

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
public interface IRssLogic {
    public List<Rss> getRss() throws Exception;

    /**
         * Save an new Rss entity
         */
    public void saveRss(Rss entity) throws Exception;

    /**
         * Delete an existing Rss entity
         *
         */
    public void deleteRss(Rss entity) throws Exception;

    /**
        * Update an existing Rss entity
        *
        */
    public void updateRss(Rss entity) throws Exception;

    /**
         * Load an existing Rss entity
         *
         */
    public Rss getRss(Long codigoRss) throws Exception;

    public List<Rss> findByCriteria(Object[] variables,
        Object[] variablesBetween, Object[] variablesBetweenDates)
        throws Exception;

    public List<Rss> findPageRss(String sortColumnName, boolean sortAscending,
        int startRow, int maxResults) throws Exception;

    public Long findTotalNumberRss() throws Exception;

    public List<RssDTO> getDataRss() throws Exception;
}