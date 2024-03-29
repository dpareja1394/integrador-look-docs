package co.edu.usbcali.lookdocs.presentation.backingBeans;

import co.edu.usbcali.lookdocs.exceptions.*;
import co.edu.usbcali.lookdocs.model.*;
import co.edu.usbcali.lookdocs.model.dto.ArticulosDTO;
import co.edu.usbcali.lookdocs.model.dto.RssDTO;
import co.edu.usbcali.lookdocs.presentation.businessDelegate.*;
import co.edu.usbcali.lookdocs.utilities.*;

import org.primefaces.component.calendar.*;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.fileupload.FileUpload;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.inputtextarea.InputTextarea;
import org.primefaces.component.media.player.PDFPlayer;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

/**
 * @author Zathura Code Generator http://code.google.com/p/zathura
 *         www.zathuracode.org
 *
 */
@ManagedBean
@ViewScoped
public class ArticulosView implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final long CATEGORIA_PREDETERMINADA = 1L;
	private static final String HOME = "http://integrador.comli.com/";
	private static final String FORMAT = ".pdf";
	private InputText txtAutor;
	private InputText txtDescripcion;
	private InputText txtEstadoRegistro;
	private InputText txtNombre;
	private InputText txtUsuCrea;
	private InputText txtUsuModifica;
	private InputText txtCodigoUsua_Usuarios;
	private InputText txtCodigoArti;
	private Calendar txtFechaCreacion;
	private Calendar txtFechaModifcacion;
	private InputTextarea txtAreaDes;
	private List<SelectItem> lasCategoriasItems;
	private List<SelectItem> lasCategoriasItemsTabla;
	// private String defaulturl =
	// "C:\\STSWorkSpace\\LookDocs\\WebContent\\pdf";
	private String defaulturl = "/public_html/";
	private String pdfUrl;
	private UploadedFile file;
	private List<Articulos> losArticulos;
	private List<Articulos> filtroArticulos;
	private String viewURL;
	private File fileUpload;
	private CommandButton btnSave;
	private CommandButton btnModify;
	private CommandButton btnDelete;
	private CommandButton btnClear;
	private CommandButton btnSubirPredeterminado;
	private List<ArticulosDTO> data;
	private List<ArticulosDTO> dataByCateg;
	private ArticulosDTO selectedArticulos;
	private Articulos entity;
	private boolean showDialog;
	private String estadoRegArticulo;
	@ManagedProperty(value = "#{BusinessDelegatorView}")
	private IBusinessDelegatorView businessDelegatorView;
	private String cateogriaSelect;
	private SelectOneMenu somCategorias;
	private SelectOneMenu somCategoriasTable;
	private Long returnCodigoArti;

	public ArticulosView() {
		super();
		//
		// Articulos articulos=null;
		// Set<CategoriasArticulos> lasCategoriasArtiulos=
		// articulos.getCategoriasArticuloses();
		// for (CategoriasArticulos categoriasArticulos : lasCategoriasArtiulos)
		// {
		// Categorias categorias=categoriasArticulos.getCategorias();
		// }

	}

	public void rowEventListener(RowEditEvent e) {
		try {
			ArticulosDTO articulosDTO = (ArticulosDTO) e.getObject();

			if (txtAutor == null) {
				txtAutor = new InputText();
			}

			txtAutor.setValue(articulosDTO.getAutor());

			if (txtDescripcion == null) {
				txtDescripcion = new InputText();
			}

			txtDescripcion.setValue(articulosDTO.getDescripcion());

			if (txtEstadoRegistro == null) {
				txtEstadoRegistro = new InputText();
			}

			txtEstadoRegistro.setValue(articulosDTO.getEstadoRegistro());

			if (txtNombre == null) {
				txtNombre = new InputText();
			}

			txtNombre.setValue(articulosDTO.getNombre());

			if (txtUsuCrea == null) {
				txtUsuCrea = new InputText();
			}

			txtUsuCrea.setValue(articulosDTO.getUsuCrea());

			if (txtUsuModifica == null) {
				txtUsuModifica = new InputText();
			}

			txtUsuModifica.setValue(articulosDTO.getUsuModifica());

			if (txtCodigoUsua_Usuarios == null) {
				txtCodigoUsua_Usuarios = new InputText();
			}

			txtCodigoUsua_Usuarios.setValue(articulosDTO
					.getCodigoUsua_Usuarios());

			if (txtCodigoArti == null) {
				txtCodigoArti = new InputText();
			}

			txtCodigoArti.setValue(articulosDTO.getCodigoArti());

			if (txtFechaCreacion == null) {
				txtFechaCreacion = new Calendar();
			}

			txtFechaCreacion.setValue(articulosDTO.getFechaCreacion());

			if (txtFechaModifcacion == null) {
				txtFechaModifcacion = new Calendar();
			}

			txtFechaModifcacion.setValue(articulosDTO.getFechaModifcacion());

			Long codigoArti = FacesUtils.checkLong(txtCodigoArti);
			entity = businessDelegatorView.getArticulos(codigoArti);

			action_modify();
		} catch (Exception ex) {
		}
	}

	public String action_new() {
		// action_clear();
		selectedArticulos = null;
		setShowDialog(true);

		return "";
	}

	// public String action_clear() {
	// entity = null;
	// selectedArticulos = null;
	//
	// if (txtAutor != null) {
	// txtAutor.setValue(null);
	// txtAutor.setDisabled(true);
	// }
	//
	// if (txtDescripcion != null) {
	// txtDescripcion.setValue(null);
	// txtDescripcion.setDisabled(true);
	// }
	//
	//
	// if (txtEstadoRegistro != null) {
	// txtEstadoRegistro.setValue(null);
	// txtEstadoRegistro.setDisabled(true);
	// }
	//
	// if (txtNombre != null) {
	// txtNombre.setValue(null);
	// txtNombre.setDisabled(false);
	// }
	//
	// if (txtUsuCrea != null) {
	// txtUsuCrea.setValue(null);
	// txtUsuCrea.setDisabled(true);
	// }
	//
	// if (txtUsuModifica != null) {
	// txtUsuModifica.setValue(null);
	// txtUsuModifica.setDisabled(true);
	// }
	//
	// if (txtCodigoUsua_Usuarios != null) {
	// txtCodigoUsua_Usuarios.setValue(null);
	// txtCodigoUsua_Usuarios.setDisabled(true);
	// }
	//
	// if (txtFechaCreacion != null) {
	// txtFechaCreacion.setValue(null);
	// txtFechaCreacion.setDisabled(true);
	// }
	//
	// if (txtFechaModifcacion != null) {
	// txtFechaModifcacion.setValue(null);
	// txtFechaModifcacion.setDisabled(true);
	// }
	//
	// if (txtCodigoArti != null) {
	// txtCodigoArti.setValue(null);
	// txtCodigoArti.setDisabled(false);
	// }
	//
	// if (btnSave != null) {
	// btnSave.setDisabled(true);
	// }
	//
	// if (btnDelete != null) {
	// btnDelete.setDisabled(true);
	// }
	//
	// return "";
	// }

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
			Long codigoArti = FacesUtils.checkLong(txtCodigoArti);
			entity = (codigoArti != null) ? businessDelegatorView
					.getArticulos(codigoArti) : null;
		} catch (Exception e) {
			entity = null;
		}

		if (entity == null) {
			txtAutor.setDisabled(false);
			txtDescripcion.setDisabled(false);
			txtEstadoRegistro.setDisabled(false);
			txtNombre.setDisabled(false);
			txtUsuCrea.setDisabled(false);
			txtUsuModifica.setDisabled(false);
			txtCodigoUsua_Usuarios.setDisabled(false);
			txtFechaCreacion.setDisabled(false);
			txtFechaModifcacion.setDisabled(false);
			txtCodigoArti.setDisabled(false);
			btnSave.setDisabled(false);
		} else {
			txtAutor.setValue(entity.getAutor());
			txtAutor.setDisabled(false);
			txtDescripcion.setValue(entity.getDescripcion());
			txtDescripcion.setDisabled(false);
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
			txtCodigoUsua_Usuarios.setValue(entity.getUsuarios()
					.getCodigoUsua());
			txtCodigoUsua_Usuarios.setDisabled(false);
			txtCodigoArti.setValue(entity.getCodigoArti());
			txtCodigoArti.setDisabled(true);
			btnSave.setDisabled(false);

			if (btnDelete != null) {
				btnDelete.setDisabled(false);
			}
		}
	}

	public String action_edit(ActionEvent evt) {
		selectedArticulos = (ArticulosDTO) (evt.getComponent().getAttributes()
				.get("selectedArticulos"));
		txtAutor.setValue(selectedArticulos.getAutor());
		txtAutor.setDisabled(false);
		txtDescripcion.setValue(selectedArticulos.getDescripcion());
		txtDescripcion.setDisabled(false);
		txtEstadoRegistro.setValue(selectedArticulos.getEstadoRegistro());
		txtEstadoRegistro.setDisabled(false);
		txtFechaCreacion.setValue(selectedArticulos.getFechaCreacion());
		txtFechaCreacion.setDisabled(false);
		txtFechaModifcacion.setValue(selectedArticulos.getFechaModifcacion());
		txtFechaModifcacion.setDisabled(false);
		txtNombre.setValue(selectedArticulos.getNombre());
		txtNombre.setDisabled(false);
		txtUsuCrea.setValue(selectedArticulos.getUsuCrea());
		txtUsuCrea.setDisabled(false);
		txtUsuModifica.setValue(selectedArticulos.getUsuModifica());
		txtUsuModifica.setDisabled(false);
		txtCodigoUsua_Usuarios.setValue(selectedArticulos
				.getCodigoUsua_Usuarios());
		txtCodigoUsua_Usuarios.setDisabled(false);
		txtCodigoArti.setValue(selectedArticulos.getCodigoArti());
		txtCodigoArti.setDisabled(true);
		btnSave.setDisabled(false);
		setShowDialog(true);

		return "";
	}

	public String action_save() {
		try {
			if ((selectedArticulos == null) && (entity == null)) {
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

	public String visualizarArticulo(ActionEvent evt) {
		selectedArticulos = (ArticulosDTO) (evt.getComponent().getAttributes()
				.get("selectedArticulos"));
		returnCodigoArti = selectedArticulos.getCodigoArti();
		HttpSession httpSession = (HttpSession) FacesContext
				.getCurrentInstance().getExternalContext().getSession(true);

		Long codigoArti = new Long(returnCodigoArti);

		try {
			Anexos download = businessDelegatorView
					.getAnexosbyArtiuclo(codigoArti);

			setViewURL(HOME + download.getUrl().split("/")[2]);
			httpSession.setAttribute("url", download);
			ExternalContext context = FacesContext.getCurrentInstance()
					.getExternalContext();
			context.redirect(context.getRequestContextPath()
					+ "/PantallasAdministrador/ViewPDF.xhtml");

		} catch (Exception ex) {
			FacesUtils.addErrorMessage(ex.getMessage());

		}

		return "viewPDF";
	}

	public String verPDF() {
		HttpSession httpSession = (HttpSession) FacesContext
				.getCurrentInstance().getExternalContext().getSession(true);
		Anexos download = (Anexos) httpSession.getAttribute("url");
		setViewURL(HOME + download.getUrl().split("/")[2]);
		return viewURL;
	}

	public String visualizarArticuloLector(ActionEvent evt) {
		selectedArticulos = (ArticulosDTO) (evt.getComponent().getAttributes()
				.get("selectedArticulos"));
		returnCodigoArti = selectedArticulos.getCodigoArti();
		HttpSession httpSession = (HttpSession) FacesContext
				.getCurrentInstance().getExternalContext().getSession(true);

		Long codigoArti = new Long(returnCodigoArti);

		try {
			Anexos download = businessDelegatorView
					.getAnexosbyArtiuclo(codigoArti);

			setViewURL(HOME + download.getUrl().split("/")[2]);
			httpSession.setAttribute("url", download);
			ExternalContext context = FacesContext.getCurrentInstance()
					.getExternalContext();
			context.redirect(context.getRequestContextPath()
					+ "/PantallasLector/ViewPDF.xhtml");

		} catch (Exception ex) {
			FacesUtils.addErrorMessage(ex.getMessage());

		}

		return "viewPDF";
	}

	public String verList() {

		returnCodigoArti = selectedArticulos.getCodigoArti();

		Long codigoArti = new Long(returnCodigoArti);

		try {
			Anexos download = businessDelegatorView
					.getAnexosbyArtiuclo(codigoArti);

			setViewURL(HOME + download.getUrl().split("/")[2]);
		} catch (Exception ex) {
			FacesUtils.addErrorMessage(ex.getMessage());

		}

		return "viewpdf";
	}

	public void download(ActionEvent evt) {

		selectedArticulos = (ArticulosDTO) (evt.getComponent().getAttributes()
				.get("selectedArticulos"));
		returnCodigoArti = selectedArticulos.getCodigoArti();

		Long codigoArti = new Long(returnCodigoArti);

		try {
			Anexos download = businessDelegatorView
					.getAnexosbyArtiuclo(codigoArti);
			businessDelegatorView.downloadFileByFTP("integrador.comli.com",
					"a6132029", "andres20021994",
					download.getNombre() + ".pdf", download.getUrl());
			FacesUtils
					.addInfoMessage("Articulo descargado. Revise su directorio de descargas predeterminado");

		} catch (Exception ex) {
			FacesUtils.addErrorMessage(ex.getMessage());

		}
	}
	
	public void publicar() {
		setShowDialog(true);
		
	}
	
	public void salir(){
		setShowDialog(false);
	}

	public void action_create() {

		HttpSession httpSession = (HttpSession) FacesContext
				.getCurrentInstance().getExternalContext().getSession(true);
		Usuarios autor = (Usuarios) httpSession
				.getAttribute("usuarioAdministrador");
		
		String extValidate;
		pdfUrl = defaulturl + file.getFileName();

		if (getFile() != null && !file.getFileName().trim().equals("")) {
			String ext = getFile().getFileName();
			if (ext != null) {
				extValidate = ext.substring(ext.indexOf(".") + 1);
			} else {
				extValidate = "null";
			}

			if (extValidate.equals("pdf")) {
				try {

					entity = new Articulos();

					entity.setAutor(autor.getNombre());

					entity.setDescripcion(FacesUtils.checkString(txtAreaDes));
					entity.setEstadoRegistro((estadoRegArticulo.equals("1")) ? "a"
							: (estadoRegArticulo.equals("2") ? "i" : null));
					entity.setNombre(FacesUtils.checkString(txtNombre));
					entity.setUsuCrea(autor.getNombre());
					entity.setUsuarios(autor);
					businessDelegatorView.TransferFile("integrador.comli.com",
							"a6132029", "andres20021994", file, pdfUrl);
					businessDelegatorView.saveArticulos(entity, somCategorias
							.getValue().toString(), pdfUrl);
					txtNombre.setValue(null);
					txtNombre.setValue("");
					txtNombre.setDisabled(false);
					txtAreaDes.setValue(null);
					txtAreaDes.setValue("");
					txtAreaDes.setDisabled(false);
					data = null;
					getData();
					FacesUtils.addInfoMessage("Archivo cargado correctamente");
					ExternalContext context = FacesContext.getCurrentInstance()
							.getExternalContext();
					context.redirect(context.getRequestContextPath()
							+ "/PantallasAdministrador/PrincipalAdministrador.xhtml");
				} catch (Exception e) {
					FacesUtils.addErrorMessage(e.getMessage());
				}

			} else {
				FacesUtils
						.addErrorMessage("Solo puede subir archivos con extension .pdf");
			}
		} else {
			FacesUtils.addErrorMessage("Porfavor seleccione un archivo");
		}

		try {

		} catch (Exception e) {
			data = null;
			FacesUtils.addErrorMessage(e.getMessage());
		}

	}

	public void crearPredeterminado() {
		
		HttpSession httpSession = (HttpSession) FacesContext
				.getCurrentInstance().getExternalContext().getSession(true);
		Usuarios autor = (Usuarios) httpSession
				.getAttribute("usuarioAdministrador");
		String prueba = somCategorias.getValue().toString().trim();
		if(!prueba.equals("0")){
			action_create();
		}else{
		
		String extValidate;
		pdfUrl = defaulturl + file.getFileName();

		if (getFile() != null && !file.getFileName().trim().equals("")) {
			String ext = getFile().getFileName();
			if (ext != null) {
				extValidate = ext.substring(ext.indexOf(".") + 1);
			} else {
				extValidate = "null";
			}

			if (extValidate.equals("pdf")) {
				try {

					entity = new Articulos();

					entity.setAutor(autor.getNombre());

					entity.setDescripcion(FacesUtils.checkString(txtAreaDes));
					entity.setEstadoRegistro((estadoRegArticulo.equals("1")) ? "a"
							: (estadoRegArticulo.equals("2") ? "i" : null));
					entity.setNombre(FacesUtils.checkString(txtNombre));
					entity.setUsuCrea(autor.getNombre());
					entity.setUsuarios(autor);
					businessDelegatorView.TransferFile("integrador.comli.com",
							"a6132029", "andres20021994", file, pdfUrl);
					Categorias defaultCate = businessDelegatorView
							.getCategorias(CATEGORIA_PREDETERMINADA);
					businessDelegatorView.saveArticulos(entity, defaultCate
							.getCodigoCate().toString(), pdfUrl);
					txtNombre.setValue(null);
					txtNombre.setValue("");
					txtNombre.setDisabled(false);
					txtAreaDes.setValue(null);
					txtAreaDes.setValue("");
					txtAreaDes.setDisabled(false);
					data = null;
					getData();
					
					FacesUtils.addInfoMessage("Archivo cargado correctamente");
					ExternalContext context = FacesContext.getCurrentInstance()
							.getExternalContext();
					context.redirect(context.getRequestContextPath()
							+ "/PantallasAdministrador/PrincipalAdministrador.xhtml");
					
				} catch (Exception e) {
					FacesUtils.addErrorMessage(e.getMessage());
				}

			} else {
				FacesUtils
						.addErrorMessage("Solo puede subir archivos con extension .pdf");
			}
		} else {
			FacesUtils.addErrorMessage("Porfavor seleccione un archivo");
		}

		try {

		} catch (Exception e) {
			data = null;
			FacesUtils.addErrorMessage(e.getMessage());
		}
		
		}
		
	}

	public void action_modify() {
		try {
			if (entity == null) {
				Long codigoArti = new Long(selectedArticulos.getCodigoArti());
				entity = businessDelegatorView.getArticulos(codigoArti);
			}

			entity.setAutor(FacesUtils.checkString(txtAutor));
			entity.setDescripcion(FacesUtils.checkString(txtDescripcion));
			entity.setEstadoRegistro(FacesUtils.checkString(txtEstadoRegistro));
			entity.setFechaCreacion(FacesUtils.checkDate(txtFechaCreacion));
			entity.setFechaModifcacion(FacesUtils
					.checkDate(txtFechaModifcacion));
			entity.setNombre(FacesUtils.checkString(txtNombre));
			entity.setUsuCrea(FacesUtils.checkString(txtUsuCrea));
			entity.setUsuModifica(FacesUtils.checkString(txtUsuModifica));
			entity.setUsuarios((FacesUtils.checkLong(txtCodigoUsua_Usuarios) != null) ? businessDelegatorView
					.getUsuarios(FacesUtils.checkLong(txtCodigoUsua_Usuarios))
					: null);
			businessDelegatorView.updateArticulos(entity);
			data = null;
			FacesUtils.addInfoMessage(ZMessManager.ENTITY_SUCCESFULLYMODIFIED);
		} catch (Exception e) {
			data = null;
			FacesUtils.addErrorMessage(e.getMessage());
		}
	}

	public List<Articulos> getLosArticulos() {

		if (losArticulos == null) {

			try {
				losArticulos = businessDelegatorView.consultarTodosArticulos();
			} catch (Exception e) {
				// TODO: handle exception
			}

		}

		return losArticulos;
	}

	public void returnCodigoArticulo(ActionEvent evt) {
		selectedArticulos = (ArticulosDTO) (evt.getComponent().getAttributes()
				.get("selectedArticulos"));
		returnCodigoArti = selectedArticulos.getCodigoArti();
	}

	public void action_modifyCategoria() {
		try {
			Long codigoArti = new Long(returnCodigoArti);
			CategoriasArticulos entity = businessDelegatorView
					.getCategoriasArticulos(codigoArti);

			entity.setCategorias(businessDelegatorView.getCategorias(Long
					.parseLong(somCategoriasTable.getValue().toString())));
			businessDelegatorView.updateCategoriasArticulos(entity);
			data = null;
			FacesUtils.addInfoMessage(ZMessManager.ENTITY_SUCCESFULLYMODIFIED);
			ExternalContext context = FacesContext.getCurrentInstance()
					.getExternalContext();
			context.redirect(context.getRequestContextPath()
					+ "/PantallasAdministrador/PrincipalAdministrador.xhtml");
		} catch (Exception e) {
			data = null;
			FacesUtils.addErrorMessage(e.getMessage());
		}

	}

	public String action_delete_datatable(ActionEvent evt) {
		try {
			selectedArticulos = (ArticulosDTO) (evt.getComponent()
					.getAttributes().get("selectedArticulos"));

			Long codigoArti = new Long(selectedArticulos.getCodigoArti());
			entity = businessDelegatorView.getArticulos(codigoArti);
			action_delete();
		} catch (Exception e) {
			FacesUtils.addErrorMessage(e.getMessage());
		}

		return "";
	}

	public String action_delete_master() {
		try {
			Long codigoArti = FacesUtils.checkLong(txtCodigoArti);
			entity = businessDelegatorView.getArticulos(codigoArti);
			action_delete();
		} catch (Exception e) {
			FacesUtils.addErrorMessage(e.getMessage());
		}

		return "";
	}

	public void action_delete() {
		try {
			businessDelegatorView.deleteArticulos(entity);
			FacesUtils.addInfoMessage(ZMessManager.ENTITY_SUCCESFULLYDELETED);
			// action_clear();
			data = null;
		} catch (Exception e) {
			FacesUtils.addErrorMessage(e.getMessage());
		}
	}

	public String action_closeDialog() {
		setShowDialog(false);
		// action_clear();

		return "";
	}

	public String actionDeleteDataTableEditable(ActionEvent evt) {

		try {
			selectedArticulos = (ArticulosDTO) (evt.getComponent()
					.getAttributes().get("selectedArticulos"));

			Long codigoArti = new Long(selectedArticulos.getCodigoArti());
			entity = businessDelegatorView.getArticulos(codigoArti);
			businessDelegatorView.deleteArticulos(entity);
			FacesUtils.addInfoMessage(ZMessManager.ENTITY_SUCCESFULLYDELETED);
			selectedArticulos = null;
			data = null;
			getData();
			// action_clear();
		} catch (Exception e) {
			FacesUtils.addErrorMessage(e.getMessage());
		}

		return "";
	}

	public String action_modifyWitDTO(String autor, Long codigoArti,
			String descripcion, String estadoRegistro, Date fechaCreacion,
			Date fechaModifcacion, String nombre, String usuCrea,
			String usuModifica, Long codigoUsua_Usuarios) throws Exception {
		try {
			entity.setAutor(FacesUtils.checkString(autor));
			entity.setDescripcion(FacesUtils.checkString(descripcion));
			entity.setEstadoRegistro(FacesUtils.checkString(estadoRegistro));
			entity.setFechaCreacion(FacesUtils.checkDate(fechaCreacion));
			entity.setFechaModifcacion(FacesUtils.checkDate(fechaModifcacion));
			entity.setNombre(FacesUtils.checkString(nombre));
			entity.setUsuCrea(FacesUtils.checkString(usuCrea));
			entity.setUsuModifica(FacesUtils.checkString(usuModifica));
			businessDelegatorView.updateArticulos(entity);
			FacesUtils.addInfoMessage(ZMessManager.ENTITY_SUCCESFULLYMODIFIED);
		} catch (Exception e) {
			// renderManager.getOnDemandRenderer("ArticulosView").requestRender();
			FacesUtils.addErrorMessage(e.getMessage());
			throw e;
		}

		return "";
	}

	public InputText getTxtAutor() {
		return txtAutor;
	}

	public void setTxtAutor(InputText txtAutor) {
		this.txtAutor = txtAutor;
	}

	public InputText getTxtDescripcion() {
		return txtDescripcion;
	}

	public void setTxtDescripcion(InputText txtDescripcion) {
		this.txtDescripcion = txtDescripcion;
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

	public InputText getTxtCodigoUsua_Usuarios() {
		return txtCodigoUsua_Usuarios;
	}

	public void setTxtCodigoUsua_Usuarios(InputText txtCodigoUsua_Usuarios) {
		this.txtCodigoUsua_Usuarios = txtCodigoUsua_Usuarios;
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

	public InputText getTxtCodigoArti() {
		return txtCodigoArti;
	}

	public void setTxtCodigoArti(InputText txtCodigoArti) {
		this.txtCodigoArti = txtCodigoArti;
	}

	public List<ArticulosDTO> getData() {
		try {
			if (data == null) {
				data = businessDelegatorView.getDataArticulos();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return data;
	}

	public void setData(List<ArticulosDTO> articulosDTO) {
		this.data = articulosDTO;
	}

	public ArticulosDTO getSelectedArticulos() {
		return selectedArticulos;
	}

	public void setSelectedArticulos(ArticulosDTO articulos) {
		this.selectedArticulos = articulos;
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

	/**
	 * @return the defaulturl
	 */
	public String getDefaulturl() {
		return defaulturl;
	}

	/**
	 * @param defaulturl
	 *            the defaulturl to set
	 */
	public void setDefaulturl(String defaulturl) {
		this.defaulturl = defaulturl;
	}

	/**
	 * @return the file
	 */
	public UploadedFile getFile() {
		return file;
	}

	/**
	 * @param file
	 *            the file to set
	 */
	public void setFile(UploadedFile file) {
		this.file = file;
	}

	/**
	 * @return the pdfUrl
	 */
	public String getPdfUrl() {
		return pdfUrl;
	}

	/**
	 * @param pdfUrl
	 *            the pdfUrl to set
	 */
	public void setPdfUrl(String pdfUrl) {
		this.pdfUrl = pdfUrl;
	}

	/**
	 * @return the fileUpload
	 */
	public File getFileUpload() {
		return fileUpload;
	}

	/**
	 * @param fileUpload
	 *            the fileUpload to set
	 */
	public void setFileUpload(File fileUpload) {
		this.fileUpload = fileUpload;
	}

	/**
	 * @return the txtAreaDescripcion
	 */

	/**
	 * @return the lasCategoriasItems
	 */
	public List<SelectItem> getLasCategoriasItems() {
		try {
			lasCategoriasItems = new ArrayList<SelectItem>();
			List<Categorias> losTiposDeCategorias = businessDelegatorView
					.getCategorias();
			for (Categorias categorias : losTiposDeCategorias) {
				SelectItem selectItem = new SelectItem(
						categorias.getCodigoCate(), categorias.getNombre());
				lasCategoriasItems.add(selectItem);

			}
		} catch (Exception ex) {
			FacesUtils.addErrorMessage("Error cargando las categorias");
		}

		return lasCategoriasItems;
	}

	/**
	 * @param lasCategoriasItems
	 *            the lasCategoriasItems to set
	 */
	public void setLasCategoriasItems(List<SelectItem> lasCategoriasItems) {
		this.lasCategoriasItems = lasCategoriasItems;
	}

	/**
	 * @return the categoriaSelected
	 */

	/**
	 * @return the txtAreaDes
	 */
	public InputTextarea getTxtAreaDes() {
		return txtAreaDes;
	}

	/**
	 * @param txtAreaDes
	 *            the txtAreaDes to set
	 */
	public void setTxtAreaDes(InputTextarea txtAreaDes) {
		this.txtAreaDes = txtAreaDes;
	}

	/**
	 * @return the estadoRegArticulo
	 */
	public String getEstadoRegArticulo() {
		return estadoRegArticulo;
	}

	/**
	 * @param estadoRegArticulo
	 *            the estadoRegArticulo to set
	 */
	public void setEstadoRegArticulo(String estadoRegArticulo) {
		this.estadoRegArticulo = estadoRegArticulo;
	}

	public List<Articulos> getFiltroArticulos() {
		return filtroArticulos;
	}

	public void setFiltroArticulos(List<Articulos> filtroArticulos) {
		this.filtroArticulos = filtroArticulos;
	}

	public void setLosArticulos(List<Articulos> losArticulos) {
		this.losArticulos = losArticulos;
	}

	/**
	 * @return the cateogriaSelect
	 */
	public String getCateogriaSelect() {
		return cateogriaSelect;
	}

	/**
	 * @param cateogriaSelect
	 *            the cateogriaSelect to set
	 */
	public void setCateogriaSelect(String cateogriaSelect) {
		this.cateogriaSelect = cateogriaSelect;
	}

	/**
	 * @return the somCategorias
	 */
	public SelectOneMenu getSomCategorias() {
		return somCategorias;
	}

	/**
	 * @param somCategorias
	 *            the somCategorias to set
	 */
	public void setSomCategorias(SelectOneMenu somCategorias) {
		this.somCategorias = somCategorias;
	}

	/**
	 * @return the lasCategoriasItemsTabla
	 */
	public List<SelectItem> getLasCategoriasItemsTabla() {
		try {
			lasCategoriasItemsTabla = new ArrayList<SelectItem>();
			List<Categorias> losTiposDeCategorias = businessDelegatorView
					.getCategorias();
			for (Categorias categorias : losTiposDeCategorias) {
				SelectItem selectItem = new SelectItem(
						categorias.getCodigoCate(), categorias.getNombre());
				lasCategoriasItemsTabla.add(selectItem);

			}
		} catch (Exception ex) {
			FacesUtils.addErrorMessage("Error cargando las categorias");
		}

		return lasCategoriasItemsTabla;

	}

	/**
	 * @param lasCategoriasItemsTabla
	 *            the lasCategoriasItemsTabla to set
	 */
	public void setLasCategoriasItemsTabla(
			List<SelectItem> lasCategoriasItemsTabla) {
		this.lasCategoriasItemsTabla = lasCategoriasItemsTabla;
	}

	/**
	 * @return the somCategoriasTable
	 */
	public SelectOneMenu getSomCategoriasTable() {
		return somCategoriasTable;
	}

	/**
	 * @param somCategoriasTable
	 *            the somCategoriasTable to set
	 */
	public void setSomCategoriasTable(SelectOneMenu somCategoriasTable) {
		this.somCategoriasTable = somCategoriasTable;
	}

	/**
	 * @return the returnCodigoArti
	 */
	public Long getReturnCodigoArti() {
		return returnCodigoArti;
	}

	/**
	 * @param returnCodigoArti
	 *            the returnCodigoArti to set
	 */
	public void setReturnCodigoArti(Long returnCodigoArti) {
		this.returnCodigoArti = returnCodigoArti;
	}

	/**
	 * @return the viewURL
	 */
	public String getViewURL() {
		return viewURL;
	}

	/**
	 * @param viewURL
	 *            the viewURL to set
	 */
	public void setViewURL(String viewURL) {
		this.viewURL = viewURL;
	}

	public List<ArticulosDTO> getDataByCateg() {

		try {
			HttpSession httpSession = (HttpSession) FacesContext
					.getCurrentInstance().getExternalContext().getSession(true);
			Categorias categorias = (Categorias) httpSession
					.getAttribute("categorieToView");
			dataByCateg = businessDelegatorView
					.getDataArticulosByCateg(categorias.getCodigoCate());
		} catch (Exception e) {
			FacesUtils
					.addErrorMessage("La categoria no se selecciono correctamente");
		}

		return dataByCateg;
	}

	public void setDataByCateg(List<ArticulosDTO> dataByCateg) {
		this.dataByCateg = dataByCateg;
	}

	public void likeDislike(ActionEvent evt) {
		selectedArticulos = (ArticulosDTO) (evt.getComponent().getAttributes()
				.get("selectedArticulos"));
		returnCodigoArti = selectedArticulos.getCodigoArti();

		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
				.currentRequestAttributes();
		HttpSession session = servletRequestAttributes.getRequest()
				.getSession();
		Usuarios usuario = (Usuarios) session.getAttribute("usuarioLector");

		try {
			Articulos articulos = new Articulos();
			EventosArticulos evento = new EventosArticulos();
			Date fechaMeGusta = new Date();

			articulos = businessDelegatorView.getArticulos(returnCodigoArti);
			evento = businessDelegatorView.consultareEventosPorArticulos(
					articulos.getCodigoArti(), usuario.getCodigoUsua());

			if (evento == null) {
				evento = new EventosArticulos();
				evento.setArticulos(articulos);
				evento.setUsuarios(usuario);
				evento.setMeGusta("S");
				evento.setFechaMeGusta(fechaMeGusta);
				businessDelegatorView.saveEventosArticulos(evento);
			} else {
				if (evento.getMeGusta().trim().equals("N")) {
					evento.setMeGusta("S");
					evento.setFechaMeGusta(fechaMeGusta);
					businessDelegatorView.updateEventosArticulos(evento);
				} else if (evento.getMeGusta().trim().equals("S")) {
					evento.setMeGusta("N");
					evento.setFechaMeGusta(fechaMeGusta);
					businessDelegatorView.updateEventosArticulos(evento);
				}
			}
			data=null;

		} catch (Exception e) {
			FacesUtils.addErrorMessage(e.getMessage());
		}
	}

	public CommandButton getBtnSubirPredeterminado() {
		return btnSubirPredeterminado;
	}

	public void setBtnSubirPredeterminado(CommandButton btnSubirPredeterminado) {
		this.btnSubirPredeterminado = btnSubirPredeterminado;
	}

}
