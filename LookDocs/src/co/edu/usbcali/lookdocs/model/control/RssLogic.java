package co.edu.usbcali.lookdocs.model.control;

import co.edu.usbcali.lookdocs.dataaccess.dao.*;
import co.edu.usbcali.lookdocs.exceptions.*;
import co.edu.usbcali.lookdocs.model.*;
import co.edu.usbcali.lookdocs.model.dto.RssDTO;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Zathura Code Generator http://code.google.com/p/zathura
 *         www.zathuracode.org
 *
 */
@Scope("singleton")
@Service("RssLogic")
public class RssLogic implements IRssLogic {
	/**
	 * DAO injected by Spring that manages Rss entities
	 *
	 */
	@Autowired
	private IRssDAO rssDAO;

	/**
	 * DAO injected by Spring that manages ColeccionesRss entities
	 *
	 */
	@Autowired
	private IColeccionesRssDAO coleccionesRssDAO;

	/**
	 * DAO injected by Spring that manages Entradas entities
	 *
	 */
	@Autowired
	private IEntradasDAO entradasDAO;
	
	@Autowired
	private IColeccionesDAO coleccionesDAO;
	
	@Autowired
	private IColeccionesRssLogic coleccionesRssLogic;
	
	private static Logger logger = LoggerFactory.getLogger(RssLogic.class);

	@Transactional(readOnly = true)
	public List<Rss> getRss() throws Exception {
		List<Rss> list = new ArrayList<Rss>();

		try {
			list = rssDAO.findAll();
		} catch (Exception e) {
			throw new ZMessManager().new GettingException(ZMessManager.ALL
					+ "Rss");
		} finally {
		}

		return list;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveRss(Rss entity) throws Exception {
		try {
			if (entity.getCodigoRss() == null) {
				throw new ZMessManager().new EmptyFieldException("codigoRss");
			}

			if (entity.getUrl() == null) {
				throw new ZMessManager().new EmptyFieldException("url");
			}

			if ((entity.getUrl() != null)
					&& (Utilities.checkWordAndCheckWithlength(entity.getUrl(),
							500) == false)) {
				throw new ZMessManager().new NotValidFormatException("url");
			}

			if (getRss(entity.getCodigoRss()) != null) {
				throw new ZMessManager(ZMessManager.ENTITY_WITHSAMEKEY);
			}

			rssDAO.save(entity);
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void deleteRss(Rss entity) throws Exception {
		if (entity == null) {
			throw new ZMessManager().new NullEntityExcepcion("Rss");
		}

		if (entity.getCodigoRss() == null) {
			throw new ZMessManager().new EmptyFieldException("codigoRss");
		}

		List<ColeccionesRss> coleccionesRsses = null;
		List<Entradas> entradases = null;

		try {
			coleccionesRsses = coleccionesRssDAO.findByProperty(
					"rss.codigoRss", entity.getCodigoRss());

			if (Utilities.validationsList(coleccionesRsses) == true) {
				throw new ZMessManager().new DeletingException(
						"coleccionesRsses");
			}

			entradases = entradasDAO.findByProperty("rss.codigoRss",
					entity.getCodigoRss());

			if (Utilities.validationsList(entradases) == true) {
				throw new ZMessManager().new DeletingException("entradases");
			}

			rssDAO.delete(entity);
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void updateRss(Rss entity) throws Exception {
		try {
			if (entity == null) {
				throw new ZMessManager().new NullEntityExcepcion("Rss");
			}

			if (entity.getCodigoRss() == null) {
				throw new ZMessManager().new EmptyFieldException("codigoRss");
			}

			if (entity.getUrl() == null) {
				throw new ZMessManager().new EmptyFieldException("url");
			}

			if ((entity.getUrl() != null)
					&& (Utilities.checkWordAndCheckWithlength(entity.getUrl(),
							500) == false)) {
				throw new ZMessManager().new NotValidFormatException("url");
			}

			rssDAO.update(entity);
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}

	@Transactional(readOnly = true)
	public List<RssDTO> getDataRss() throws Exception {
		try {
			List<Rss> rss = rssDAO.findAll();

			List<RssDTO> rssDTO = new ArrayList<RssDTO>();

			for (Rss rssTmp : rss) {
				RssDTO rssDTO2 = new RssDTO();

				rssDTO2.setCodigoRss(rssTmp.getCodigoRss());
				rssDTO2.setUrl((rssTmp.getUrl() != null) ? rssTmp.getUrl()
						: null);
				rssDTO.add(rssDTO2);
			}

			return rssDTO;
		} catch (Exception e) {
			throw e;
		}
	}

	@Transactional(readOnly = true)
	public Rss getRss(Long codigoRss) throws Exception {
		Rss entity = null;

		try {
			entity = rssDAO.findById(codigoRss);
		} catch (Exception e) {
			throw new ZMessManager().new FindingException("Rss");
		} finally {
		}

		return entity;
	}

	@Transactional(readOnly = true)
	public List<Rss> findPageRss(String sortColumnName, boolean sortAscending,
			int startRow, int maxResults) throws Exception {
		List<Rss> entity = null;

		try {
			entity = rssDAO.findPage(sortColumnName, sortAscending, startRow,
					maxResults);
		} catch (Exception e) {
			throw new ZMessManager().new FindingException("Rss Count");
		} finally {
		}

		return entity;
	}

	@Transactional(readOnly = true)
	public Long findTotalNumberRss() throws Exception {
		Long entity = null;

		try {
			entity = rssDAO.count();
		} catch (Exception e) {
			throw new ZMessManager().new FindingException("Rss Count");
		} finally {
		}

		return entity;
	}

	/**
	 *
	 * @param varibles
	 *            este arreglo debera tener:
	 *
	 *            [0] = String variable = (String) varibles[i]; representa como
	 *            se llama la variable en el pojo
	 *
	 *            [1] = Boolean booVariable = (Boolean) varibles[i + 1];
	 *            representa si el valor necesita o no ''(comillas simples)usado
	 *            para campos de tipo string
	 *
	 *            [2] = Object value = varibles[i + 2]; representa el valor que
	 *            se va a buscar en la BD
	 *
	 *            [3] = String comparator = (String) varibles[i + 3]; representa
	 *            que tipo de busqueda voy a hacer.., ejemplo: where
	 *            nombre=william o where nombre<>william, en este campo iria el
	 *            tipo de comparador que quiero si es = o <>
	 *
	 *            Se itera de 4 en 4..., entonces 4 registros del arreglo
	 *            representan 1 busqueda en un campo, si se ponen mas pues el
	 *            continuara buscando en lo que se le ingresen en los otros 4
	 *
	 *
	 * @param variablesBetween
	 *
	 *            la diferencia son estas dos posiciones
	 *
	 *            [0] = String variable = (String) varibles[j]; la variable ne
	 *            la BD que va a ser buscada en un rango
	 *
	 *            [1] = Object value = varibles[j + 1]; valor 1 para buscar en
	 *            un rango
	 *
	 *            [2] = Object value2 = varibles[j + 2]; valor 2 para buscar en
	 *            un rango ejempolo: a > 1 and a < 5 --> 1 seria value y 5 seria
	 *            value2
	 *
	 *            [3] = String comparator1 = (String) varibles[j + 3];
	 *            comparador 1 ejemplo: a comparator1 1 and a < 5
	 *
	 *            [4] = String comparator2 = (String) varibles[j + 4];
	 *            comparador 2 ejemplo: a comparador1>1 and a comparador2<5 (el
	 *            original: a > 1 and a < 5) *
	 * @param variablesBetweenDates
	 *            (en este caso solo para mysql) [0] = String variable =
	 *            (String) varibles[k]; el nombre de la variable que hace
	 *            referencia a una fecha
	 *
	 *            [1] = Object object1 = varibles[k + 2]; fecha 1 a
	 *            comparar(deben ser dates)
	 *
	 *            [2] = Object object2 = varibles[k + 3]; fecha 2 a
	 *            comparar(deben ser dates)
	 *
	 *            esto hace un between entre las dos fechas.
	 *
	 * @return lista con los objetos que se necesiten
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public List<Rss> findByCriteria(Object[] variables,
			Object[] variablesBetween, Object[] variablesBetweenDates)
			throws Exception {
		List<Rss> list = new ArrayList<Rss>();
		String where = new String();
		String tempWhere = new String();

		if (variables != null) {
			for (int i = 0; i < variables.length; i++) {
				if ((variables[i] != null) && (variables[i + 1] != null)
						&& (variables[i + 2] != null)
						&& (variables[i + 3] != null)) {
					String variable = (String) variables[i];
					Boolean booVariable = (Boolean) variables[i + 1];
					Object value = variables[i + 2];
					String comparator = (String) variables[i + 3];

					if (booVariable.booleanValue()) {
						tempWhere = (tempWhere.length() == 0) ? ("(model."
								+ variable + " " + comparator + " \'" + value + "\' )")
								: (tempWhere + " AND (model." + variable + " "
										+ comparator + " \'" + value + "\' )");
					} else {
						tempWhere = (tempWhere.length() == 0) ? ("(model."
								+ variable + " " + comparator + " " + value + " )")
								: (tempWhere + " AND (model." + variable + " "
										+ comparator + " " + value + " )");
					}
				}

				i = i + 3;
			}
		}

		if (variablesBetween != null) {
			for (int j = 0; j < variablesBetween.length; j++) {
				if ((variablesBetween[j] != null)
						&& (variablesBetween[j + 1] != null)
						&& (variablesBetween[j + 2] != null)
						&& (variablesBetween[j + 3] != null)
						&& (variablesBetween[j + 4] != null)) {
					String variable = (String) variablesBetween[j];
					Object value = variablesBetween[j + 1];
					Object value2 = variablesBetween[j + 2];
					String comparator1 = (String) variablesBetween[j + 3];
					String comparator2 = (String) variablesBetween[j + 4];
					tempWhere = (tempWhere.length() == 0) ? ("(" + value + " "
							+ comparator1 + " " + variable + " and " + variable
							+ " " + comparator2 + " " + value2 + " )")
							: (tempWhere + " AND (" + value + " " + comparator1
									+ " " + variable + " and " + variable + " "
									+ comparator2 + " " + value2 + " )");
				}

				j = j + 4;
			}
		}

		if (variablesBetweenDates != null) {
			for (int k = 0; k < variablesBetweenDates.length; k++) {
				if ((variablesBetweenDates[k] != null)
						&& (variablesBetweenDates[k + 1] != null)
						&& (variablesBetweenDates[k + 2] != null)) {
					String variable = (String) variablesBetweenDates[k];
					Object object1 = variablesBetweenDates[k + 1];
					Object object2 = variablesBetweenDates[k + 2];
					String value = null;
					String value2 = null;

					try {
						Date date1 = (Date) object1;
						Date date2 = (Date) object2;
						value = Utilities
								.formatDateWithoutTimeInAStringForBetweenWhere(date1);
						value2 = Utilities
								.formatDateWithoutTimeInAStringForBetweenWhere(date2);
					} catch (Exception e) {
						list = null;
						throw e;
					}

					tempWhere = (tempWhere.length() == 0) ? ("(model."
							+ variable + " between \'" + value + "\' and \'"
							+ value2 + "\')") : (tempWhere + " AND (model."
							+ variable + " between \'" + value + "\' and \'"
							+ value2 + "\')");
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
			list = rssDAO.findByCriteria(where);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		} finally {
		}

		return list;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void guardarRSS(String urlRss, Long idColeccion) throws Exception {
		if(urlRss.trim().equals("")==true){
			throw new Exception("Debe ingresar el url del sitio");
		}
		if(idColeccion<=0){
			throw new Exception("Debe seleccionar una colección para agregar el RSS");
		}
		
		Colecciones colecciones = coleccionesDAO.findById(idColeccion);
		if(colecciones==null){
			throw new Exception("No existe la colección");
		}
		ColeccionesRss consulta = coleccionesRssDAO.consultarSiExisteRssEnLaColeccion(idColeccion, urlRss);
		if(consulta!=null){
			throw new Exception("Ya existe este Rss en esta colección");
		}
		Long codigoRss = getConsecutivo("rss_codigo_rss_seq");
		Rss rss = new Rss();
		rss.setCodigoRss(codigoRss);
		rss.setUrl(urlRss);
		rssDAO.save(rss);
		ColeccionesRss coleccionesRss = new ColeccionesRss();
		coleccionesRss.setColecciones(colecciones);
		coleccionesRss.setRss(rss);
		coleccionesRssLogic.guardarColeccionesRSS(coleccionesRss);
		
		
		
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Long getConsecutivo(String sqlName) throws Exception {
		if (sqlName.trim().equals("")) {
			throw new Exception("El nombre del Sql no debe estar vacío");
		}
		return rssDAO.getConsecutivo(sqlName);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Rss> getRssDadoIdColeccion(Long codigoCole) throws Exception {
		return rssDAO.getRssDadoIdColeccion(codigoCole);
	}
}
