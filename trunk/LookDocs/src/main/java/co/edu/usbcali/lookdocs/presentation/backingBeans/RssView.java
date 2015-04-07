package co.edu.usbcali.lookdocs.presentation.backingBeans;

import co.edu.usbcali.lookdocs.exceptions.*;
import co.edu.usbcali.lookdocs.model.*;
import co.edu.usbcali.lookdocs.model.control.RssLogic;
import co.edu.usbcali.lookdocs.model.dto.RssDTO;
import co.edu.usbcali.lookdocs.presentation.businessDelegate.*;
import co.edu.usbcali.lookdocs.utilities.*;

import org.primefaces.component.calendar.*;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.feedreader.FeedReader;
import org.primefaces.component.inputtext.InputText;
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
			action_delete();
		} catch (Exception e) {
			FacesUtils.addErrorMessage(e.getMessage());
		}

		return "";
	}

	public String action_delete_master() {
		try {
			Long codigoRss = FacesUtils.checkLong(txtCodigoRss);
			entity = businessDelegatorView.getRss(codigoRss);
			action_delete();
		} catch (Exception e) {
			FacesUtils.addErrorMessage(e.getMessage());
		}

		return "";
	}

	public void action_delete() throws Exception {
		try {
			businessDelegatorView.deleteRss(entity);
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

	public void actionGuardarRss() {
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
				
				
				txtRSSBuscar.setValue("");
				feedReaderMostrar.setValue("");
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					"",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, e
							.getMessage(), e.getMessage()));
		}
		
		
	}
	
	public String mensajeGuardar(){
		return "guardar";
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

}
