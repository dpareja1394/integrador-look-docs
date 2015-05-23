package co.edu.usbcali.lookdocs.model.control;

import co.edu.usbcali.lookdocs.model.Colecciones;
import co.edu.usbcali.lookdocs.model.Rss;
import co.edu.usbcali.lookdocs.model.dto.RssDTO;

import java.math.BigDecimal;
import java.util.*;


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
    
    public void guardarRSS(String urlRss, Long idColeccion) throws Exception;
    
    public Long getConsecutivo(String sqlName) throws Exception;
    
    public List<Rss> getRssDadoIdColeccion(Long codigoCole) throws Exception;
    
    public List<Colecciones> consultarColeccionesPorURL(String url);
    
    public Rss consultarRssPorURl(String url);
    
    public Rss consultarRssPorUrlCole(String rss, Colecciones coleccion);
    
    public List<RssDTO> getDataRssPorColeccion(Colecciones coleccion) throws Exception; 
    
    public List<Colecciones> consultarColePorURL(String url);
    
    public List<Rss> consultarRssPorURlList(String url);

    public List<RssDTO> getRssDTODadoIdColeccion(Long codigoCole) throws Exception;
}
