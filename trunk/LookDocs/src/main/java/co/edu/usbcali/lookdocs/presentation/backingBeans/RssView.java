package co.edu.usbcali.lookdocs.presentation.backingBeans;

import co.edu.usbcali.lookdocs.exceptions.*;
import co.edu.usbcali.lookdocs.model.*;
import co.edu.usbcali.lookdocs.model.control.ColeccionesRssLogic;
import co.edu.usbcali.lookdocs.model.control.RssLogic;
import co.edu.usbcali.lookdocs.model.dto.RssDTO;
import co.edu.usbcali.lookdocs.presentation.businessDelegate.*;
import co.edu.usbcali.lookdocs.utilities.*;

import org.primefaces.component.calendar.*;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.feedreader.FeedReader;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.outputlabel.OutputLabel;
import org.primefaces.component.panel.Panel;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.jamonapi.utils.Logger;

import java.io.Serializable;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpSession;


/**
 * @author Zathura Code Generator http://code.google.com/p/zathura
 *         www.zathuracode.org
 *
 */
@ManagedBean
@ViewScoped
public class RssView implements Serializable {
	private static final long serialVersionUID = 1L;
	private InputText txtUrl;
	private InputText txtCodigoRss;
	private CommandButton btnSave;
	private CommandButton btnModify;
	private CommandButton btnDelete;
	private CommandButton btnClear;
	private List<RssDTO> data;
	private RssDTO selectedRss;
	private Rss entity;
	private boolean showDialog;
	private InputText txtRSSBuscar;
	private CommandButton btnBuscarRSS;
	private FeedReader feedReaderMostrar;
	private SelectOneMenu somColeccionesRSSLector;
	private Usuarios usuarioSecurity;
	private String urlSeleccionado;
	private Panel panelSolo;
	private Panel panelVarios;
	private List<Colecciones> lasColeccionesUrl;
	private SelectOneMenu somColeccionesUrl;
	private OutputLabel lblUrlEliminar;
	private InputText txtUrlEliminar;
	private String coleccionSeleSOM;
	private CommandButton btnFavorito;
	private List<Rss> rssColeccion;
	private String rssSeleccionado;

	@ManagedProperty(value = "#{BusinessDelegatorView}")
	private IBusinessDelegatorView businessDelegatorView;

	
	
	public RssView() {
		super();
	}

	public void rowEventListener(RowEditEvent e) {
		try {
			RssDTO rssDTO = (RssDTO) e.getObject();

			if (txtUrl == null) {
				txtUrl = new InputText();
			}

			txtUrl.setValue(rssDTO.getUrl());

			if (txtCodigoRss == null) {
				txtCodigoRss = new InputText();
			}

			txtCodigoRss.setValue(rssDTO.getCodigoRss());

			Long codigoRss = FacesUtils.checkLong(txtCodigoRss);
			entity = businessDelegatorView.getRss(codigoRss);

			action_modify();
		} catch (Exception ex) {
		}
	}

	public String action_new() {
		action_clear();
		selectedRss = null;
		setShowDialog(true);

		return "";
	}

	public String action_clear() {
		entity = null;
		selectedRss = null;

		if (txtUrl != null) {
			txtUrl.setValue(null);
			txtUrl.setDisabled(true);
		}

		if (txtCodigoRss != null) {
			txtCodigoRss.setValue(null);
			txtCodigoRss.setDisabled(false);
		}

		if (btnSave != null) {
			btnSave.setDisabled(true);
		}

		if (btnDelete != null) {
			btnDelete.setDisabled(true);
		}

		return "";
	}

	public void listener_txtId() {
		try {
			Long codigoRss = FacesUtils.checkLong(txtCodigoRss);
			entity = (codigoRss != null) ? businessDelegatorView
					.getRss(codigoRss) : null;
		} catch (Exception e) {
			entity = null;
		}

		if (entity == null) {
			txtUrl.setDisabled(false);
			txtCodigoRss.setDisabled(false);
			btnSave.setDisabled(false);
		} else {
			txtUrl.setValue(entity.getUrl());
			txtUrl.setDisabled(false);
			txtCodigoRss.setValue(entity.getCodigoRss());
			txtCodigoRss.setDisabled(true);
			btnSave.setDisabled(false);

			if (btnDelete != null) {
				btnDelete.setDisabled(false);
			}
		}
	}

	public String action_edit(ActionEvent evt) {
		selectedRss = (RssDTO) (evt.getComponent().getAttributes()
				.get("selectedRss"));
		txtUrl.setValue(selectedRss.getUrl());
		txtUrl.setDisabled(false);
		txtCodigoRss.setValue(selectedRss.getCodigoRss());
		txtCodigoRss.setDisabled(true);
		btnSave.setDisabled(false);
		setShowDialog(true);

		return "";
	}

	public String action_save() {
		try {
			if ((selectedRss == null) && (entity == null)) {
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
			entity = new Rss();

			Long codigoRss = FacesUtils.checkLong(txtCodigoRss);

			entity.setCodigoRss(codigoRss);
			entity.setUrl(FacesUtils.checkString(txtUrl));
			businessDelegatorView.saveRss(entity);
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
				Long codigoRss = new Long(selectedRss.getCodigoRss());
				entity = businessDelegatorView.getRss(codigoRss);
			}

			entity.setUrl(FacesUtils.checkString(txtUrl));
			businessDelegatorView.updateRss(entity);
			FacesUtils.addInfoMessage(ZMessManager.ENTITY_SUCCESFULLYMODIFIED);
		} catch (Exception e) {
			data = null;
			FacesUtils.addErrorMessage(e.getMessage());
		}

		return "";
	}

	public String action_delete_datatable(ActionEvent evt) {
		try {
			selectedRss = (RssDTO) (evt.getComponent().getAttributes()
					.get("selectedRss"));

			Long codigoRss = new Long(selectedRss.getCodigoRss());
			entity = businessDelegatorView.getRss(codigoRss);
			//action_delete();
		} catch (Exception e) {
			FacesUtils.addErrorMessage(e.getMessage());
		}

		return "";
	}

	public String action_delete_master() {
		try {
			Long codigoRss = FacesUtils.checkLong(txtCodigoRss);
			entity = businessDelegatorView.getRss(codigoRss);
			//action_delete();
		} catch (Exception e) {
			FacesUtils.addErrorMessage(e.getMessage());
		}

		return "";
	}
	
	public void consultarUrl(ActionEvent evt){
		Rss rss = (Rss) (evt.getComponent()
				.getAttributes().get("selectedUrl"));
		urlSeleccionado = rss.getUrl();		
	}
	
	public void consultarRss(ActionEvent evt){
		RssDTO rssDto = (RssDTO) (evt.getComponent()
				.getAttributes().get("selectedUrl"));
		urlSeleccionado = rssDto.getUrl();
	}

	public void consultarPanel(){
		
		lasColeccionesUrl = businessDelegatorView.consultarColeccionesPorURL(urlSeleccionado);
		
		if(lasColeccionesUrl.size()==1){
			//panelSolo = new Panel(); 
			panelSolo.setVisible(true);
			//panelVarios = new Panel();
			panelVarios.setVisible(false);
			txtUrlEliminar.setValue(urlSeleccionado);
			
		}else{
			//panelSolo = new Panel(); 
			panelSolo.setVisible(false);
			//panelVarios = new Panel();
			panelVarios.setVisible(true);
		}
	}
	
	public String action_delete() throws Exception {
		try {
			Rss rss = new Rss();
			Colecciones laColeccion = new Colecciones();
			ColeccionesRss colrss = new ColeccionesRss();
			Entradas entrada = new Entradas();
			
			ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
					.currentRequestAttributes();
			HttpSession session = servletRequestAttributes.getRequest()
					.getSession();
			usuarioSecurity = (Usuarios) session.getAttribute("usuarioLector");
			
			if(lasColeccionesUrl.size()==1){
				
				rss = businessDelegatorView.consultarRssPorURl(urlSeleccionado);
				
				String nombreColeccion = businessDelegatorView.nombreColeccionPorCodigoRss(rss);
				laColeccion = businessDelegatorView.consultarColeccionPorNombreYUsuario(usuarioSecurity, nombreColeccion);
				colrss = businessDelegatorView.consultarColeccionRssPorColeRss(rss, laColeccion);						
				businessDelegatorView.deleteColeccionesRss(colrss);
				
				Entradas laEntrada = businessDelegatorView.consultarEntradas(rss);
				if(laEntrada != null){
					businessDelegatorView.deleteEntradas(laEntrada);
				}
												
								
				businessDelegatorView.deleteRss(rss);
				txtUrlEliminar.setValue("");
				FacesUtils.addInfoMessage(ZMessManager.ENTITY_SUCCESFULLYDELETED);
			}else{
				
				lasColeccionesUrl = businessDelegatorView.consultarColeccionesPorURL(urlSeleccionado);
				
				coleccionSeleSOM =  (String) somColeccionesUrl.getValue();
				
				laColeccion = businessDelegatorView.consultarColeccionPorNombreYUsuario(usuarioSecurity, coleccionSeleSOM);
				
				rss = businessDelegatorView.consultarRssPorUrlCole(urlSeleccionado, laColeccion);
				
				colrss = businessDelegatorView.consultarColeccionRssPorColeRss(rss, laColeccion);
				businessDelegatorView.deleteColeccionesRss(colrss);
				
				Entradas laEntrada = businessDelegatorView.consultarEntradas(rss);
				if(laEntrada != null){
					businessDelegatorView.deleteEntradas(laEntrada);
				}
				
				businessDelegatorView.deleteRss(rss);
				lasColeccionesUrl = null;
				coleccionSeleSOM = null;
				FacesUtils.addInfoMessage(ZMessManager.ENTITY_SUCCESFULLYDELETED);				
				//action_clear();
			}		
			
		} catch (Exception e) {
			data = null;
			throw e;
		}
		
		return "rssEliminado";
	}
	
	/*public void cargarBtn(){
		btnFavorito = new CommandButton();
		btnFavorito.setValue("Me Gusta");
	}*/
	
	public void consultarRssFavorito(ActionEvent evt){
		RssDTO rssDto = (RssDTO) (evt.getComponent()
				.getAttributes().get("selectedUrl"));
		rssSeleccionado = rssDto.getUrl();
	}
	
	public String favorito() throws Exception {
		
		try {
			Rss rss = new Rss();
			Entradas entrada = new Entradas();
			Date fechaMeGusta = new Date();			
			
			rss = businessDelegatorView.consultarRssPorURl(rssSeleccionado);
			entrada = businessDelegatorView.consultarEntradaPorRss(rss);
			
			if(entrada.getFavorito().equals("N")){
				entrada.setFavorito("S");
				entrada.setFechaFavorito(fechaMeGusta);
				businessDelegatorView.updateEntradas(entrada);
			}else
				if(entrada.getFavorito().equals("S")){
					entrada.setFavorito("N");
					businessDelegatorView.updateEntradas(entrada);
				}			
			
			rssSeleccionado = null;
			//urlSeleccionado = null;
			
		} catch (Exception e) {
			throw e;
		}
		
		return "favorito";
	}

	public String action_closeDialog() {
		setShowDialog(false);
		action_clear();

		return "";
	}

	public String actionDeleteDataTableEditable(ActionEvent evt) {
		try {
			selectedRss = (RssDTO) (evt.getComponent().getAttributes()
					.get("selectedRss"));

			Long codigoRss = new Long(selectedRss.getCodigoRss());
			entity = businessDelegatorView.getRss(codigoRss);
			businessDelegatorView.deleteRss(entity);
			data.remove(selectedRss);
			FacesUtils.addInfoMessage(ZMessManager.ENTITY_SUCCESFULLYDELETED);
			action_clear();
		} catch (Exception e) {
			FacesUtils.addErrorMessage(e.getMessage());
		}

		return "";
	}

	public String action_modifyWitDTO(Long codigoRss, String url)
			throws Exception {
		try {
			entity.setUrl(FacesUtils.checkString(url));
			businessDelegatorView.updateRss(entity);
			FacesUtils.addInfoMessage(ZMessManager.ENTITY_SUCCESFULLYMODIFIED);
		} catch (Exception e) {
			// renderManager.getOnDemandRenderer("RssView").requestRender();
			FacesUtils.addErrorMessage(e.getMessage());
			throw e;
		}

		return "";
	}

	public InputText getTxtUrl() {
		return txtUrl;
	}

	public void setTxtUrl(InputText txtUrl) {
		this.txtUrl = txtUrl;
	}

	public InputText getTxtCodigoRss() {
		return txtCodigoRss;
	}

	public void setTxtCodigoRss(InputText txtCodigoRss) {
		this.txtCodigoRss = txtCodigoRss;
	}

	public List<RssDTO> getData() {
		try {
			if (data == null) {
				data = businessDelegatorView.getDataRss();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return data;
	}

	public void setData(List<RssDTO> rssDTO) {
		this.data = rssDTO;
	}

	public RssDTO getSelectedRss() {
		return selectedRss;
	}

	public void setSelectedRss(RssDTO rss) {
		this.selectedRss = rss;
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

	public InputText getTxtRSSBuscar() {
		return txtRSSBuscar;
	}

	public void setTxtRSSBuscar(InputText txtRSSBuscar) {
		this.txtRSSBuscar = txtRSSBuscar;
	}

	public CommandButton getBtnBuscarRSS() {
		return btnBuscarRSS;
	}

	public void setBtnBuscarRSS(CommandButton btnBuscarRSS) {
		this.btnBuscarRSS = btnBuscarRSS;
	}

	public FeedReader getFeedReaderMostrar() {
		return feedReaderMostrar;
	}

	public void setFeedReaderMostrar(FeedReader feedReaderMostrar) {
		this.feedReaderMostrar = feedReaderMostrar;
	}

	public SelectOneMenu getSomColeccionesRSSLector() {
		return somColeccionesRSSLector;
	}

	public void setSomColeccionesRSSLector(SelectOneMenu somColeccionesRSSLector) {
		this.somColeccionesRSSLector = somColeccionesRSSLector;
	}

	public void actionBuscarRss() {
		try {
			if (txtRSSBuscar.getValue().toString().trim().equals("") == true) {
				FacesContext.getCurrentInstance().addMessage(
						"",
						new FacesMessage(
								"Debe ingresar una URL de Rss para buscar"));
			} else {
				String urlRss = txtRSSBuscar.getValue().toString();
			
				feedReaderMostrar.setValue(urlRss);
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					"",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), e.getMessage()));
		}
	}

	public String actionGuardarRss() {
		try {
			if (txtRSSBuscar.getValue().toString().trim().equals("") == true) {
				FacesContext.getCurrentInstance().addMessage(
						"",
						new FacesMessage(
								"Debe ingresar una URL de Rss para agregar"));
			} else {
				String urlRss = txtRSSBuscar.getValue().toString();
				Long idColeccion = Long
						.parseLong((String) somColeccionesRSSLector.getValue());
				
				businessDelegatorView.guardarRSS(urlRss, idColeccion);
				FacesContext.getCurrentInstance().addMessage("",
						new FacesMessage("Se ha guardado el RSS"));
				
				txtRSSBuscar.setValue("");
				feedReaderMostrar.setValue("");
				return "guardar";
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					"",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), e.getMessage()));
		}
		return "";
		
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

	public Panel getPanelSolo() {
		return panelSolo;
	}

	public void setPanelSolo(Panel panelSolo) {
		this.panelSolo = panelSolo;
	}

	public Panel getPanelVarios() {
		return panelVarios;
	}

	public void setPanelVarios(Panel panelVarios) {
		this.panelVarios = panelVarios;
	}

	public List<Colecciones> getLasColeccionesUrl() {
		return lasColeccionesUrl;
	}

	public void setLasColeccionesUrl(List<Colecciones> lasColeccionesUrl) {
		this.lasColeccionesUrl = lasColeccionesUrl;
	}

	public SelectOneMenu getSomColeccionesUrl() {
		return somColeccionesUrl;
	}

	public void setSomColeccionesUrl(SelectOneMenu somColeccionesUrl) {
		this.somColeccionesUrl = somColeccionesUrl;
	}

	public OutputLabel getLblUrlEliminar() {
		return lblUrlEliminar;
	}

	public void setLblUrlEliminar(OutputLabel lblUrlEliminar) {
		this.lblUrlEliminar = lblUrlEliminar;
	}

	public InputText getTxtUrlEliminar() {
		return txtUrlEliminar;
	}

	public void setTxtUrlEliminar(InputText txtUrlEliminar) {
		this.txtUrlEliminar = txtUrlEliminar;
	}

	public CommandButton getBtnFavorito() {
		return btnFavorito;
	}

	public void setBtnFavorito(CommandButton btnFavorito) {
		this.btnFavorito = btnFavorito;
	}

	public List<Rss> getRssColeccion() {
		return rssColeccion;
	}

	public void setRssColeccion(List<Rss> rssColeccion) {
		this.rssColeccion = rssColeccion;
	}

	public String getRssSeleccionado() {
		return rssSeleccionado;
	}

	public void setRssSeleccionado(String rssSeleccionado) {
		this.rssSeleccionado = rssSeleccionado;
	}
	
	

}
