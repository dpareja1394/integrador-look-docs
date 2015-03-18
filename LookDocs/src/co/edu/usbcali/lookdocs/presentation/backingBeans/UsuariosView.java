package co.edu.usbcali.lookdocs.presentation.backingBeans;

import co.edu.usbcali.lookdocs.exceptions.*;
import co.edu.usbcali.lookdocs.model.*;
import co.edu.usbcali.lookdocs.model.dto.UsuariosDTO;
import co.edu.usbcali.lookdocs.presentation.businessDelegate.*;
import co.edu.usbcali.lookdocs.utilities.*;

import org.primefaces.component.calendar.*;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.password.Password;
import org.primefaces.event.RowEditEvent;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.Serializable;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 * @author Zathura Code Generator http://code.google.com/p/zathura
 *         www.zathuracode.org
 *
 */
@ManagedBean
@ViewScoped
public class UsuariosView implements Serializable {
	private static final long serialVersionUID = 1L;
	private Password pswClave;
	private Password pswClaveAdmin;
	private Password pswClaveConfirmacion;
	private InputText txtEmail;
	private InputText txtEmailAdmin;
	private InputText txtEstadoRegistro;
	private InputText txtNombre;
	private InputText txtUsuCrea;
	private InputText txtUsuModifica;
	private InputText txtCodigoRol_Roles;
	private InputText txtCodigoUsua;
	private InputText txtEmailClaveAdmin;
	private InputText txtEmailClaveLector;
	private Calendar txtFechaCreacion;
	private Calendar txtFechaModifcacion;
	private CommandButton btnSave;
	private CommandButton btnModify;
	private CommandButton btnDelete;
	private CommandButton btnClear;
	private CommandButton btnLoginLector;
	private CommandButton btnLoginAdministrador;
	private CommandButton btnCambiarClaveAdministrador;
	private CommandButton btnCambiarNombreUsuarioAdministrador;
	private CommandButton btnCambiarClaveLector;
	private CommandButton btnCambiarNombreUsuarioLector;
	private CommandButton btnRecuperarClaveAdmin;
	private CommandButton btnRecuperarClaveLector;
	private List<UsuariosDTO> data;
	private UsuariosDTO selectedUsuarios;
	private Usuarios entity;
	private boolean showDialog;

	@ManagedProperty(value = "#{BusinessDelegatorView}")
	private IBusinessDelegatorView businessDelegatorView;

	public UsuariosView() {
		super();
	}

	public void rowEventListener(RowEditEvent e) {
		try {
			UsuariosDTO usuariosDTO = (UsuariosDTO) e.getObject();

			if (pswClave == null) {
				pswClave = new Password();
			}

			pswClave.setValue(usuariosDTO.getClave());

			if (txtEmail == null) {
				txtEmail = new InputText();
			}

			txtEmail.setValue(usuariosDTO.getEmail());

			if (txtEstadoRegistro == null) {
				txtEstadoRegistro = new InputText();
			}

			txtEstadoRegistro.setValue(usuariosDTO.getEstadoRegistro());

			if (txtNombre == null) {
				txtNombre = new InputText();
			}

			txtNombre.setValue(usuariosDTO.getNombre());

			if (txtUsuCrea == null) {
				txtUsuCrea = new InputText();
			}

			txtUsuCrea.setValue(usuariosDTO.getUsuCrea());

			if (txtUsuModifica == null) {
				txtUsuModifica = new InputText();
			}

			txtUsuModifica.setValue(usuariosDTO.getUsuModifica());

			if (txtCodigoRol_Roles == null) {
				txtCodigoRol_Roles = new InputText();
			}

			txtCodigoRol_Roles.setValue(usuariosDTO.getCodigoRol_Roles());

			if (txtCodigoUsua == null) {
				txtCodigoUsua = new InputText();
			}

			txtCodigoUsua.setValue(usuariosDTO.getCodigoUsua());

			if (txtFechaCreacion == null) {
				txtFechaCreacion = new Calendar();
			}

			txtFechaCreacion.setValue(usuariosDTO.getFechaCreacion());

			if (txtFechaModifcacion == null) {
				txtFechaModifcacion = new Calendar();
			}

			txtFechaModifcacion.setValue(usuariosDTO.getFechaModifcacion());

			Long codigoUsua = FacesUtils.checkLong(txtCodigoUsua);
			entity = businessDelegatorView.getUsuarios(codigoUsua);

			action_modify();
		} catch (Exception ex) {
		}
	}

	public String action_new() {
		action_clear();
		selectedUsuarios = null;
		setShowDialog(true);

		return "";
	}

	public String action_clear() {
		entity = null;
		selectedUsuarios = null;

		if (pswClave != null) {
			pswClave.setValue(null);
			pswClave.setDisabled(true);
		}

		if (txtEmail != null) {
			txtEmail.setValue(null);
			txtEmail.setDisabled(true);
		}

		if (txtEstadoRegistro != null) {
			txtEstadoRegistro.setValue(null);
			txtEstadoRegistro.setDisabled(true);
		}

		if (txtNombre != null) {
			txtNombre.setValue(null);
			txtNombre.setDisabled(true);
		}

		if (txtUsuCrea != null) {
			txtUsuCrea.setValue(null);
			txtUsuCrea.setDisabled(true);
		}

		if (txtUsuModifica != null) {
			txtUsuModifica.setValue(null);
			txtUsuModifica.setDisabled(true);
		}

		if (txtCodigoRol_Roles != null) {
			txtCodigoRol_Roles.setValue(null);
			txtCodigoRol_Roles.setDisabled(true);
		}

		if (txtFechaCreacion != null) {
			txtFechaCreacion.setValue(null);
			txtFechaCreacion.setDisabled(true);
		}

		if (txtFechaModifcacion != null) {
			txtFechaModifcacion.setValue(null);
			txtFechaModifcacion.setDisabled(true);
		}

		if (txtCodigoUsua != null) {
			txtCodigoUsua.setValue(null);
			txtCodigoUsua.setDisabled(false);
		}

		if (btnSave != null) {
			btnSave.setDisabled(true);
		}

		if (btnDelete != null) {
			btnDelete.setDisabled(true);
		}

		return "";
	}

	public void listener_txtFechaCreacion() {
		Date inputDate = (Date) txtFechaCreacion.getValue();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		FacesContext.getCurrentInstance().addMessage(
				"",
				new FacesMessage("Selected Date "
						+ dateFormat.format(inputDate)));
	}

	public void listener_txtFechaModifcacion() {
		Date inputDate = (Date) txtFechaModifcacion.getValue();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		FacesContext.getCurrentInstance().addMessage(
				"",
				new FacesMessage("Selected Date "
						+ dateFormat.format(inputDate)));
	}

	public void listener_txtId() {
		try {
			Long codigoUsua = FacesUtils.checkLong(txtCodigoUsua);
			entity = (codigoUsua != null) ? businessDelegatorView
					.getUsuarios(codigoUsua) : null;
		} catch (Exception e) {
			entity = null;
		}

		if (entity == null) {
			pswClave.setDisabled(false);
			txtEmail.setDisabled(false);
			txtEstadoRegistro.setDisabled(false);
			txtNombre.setDisabled(false);
			txtUsuCrea.setDisabled(false);
			txtUsuModifica.setDisabled(false);
			txtCodigoRol_Roles.setDisabled(false);
			txtFechaCreacion.setDisabled(false);
			txtFechaModifcacion.setDisabled(false);
			txtCodigoUsua.setDisabled(false);
			btnSave.setDisabled(false);
		} else {
			pswClave.setValue(entity.getClave());
			pswClave.setDisabled(false);
			txtEmail.setValue(entity.getEmail());
			txtEmail.setDisabled(false);
			txtEstadoRegistro.setValue(entity.getEstadoRegistro());
			txtEstadoRegistro.setDisabled(false);
			txtFechaCreacion.setValue(entity.getFechaCreacion());
			txtFechaCreacion.setDisabled(false);
			txtFechaModifcacion.setValue(entity.getFechaModifcacion());
			txtFechaModifcacion.setDisabled(false);
			txtNombre.setValue(entity.getNombre());
			txtNombre.setDisabled(false);
			txtUsuCrea.setValue(entity.getUsuCrea());
			txtUsuCrea.setDisabled(false);
			txtUsuModifica.setValue(entity.getUsuModifica());
			txtUsuModifica.setDisabled(false);
			txtCodigoRol_Roles.setValue(entity.getRoles().getCodigoRol());
			txtCodigoRol_Roles.setDisabled(false);
			txtCodigoUsua.setValue(entity.getCodigoUsua());
			txtCodigoUsua.setDisabled(true);
			btnSave.setDisabled(false);

			if (btnDelete != null) {
				btnDelete.setDisabled(false);
			}
		}
	}

	public String action_edit(ActionEvent evt) {
		selectedUsuarios = (UsuariosDTO) (evt.getComponent().getAttributes()
				.get("selectedUsuarios"));
		pswClave.setValue(selectedUsuarios.getClave());
		pswClave.setDisabled(false);
		txtEmail.setValue(selectedUsuarios.getEmail());
		txtEmail.setDisabled(false);
		txtEstadoRegistro.setValue(selectedUsuarios.getEstadoRegistro());
		txtEstadoRegistro.setDisabled(false);
		txtFechaCreacion.setValue(selectedUsuarios.getFechaCreacion());
		txtFechaCreacion.setDisabled(false);
		txtFechaModifcacion.setValue(selectedUsuarios.getFechaModifcacion());
		txtFechaModifcacion.setDisabled(false);
		txtNombre.setValue(selectedUsuarios.getNombre());
		txtNombre.setDisabled(false);
		txtUsuCrea.setValue(selectedUsuarios.getUsuCrea());
		txtUsuCrea.setDisabled(false);
		txtUsuModifica.setValue(selectedUsuarios.getUsuModifica());
		txtUsuModifica.setDisabled(false);
		txtCodigoRol_Roles.setValue(selectedUsuarios.getCodigoRol_Roles());
		txtCodigoRol_Roles.setDisabled(false);
		txtCodigoUsua.setValue(selectedUsuarios.getCodigoUsua());
		txtCodigoUsua.setDisabled(true);
		btnSave.setDisabled(false);
		setShowDialog(true);

		return "";
	}

	public String action_save() {
		try {
			if ((selectedUsuarios == null) && (entity == null)) {
				action_create();
			} else {
				action_modify();
			}

			data = null;
		} catch (Exception e) {
			FacesUtils.addErrorMessage(e.getMessage());
		}

		return "";
	}

	public String action_create() {
		try {
			entity = new Usuarios();

			Long codigoUsua = FacesUtils.checkLong(txtCodigoUsua);

			entity.setClave(FacesUtils.checkString(pswClave));
			entity.setCodigoUsua(codigoUsua);
			entity.setEmail(FacesUtils.checkString(txtEmail));
			entity.setEstadoRegistro(FacesUtils.checkString(txtEstadoRegistro));
			entity.setFechaCreacion(FacesUtils.checkDate(txtFechaCreacion));
			entity.setFechaModifcacion(FacesUtils
					.checkDate(txtFechaModifcacion));
			entity.setNombre(FacesUtils.checkString(txtNombre));
			entity.setUsuCrea(FacesUtils.checkString(txtUsuCrea));
			entity.setUsuModifica(FacesUtils.checkString(txtUsuModifica));
			entity.setRoles((FacesUtils.checkLong(txtCodigoRol_Roles) != null) ? businessDelegatorView
					.getRoles(FacesUtils.checkLong(txtCodigoRol_Roles)) : null);
			businessDelegatorView.saveUsuarios(entity);
			FacesUtils.addInfoMessage(ZMessManager.ENTITY_SUCCESFULLYSAVED);
			action_clear();
		} catch (Exception e) {
			entity = null;
			FacesUtils.addErrorMessage(e.getMessage());
		}

		return "";
	}

	public String action_modify() {
		try {
			if (entity == null) {
				Long codigoUsua = new Long(selectedUsuarios.getCodigoUsua());
				entity = businessDelegatorView.getUsuarios(codigoUsua);
			}

			entity.setClave(FacesUtils.checkString(pswClave));
			entity.setEmail(FacesUtils.checkString(txtEmail));
			entity.setEstadoRegistro(FacesUtils.checkString(txtEstadoRegistro));
			entity.setFechaCreacion(FacesUtils.checkDate(txtFechaCreacion));
			entity.setFechaModifcacion(FacesUtils
					.checkDate(txtFechaModifcacion));
			entity.setNombre(FacesUtils.checkString(txtNombre));
			entity.setUsuCrea(FacesUtils.checkString(txtUsuCrea));
			entity.setUsuModifica(FacesUtils.checkString(txtUsuModifica));
			entity.setRoles((FacesUtils.checkLong(txtCodigoRol_Roles) != null) ? businessDelegatorView
					.getRoles(FacesUtils.checkLong(txtCodigoRol_Roles)) : null);
			businessDelegatorView.updateUsuarios(entity);
			FacesUtils.addInfoMessage(ZMessManager.ENTITY_SUCCESFULLYMODIFIED);
		} catch (Exception e) {
			data = null;
			FacesUtils.addErrorMessage(e.getMessage());
		}

		return "";
	}

	public String action_delete_datatable(ActionEvent evt) {
		try {
			selectedUsuarios = (UsuariosDTO) (evt.getComponent()
					.getAttributes().get("selectedUsuarios"));

			Long codigoUsua = new Long(selectedUsuarios.getCodigoUsua());
			entity = businessDelegatorView.getUsuarios(codigoUsua);
			action_delete();
		} catch (Exception e) {
			FacesUtils.addErrorMessage(e.getMessage());
		}

		return "";
	}

	public String action_delete_master() {
		try {
			Long codigoUsua = FacesUtils.checkLong(txtCodigoUsua);
			entity = businessDelegatorView.getUsuarios(codigoUsua);
			action_delete();
		} catch (Exception e) {
			FacesUtils.addErrorMessage(e.getMessage());
		}

		return "";
	}

	public void action_delete() throws Exception {
		try {
			businessDelegatorView.deleteUsuarios(entity);
			FacesUtils.addInfoMessage(ZMessManager.ENTITY_SUCCESFULLYDELETED);
			action_clear();
			data = null;
		} catch (Exception e) {
			throw e;
		}
	}

	public String action_closeDialog() {
		setShowDialog(false);
		action_clear();

		return "";
	}

	public String actionDeleteDataTableEditable(ActionEvent evt) {
		try {
			selectedUsuarios = (UsuariosDTO) (evt.getComponent()
					.getAttributes().get("selectedUsuarios"));

			Long codigoUsua = new Long(selectedUsuarios.getCodigoUsua());
			entity = businessDelegatorView.getUsuarios(codigoUsua);
			businessDelegatorView.deleteUsuarios(entity);
			data.remove(selectedUsuarios);
			FacesUtils.addInfoMessage(ZMessManager.ENTITY_SUCCESFULLYDELETED);
			action_clear();
		} catch (Exception e) {
			FacesUtils.addErrorMessage(e.getMessage());
		}

		return "";
	}

	public String action_modifyWitDTO(String clave, Long codigoUsua,
			String email, String estadoRegistro, Date fechaCreacion,
			Date fechaModifcacion, String nombre, String usuCrea,
			String usuModifica, Long codigoRol_Roles) throws Exception {
		try {
			entity.setClave(FacesUtils.checkString(clave));
			entity.setEmail(FacesUtils.checkString(email));
			entity.setEstadoRegistro(FacesUtils.checkString(estadoRegistro));
			entity.setFechaCreacion(FacesUtils.checkDate(fechaCreacion));
			entity.setFechaModifcacion(FacesUtils.checkDate(fechaModifcacion));
			entity.setNombre(FacesUtils.checkString(nombre));
			entity.setUsuCrea(FacesUtils.checkString(usuCrea));
			entity.setUsuModifica(FacesUtils.checkString(usuModifica));
			businessDelegatorView.updateUsuarios(entity);
			FacesUtils.addInfoMessage(ZMessManager.ENTITY_SUCCESFULLYMODIFIED);
		} catch (Exception e) {
			// renderManager.getOnDemandRenderer("UsuariosView").requestRender();
			FacesUtils.addErrorMessage(e.getMessage());
			throw e;
		}

		return "";
	}

	public InputText getTxtEmail() {
		return txtEmail;
	}

	public void setTxtEmail(InputText txtEmail) {
		this.txtEmail = txtEmail;
	}

	public InputText getTxtEstadoRegistro() {
		return txtEstadoRegistro;
	}

	public void setTxtEstadoRegistro(InputText txtEstadoRegistro) {
		this.txtEstadoRegistro = txtEstadoRegistro;
	}

	public InputText getTxtNombre() {
		return txtNombre;
	}

	public void setTxtNombre(InputText txtNombre) {
		this.txtNombre = txtNombre;
	}

	public InputText getTxtUsuCrea() {
		return txtUsuCrea;
	}

	public void setTxtUsuCrea(InputText txtUsuCrea) {
		this.txtUsuCrea = txtUsuCrea;
	}

	public InputText getTxtUsuModifica() {
		return txtUsuModifica;
	}

	public void setTxtUsuModifica(InputText txtUsuModifica) {
		this.txtUsuModifica = txtUsuModifica;
	}

	public InputText getTxtCodigoRol_Roles() {
		return txtCodigoRol_Roles;
	}

	public void setTxtCodigoRol_Roles(InputText txtCodigoRol_Roles) {
		this.txtCodigoRol_Roles = txtCodigoRol_Roles;
	}

	public Calendar getTxtFechaCreacion() {
		return txtFechaCreacion;
	}

	public void setTxtFechaCreacion(Calendar txtFechaCreacion) {
		this.txtFechaCreacion = txtFechaCreacion;
	}

	public Calendar getTxtFechaModifcacion() {
		return txtFechaModifcacion;
	}

	public void setTxtFechaModifcacion(Calendar txtFechaModifcacion) {
		this.txtFechaModifcacion = txtFechaModifcacion;
	}

	public InputText getTxtCodigoUsua() {
		return txtCodigoUsua;
	}

	public void setTxtCodigoUsua(InputText txtCodigoUsua) {
		this.txtCodigoUsua = txtCodigoUsua;
	}

	public List<UsuariosDTO> getData() {
		try {
			if (data == null) {
				data = businessDelegatorView.getDataUsuarios();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return data;
	}

	public void setData(List<UsuariosDTO> usuariosDTO) {
		this.data = usuariosDTO;
	}

	public UsuariosDTO getSelectedUsuarios() {
		return selectedUsuarios;
	}

	public void setSelectedUsuarios(UsuariosDTO usuarios) {
		this.selectedUsuarios = usuarios;
	}

	public CommandButton getBtnSave() {
		return btnSave;
	}

	public void setBtnSave(CommandButton btnSave) {
		this.btnSave = btnSave;
	}

	public CommandButton getBtnModify() {
		return btnModify;
	}

	public void setBtnModify(CommandButton btnModify) {
		this.btnModify = btnModify;
	}

	public CommandButton getBtnDelete() {
		return btnDelete;
	}

	public void setBtnDelete(CommandButton btnDelete) {
		this.btnDelete = btnDelete;
	}

	public CommandButton getBtnClear() {
		return btnClear;
	}

	public void setBtnClear(CommandButton btnClear) {
		this.btnClear = btnClear;
	}

	public TimeZone getTimeZone() {
		return java.util.TimeZone.getDefault();
	}

	public IBusinessDelegatorView getBusinessDelegatorView() {
		return businessDelegatorView;
	}

	public void setBusinessDelegatorView(
			IBusinessDelegatorView businessDelegatorView) {
		this.businessDelegatorView = businessDelegatorView;
	}

	public boolean isShowDialog() {
		return showDialog;
	}

	public void setShowDialog(boolean showDialog) {
		this.showDialog = showDialog;
	}

	public Password getPswClave() {
		return pswClave;
	}

	public void setPswClave(Password pswClave) {
		this.pswClave = pswClave;
	}

	public Password getPswClaveConfirmacion() {
		return pswClaveConfirmacion;
	}

	public void setPswClaveConfirmacion(Password pswClaveConfirmacion) {
		this.pswClaveConfirmacion = pswClaveConfirmacion;
	}

	public CommandButton getbtnLoginLector() {
		return btnLoginLector;
	}

	public void setbtnLoginLector(CommandButton btnLoginLector) {
		this.btnLoginLector = btnLoginLector;
	}

	public CommandButton getBtnLoginAdministrador() {
		return btnLoginAdministrador;
	}

	public void setBtnLoginAdministrador(CommandButton btnLoginAdministrador) {
		this.btnLoginAdministrador = btnLoginAdministrador;
	}

	public Password getPswClaveAdmin() {
		return pswClaveAdmin;
	}

	public void setPswClaveAdmin(Password pswClaveAdmin) {
		this.pswClaveAdmin = pswClaveAdmin;
	}

	public InputText getTxtEmailAdmin() {
		return txtEmailAdmin;
	}

	public void setTxtEmailAdmin(InputText txtEmailAdmin) {
		this.txtEmailAdmin = txtEmailAdmin;
	}

	public CommandButton getBtnCambiarClaveAdministrador() {
		return btnCambiarClaveAdministrador;
	}

	public void setBtnCambiarClaveAdministrador(
			CommandButton btnCambiarClaveAdministrador) {
		this.btnCambiarClaveAdministrador = btnCambiarClaveAdministrador;
	}

	public CommandButton getBtnCambiarNombreUsuarioAdministrador() {
		return btnCambiarNombreUsuarioAdministrador;
	}

	public void setBtnCambiarNombreUsuarioAdministrador(
			CommandButton btnCambiarNombreUsuarioAdministrador) {
		this.btnCambiarNombreUsuarioAdministrador = btnCambiarNombreUsuarioAdministrador;
	}

	public CommandButton getBtnCambiarNombreUsuarioLector() {
		return btnCambiarNombreUsuarioLector;
	}

	public void setBtnCambiarNombreUsuarioLector(
			CommandButton btnCambiarNombreUsuarioLector) {
		this.btnCambiarNombreUsuarioLector = btnCambiarNombreUsuarioLector;
	}

	public CommandButton getBtnCambiarClaveLector() {
		return btnCambiarClaveLector;
	}

	public void setBtnCambiarClaveLector(CommandButton btnCambiarClaveLector) {
		this.btnCambiarClaveLector = btnCambiarClaveLector;
	}

	public InputText getTxtEmailClaveAdmin() {
		return txtEmailClaveAdmin;
	}

	public void setTxtEmailClaveAdmin(InputText txtEmailClaveAdmin) {
		this.txtEmailClaveAdmin = txtEmailClaveAdmin;
	}

	public InputText getTxtEmailClaveLector() {
		return txtEmailClaveLector;
	}

	public void setTxtEmailClaveLector(InputText txtEmailClaveLector) {
		this.txtEmailClaveLector = txtEmailClaveLector;
	}

	public CommandButton getBtnRecuperarClaveAdmin() {
		return btnRecuperarClaveAdmin;
	}

	public void setBtnRecuperarClaveAdmin(CommandButton btnRecuperarClaveAdmin) {
		this.btnRecuperarClaveAdmin = btnRecuperarClaveAdmin;
	}

	public CommandButton getBtnRecuperarClaveLector() {
		return btnRecuperarClaveLector;
	}

	public void setBtnRecuperarClaveLector(CommandButton btnRecuperarClaveLector) {
		this.btnRecuperarClaveLector = btnRecuperarClaveLector;
	}

	public void clearAction() {
		txtEmail.setValue("");
		txtNombre.setValue("");
		pswClave.setValue("");
		pswClaveConfirmacion.setValue("");
		pswClaveAdmin.setValue("");
	}

	public String iniciarLectorAction() {
		try {
			if (txtEmail.getValue().toString().trim().equals("") == true
					|| pswClave.getValue().toString().trim().equals("") == true) {
				FacesContext.getCurrentInstance().addMessage("",
						new FacesMessage("Debe llenar todos los campos"));
			} else {
				String email = txtEmail.getValue().toString();
				String password = pswClave.getValue().toString();
				businessDelegatorView.iniciarSesionLector(email, password);
				Usuarios usuarios = businessDelegatorView.obtenerPorMail(email);
				HttpSession httpSession = (HttpSession) FacesContext
						.getCurrentInstance().getExternalContext()
						.getSession(true);
				httpSession.setAttribute("usuarioLector", usuarios);
				txtEmail.setValue("");
				pswClave.setValue("");
				return "iniciarLector";
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					"",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), e.getMessage()));
		}
		return "";
	}

	public String iniciarAdministradorAction() {
		try {
			if (txtEmailAdmin.getValue().toString().trim().equals("") == true
					|| pswClaveAdmin.getValue().toString().trim().equals("") == true) {
				FacesContext.getCurrentInstance().addMessage("",
						new FacesMessage("Debe llenar todos los campos"));
			} else {
				String email = txtEmailAdmin.getValue().toString();
				String password = pswClaveAdmin.getValue().toString();
				businessDelegatorView.iniciarSesionAdministrador(email,
						password);
				Usuarios usuarios = businessDelegatorView.obtenerPorMail(email);
				HttpSession httpSession = (HttpSession) FacesContext
						.getCurrentInstance().getExternalContext()
						.getSession(true);
				httpSession.setAttribute("usuarioAdministrador", usuarios);
				txtEmailAdmin.setValue("");
				pswClaveAdmin.setValue("");
				return "iniciarAdministrador";
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					"",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), e.getMessage()));
		}
		return "";
	}

	public String registrarLectorAction() {
		try {

			String clave = pswClave.getValue().toString();
			String confirmaClave = pswClaveConfirmacion.getValue().toString();
			if (clave.equals(confirmaClave)) {
				Usuarios usuarios = new Usuarios();
				usuarios.setClave(confirmaClave);
				usuarios.setEmail(txtEmail.getValue().toString());
				usuarios.setNombre(txtNombre.getValue().toString());
				businessDelegatorView.registrarUsuarioLector(usuarios);
				FacesContext.getCurrentInstance().addMessage("",
						new FacesMessage("Usuario Creado Satisfactoriamente"));
				txtEmail.setValue("");
				txtNombre.setValue("");
				pswClave.setValue("");
				pswClaveConfirmacion.setValue("");
				return "registroLector";
			} else {
				FacesContext.getCurrentInstance().addMessage("",
						new FacesMessage("Las contraseñas no coinciden"));
				
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					"",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), e.getMessage()));
		}
		return "";
	}

	public void pswClaveConfirmacionListener() {
		try {
			FacesContext.getCurrentInstance().addMessage("",
					new FacesMessage(pswClave.getValue().toString()));
			// String clave = pswClave.getValue().toString();
			// String claveConfirma =
			// pswClaveConfirmacion.getValue().toString();
			/*
			 * if(clave.equals(claveConfirma)==false){
			 * FacesContext.getCurrentInstance().addMessage("", new
			 * FacesMessage("Las contraseñas no coinciden")); }
			 */
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					"",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), e.getMessage()));
		}
	}

	public String cerrarSesionAdministrador() {
		HttpSession httpSession = (HttpSession) FacesContext
				.getCurrentInstance().getExternalContext().getSession(true);
		httpSession.removeAttribute("usuarioAdministrador");
		httpSession.invalidate();
		httpSession=null;
//		ServletRequestAttributes servletRequestAttributes=(ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
//    	HttpSession session = servletRequestAttributes.getRequest().getSession();
//    	session.invalidate();
//    	session.removeAttribute("usuarioAdministrador");

		return "cerrarAdministrador";

	}

	public String cerrarSesionLector() {
//		HttpSession httpSession = (HttpSession) FacesContext
//				.getCurrentInstance().getExternalContext().getSession(true);
//		httpSession.removeAttribute("usuarioLector");
//		httpSession.invalidate();
		ServletRequestAttributes servletRequestAttributes=(ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    	HttpSession session = servletRequestAttributes.getRequest().getSession();
    	session.removeAttribute("usuarioLector");
    	session.invalidate();
    	session=null;
    	
		return "cerrarLector";

	}

	public void cambiarClaveAdministrador() {
		try {
			HttpSession httpSession = (HttpSession) FacesContext
					.getCurrentInstance().getExternalContext().getSession(true);
			Usuarios usuarios = (Usuarios) httpSession
					.getAttribute("usuarioAdministrador");

			if (pswClave.getValue().toString().trim().equals("") == true
					|| pswClaveAdmin.getValue().toString().trim().equals("") == true
					|| pswClaveConfirmacion.getValue().toString().trim()
							.equals("") == true) {
				FacesContext.getCurrentInstance().addMessage("",
						new FacesMessage("Debe llenar todos los campos"));
			} else {
				String claveActual = pswClaveAdmin.getValue().toString();
				String nuevaClave = pswClave.getValue().toString();
				String confirmaClave = pswClaveConfirmacion.getValue()
						.toString();
				businessDelegatorView.modificarPasswordUsuarios(usuarios,
						claveActual, nuevaClave, confirmaClave);
				FacesContext.getCurrentInstance().addMessage("",
						new FacesMessage("Su contraseña ha sido modificada"));
			}

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					"",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), e.getMessage()));
		}

	}

	public void cambiarClaveLector() {
		try {
			HttpSession httpSession = (HttpSession) FacesContext
					.getCurrentInstance().getExternalContext().getSession(true);
			Usuarios usuarios = (Usuarios) httpSession
					.getAttribute("usuarioLector");

			if (pswClave.getValue().toString().trim().equals("") == true
					|| pswClaveAdmin.getValue().toString().trim().equals("") == true
					|| pswClaveConfirmacion.getValue().toString().trim()
							.equals("") == true) {
				FacesContext.getCurrentInstance().addMessage("",
						new FacesMessage("Debe llenar todos los campos"));
			} else {
				String claveActual = pswClaveAdmin.getValue().toString();
				String nuevaClave = pswClave.getValue().toString();
				String confirmaClave = pswClaveConfirmacion.getValue()
						.toString();
				businessDelegatorView.modificarPasswordUsuarios(usuarios,
						claveActual, nuevaClave, confirmaClave);
				FacesContext.getCurrentInstance().addMessage("",
						new FacesMessage("Su contraseña ha sido modificada"));
			}

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					"",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), e.getMessage()));
		}

	}

	public void cambiarNombreDeUsuarioAdministrador() {
		try {
			HttpSession httpSession = (HttpSession) FacesContext
					.getCurrentInstance().getExternalContext().getSession(true);
			Usuarios usuarios = (Usuarios) httpSession
					.getAttribute("usuarioAdministrador");

			if (txtNombre.getValue().toString().trim().equals("") == true) {
				FacesContext.getCurrentInstance().addMessage(
						"",
						new FacesMessage(
								"Debe poner un nuevo nombre de usuario"));
			} else {
				String nombreDeUsuarioNuevo = txtNombre.getValue().toString();
				businessDelegatorView.modificarNombreDeUsuario(usuarios,
						nombreDeUsuarioNuevo);
				FacesContext.getCurrentInstance().addMessage(
						"",
						new FacesMessage(
								"Se ha modificado el nombre de usuario"));
			}

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					"",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), e.getMessage()));
		}

	}

	public void cambiarNombreDeUsuarioLector() {
		try {
			HttpSession httpSession = (HttpSession) FacesContext
					.getCurrentInstance().getExternalContext().getSession(true);
			Usuarios usuarios = (Usuarios) httpSession
					.getAttribute("usuarioLector");

			if (txtNombre.getValue().toString().trim().equals("") == true) {
				FacesContext.getCurrentInstance().addMessage(
						"",
						new FacesMessage(
								"Debe poner un nuevo nombre de usuario"));
			} else {
				String nombreDeUsuarioNuevo = txtNombre.getValue().toString();
				businessDelegatorView.modificarNombreDeUsuario(usuarios,
						nombreDeUsuarioNuevo);
				FacesContext.getCurrentInstance().addMessage(
						"",
						new FacesMessage(
								"Se ha modificado el nombre de usuario"));
			}

		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					"",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), e.getMessage()));
		}

	}

	public void recuperarClaveAdminAction() {
		try {

			if (txtEmailClaveAdmin.getValue().toString().trim().equals("") == true) {
				FacesContext.getCurrentInstance().addMessage("",
						new FacesMessage("Debe ingresar un email"));
			} else {
				String email = txtEmailClaveAdmin.getValue().toString();
				businessDelegatorView.recuperarClave(email);
				FacesContext.getCurrentInstance().addMessage("",
						new FacesMessage("Se ha enviado el correo"));
				txtEmailClaveAdmin.setValue("");
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					"",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), e.getMessage()));
		}
	}

	public void recuperarClaveLectorAction() {
		try {

			if (txtEmailClaveLector.getValue().toString().trim().equals("") == true) {
				FacesContext.getCurrentInstance().addMessage("",
						new FacesMessage("Debe ingresar un email"));
			} else {
				String email = txtEmailClaveLector.getValue().toString();
				businessDelegatorView.recuperarClave(email);
				FacesContext.getCurrentInstance().addMessage("",
						new FacesMessage("Se ha enviado el correo"));
				txtEmailClaveLector.setValue("");
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					"",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), e.getMessage()));
		}
	}

}
