package co.edu.usbcali.lookdocs.model.control;

import co.edu.usbcali.lookdocs.dataaccess.dao.*;
import co.edu.usbcali.lookdocs.exceptions.*;
import co.edu.usbcali.lookdocs.model.*;
import co.edu.usbcali.lookdocs.model.dto.AnexosDTO;
import co.edu.usbcali.lookdocs.utilities.Utilities;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Scope;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;


/**
* @author Zathura Code Generator http://code.google.com/p/zathura
* www.zathuracode.org
*
*/
@Scope("singleton")
@Service("AnexosLogic")
public class AnexosLogic implements IAnexosLogic {
    /**
     * DAO injected by Spring that manages Anexos entities
     *
     */
    @Autowired
    private IAnexosDAO anexosDAO;

    /**
    * Logic injected by Spring that manages Articulos entities
    *
    */
    @Autowired
    IArticulosLogic logicArticulos1;

    @Transactional(readOnly = true)
    public List<Anexos> getAnexos() throws Exception {
        List<Anexos> list = new ArrayList<Anexos>();

        try {
            list = anexosDAO.findAll();
        } catch (Exception e) {
            throw new ZMessManager().new GettingException(ZMessManager.ALL +
                "Anexos");
        } finally {
        }

        return list;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void saveAnexos(Anexos entity) throws Exception {
        try {
            if (entity.getArticulos() == null) {
                throw new ZMessManager().new ForeignException("articulos");
            }

            if (entity.getCodigoAnexo() == null) {
                throw new ZMessManager().new EmptyFieldException("codigoAnexo");
            }

            if (entity.getFormato() == null) {
                throw new ZMessManager().new EmptyFieldException("formato");
            }

            if ((entity.getFormato() != null) &&
                    (Utilities.checkWordAndCheckWithlength(
                        entity.getFormato(), 20) == false)) {
                throw new ZMessManager().new NotValidFormatException("formato");
            }

            if (entity.getNombre() == null) {
                throw new ZMessManager().new EmptyFieldException("nombre");
            }

            if ((entity.getNombre() != null) &&
                    (Utilities.checkWordAndCheckWithlength(entity.getNombre(),
                        500) == false)) {
                throw new ZMessManager().new NotValidFormatException("nombre");
            }

            if ((entity.getUrl() != null) &&
                    (Utilities.checkWordAndCheckWithlength(entity.getUrl(), 500) == false)) {
                throw new ZMessManager().new NotValidFormatException("url");
            }

            if (entity.getArticulos().getCodigoArti() == null) {
                throw new ZMessManager().new EmptyFieldException(
                    "codigoArti_Articulos");
            }

            if (getAnexos(entity.getCodigoAnexo()) != null) {
                throw new ZMessManager(ZMessManager.ENTITY_WITHSAMEKEY);
            }

            anexosDAO.save(entity);
        } catch (Exception e) {
            throw e;
        } finally {
        }
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deleteAnexos(Anexos entity) throws Exception {
        if (entity == null) {
            throw new ZMessManager().new NullEntityExcepcion("Anexos");
        }

        if (entity.getCodigoAnexo() == null) {
            throw new ZMessManager().new EmptyFieldException("codigoAnexo");
        }

        try {
            anexosDAO.delete(entity);
        } catch (Exception e) {
            throw e;
        } finally {
        }
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void updateAnexos(Anexos entity) throws Exception {
        try {
            if (entity == null) {
                throw new ZMessManager().new NullEntityExcepcion("Anexos");
            }

            if (entity.getArticulos() == null) {
                throw new ZMessManager().new ForeignException("articulos");
            }

            if (entity.getCodigoAnexo() == null) {
                throw new ZMessManager().new EmptyFieldException("codigoAnexo");
            }

            if (entity.getFormato() == null) {
                throw new ZMessManager().new EmptyFieldException("formato");
            }

            if ((entity.getFormato() != null) &&
                    (Utilities.checkWordAndCheckWithlength(
                        entity.getFormato(), 20) == false)) {
                throw new ZMessManager().new NotValidFormatException("formato");
            }

            if (entity.getNombre() == null) {
                throw new ZMessManager().new EmptyFieldException("nombre");
            }

            if ((entity.getNombre() != null) &&
                    (Utilities.checkWordAndCheckWithlength(entity.getNombre(),
                        500) == false)) {
                throw new ZMessManager().new NotValidFormatException("nombre");
            }

            if ((entity.getUrl() != null) &&
                    (Utilities.checkWordAndCheckWithlength(entity.getUrl(), 500) == false)) {
                throw new ZMessManager().new NotValidFormatException("url");
            }

            if (entity.getArticulos().getCodigoArti() == null) {
                throw new ZMessManager().new EmptyFieldException(
                    "codigoArti_Articulos");
            }

            anexosDAO.update(entity);
        } catch (Exception e) {
            throw e;
        } finally {
        }
    }

    @Transactional(readOnly = true)
    public List<AnexosDTO> getDataAnexos() throws Exception {
        try {
            List<Anexos> anexos = anexosDAO.findAll();

            List<AnexosDTO> anexosDTO = new ArrayList<AnexosDTO>();

            for (Anexos anexosTmp : anexos) {
                AnexosDTO anexosDTO2 = new AnexosDTO();

                anexosDTO2.setCodigoAnexo(anexosTmp.getCodigoAnexo());
                anexosDTO2.setFormato((anexosTmp.getFormato() != null)
                    ? anexosTmp.getFormato() : null);
                anexosDTO2.setNombre((anexosTmp.getNombre() != null)
                    ? anexosTmp.getNombre() : null);
                anexosDTO2.setUrl((anexosTmp.getUrl() != null)
                    ? anexosTmp.getUrl() : null);
                anexosDTO2.setCodigoArti_Articulos((anexosTmp.getArticulos()
                                                             .getCodigoArti() != null)
                    ? anexosTmp.getArticulos().getCodigoArti() : null);
                anexosDTO.add(anexosDTO2);
            }

            return anexosDTO;
        } catch (Exception e) {
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public Anexos getAnexos(Long codigoAnexo) throws Exception {
        Anexos entity = null;

        try {
            entity = anexosDAO.findById(codigoAnexo);
        } catch (Exception e) {
            throw new ZMessManager().new FindingException("Anexos");
        } finally {
        }

        return entity;
    }
    
    @Transactional(readOnly = true)
    public Anexos getAnexosbyArtiuclo(Long codigoArticulo) throws Exception {
        Anexos entity = null;

        try {
            entity = anexosDAO.consultarAnexosPorArticulo(codigoArticulo);
        } catch (Exception e) {
            throw new ZMessManager().new FindingException("Anexos");
        } finally {
        }

        return entity;
    }

    @Transactional(readOnly = true)
    public List<Anexos> findPageAnexos(String sortColumnName,
        boolean sortAscending, int startRow, int maxResults)
        throws Exception {
        List<Anexos> entity = null;

        try {
            entity = anexosDAO.findPage(sortColumnName, sortAscending,
                    startRow, maxResults);
        } catch (Exception e) {
            throw new ZMessManager().new FindingException("Anexos Count");
        } finally {
        }

        return entity;
    }

    @Transactional(readOnly = true)
    public Long findTotalNumberAnexos() throws Exception {
        Long entity = null;

        try {
            entity = anexosDAO.count();
        } catch (Exception e) {
            throw new ZMessManager().new FindingException("Anexos Count");
        } finally {
        }

        return entity;
    }

    /**
    *
    * @param varibles
    *            este arreglo debera tener:
    *
    * [0] = String variable = (String) varibles[i]; representa como se llama la
    * variable en el pojo
    *
    * [1] = Boolean booVariable = (Boolean) varibles[i + 1]; representa si el
    * valor necesita o no ''(comillas simples)usado para campos de tipo string
    *
    * [2] = Object value = varibles[i + 2]; representa el valor que se va a
    * buscar en la BD
    *
    * [3] = String comparator = (String) varibles[i + 3]; representa que tipo
    * de busqueda voy a hacer.., ejemplo: where nombre=william o where nombre<>william,
        * en este campo iria el tipo de comparador que quiero si es = o <>
            *
            * Se itera de 4 en 4..., entonces 4 registros del arreglo representan 1
            * busqueda en un campo, si se ponen mas pues el continuara buscando en lo
            * que se le ingresen en los otros 4
            *
            *
            * @param variablesBetween
            *
            * la diferencia son estas dos posiciones
            *
            * [0] = String variable = (String) varibles[j]; la variable ne la BD que va
            * a ser buscada en un rango
            *
            * [1] = Object value = varibles[j + 1]; valor 1 para buscar en un rango
            *
            * [2] = Object value2 = varibles[j + 2]; valor 2 para buscar en un rango
            * ejempolo: a > 1 and a < 5 --> 1 seria value y 5 seria value2
                *
                * [3] = String comparator1 = (String) varibles[j + 3]; comparador 1
                * ejemplo: a comparator1 1 and a < 5
                    *
                    * [4] = String comparator2 = (String) varibles[j + 4]; comparador 2
                    * ejemplo: a comparador1>1  and a comparador2<5  (el original: a > 1 and a <
                            * 5) *
                            * @param variablesBetweenDates(en
                            *            este caso solo para mysql)
                            *  [0] = String variable = (String) varibles[k]; el nombre de la variable que hace referencia a
                            *            una fecha
                            *
                            * [1] = Object object1 = varibles[k + 2]; fecha 1 a comparar(deben ser
                            * dates)
                            *
                            * [2] = Object object2 = varibles[k + 3]; fecha 2 a comparar(deben ser
                            * dates)
                            *
                            * esto hace un between entre las dos fechas.
                            *
                            * @return lista con los objetos que se necesiten
                            * @throws Exception
                            */
    @Transactional(readOnly = true)
    public List<Anexos> findByCriteria(Object[] variables,
        Object[] variablesBetween, Object[] variablesBetweenDates)
        throws Exception {
        List<Anexos> list = new ArrayList<Anexos>();
        String where = new String();
        String tempWhere = new String();

        if (variables != null) {
            for (int i = 0; i < variables.length; i++) {
                if ((variables[i] != null) && (variables[i + 1] != null) &&
                        (variables[i + 2] != null) &&
                        (variables[i + 3] != null)) {
                    String variable = (String) variables[i];
                    Boolean booVariable = (Boolean) variables[i + 1];
                    Object value = variables[i + 2];
                    String comparator = (String) variables[i + 3];

                    if (booVariable.booleanValue()) {
                        tempWhere = (tempWhere.length() == 0)
                            ? ("(model." + variable + " " + comparator + " \'" +
                            value + "\' )")
                            : (tempWhere + " AND (model." + variable + " " +
                            comparator + " \'" + value + "\' )");
                    } else {
                        tempWhere = (tempWhere.length() == 0)
                            ? ("(model." + variable + " " + comparator + " " +
                            value + " )")
                            : (tempWhere + " AND (model." + variable + " " +
                            comparator + " " + value + " )");
                    }
                }

                i = i + 3;
            }
        }

        if (variablesBetween != null) {
            for (int j = 0; j < variablesBetween.length; j++) {
                if ((variablesBetween[j] != null) &&
                        (variablesBetween[j + 1] != null) &&
                        (variablesBetween[j + 2] != null) &&
                        (variablesBetween[j + 3] != null) &&
                        (variablesBetween[j + 4] != null)) {
                    String variable = (String) variablesBetween[j];
                    Object value = variablesBetween[j + 1];
                    Object value2 = variablesBetween[j + 2];
                    String comparator1 = (String) variablesBetween[j + 3];
                    String comparator2 = (String) variablesBetween[j + 4];
                    tempWhere = (tempWhere.length() == 0)
                        ? ("(" + value + " " + comparator1 + " " + variable +
                        " and " + variable + " " + comparator2 + " " + value2 +
                        " )")
                        : (tempWhere + " AND (" + value + " " + comparator1 +
                        " " + variable + " and " + variable + " " +
                        comparator2 + " " + value2 + " )");
                }

                j = j + 4;
            }
        }

        if (variablesBetweenDates != null) {
            for (int k = 0; k < variablesBetweenDates.length; k++) {
                if ((variablesBetweenDates[k] != null) &&
                        (variablesBetweenDates[k + 1] != null) &&
                        (variablesBetweenDates[k + 2] != null)) {
                    String variable = (String) variablesBetweenDates[k];
                    Object object1 = variablesBetweenDates[k + 1];
                    Object object2 = variablesBetweenDates[k + 2];
                    String value = null;
                    String value2 = null;

                    try {
                        Date date1 = (Date) object1;
                        Date date2 = (Date) object2;
                        value = Utilities.formatDateWithoutTimeInAStringForBetweenWhere(date1);
                        value2 = Utilities.formatDateWithoutTimeInAStringForBetweenWhere(date2);
                    } catch (Exception e) {
                        list = null;
                        throw e;
                    }

                    tempWhere = (tempWhere.length() == 0)
                        ? ("(model." + variable + " between \'" + value +
                        "\' and \'" + value2 + "\')")
                        : (tempWhere + " AND (model." + variable +
                        " between \'" + value + "\' and \'" + value2 + "\')");
                }

                k = k + 2;
            }
        }

        if (tempWhere.length() == 0) {
            where = null;
        } else {
            where = "(" + tempWhere + ")";
        }

        try {
            list = anexosDAO.findByCriteria(where);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
        }

        return list;
    }
}
