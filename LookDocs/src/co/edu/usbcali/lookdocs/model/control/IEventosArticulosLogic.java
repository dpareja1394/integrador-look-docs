package co.edu.usbcali.lookdocs.model.control;

import co.edu.usbcali.lookdocs.model.EventosArticulos;
import co.edu.usbcali.lookdocs.model.dto.EventosArticulosDTO;

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
public interface IEventosArticulosLogic {
    public List<EventosArticulos> getEventosArticulos()
        throws Exception;

    /**
         * Save an new EventosArticulos entity
         */
    public void saveEventosArticulos(EventosArticulos entity)
        throws Exception;

    /**
         * Delete an existing EventosArticulos entity
         *
         */
    public void deleteEventosArticulos(EventosArticulos entity)
        throws Exception;

    /**
        * Update an existing EventosArticulos entity
        *
        */
    public void updateEventosArticulos(EventosArticulos entity)
        throws Exception;

    /**
         * Load an existing EventosArticulos entity
         *
         */
    public EventosArticulos getEventosArticulos(Long codigoEveArt)
        throws Exception;

    public List<EventosArticulos> findByCriteria(Object[] variables,
        Object[] variablesBetween, Object[] variablesBetweenDates)
        throws Exception;

    public List<EventosArticulos> findPageEventosArticulos(
        String sortColumnName, boolean sortAscending, int startRow,
        int maxResults) throws Exception;

    public Long findTotalNumberEventosArticulos() throws Exception;

    public List<EventosArticulosDTO> getDataEventosArticulos()
        throws Exception;
}
