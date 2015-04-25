package co.edu.usbcali.lookdocs.presentation.backingBeans;

import co.edu.usbcali.lookdocs.exceptions.*;
import co.edu.usbcali.lookdocs.model.*;
import co.edu.usbcali.lookdocs.model.dto.EntradasDTO;
import co.edu.usbcali.lookdocs.presentation.businessDelegate.*;
import co.edu.usbcali.lookdocs.utilities.*;

import org.primefaces.component.calendar.*;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.inputtext.InputText;

import org.primefaces.event.RowEditEvent;

import java.io.Serializable;

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
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;


/**
 * @author Zathura Code Generator http://code.google.com/p/zathura
 * www.zathuracode.org
 *
 */
@ManagedBean
@ViewScoped
public class EntradasView implements Serializable {
    private static final long serialVersionUID = 1L;
    private InputText txtFavorito;
    private InputText txtLeido;
    private InputText txtCodigoRss_Rss;
    private InputText txtCodigoEntra;
    private Calendar txtFechaFavorito;
    private Calendar txtFechaLeido;
    private CommandButton btnSave;
    private CommandButton btnModify;
    private CommandButton btnDelete;
    private CommandButton btnClear;
    private List<EntradasDTO> data;
    private EntradasDTO selectedEntradas;
    private Entradas entity;
    private boolean showDialog;
    @ManagedProperty(value = "#{BusinessDelegatorView}")
    private IBusinessDelegatorView businessDelegatorView;

    public EntradasView() {
        super();
    }

    public void rowEventListener(RowEditEvent e) {
        try {
            EntradasDTO entradasDTO = (EntradasDTO) e.getObject();

            if (txtFavorito == null) {
                txtFavorito = new InputText();
            }

            txtFavorito.setValue(entradasDTO.getFavorito());

            if (txtLeido == null) {
                txtLeido = new InputText();
            }

            txtLeido.setValue(entradasDTO.getLeido());

            if (txtCodigoRss_Rss == null) {
                txtCodigoRss_Rss = new InputText();
            }

            txtCodigoRss_Rss.setValue(entradasDTO.getCodigoRss_Rss());

            if (txtCodigoEntra == null) {
                txtCodigoEntra = new InputText();
            }

            txtCodigoEntra.setValue(entradasDTO.getCodigoEntra());

            if (txtFechaFavorito == null) {
                txtFechaFavorito = new Calendar();
            }

            txtFechaFavorito.setValue(entradasDTO.getFechaFavorito());

            if (txtFechaLeido == null) {
                txtFechaLeido = new Calendar();
            }

            txtFechaLeido.setValue(entradasDTO.getFechaLeido());

            Long codigoEntra = FacesUtils.checkLong(txtCodigoEntra);
            entity = businessDelegatorView.getEntradas(codigoEntra);

            action_modify();
        } catch (Exception ex) {
        }
    }

    public String action_new() {
        action_clear();
        selectedEntradas = null;
        setShowDialog(true);

        return "";
    }

    public String action_clear() {
        entity = null;
        selectedEntradas = null;

        if (txtFavorito != null) {
            txtFavorito.setValue(null);
            txtFavorito.setDisabled(true);
        }

        if (txtLeido != null) {
            txtLeido.setValue(null);
            txtLeido.setDisabled(true);
        }

        if (txtCodigoRss_Rss != null) {
            txtCodigoRss_Rss.setValue(null);
            txtCodigoRss_Rss.setDisabled(true);
        }

        if (txtFechaFavorito != null) {
            txtFechaFavorito.setValue(null);
            txtFechaFavorito.setDisabled(true);
        }

        if (txtFechaLeido != null) {
            txtFechaLeido.setValue(null);
            txtFechaLeido.setDisabled(true);
        }

        if (txtCodigoEntra != null) {
            txtCodigoEntra.setValue(null);
            txtCodigoEntra.setDisabled(false);
        }

        if (btnSave != null) {
            btnSave.setDisabled(true);
        }

        if (btnDelete != null) {
            btnDelete.setDisabled(true);
        }

        return "";
    }

    public void listener_txtFechaFavorito() {
        Date inputDate = (Date) txtFechaFavorito.getValue();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        FacesContext.getCurrentInstance()
                    .addMessage("",
            new FacesMessage("Selected Date " + dateFormat.format(inputDate)));
    }

    public void listener_txtFechaLeido() {
        Date inputDate = (Date) txtFechaLeido.getValue();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        FacesContext.getCurrentInstance()
                    .addMessage("",
            new FacesMessage("Selected Date " + dateFormat.format(inputDate)));
    }

    public void listener_txtId() {
        try {
            Long codigoEntra = FacesUtils.checkLong(txtCodigoEntra);
            entity = (codigoEntra != null)
                ? businessDelegatorView.getEntradas(codigoEntra) : null;
        } catch (Exception e) {
            entity = null;
        }

        if (entity == null) {
            txtFavorito.setDisabled(false);
            txtLeido.setDisabled(false);
            txtCodigoRss_Rss.setDisabled(false);
            txtFechaFavorito.setDisabled(false);
            txtFechaLeido.setDisabled(false);
            txtCodigoEntra.setDisabled(false);
            btnSave.setDisabled(false);
        } else {
            txtFavorito.setValue(entity.getFavorito());
            txtFavorito.setDisabled(false);
            txtFechaFavorito.setValue(entity.getFechaFavorito());
            txtFechaFavorito.setDisabled(false);
            txtFechaLeido.setValue(entity.getFechaLeido());
            txtFechaLeido.setDisabled(false);
            txtLeido.setValue(entity.getLeido());
            txtLeido.setDisabled(false);
            txtCodigoRss_Rss.setValue(entity.getRss().getCodigoRss());
            txtCodigoRss_Rss.setDisabled(false);
            txtCodigoEntra.setValue(entity.getCodigoEntra());
            txtCodigoEntra.setDisabled(true);
            btnSave.setDisabled(false);

            if (btnDelete != null) {
                btnDelete.setDisabled(false);
            }
        }
    }

    public String action_edit(ActionEvent evt) {
        selectedEntradas = (EntradasDTO) (evt.getComponent().getAttributes()
                                             .get("selectedEntradas"));
        txtFavorito.setValue(selectedEntradas.getFavorito());
        txtFavorito.setDisabled(false);
        txtFechaFavorito.setValue(selectedEntradas.getFechaFavorito());
        txtFechaFavorito.setDisabled(false);
        txtFechaLeido.setValue(selectedEntradas.getFechaLeido());
        txtFechaLeido.setDisabled(false);
        txtLeido.setValue(selectedEntradas.getLeido());
        txtLeido.setDisabled(false);
        txtCodigoRss_Rss.setValue(selectedEntradas.getCodigoRss_Rss());
        txtCodigoRss_Rss.setDisabled(false);
        txtCodigoEntra.setValue(selectedEntradas.getCodigoEntra());
        txtCodigoEntra.setDisabled(true);
        btnSave.setDisabled(false);
        setShowDialog(true);

        return "";
    }

    public String action_save() {
        try {
            if ((selectedEntradas == null) && (entity == null)) {
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
            entity = new Entradas();

            Long codigoEntra = FacesUtils.checkLong(txtCodigoEntra);

            entity.setCodigoEntra(codigoEntra);
            entity.setFavorito(FacesUtils.checkString(txtFavorito));
            entity.setFechaFavorito(FacesUtils.checkDate(txtFechaFavorito));
            entity.setFechaLeido(FacesUtils.checkDate(txtFechaLeido));
            entity.setLeido(FacesUtils.checkString(txtLeido));
            entity.setRss((FacesUtils.checkLong(txtCodigoRss_Rss) != null)
                ? businessDelegatorView.getRss(FacesUtils.checkLong(
                        txtCodigoRss_Rss)) : null);
            businessDelegatorView.saveEntradas(entity);
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
                Long codigoEntra = new Long(selectedEntradas.getCodigoEntra());
                entity = businessDelegatorView.getEntradas(codigoEntra);
            }

            entity.setFavorito(FacesUtils.checkString(txtFavorito));
            entity.setFechaFavorito(FacesUtils.checkDate(txtFechaFavorito));
            entity.setFechaLeido(FacesUtils.checkDate(txtFechaLeido));
            entity.setLeido(FacesUtils.checkString(txtLeido));
            entity.setRss((FacesUtils.checkLong(txtCodigoRss_Rss) != null)
                ? businessDelegatorView.getRss(FacesUtils.checkLong(
                        txtCodigoRss_Rss)) : null);
            businessDelegatorView.updateEntradas(entity);
            FacesUtils.addInfoMessage(ZMessManager.ENTITY_SUCCESFULLYMODIFIED);
        } catch (Exception e) {
            data = null;
            FacesUtils.addErrorMessage(e.getMessage());
        }

        return "";
    }

    public String action_delete_datatable(ActionEvent evt) {
        try {
            selectedEntradas = (EntradasDTO) (evt.getComponent().getAttributes()
                                                 .get("selectedEntradas"));

            Long codigoEntra = new Long(selectedEntradas.getCodigoEntra());
            entity = businessDelegatorView.getEntradas(codigoEntra);
            action_delete();
        } catch (Exception e) {
            FacesUtils.addErrorMessage(e.getMessage());
        }

        return "";
    }

    public String action_delete_master() {
        try {
            Long codigoEntra = FacesUtils.checkLong(txtCodigoEntra);
            entity = businessDelegatorView.getEntradas(codigoEntra);
            action_delete();
        } catch (Exception e) {
            FacesUtils.addErrorMessage(e.getMessage());
        }

        return "";
    }

    public void action_delete() throws Exception {
        try {
            businessDelegatorView.deleteEntradas(entity);
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
            selectedEntradas = (EntradasDTO) (evt.getComponent().getAttributes()
                                                 .get("selectedEntradas"));

            Long codigoEntra = new Long(selectedEntradas.getCodigoEntra());
            entity = businessDelegatorView.getEntradas(codigoEntra);
            businessDelegatorView.deleteEntradas(entity);
            data.remove(selectedEntradas);
            FacesUtils.addInfoMessage(ZMessManager.ENTITY_SUCCESFULLYDELETED);
            action_clear();
        } catch (Exception e) {
            FacesUtils.addErrorMessage(e.getMessage());
        }

        return "";
    }

    public String action_modifyWitDTO(Long codigoEntra, String favorito,
        Date fechaFavorito, Date fechaLeido, String leido, Long codigoRss_Rss)
        throws Exception {
        try {
            entity.setFavorito(FacesUtils.checkString(favorito));
            entity.setFechaFavorito(FacesUtils.checkDate(fechaFavorito));
            entity.setFechaLeido(FacesUtils.checkDate(fechaLeido));
            entity.setLeido(FacesUtils.checkString(leido));
            businessDelegatorView.updateEntradas(entity);
            FacesUtils.addInfoMessage(ZMessManager.ENTITY_SUCCESFULLYMODIFIED);
        } catch (Exception e) {
            //renderManager.getOnDemandRenderer("EntradasView").requestRender();
            FacesUtils.addErrorMessage(e.getMessage());
            throw e;
        }

        return "";
    }

    public InputText getTxtFavorito() {
        return txtFavorito;
    }

    public void setTxtFavorito(InputText txtFavorito) {
        this.txtFavorito = txtFavorito;
    }

    public InputText getTxtLeido() {
        return txtLeido;
    }

    public void setTxtLeido(InputText txtLeido) {
        this.txtLeido = txtLeido;
    }

    public InputText getTxtCodigoRss_Rss() {
        return txtCodigoRss_Rss;
    }

    public void setTxtCodigoRss_Rss(InputText txtCodigoRss_Rss) {
        this.txtCodigoRss_Rss = txtCodigoRss_Rss;
    }

    public Calendar getTxtFechaFavorito() {
        return txtFechaFavorito;
    }

    public void setTxtFechaFavorito(Calendar txtFechaFavorito) {
        this.txtFechaFavorito = txtFechaFavorito;
    }

    public Calendar getTxtFechaLeido() {
        return txtFechaLeido;
    }

    public void setTxtFechaLeido(Calendar txtFechaLeido) {
        this.txtFechaLeido = txtFechaLeido;
    }

    public InputText getTxtCodigoEntra() {
        return txtCodigoEntra;
    }

    public void setTxtCodigoEntra(InputText txtCodigoEntra) {
        this.txtCodigoEntra = txtCodigoEntra;
    }

    public List<EntradasDTO> getData() {
        try {
            if (data == null) {
                data = businessDelegatorView.getDataEntradas();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    public void setData(List<EntradasDTO> entradasDTO) {
        this.data = entradasDTO;
    }

    public EntradasDTO getSelectedEntradas() {
        return selectedEntradas;
    }

    public void setSelectedEntradas(EntradasDTO entradas) {
        this.selectedEntradas = entradas;
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
}
