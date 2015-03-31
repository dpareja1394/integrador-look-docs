package co.edu.usbcali.lookdocs.model.control;

import co.edu.usbcali.lookdocs.dataaccess.dao.*;
import co.edu.usbcali.lookdocs.exceptions.*;
import co.edu.usbcali.lookdocs.model.*;
import co.edu.usbcali.lookdocs.model.dto.UsuariosDTO;
import co.edu.usbcali.lookdocs.utilities.FacesUtils;
import co.edu.usbcali.lookdocs.utilities.Utilities;

import org.apache.commons.validator.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 * @author Zathura Code Generator http://code.google.com/p/zathura
 *         www.zathuracode.org
 *
 */
@Scope("singleton")
@Service("UsuariosLogic")
public class UsuariosLogic implements IUsuariosLogic {
	@Autowired
	private MailSender mailSender;

	@Autowired
	private SimpleMailMessage templateMessage;

	/**
	 * DAO injected by Spring that manages Usuarios entities
	 *
	 */
	@Autowired
	private IUsuariosDAO usuariosDAO;

	/**
	 * DAO injected by Spring that manages Articulos entities
	 *
	 */
	@Autowired
	private IArticulosDAO articulosDAO;

	/**
	 * DAO injected by Spring that manages Colecciones entities
	 *
	 */
	@Autowired
	private IColeccionesDAO coleccionesDAO;

	/**
	 * DAO injected by Spring that manages EventosArticulos entities
	 *
	 */
	@Autowired
	private IEventosArticulosDAO eventosArticulosDAO;

	/**
	 * Logic injected by Spring that manages Roles entities
	 *
	 */
	@Autowired
	IRolesLogic logicRoles1;

	@Autowired
	IRolesDAO rolesDAO;

	@Transactional(readOnly = true)
	public List<Usuarios> getUsuarios() throws Exception {
		List<Usuarios> list = new ArrayList<Usuarios>();

		try {
			list = usuariosDAO.findAll();
		} catch (Exception e) {
			throw new ZMessManager().new GettingException(ZMessManager.ALL
					+ "Usuarios");
		} finally {
		}

		return list;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void saveUsuarios(Usuarios entity) throws Exception {
		try {
			if (entity.getRoles() == null) {
				throw new ZMessManager().new ForeignException("roles");
			}

			if (entity.getClave() == null) {
				throw new ZMessManager().new EmptyFieldException("clave");
			}

			if ((entity.getClave() != null)
					&& (Utilities.checkWordAndCheckWithlength(
							entity.getClave(), 150) == false)) {
				throw new ZMessManager().new NotValidFormatException("clave");
			}

			if (entity.getCodigoUsua() == null) {
				throw new ZMessManager().new EmptyFieldException("codigoUsua");
			}

			if (entity.getEmail() == null) {
				throw new ZMessManager().new EmptyFieldException("email");
			}

			if ((entity.getEmail() != null)
					&& (Utilities.checkWordAndCheckWithlength(
							entity.getEmail(), 150) == false)) {
				throw new ZMessManager().new NotValidFormatException("email");
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
							entity.getNombre(), 150) == false)) {
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

			if (entity.getRoles().getCodigoRol() == null) {
				throw new ZMessManager().new EmptyFieldException(
						"codigoRol_Roles");
			}

			if (getUsuarios(entity.getCodigoUsua()) != null) {
				throw new ZMessManager(ZMessManager.ENTITY_WITHSAMEKEY);
			}

			usuariosDAO.save(entity);
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void deleteUsuarios(Usuarios entity) throws Exception {
		if (entity == null) {
			throw new ZMessManager().new NullEntityExcepcion("Usuarios");
		}

		if (entity.getCodigoUsua() == null) {
			throw new ZMessManager().new EmptyFieldException("codigoUsua");
		}

		List<Articulos> articuloses = null;
		List<Colecciones> coleccioneses = null;
		List<EventosArticulos> eventosArticuloses = null;

		try {
			articuloses = articulosDAO.findByProperty("usuarios.codigoUsua",
					entity.getCodigoUsua());

			if (Utilities.validationsList(articuloses) == true) {
				throw new ZMessManager().new DeletingException("articuloses");
			}

			coleccioneses = coleccionesDAO.findByProperty(
					"usuarios.codigoUsua", entity.getCodigoUsua());

			if (Utilities.validationsList(coleccioneses) == true) {
				throw new ZMessManager().new DeletingException("coleccioneses");
			}

			eventosArticuloses = eventosArticulosDAO.findByProperty(
					"usuarios.codigoUsua", entity.getCodigoUsua());

			if (Utilities.validationsList(eventosArticuloses) == true) {
				throw new ZMessManager().new DeletingException(
						"eventosArticuloses");
			}

			usuariosDAO.delete(entity);
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void updateUsuarios(Usuarios entity) throws Exception {
		try {
			if (entity == null) {
				throw new ZMessManager().new NullEntityExcepcion("Usuarios");
			}

			if (entity.getRoles() == null) {
				throw new ZMessManager().new ForeignException("roles");
			}

			if (entity.getClave() == null) {
				throw new ZMessManager().new EmptyFieldException("clave");
			}

			if ((entity.getClave() != null)
					&& (Utilities.checkWordAndCheckWithlength(
							entity.getClave(), 150) == false)) {
				throw new ZMessManager().new NotValidFormatException("clave");
			}

			if (entity.getCodigoUsua() == null) {
				throw new ZMessManager().new EmptyFieldException("codigoUsua");
			}

			if (entity.getEmail() == null) {
				throw new ZMessManager().new EmptyFieldException("email");
			}

			if ((entity.getEmail() != null)
					&& (Utilities.checkWordAndCheckWithlength(
							entity.getEmail(), 150) == false)) {
				throw new ZMessManager().new NotValidFormatException("email");
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
							entity.getNombre(), 150) == false)) {
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

			if (entity.getRoles().getCodigoRol() == null) {
				throw new ZMessManager().new EmptyFieldException(
						"codigoRol_Roles");
			}

			usuariosDAO.update(entity);
		} catch (Exception e) {
			throw e;
		} finally {
		}
	}

	@Transactional(readOnly = true)
	public List<UsuariosDTO> getDataUsuarios() throws Exception {
		try {
			List<Usuarios> usuarios = usuariosDAO.findAll();

			List<UsuariosDTO> usuariosDTO = new ArrayList<UsuariosDTO>();

			for (Usuarios usuariosTmp : usuarios) {
				UsuariosDTO usuariosDTO2 = new UsuariosDTO();

				usuariosDTO2.setCodigoUsua(usuariosTmp.getCodigoUsua());
				usuariosDTO2
						.setClave((usuariosTmp.getClave() != null) ? usuariosTmp
								.getClave() : null);
				usuariosDTO2
						.setEmail((usuariosTmp.getEmail() != null) ? usuariosTmp
								.getEmail() : null);
				usuariosDTO2
						.setEstadoRegistro((usuariosTmp.getEstadoRegistro() != null) ? usuariosTmp
								.getEstadoRegistro() : null);
				usuariosDTO2.setFechaCreacion(usuariosTmp.getFechaCreacion());
				usuariosDTO2.setFechaModifcacion(usuariosTmp
						.getFechaModifcacion());
				usuariosDTO2
						.setNombre((usuariosTmp.getNombre() != null) ? usuariosTmp
								.getNombre() : null);
				usuariosDTO2
						.setUsuCrea((usuariosTmp.getUsuCrea() != null) ? usuariosTmp
								.getUsuCrea() : null);
				usuariosDTO2
						.setUsuModifica((usuariosTmp.getUsuModifica() != null) ? usuariosTmp
								.getUsuModifica() : null);
				usuariosDTO2.setCodigoRol_Roles((usuariosTmp.getRoles()
						.getCodigoRol() != null) ? usuariosTmp.getRoles()
						.getCodigoRol() : null);
				usuariosDTO.add(usuariosDTO2);
			}

			return usuariosDTO;
		} catch (Exception e) {
			throw e;
		}
	}

	@Transactional(readOnly = true)
	public Usuarios getUsuarios(Long codigoUsua) throws Exception {
		Usuarios entity = null;

		try {
			entity = usuariosDAO.findById(codigoUsua);
		} catch (Exception e) {
			throw new ZMessManager().new FindingException("Usuarios");
		} finally {
		}

		return entity;
	}

	@Transactional(readOnly = true)
	public List<Usuarios> findPageUsuarios(String sortColumnName,
			boolean sortAscending, int startRow, int maxResults)
			throws Exception {
		List<Usuarios> entity = null;

		try {
			entity = usuariosDAO.findPage(sortColumnName, sortAscending,
					startRow, maxResults);
		} catch (Exception e) {
			throw new ZMessManager().new FindingException("Usuarios Count");
		} finally {
		}

		return entity;
	}

	@Transactional(readOnly = true)
	public Long findTotalNumberUsuarios() throws Exception {
		Long entity = null;

		try {
			entity = usuariosDAO.count();
		} catch (Exception e) {
			throw new ZMessManager().new FindingException("Usuarios Count");
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
	public List<Usuarios> findByCriteria(Object[] variables,
			Object[] variablesBetween, Object[] variablesBetweenDates)
			throws Exception {
		List<Usuarios> list = new ArrayList<Usuarios>();
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
			list = usuariosDAO.findByCriteria(where);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		} finally {
		}

		return list;
	}

	@Override
	public boolean validateEmailAddress(String sEmail) throws Exception {
		EmailValidator emailValidator = EmailValidator.getInstance();
		return emailValidator.isValid(sEmail);

	}

	@Override
	@Transactional(readOnly = true)
	public String iniciarSesionLector(String email, String password)
			throws Exception {
		
		if (email.trim().equals("") == true
				|| password.trim().equals("") == true) {
			throw new Exception("Debe llenar todos los campos");
		}		
		
		boolean mailCorrecto = validateEmailAddress(email);
		if (mailCorrecto == false) {
			throw new Exception("Dirección de Email es incorrecta");
		}
		Usuarios usuarios = usuariosDAO.obtenerPorMail(email);
		if (usuarios == null) {
			throw new Exception("Email o contraseña incorrectos");
		}
		if (usuarios.getClave().equals(password) == false) {
			throw new Exception("Email o contraseña incorrectos");
		}
		if (usuarios.getRoles().getCodigoRol() != 2) {
			throw new Exception("El usuario no es lector");
		}
		
		return "exito";
	}

	@Override
	@Transactional(readOnly = true)
	public String iniciarSesionAdministrador(String email, String password)
			throws Exception {
		
		if (email.trim().equals("") == true
				|| password.trim().equals("") == true) {
			throw new Exception("Debe llenar todos los campos");
		}
		
		boolean mailCorrecto = validateEmailAddress(email);
		if (mailCorrecto == false) {
			throw new Exception("Dirección de Email es incorrecta");
		}
		Usuarios usuarios = usuariosDAO.obtenerPorMail(email);
		if (usuarios == null) {
			throw new Exception("Email o contraseña incorrectos");
		}
		if (usuarios.getClave().equals(password) == false) {
			throw new Exception("Email o contraseña incorrectos");
		}
		if (usuarios.getRoles().getCodigoRol() != 1) {
			throw new Exception("El usuario no es Administrador");
		}
		
		return "exito";
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void registrarUsuarioLector(Usuarios usuarios) throws Exception {
		if (usuarios == null) {
			throw new Exception("Error no ha llegado el Usuario para registrar");
		}
		if (usuarios.getEmail().trim().equals("") == true) {
			throw new Exception("Debe ingresar el Mail para registrar");
		}
		if (usuarios.getClave().trim().equals("") == true) {
			throw new Exception("Debe ingresar la contraseña");
		}
		if (usuarios.getNombre().trim().equals("") == true) {
			throw new Exception("Debe ingresar su nombre");
		}
		boolean mailCorrecto = validateEmailAddress(usuarios.getEmail());
		if (mailCorrecto == false) {
			throw new Exception("Dirección de Email es incorrecta");
		}
		Usuarios entidad = usuariosDAO.obtenerPorMail(usuarios.getEmail());
		if (entidad != null) {
			throw new Exception(
					"La dirección de correo ya está asociada a otra cuenta");
		}

		Roles roles = rolesDAO.findById(2L);
		long codigoUsuario = getConsecutivo("usuarios_codigo_usua_seq");
		usuarios.setCodigoUsua(codigoUsuario);
		usuarios.setRoles(roles);
		usuarios.setFechaCreacion(new Date());
		usuarios.setUsuCrea(usuarios.getEmail());
		usuarios.setEstadoRegistro("A");

		usuariosDAO.registrarUsuarioLector(usuarios);
		enviarMensajeRegistro(usuarios);

	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Long getConsecutivo(String sqlName) throws Exception {
		if (sqlName.trim().equals("")) {
			throw new Exception("El nombre del Sql no debe estar vacío");
		}
		return usuariosDAO.getConsecutivo(sqlName);
	}

	@Override
	public void enviarMensajeRegistro(Usuarios usuarios) throws Exception {
		String mensajeAEnviar = "Hola "
				+ usuarios.getNombre()
				+ "\n"
				+ "Bienvenido a Look Docs, tus datos para iniciar sesión son: \n"
				+ "Nombre de Usuario: " + usuarios.getEmail() + " \n"
				+ "Contraseña: " + usuarios.getClave() + " \n"
				+ "Ya puedes disfrutar de los servicios de LookDocs.";

		SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
		msg.setTo(usuarios.getEmail());
		msg.setSubject("Bienvenido a LookDocs");
		msg.setText(mensajeAEnviar);

		try {
			this.mailSender.send(msg);
		} catch (MailException ex) {
			System.err.println(ex.getMessage());
		}
	}

	@Override
	public Usuarios obtenerPorMail(String email) throws Exception {
		// TODO Auto-generated method stub
		return usuariosDAO.obtenerPorMail(email);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void modificarPasswordUsuarios(Usuarios usuarios,
			String claveActual, String nuevaClave, String confirmaClave)
			throws Exception {
		if(usuarios==null){
			throw new Exception("No ha iniciado Sesión");
		}
		if(usuarios.getClave().equals(claveActual)==false){
			throw new Exception("Contraseña actual errónea");
		}
		if(nuevaClave.equals(confirmaClave)==false){
			throw new Exception("Las contraseñas no coinciden");
		}
		Usuarios entidad = usuariosDAO.consultarPorId(usuarios.getCodigoUsua());
		if(entidad == null){
			throw new Exception("El usuario no existe");
		}
		entidad.setClave(nuevaClave);
		entidad.setFechaModifcacion(new Date());
		entidad.setUsuModifica(usuarios.getEmail());
		usuariosDAO.update(entidad);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void modificarNombreDeUsuario(Usuarios usuarios, String nombre) throws Exception{
		if(usuarios==null){
			throw new Exception("No ha iniciado Sesión");
		}
		if(usuarios.getNombre().equals(nombre)==true){
			throw new Exception("No ha puesto un nuevo nombre");
		}
		Usuarios entidad = usuariosDAO.consultarPorId(usuarios.getCodigoUsua());
		if(entidad == null){
			throw new Exception("El usuario no existe");
		}
		entidad.setNombre(nombre);
		entidad.setFechaModifcacion(new Date());
		entidad.setUsuModifica(usuarios.getEmail());
		usuariosDAO.update(entidad);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void recuperarClave(String email) throws Exception {
		boolean mailCorrecto = validateEmailAddress(email);
		if (mailCorrecto == false) {
			throw new Exception("Dirección de Email es incorrecta");
		}
		Usuarios usuarios = usuariosDAO.obtenerPorMail(email);
		if(usuarios==null){
			throw new Exception("El usuario no está registrado en la base de datos");
		}
		String nuevaClave = "lOoKdoCs"+usuarios.getNombre().trim()+""+usuarios.getCodigoUsua();
		usuarios.setClave(nuevaClave);
		usuarios.setUsuModifica("LookDocs");
		usuarios.setFechaModifcacion(new Date());
		usuariosDAO.update(usuarios);
		enviarMensajeCambioClave(usuarios);
	}

	@Override
	public void enviarMensajeCambioClave(Usuarios usuarios) throws Exception {
		String mensajeAEnviar = "Hola "
				+ usuarios.getNombre()
				+ "\n"
				+ "Has solicitado recuperar tu contraseña, tu nueva contraseña es: \n"
				+ "Contraseña: " + usuarios.getClave() + " \n"
				+ "Recuerda que puedes modificarla en el Feed de tu cuenta en LookDocs";

		SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
		msg.setTo(usuarios.getEmail());
		msg.setSubject("Recuperar Contraseña");
		msg.setText(mensajeAEnviar);

		try {
			this.mailSender.send(msg);
		} catch (MailException ex) {
			System.err.println(ex.getMessage());
		}
	}

}
