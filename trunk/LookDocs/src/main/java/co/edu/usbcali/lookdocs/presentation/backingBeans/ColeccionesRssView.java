package co.edu.usbcali.lookdocs.presentation.backingBeans;

import co.edu.usbcali.lookdocs.exceptions.*;
import co.edu.usbcali.lookdocs.model.*;
import co.edu.usbcali.lookdocs.model.dto.ColeccionesRssDTO;
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
public class ColeccionesRssView implements Serializable {
    private static final long serialVersionUID = 1L;
    private InputText txtCodigoCole_Colecciones;
    private InputText txtCodigoRss_Rss;
    private InputText txtCodigoColRss;
    private CommandButton btnSave;
    private CommandButton btnModify;
    private CommandButton btnDelete;
    private CommandButton btnClear;
    private List<ColeccionesRssDTO> data;
    private ColeccionesRssDTO selectedColeccionesRss;
    private ColeccionesRss entity;
    private boolean showDialog;
    @ManagedProperty(value = "#{BusinessDelegatorView}")
    private IBusinessDelegatorView businessDelegatorView;

    public ColeccionesRssView() {
        super();
    }

    public void rowEventListener(RowEditEvent e) {
        try {
            ColeccionesRssDTO coleccionesRssDTO = (ColeccionesRssDTO) e.getObject();

            if (txtCodigoCole_Colecciones == null) {
                txtCodigoCole_Colecciones = new InputText();
            }

            txtCodigoCole_Colecciones.setValue(coleccionesRssDTO.getCodigoCole_Colecciones());

            if (txtCodigoRss_Rss == null) {
                txtCodigoRss_Rss = new InputText();
            }

            txtCodigoRss_Rss.setValue(coleccionesRssDTO.getCodigoRss_Rss());

            if (txtCodigoColRss == null) {
                txtCodigoColRss = new InputText();
            }

            txtCodigoColRss.setValue(coleccionesRssDTO.getCodigoColRss());

            Long codigoColRss = FacesUtils.checkLong(txtCodigoColRss);
            entity = businessDelegatorView.getColeccionesRss(codigoColRss);

            action_modify();
        } catch (Exception ex) {
        }
    }

    public String action_new() {
        action_clear();
        selectedColeccionesRss = null;
        setShowDialog(true);

        return "";
    }

    public String action_clear() {
        entity = null;
        selectedColeccionesRss = null;

        if (txtCodigoCole_Colecciones != null) {
            txtCodigoCole_Colecciones.setValue(null);
            txtCodigoCole_Colecciones.setDisabled(true);
        }

        if (txtCodigoRss_Rss != null) {
            txtCodigoRss_Rss.setValue(null);
            txtCodigoRss_Rss.setDisabled(true);
        }

        if (txtCodigoColRss != null) {
            txtCodigoColRss.setValue(null);
            txtCodigoColRss.setDisabled(false);
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
            Long codigoColRss = FacesUtils.checkLong(txtCodigoColRss);
            entity = (codigoColRss != null)
                ? businessDelegatorView.getColeccionesRss(codigoColRss) : null;
        } catch (Exception e) {
            entity = null;
        }

        if (entity == null) {
            txtCodigoCole_Colecciones.setDisabled(false);
            txtCodigoRss_Rss.setDisabled(false);
            txtCodigoColRss.setDisabled(false);
            btnSave.setDisabled(false);
        } else {
            txtCodigoCole_Colecciones.setValue(entity.getColecciones()
                                                     .getCodigoCole());
            txtCodigoCole_Colecciones.setDisabled(false);
            txtCodigoRss_Rss.setValue(entity.getRss().getCodigoRss());
            txtCodigoRss_Rss.setDisabled(false);
            txtCodigoColRss.setValue(entity.getCodigoColRss());
            txtCodigoColRss.setDisabled(true);
            btnSave.setDisabled(false);

            if (btnDelete != null) {
                btnDelete.setDisabled(false);
            }
        }
    }

    public String action_edit(ActionEvent evt) {
        selectedColeccionesRss = (ColeccionesRssDTO) (evt.getComponent()
                                                         .getAttributes()
                                                         .get("selectedColeccionesRss"));
        txtCodigoCole_Colecciones.setValue(selectedColeccionesRss.getCodigoCole_Colecciones());
        txtCodigoCole_Colecciones.setDisabled(false);
        txtCodigoRss_Rss.setValue(selectedColeccionesRss.getCodigoRss_Rss());
        txtCodigoRss_Rss.setDisabled(false);
        txtCodigoColRss.setValue(selectedColeccionesRss.getCodigoColRss());
        txtCodigoColRss.setDisabled(true);
        btnSave.setDisabled(false);
        setShowDialog(true);

        return "";
    }

    public String action_save() {
        try {
            if ((selectedColeccionesRss == null) && (entity == null)) {
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
            entity = new ColeccionesRss();

            Long codigoColRss = FacesUtils.checkLong(txtCodigoColRss);

            entity.setCodigoColRss(codigoColRss);
            entity.setColecciones((FacesUtils.checkLong(
                    txtCodigoCole_Colecciones) != null)
                ? businessDelegatorView.getColecciones(FacesUtils.checkLong(
                        txtCodigoCole_Colecciones)) : null);
            entity.setRss((FacesUtils.checkLong(txtCodigoRss_Rss) != null)
                ? businessDelegatorView.getRss(FacesUtils.checkLong(
                        txtCodigoRss_Rss)) : null);
            businessDelegatorView.saveColeccionesRss(entity);
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
                Long codigoColRss = new Long(selectedColeccionesRss.getCodigoColRss());
                entity = businessDelegatorView.getColeccionesRss(codigoColRss);
            }

            entity.setColecciones((FacesUtils.checkLong(
                    txtCodigoCole_Colecciones) != null)
                ? businessDelegatorView.getColecciones(FacesUtils.checkLong(
                        txtCodigoCole_Colecciones)) : null);
            entity.setRss((FacesUtils.checkLong(txtCodigoRss_Rss) != null)
                ? businessDelegatorView.getRss(FacesUtils.checkLong(
                        txtCodigoRss_Rss)) : null);
            businessDelegatorView.updateColeccionesRss(entity);
            FacesUtils.addInfoMessage(ZMessManager.ENTITY_SUCCESFULLYMODIFIED);
        } catch (Exception e) {
            data = null;
            FacesUtils.addErrorMessage(e.getMessage());
        }

        return "";
    }

    public String action_delete_datatable(ActionEvent evt) {
        try {
            selectedColeccionesRss = (ColeccionesRssDTO) (evt.getComponent()
                                                             .getAttributes()
                                                             .get("selectedColeccionesRss"));

            Long codigoColRss = new Long(selectedColeccionesRss.getCodigoColRss());
            entity = businessDelegatorView.getColeccionesRss(codigoColRss);
            action_delete();
        } catch (Exception e) {
            FacesUtils.addErrorMessage(e.getMessage());
        }

        return "";
    }

    public String action_delete_master() {
        try {
            Long codigoColRss = FacesUtils.checkLong(txtCodigoColRss);
            entity = businessDelegatorView.getColeccionesRss(codigoColRss);
            action_delete();
        } catch (Exception e) {
            FacesUtils.addErrorMessage(e.getMessage());
        }

        return "";
    }

    public void action_delete() throws Exception {
        try {
            businessDelegatorView.deleteColeccionesRss(entity);
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
            selectedColeccionesRss = (ColeccionesRssDTO) (evt.getComponent()
                                                             .getAttributes()
                                                             .get("selectedColeccionesRss"));

            Long codigoColRss = new Long(selectedColeccionesRss.getCodigoColRss());
            entity = businessDelegatorView.getColeccionesRss(codigoColRss);
            businessDelegatorView.deleteColeccionesRss(entity);
            data.remove(selectedColeccionesRss);
            FacesUtils.addInfoMessage(ZMessManager.ENTITY_SUCCESFULLYDELETED);
            action_clear();
        } catch (Exception e) {
            FacesUtils.addErrorMessage(e.getMessage());
        }

        return "";
    }

    public String action_modifyWitDTO(Long codigoColRss,
        Long codigoCole_Colecciones, Long codigoRss_Rss)
        throws Exception {
        try {
            businessDelegatorView.updateColeccionesRss(entity);
            FacesUtils.addInfoMessage(ZMessManager.ENTITY_SUCCESFULLYMODIFIED);
        } catch (Exception e) {
            //renderManager.getOnDemandRenderer("ColeccionesRssView").requestRender();
            FacesUtils.addErrorMessage(e.getMessage());
            throw e;
        }

        return "";
    }

    public InputText getTxtCodigoCole_Colecciones() {
        return txtCodigoCole_Colecciones;
    }

    public void setTxtCodigoCole_Colecciones(
        InputText txtCodigoCole_Colecciones) {
        this.txtCodigoCole_Colecciones = txtCodigoCole_Colecciones;
    }

    public InputText getTxtCodigoRss_Rss() {
        return txtCodigoRss_Rss;
    }

    public void setTxtCodigoRss_Rss(InputText txtCodigoRss_Rss) {
        this.txtCodigoRss_Rss = txtCodigoRss_Rss;
    }

    public InputText getTxtCodigoColRss() {
        return txtCodigoColRss;
    }

    public void setTxtCodigoColRss(InputText txtCodigoColRss) {
        this.txtCodigoColRss = txtCodigoColRss;
    }

    public List<ColeccionesRssDTO> getData() {
        try {
            if (data == null) {
                data = businessDelegatorView.getDataColeccionesRss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    public void setData(List<ColeccionesRssDTO> coleccionesRssDTO) {
        this.data = coleccionesRssDTO;
    }

    public ColeccionesRssDTO getSelectedColeccionesRss() {
        return selectedColeccionesRss;
    }

    public void setSelectedColeccionesRss(ColeccionesRssDTO coleccionesRss) {
        this.selectedColeccionesRss = coleccionesRss;
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
