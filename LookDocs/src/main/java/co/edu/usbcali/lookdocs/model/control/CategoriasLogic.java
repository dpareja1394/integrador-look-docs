package co.edu.usbcali.lookdocs.model.control;

import co.edu.usbcali.lookdocs.dataaccess.dao.*;
import co.edu.usbcali.lookdocs.exceptions.*;
import co.edu.usbcali.lookdocs.model.*;
import co.edu.usbcali.lookdocs.model.dto.CategoriasDTO;
import co.edu.usbcali.lookdocs.utilities.FacesUtils;
import co.edu.usbcali.lookdocs.utilities.Utilities;

import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author Zathura Code Generator http://code.google.com/p/zathura
 *         www.zathuracode.org
 *
 */
@Scope("singleton")
@Service("CategoriasLogic")
public class CategoriasLogic implements ICategoriasLogic {
	/**
	 * DAO injected by Spring that manages Categorias entities
	 *
	 */
	@Autowired
	private ICategoriasDAO categoriasDAO;

	/**
	 * DAO injected by Spring that manages CategoriasArticulos entities
	 *
	 */
	@Autowired
	private ICategoriasArticulosDAO categoriasArticulosDAO;

	@Transactional(readOnly = true)
	public List<Categorias> getCategorias() throws Exception {
		List<Categorias> list = new ArrayList<Categorias>();

		try {
			list = categoriasDAO.findAll();
		} catch (Exception e) {
			throw new ZMessManager().new GettingException(ZMessManager.ALL
					+ "Categorias");
		} finally {
		}

		return list;
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Articulos> consultaArticulosPorCategoria(Long idCate) throws Exception {
		if(idCate==null){
			throw new ZMessManager("El id de la categoria es nulo");
		}
		
		if((idCate.toString()).trim().equals("")){
			throw new ZMessManager("El id de la categoria esta vacio");
		}
		
		List<Articulos> losAritculos = categoriasDAO.consultaArticulosPorCategoria(idCate);
		
		
		return losAritculos;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveCategorias(Categorias entity) throws Exception {
		try {
			Categorias entityCompare = categoriasDAO.findEntityByProperty(
					"nombre", entity.getNombre());

			if (entityCompare != null) {
				throw new Exception("Ya existe una categoria con ese nombre");
			}

			entity.setCodigoCate(categoriasDAO
					.getConsecutivo("CATEGORIAS_CODIGO_CATE_SEQ"));
			entity.setFechaCreacion(new Date());

			if (entity.getCodigoCate() == null) {
				throw new ZMessManager().new EmptyFieldException("Codigo Categoria");
			}

			if (entity.getEstadoRegistro() == null) {
				throw new ZMessManager().new EmptyFieldException(
						"Tipo");
			}

			if ((entity.getEstadoRegistro() != null)
					&& (Utilities.checkWordAndCheckWithlength(
							entity.getEstadoRegistro(), 1) == false)) {
				throw new ZMessManager().new NotValidFormatException(
						"Tipo");
			}

			if (entity.getFechaCreacion() == null) {
				throw new ZMessManager().new EmptyFieldException(
						"Fecha de creacion");
			}

			if (entity.getNombre() == null) {
				throw new ZMessManager().new EmptyFieldException("Nombre");
			}
			
			if (entity.getNombre().trim().equals("")) {
				throw new ZMessManager().new EmptyFieldException("Nombre");
			}

			if ((entity.getNombre() != null)
					&& (Utilities.checkWordAndCheckWithlength(
							entity.getNombre(), 150) == false)) {
				throw new ZMessManager().new NotValidFormatException("Nombre");
			}

			if (entity.getUsuCrea() == null) {
				throw new ZMessManager().new EmptyFieldException("Usuario");
			}

			if ((entity.getUsuCrea() != null)
					&& (Utilities.checkWordAndCheckWithlength(
							entity.getUsuCrea(), 150) == false)) {
				throw new ZMessManager().new NotValidFormatException("Usuario");
			}

			if ((entity.getUsuModifica() != null)
					&& (Utilities.checkWordAndCheckWithlength(
							entity.getUsuModifica(), 150) == false)) {
				throw new ZMessManager().new NotValidFormatException(
						"usuModifica");
			}

			if (getCategorias(entity.getCodigoCate()) != null) {
				throw new ZMessManager(ZMessManager.ENTITY_WITHSAMEKEY);
			}
			
			Object[] variables = {"nombre",true,entity.getNombre().trim(),"="};
			List<Categorias> validacion = findByCriteria(variables, null, null);
			if(validacion.size()>0){
				throw new ZMessManager("Ya existe una categoria con ese nombre");
			}

			categoriasDAO.save(entity);
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void deleteCategorias(Categorias entity) throws Exception {

		if (entity == null) {
			throw new ZMessManager().new NullEntityExcepcion("Categorias");
		}

		Categorias entityByName = categoriasDAO.findEntityByProperty("nombre",
				entity.getNombre());

		if (entityByName == null) {
			throw new Exception("La categoria no existe");
		}

		if (entityByName.getCodigoCate() == 1) {
			throw new Exception("Esta categoria no se puede eliminar");
		}

		categoriasDAO.deleteById(entityByName.getCodigoCate());
		/*
		 * List<CategoriasArticulos> categoriasArticuloses = null; //PENDIENTE
		 * POR PROBAR/////////////////////////////////////////////////////////
		 * 
		 * try { categoriasArticuloses = categoriasArticulosDAO.findByProperty(
		 * "categorias.codigoCate", entity.getCodigoCate());
		 * 
		 * if (Utilities.validationsList(categoriasArticuloses) == true) { for
		 * (CategoriasArticulos categoriasArticulos : categoriasArticuloses) {
		 * categoriasArticulos.setCategorias(categoriasDAO.findById(1L));
		 * categoriasArticulosDAO.update(categoriasArticulos); } }
		 * 
		 * categoriasDAO.delete(entity); /
		 * //////////////////////////////////////
		 * /////////////////////////////////////////
		 * 
		 * } catch (Exception e) { throw e; } finally {
		 * 
		 * }
		 */
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void deleteCategoriasbyNode(TreeNode selectedNode) throws Exception {

		if (selectedNode == null) {
			throw new Exception(
					"No ha seleccionado ninguna carpeta del men� para borrar.");
		}

		Categorias entity = (Categorias) selectedNode.getData();

		if (entity == null) {
			throw new ZMessManager().new NullEntityExcepcion("Categorias");
		}

		Categorias entityByName = categoriasDAO.findEntityByProperty("nombre",
				entity.getNombre());

		if (entityByName == null) {
			throw new Exception("La categoria no existe");
		}

		if (entityByName.getCodigoCate() == 1) {
			throw new Exception("Esta categoria no se puede eliminar");
		}

		categoriasDAO.deleteById(entityByName.getCodigoCate());
		/*
		 * List<CategoriasArticulos> categoriasArticuloses = null; //PENDIENTE
		 * POR PROBAR/////////////////////////////////////////////////////////
		 * 
		 * try { categoriasArticuloses = categoriasArticulosDAO.findByProperty(
		 * "categorias.codigoCate", entity.getCodigoCate());
		 * 
		 * if (Utilities.validationsList(categoriasArticuloses) == true) { for
		 * (CategoriasArticulos categoriasArticulos : categoriasArticuloses) {
		 * categoriasArticulos.setCategorias(categoriasDAO.findById(1L));
		 * categoriasArticulosDAO.update(categoriasArticulos); } }
		 * 
		 * categoriasDAO.delete(entity); /
		 * //////////////////////////////////////
		 * /////////////////////////////////////////
		 * 
		 * } catch (Exception e) { throw e; } finally {
		 * 
		 * }
		 */
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void updateCategorias(Categorias entity) throws Exception {
		try {

			if (entity == null) {
				throw new ZMessManager().new NullEntityExcepcion("Categorias");
			}
			
			Categorias entityByName = categoriasDAO.findById(entity.getCodigoCate());

			if (entity.getCodigoCate() == 1) {
				throw new Exception("Esta categor�a no se puede modificar");
			}

			if (entity.getEstadoRegistro() == null) {
				//throw new ZMessManager().new EmptyFieldException("estadoRegistro");
				entity.setEstadoRegistro(entityByName.getEstadoRegistro());
			}

			if ((entity.getEstadoRegistro() != null)
					&& (Utilities.checkWordAndCheckWithlength(
							entity.getEstadoRegistro(), 1) == false)) {
				throw new ZMessManager().new NotValidFormatException(
						"Tipo");
			}
			
			if (entity.getNombre() == null) {
				//throw new ZMessManager().new EmptyFieldException("estadoRegistro");
				entity.setNombre(entityByName.getNombre());
			}
			
			if (entity.getNombre().trim().equals("")) {
				//throw new ZMessManager().new EmptyFieldException("estadoRegistro");
				entity.setNombre(entityByName.getNombre());
			}

			if ((entity.getNombre() != null)
					&& (Utilities.checkWordAndCheckWithlength(
							entity.getNombre(), 150) == false)) {
				throw new ZMessManager().new NotValidFormatException("Nombre");
			}

			if ((entity.getUsuModifica() != null)
					&& (Utilities.checkWordAndCheckWithlength(
							entity.getUsuModifica(), 150) == false)) {
				throw new ZMessManager().new NotValidFormatException(
						"Usuario");
			}
			
			Object[] variables = {"nombre",true,entity.getNombre().trim(),"="};
			List<Categorias> validacion = findByCriteria(variables, null, null);
			if(validacion.size()>0){
				throw new ZMessManager("Ya existe una categoria con ese nombre");
			}

			entity.setFechaModifcacion(new Date());
			categoriasDAO.delete(entityByName);
			categoriasDAO.save(entity);
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}

	@Transactional(readOnly = true)
	public List<CategoriasDTO> getDataCategorias() throws Exception {
		try {
			List<Categorias> categorias = categoriasDAO.findAll();

			List<CategoriasDTO> categoriasDTO = new ArrayList<CategoriasDTO>();

			for (Categorias categoriasTmp : categorias) {
				CategoriasDTO categoriasDTO2 = new CategoriasDTO();

				categoriasDTO2.setCodigoCate(categoriasTmp.getCodigoCate());
				categoriasDTO2.setEstadoRegistro((categoriasTmp
						.getEstadoRegistro() != null) ? categoriasTmp
						.getEstadoRegistro() : null);
				categoriasDTO2.setFechaCreacion(categoriasTmp
						.getFechaCreacion());
				categoriasDTO2.setFechaModifcacion(categoriasTmp
						.getFechaModifcacion());
				categoriasDTO2
						.setNombre((categoriasTmp.getNombre() != null) ? categoriasTmp
								.getNombre() : null);
				categoriasDTO2
						.setUsuCrea((categoriasTmp.getUsuCrea() != null) ? categoriasTmp
								.getUsuCrea() : null);
				categoriasDTO2
						.setUsuModifica((categoriasTmp.getUsuModifica() != null) ? categoriasTmp
								.getUsuModifica() : null);
				categoriasDTO.add(categoriasDTO2);
			}

			return categoriasDTO;
		} catch (Exception e) {
			throw e;
		}
	}

	@Transactional(readOnly = true)
	public Categorias getCategorias(Long codigoCate) throws Exception {
		Categorias entity = null;

		try {
			entity = categoriasDAO.findById(codigoCate);
		} catch (Exception e) {
			throw new ZMessManager().new FindingException("Categorias");
		} finally {
		}

		return entity;
	}

	@Transactional(readOnly = true)
	public List<Categorias> findPageCategorias(String sortColumnName,
			boolean sortAscending, int startRow, int maxResults)
			throws Exception {
		List<Categorias> entity = null;

		try {
			entity = categoriasDAO.findPage(sortColumnName, sortAscending,
					startRow, maxResults);
		} catch (Exception e) {
			throw new ZMessManager().new FindingException("Categorias Count");
		} finally {
		}

		return entity;
	}

	@Transactional(readOnly = true)
	public Long findTotalNumberCategorias() throws Exception {
		Long entity = null;

		try {
			entity = categoriasDAO.count();
		} catch (Exception e) {
			throw new ZMessManager().new FindingException("Categorias Count");
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
	public List<Categorias> findByCriteria(Object[] variables,
			Object[] variablesBetween, Object[] variablesBetweenDates)
			throws Exception {
		List<Categorias> list = new ArrayList<Categorias>();
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
			list = categoriasDAO.findByCriteria(where);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		} finally {
		}

		return list;
	}
}
