package co.edu.usbcali.lookdocs.model.control;

import co.edu.usbcali.lookdocs.model.Articulos;
import co.edu.usbcali.lookdocs.model.Categorias;
import co.edu.usbcali.lookdocs.model.dto.CategoriasDTO;

import java.math.BigDecimal;
import java.util.*;

import org.primefaces.model.TreeNode;


/**
* @author Zathura Code Generator http://code.google.com/p/zathura
* www.zathuracode.org
*
*/
public interface ICategoriasLogic {
    public List<Categorias> getCategorias() throws Exception;

    /**
         * Save an new Categorias entity
         */
    public void saveCategorias(Categorias entity) throws Exception;

    /**
         * Delete an existing Categorias entity
         *
         */
    public void deleteCategorias(Categorias entity) throws Exception;

    /**
        * Update an existing Categorias entity
        *
        */
    public void updateCategorias(Categorias entity) throws Exception;

    /**
         * Load an existing Categorias entity
         *
         */
    public Categorias getCategorias(Long codigoCate) throws Exception;

    public List<Categorias> findByCriteria(Object[] variables,
        Object[] variablesBetween, Object[] variablesBetweenDates)
        throws Exception;

    public List<Categorias> findPageCategorias(String sortColumnName,
        boolean sortAscending, int startRow, int maxResults)
        throws Exception;

    public Long findTotalNumberCategorias() throws Exception;

    public List<CategoriasDTO> getDataCategorias() throws Exception;
    
    public void deleteCategoriasbyNode(TreeNode selectedNode) throws Exception;
    
    public List<Articulos> consultaArticulosPorCategoria(Long idCate) throws Exception;
}
