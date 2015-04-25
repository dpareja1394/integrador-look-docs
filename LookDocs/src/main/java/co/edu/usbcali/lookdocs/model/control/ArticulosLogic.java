package co.edu.usbcali.lookdocs.model.control;

import co.edu.usbcali.lookdocs.dataaccess.dao.*;
import co.edu.usbcali.lookdocs.exceptions.*;
import co.edu.usbcali.lookdocs.exceptions.ZMessManager.EmptyFieldException;
import co.edu.usbcali.lookdocs.exceptions.ZMessManager.NotValidFormatException;
import co.edu.usbcali.lookdocs.model.*;
import co.edu.usbcali.lookdocs.model.dto.ArticulosDTO;
import co.edu.usbcali.lookdocs.utilities.Utilities;

import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author Zathura Code Generator http://code.google.com/p/zathura
 *         www.zathuracode.org
 *
 */
@Scope("singleton")
@Service("ArticulosLogic")
public class ArticulosLogic implements IArticulosLogic {
	/**
	 * DAO injected by Spring that manages Articulos entities
	 *
	 */
	@Autowired
	private IArticulosDAO articulosDAO;

	/**
	 * DAO injected by Spring that manages Anexos entities
	 *
	 */
	@Autowired
	private IAnexosDAO anexosDAO;

	/**
	 * DAO injected by Spring that manages CategoriasArticulos entities
	 *
	 */
	@Autowired
	private ICategoriasArticulosDAO categoriasArticulosDAO;

	/**
	 * DAO injected by Spring that manages EventosArticulos entities
	 *
	 */
	@Autowired
	private IEventosArticulosDAO eventosArticulosDAO;
	
	@Autowired
	private ICategoriasDAO categoriasDAO;

	/**
	 * Logic injected by Spring that manages Usuarios entities
	 *
	 */
	@Autowired
	IUsuariosLogic logicUsuarios1;

	@Transactional(readOnly = true)
	public List<Articulos> getArticulos() throws Exception {
		List<Articulos> list = new ArrayList<Articulos>();

		try {
			list = articulosDAO.findAll();
		} catch (Exception e) {
			throw new ZMessManager().new GettingException(ZMessManager.ALL
					+ "los articulos");
		} finally {
		}

		return list;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void TransferFile(String server, String user, String pass,
			UploadedFile file, String remotePath) throws Exception {

		if (server == null) {
			throw new Exception("El servidor esta vacio o nulo");
		}

		if (server.trim().equals("")) {
			throw new Exception("El servidor esta vacio o nulo");
		}

		if (user == null) {
			throw new Exception("El usuario est� vacio o nulo");
		}

		if (user.trim().equals("")) {
			throw new Exception("El usuario est� vacio o nulo");
		}

		if (pass == null) {
			throw new Exception("La contrase�a esta vacia o nula");
		}

		if (pass.trim().equals("")) {
			throw new Exception("La contrase�a esta vacia o nula");
		}

		if (file == null) {
			throw new Exception("Debe escoger un archivo");
		}

		if (file.getFileName().trim().equals("")) {
			throw new Exception("Debe escoger un archivo");
		}

		if (remotePath == null) {
			throw new Exception("La ruta en el servidor esta vacia o nula");
		}

		if (remotePath.trim().equals("")) {
			throw new Exception("La ruta en el servidor esta vacia o nula");
		}

		articulosDAO.TransferFile(server, user, pass, file, remotePath);
			
		
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveArticulos(Articulos entity, String categoriaSelected, String pdfUrl) throws Exception {
		Categorias categ_arti = new Categorias();
		try {
			
			if (entity == null) {
				throw new Exception("Entidad nula o vacia");
			}
			
			if (entity.getNombre() == null) {
				throw new ZMessManager().new EmptyFieldException("Nombre");
			}

			if ((entity.getNombre() != null)
					&& (Utilities.checkWordAndCheckWithlength(
							entity.getNombre(), 500) == false)) {
				throw new ZMessManager().new NotValidFormatException("Nombre");
			}
			
			Articulos entityCompare =articulosDAO.findEntityByProperty(
					"nombre", entity.getNombre());

			if (entityCompare != null) {
				throw new Exception("Ya existe un articulo con ese nombre");
			}
			
			entity.setCodigoArti(articulosDAO
					.getConsecutivo("ARTICULOS_CODIGO_ARTI_SEQ"));
			entity.setFechaCreacion(new Date());
			
			if (entity.getUsuarios() == null) {
				throw new ZMessManager().new ForeignException("Usuario");
			}

			if (entity.getAutor() == null) {
				throw new ZMessManager().new EmptyFieldException("Autor");
			}

			if ((entity.getAutor() != null)
					&& (Utilities.checkWordAndCheckWithlength(
							entity.getAutor(), 150) == false)) {
				throw new ZMessManager().new NotValidFormatException("Autor");
			}

			if (entity.getCodigoArti() == null) {
				throw new ZMessManager().new EmptyFieldException("Codigo del art�culo");
			}

			if (entity.getDescripcion() == null) {
				throw new ZMessManager().new EmptyFieldException("Descripcion");
			}

			if ((entity.getDescripcion() != null)
					&& (Utilities.checkWordAndCheckWithlength(
							entity.getDescripcion(), 500) == false)) {
				throw new ZMessManager().new NotValidFormatException(
						"Descripcion");
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
						"Fecha de Creacion");
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
						"Usuario");
			}

			if (entity.getUsuarios().getCodigoUsua() == null) {
				throw new ZMessManager().new EmptyFieldException(
						"Usuario");
			}

			if (getArticulos(entity.getCodigoArti()) != null) {
				throw new ZMessManager(ZMessManager.ENTITY_WITHSAMEKEY);
			}
			
			CategoriasArticulos categoriasArticulos = new CategoriasArticulos();
			categoriasArticulos.setCodigoCateArti(
					categoriasArticulosDAO.getConsecutivo("CATEGORIAS_ARTICULOS_CODIGO_CATE_ARTI_SEQ"));
			categoriasArticulos.setArticulos(entity);
			
			 if(categoriaSelected==null || categoriaSelected.trim().equals("")){
				 categ_arti = categoriasDAO.findById(1L);
				 categoriasArticulos.setCategorias(categ_arti);
			 }else{
				 categ_arti = categoriasDAO.findById(Long.parseLong(categoriaSelected));
				 categoriasArticulos.setCategorias(categ_arti);
			 }
			
			categoriasArticulos.setFechaCreacion(new Date());
			categoriasArticulos.setUsuCrea(entity.getUsuCrea());
			categoriasArticulos.setEstadoRegistro(entity.getEstadoRegistro());
			
			Anexos anexos = new Anexos();
			anexos.setArticulos(entity);
			anexos.setCodigoAnexo(anexosDAO.getConsecutivo("ANEXOS_CODIGO_ANEXO_SEQ"));
			anexos.setNombre(entity.getNombre());
			anexos.setUrl(pdfUrl);
			anexos.setFormato("pdf");
			
			articulosDAO.save(entity);
			categoriasArticulosDAO.save(categoriasArticulos);
			anexosDAO.save(anexos);
			
			
			
			
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}

	public String downloadFileByFTP(String server, String user, String pass,
			String fileName, String remotePath) throws Exception {

		if (server == null) {
			throw new Exception("El servidor esta vacio o nulo");
		}

		if (server.trim().equals("")) {
			throw new Exception("El servidor esta vacio o nulo");
		}

		if (user == null) {
			throw new Exception("El usuario est� vacio o nulo");
		}

		if (user.trim().equals("")) {
			throw new Exception("El usuario est� vacio o nulo");
		}

		if (pass == null) {
			throw new Exception("La contrase�a esta vacia o nula");
		}

		if (pass.trim().equals("")) {
			throw new Exception("La contrase�a esta vacia o nula");
		}

		if (fileName == null) {
			throw new Exception("Debe escoger un archivo");
		}

		if (fileName.trim().equals("")) {
			throw new Exception("Debe escoger un archivo");
		}

		if (remotePath == null) {
			throw new Exception("La ruta en el servidor esta vacia o nula");
		}

		if (remotePath.trim().equals("")) {
			throw new Exception("La ruta en el servidor esta vacia o nula");
		}

		return articulosDAO.downloadFileByFTP(server, user, pass, fileName,
				remotePath);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void deleteArticulos(Articulos entity) throws Exception {
		if (entity == null) {
			throw new ZMessManager().new NullEntityExcepcion("Articulos");
		}

		if (entity.getCodigoArti() == null) {
			throw new ZMessManager().new EmptyFieldException("Codigo del Art�culo");
		}

		List<Anexos> anexoses = null;
		List<CategoriasArticulos> categoriasArticuloses = null;
		List<EventosArticulos> eventosArticuloses = null;

		try {
			List<CategoriasArticulos> categoriasArticulos = categoriasArticulosDAO.findByProperty("articulos.codigoArti",entity.getCodigoArti());
			List<Anexos> anexosdelte = anexosDAO.findByProperty("articulos.codigoArti",entity.getCodigoArti()); 
			categoriasArticulosDAO.delete(categoriasArticulos.get(0));
			anexosDAO.delete(anexosdelte.get(0));
			
			anexoses = anexosDAO.findByProperty("articulos.codigoArti",
					entity.getCodigoArti());

			if (Utilities.validationsList(anexoses) == true) {
				throw new ZMessManager().new DeletingException("Tabla Anexos");
			}

			categoriasArticuloses = categoriasArticulosDAO.findByProperty(
					"articulos.codigoArti", entity.getCodigoArti());

			if (Utilities.validationsList(categoriasArticuloses) == true) {
				throw new ZMessManager().new DeletingException(
						"Tabla Categorias Articulos");
			}

			eventosArticuloses = eventosArticulosDAO.findByProperty(
					"articulos.codigoArti", entity.getCodigoArti());

			if (Utilities.validationsList(eventosArticuloses) == true) {
				throw new ZMessManager().new DeletingException(
						"Tabla Eventos");
			}
			
			articulosDAO.delete(entity);
		} catch (Exception e) {
			throw new Exception("No se puede eliminar hay otros datos relacionados");
		} finally {
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void updateArticulos(Articulos entity) throws Exception {
		try {
			if (entity == null) {
				throw new ZMessManager().new NullEntityExcepcion("Articulos");
			}

			if (entity.getUsuarios() == null) {
				throw new ZMessManager().new ForeignException("usuarios");
			}

			if (entity.getAutor() == null) {
				throw new ZMessManager().new EmptyFieldException("autor");
			}

			if ((entity.getAutor() != null)
					&& (Utilities.checkWordAndCheckWithlength(
							entity.getAutor(), 150) == false)) {
				throw new ZMessManager().new NotValidFormatException("autor");
			}

			if (entity.getCodigoArti() == null) {
				throw new ZMessManager().new EmptyFieldException("codigoArti");
			}

			if (entity.getDescripcion() == null) {
				throw new ZMessManager().new EmptyFieldException("descripcion");
			}

			if ((entity.getDescripcion() != null)
					&& (Utilities.checkWordAndCheckWithlength(
							entity.getDescripcion(), 500) == false)) {
				throw new ZMessManager().new NotValidFormatException(
						"descripcion");
			}

			if (entity.getEstadoRegistro() == null) {
				throw new ZMessManager().new EmptyFieldException(
						"estadoRegistro");
			}

			if ((entity.getEstadoRegistro() != null)
					&& (Utilities.checkWordAndCheckWithlength(
							entity.getEstadoRegistro(), 1) == false)) {
				throw new ZMessManager().new NotValidFormatException(
						"estadoRegistro");
			}

			if (entity.getFechaCreacion() == null) {
				throw new ZMessManager().new EmptyFieldException(
						"fechaCreacion");
			}

			if (entity.getNombre() == null) {
				throw new ZMessManager().new EmptyFieldException("nombre");
			}

			if ((entity.getNombre() != null)
					&& (Utilities.checkWordAndCheckWithlength(
							entity.getNombre(), 500) == false)) {
				throw new ZMessManager().new NotValidFormatException("nombre");
			}

			if (entity.getUsuCrea() == null) {
				throw new ZMessManager().new EmptyFieldException("usuCrea");
			}

			if ((entity.getUsuCrea() != null)
					&& (Utilities.checkWordAndCheckWithlength(
							entity.getUsuCrea(), 150) == false)) {
				throw new ZMessManager().new NotValidFormatException("usuCrea");
			}

			if ((entity.getUsuModifica() != null)
					&& (Utilities.checkWordAndCheckWithlength(
							entity.getUsuModifica(), 150) == false)) {
				throw new ZMessManager().new NotValidFormatException(
						"usuModifica");
			}

			if (entity.getUsuarios().getCodigoUsua() == null) {
				throw new ZMessManager().new EmptyFieldException(
						"codigoUsua_Usuarios");
			}

			articulosDAO.update(entity);
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}

	@Transactional(readOnly = true)
	public List<ArticulosDTO> getDataArticulos() throws Exception {
		try {
			List<Object> articulos = articulosDAO.consultaArticulosCategoria();
			EventosArticulos evento = new EventosArticulos();

			List<ArticulosDTO> articulosDTO = new ArrayList<ArticulosDTO>();			
			for (Object articulosTmp : articulos) {
				
				ArticulosDTO articulosDTO2 = new ArticulosDTO();
				Object[] articulosTemp = (Object[]) articulosTmp;
				evento = eventosArticulosDAO.consultareEventosArticulos((Long) articulosTemp[0]);
				
				
				articulosDTO2
						.setCodigoArti((articulosTemp[0] != null) ? (Long) articulosTemp[0]
								: null);
				articulosDTO2
						.setAutor((articulosTemp[3] != null) ? (String) articulosTemp[3]
								: null);
				articulosDTO2
						.setDescripcion((articulosTemp[2] != null) ? (String) articulosTemp[2]
								: null);
				
				articulosDTO2.setEstadoRegistro((articulosTemp[8] != null) ? (String) articulosTemp[8]
						: null);
				articulosDTO2.setFechaCreacion((articulosTemp[4] != null) ? (Date) articulosTemp[4]
						: null);
				articulosDTO2.setFechaModifcacion((articulosTemp[5] != null) ? (Date) articulosTemp[5]
						: null);
				articulosDTO2
						.setNombre((articulosTemp[1] != null) ? (String) articulosTemp[1]
								: null);
				articulosDTO2
						.setUsuCrea((articulosTemp[6] != null) ? (String) articulosTemp[6]
								: null);
				articulosDTO2
						.setUsuModifica((articulosTemp[7] != null) ? (String) articulosTemp[7]
								: null);
				articulosDTO2.setCodigoUsua_Usuarios((articulosTemp[9] != null) ? (Long) articulosTemp[9]
						: null);
				articulosDTO2.setCategoria((articulosTemp[10] != null) ? (String) articulosTemp[10]
						: null);
				articulosDTO2.setLike((evento!=null) ? ((evento.getMeGusta()).equals("S")) ? "Me gusta" : "No me gusta" : null);
				articulosDTO.add(articulosDTO2);
			}

			return articulosDTO;
		} catch (Exception e) {
			throw e;
		}
	}
	
	@Transactional(readOnly = true)
	public List<ArticulosDTO> getDataArticulosByCateg(Long codigoCate) throws Exception {
		try {
			List<Object> articulos = articulosDAO.consultaArticulosCategoriaById(codigoCate);

			List<ArticulosDTO> articulosDTO = new ArrayList<ArticulosDTO>();			
			for (Object articulosTmp : articulos) {
				
				ArticulosDTO articulosDTO2 = new ArticulosDTO();
				Object[] articulosTemp = (Object[]) articulosTmp;
				
				articulosDTO2
						.setCodigoArti((articulosTemp[0] != null) ? (Long) articulosTemp[0]
								: null);
				articulosDTO2
						.setAutor((articulosTemp[3] != null) ? (String) articulosTemp[3]
								: null);
				articulosDTO2
						.setDescripcion((articulosTemp[2] != null) ? (String) articulosTemp[2]
								: null);
				
				articulosDTO2.setEstadoRegistro((articulosTemp[8] != null) ? (String) articulosTemp[8]
						: null);
				articulosDTO2.setFechaCreacion((articulosTemp[4] != null) ? (Date) articulosTemp[4]
						: null);
				articulosDTO2.setFechaModifcacion((articulosTemp[5] != null) ? (Date) articulosTemp[5]
						: null);
				articulosDTO2
						.setNombre((articulosTemp[1] != null) ? (String) articulosTemp[1]
								: null);
				articulosDTO2
						.setUsuCrea((articulosTemp[6] != null) ? (String) articulosTemp[6]
								: null);
				articulosDTO2
						.setUsuModifica((articulosTemp[7] != null) ? (String) articulosTemp[7]
								: null);
				articulosDTO2.setCodigoUsua_Usuarios((articulosTemp[9] != null) ? (Long) articulosTemp[9]
						: null);
				articulosDTO2.setCategoria((articulosTemp[10] != null) ? (String) articulosTemp[10]
						: null);
				articulosDTO.add(articulosDTO2);
			}

			return articulosDTO;
		} catch (Exception e) {
			throw e;
		}
	}

	@Transactional(readOnly = true)
	public Articulos getArticulos(Long codigoArti) throws Exception {
		Articulos entity = null;

		try {
			entity = articulosDAO.findById(codigoArti);
		} catch (Exception e) {
			throw new ZMessManager().new FindingException("Articulos");
		} finally {
		}

		return entity;
	}

	@Transactional(readOnly = true)
	public List<Articulos> findPageArticulos(String sortColumnName,
			boolean sortAscending, int startRow, int maxResults)
			throws Exception {
		List<Articulos> entity = null;

		try {
			entity = articulosDAO.findPage(sortColumnName, sortAscending,
					startRow, maxResults);
		} catch (Exception e) {
			throw new ZMessManager().new FindingException("Articulos Count");
		} finally {
		}

		return entity;
	}

	@Transactional(readOnly = true)
	public Long findTotalNumberArticulos() throws Exception {
		Long entity = null;

		try {
			entity = articulosDAO.count();
		} catch (Exception e) {
			throw new ZMessManager().new FindingException("Articulos Count");
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
	public List<Articulos> findByCriteria(Object[] variables,
			Object[] variablesBetween, Object[] variablesBetweenDates)
			throws Exception {
		List<Articulos> list = new ArrayList<Articulos>();
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
			list = articulosDAO.findByCriteria(where);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		} finally {
		}

		return list;
	}
	
	 @Override
	    @Transactional(readOnly = true)
	    public List<Articulos> consultarTodosArticulos() throws Exception{
	    	return articulosDAO.consultarTodosArticulos();
	    }
}
