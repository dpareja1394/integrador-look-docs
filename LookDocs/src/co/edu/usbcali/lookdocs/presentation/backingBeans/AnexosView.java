package co.edu.usbcali.lookdocs.presentation.backingBeans;

import co.edu.usbcali.lookdocs.exceptions.*;
import co.edu.usbcali.lookdocs.model.*;
import co.edu.usbcali.lookdocs.model.dto.AnexosDTO;
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
public class AnexosView implements Serializable {
    private static final long serialVersionUID = 1L;
    private InputText txtFormato;
    private InputText txtNombre;
    private InputText txtUrl;
    private InputText txtCodigoArti_Articulos;
    private InputText txtCodigoAnexo;
    private CommandButton btnSave;
    private CommandButton btnModify;
    private CommandButton btnDelete;
    private CommandButton btnClear;
    private List<AnexosDTO> data;
    private AnexosDTO selectedAnexos;
    private Anexos entity;
    private boolean showDialog;
    @ManagedProperty(value = "#{BusinessDelegatorView}")
    private IBusinessDelegatorView businessDelegatorView;

    public AnexosView() {
        super();
    }

    public void rowEventListener(RowEditEvent e) {
        try {
            AnexosDTO anexosDTO = (AnexosDTO) e.getObject();

            if (txtFormato == null) {
                txtFormato = new InputText();
            }

            txtFormato.setValue(anexosDTO.getFormato());

            if (txtNombre == null) {
                txtNombre = new InputText();
            }

            txtNombre.setValue(anexosDTO.getNombre());

            if (txtUrl == null) {
                txtUrl = new InputText();
            }

            txtUrl.setValue(anexosDTO.getUrl());

            if (txtCodigoArti_Articulos == null) {
                txtCodigoArti_Articulos = new InputText();
            }

            txtCodigoArti_Articulos.setValue(anexosDTO.getCodigoArti_Articulos());

            if (txtCodigoAnexo == null) {
                txtCodigoAnexo = new InputText();
            }

            txtCodigoAnexo.setValue(anexosDTO.getCodigoAnexo());

            Long codigoAnexo = FacesUtils.checkLong(txtCodigoAnexo);
            entity = businessDelegatorView.getAnexos(codigoAnexo);

            action_modify();
        } catch (Exception ex) {
        }
    }

    public String action_new() {
        action_clear();
        selectedAnexos = null;
        setShowDialog(true);

        return "";
    }

    public String action_clear() {
        entity = null;
        selectedAnexos = null;

        if (txtFormato != null) {
            txtFormato.setValue(null);
            txtFormato.setDisabled(true);
        }

        if (txtNombre != null) {
            txtNombre.setValue(null);
            txtNombre.setDisabled(true);
        }

        if (txtUrl != null) {
            txtUrl.setValue(null);
            txtUrl.setDisabled(true);
        }

        if (txtCodigoArti_Articulos != null) {
            txtCodigoArti_Articulos.setValue(null);
            txtCodigoArti_Articulos.setDisabled(true);
        }

        if (txtCodigoAnexo != null) {
            txtCodigoAnexo.setValue(null);
            txtCodigoAnexo.setDisabled(false);
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
            Long codigoAnexo = FacesUtils.checkLong(txtCodigoAnexo);
            entity = (codigoAnexo != null)
                ? businessDelegatorView.getAnexos(codigoAnexo) : null;
        } catch (Exception e) {
            entity = null;
        }

        if (entity == null) {
            txtFormato.setDisabled(false);
            txtNombre.setDisabled(false);
            txtUrl.setDisabled(false);
            txtCodigoArti_Articulos.setDisabled(false);
            txtCodigoAnexo.setDisabled(false);
            btnSave.setDisabled(false);
        } else {
            txtFormato.setValue(entity.getFormato());
            txtFormato.setDisabled(false);
            txtNombre.setValue(entity.getNombre());
            txtNombre.setDisabled(false);
            txtUrl.setValue(entity.getUrl());
            txtUrl.setDisabled(false);
            txtCodigoArti_Articulos.setValue(entity.getArticulos()
                                                   .getCodigoArti());
            txtCodigoArti_Articulos.setDisabled(false);
            txtCodigoAnexo.setValue(entity.getCodigoAnexo());
            txtCodigoAnexo.setDisabled(true);
            btnSave.setDisabled(false);

            if (btnDelete != null) {
                btnDelete.setDisabled(false);
            }
        }
    }

    public String action_edit(ActionEvent evt) {
        selectedAnexos = (AnexosDTO) (evt.getComponent().getAttributes()
                                         .get("selectedAnexos"));
        txtFormato.setValue(selectedAnexos.getFormato());
        txtFormato.setDisabled(false);
        txtNombre.setValue(selectedAnexos.getNombre());
        txtNombre.setDisabled(false);
        txtUrl.setValue(selectedAnexos.getUrl());
        txtUrl.setDisabled(false);
        txtCodigoArti_Articulos.setValue(selectedAnexos.getCodigoArti_Articulos());
        txtCodigoArti_Articulos.setDisabled(false);
        txtCodigoAnexo.setValue(selectedAnexos.getCodigoAnexo());
        txtCodigoAnexo.setDisabled(true);
        btnSave.setDisabled(false);
        setShowDialog(true);

        return "";
    }

    public String action_save() {
        try {
            if ((selectedAnexos == null) && (entity == null)) {
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
            entity = new Anexos();

            Long codigoAnexo = FacesUtils.checkLong(txtCodigoAnexo);

            entity.setCodigoAnexo(codigoAnexo);
            entity.setFormato(FacesUtils.checkString(txtFormato));
            entity.setNombre(FacesUtils.checkString(txtNombre));
            entity.setUrl(FacesUtils.checkString(txtUrl));
            entity.setArticulos((FacesUtils.checkLong(txtCodigoArti_Articulos) != null)
                ? businessDelegatorView.getArticulos(FacesUtils.checkLong(
                        txtCodigoArti_Articulos)) : null);
            businessDelegatorView.saveAnexos(entity);
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
                Long codigoAnexo = new Long(selectedAnexos.getCodigoAnexo());
                entity = businessDelegatorView.getAnexos(codigoAnexo);
            }

            entity.setFormato(FacesUtils.checkString(txtFormato));
            entity.setNombre(FacesUtils.checkString(txtNombre));
            entity.setUrl(FacesUtils.checkString(txtUrl));
            entity.setArticulos((FacesUtils.checkLong(txtCodigoArti_Articulos) != null)
                ? businessDelegatorView.getArticulos(FacesUtils.checkLong(
                        txtCodigoArti_Articulos)) : null);
            businessDelegatorView.updateAnexos(entity);
            FacesUtils.addInfoMessage(ZMessManager.ENTITY_SUCCESFULLYMODIFIED);
        } catch (Exception e) {
            data = null;
            FacesUtils.addErrorMessage(e.getMessage());
        }

        return "";
    }

    public String action_delete_datatable(ActionEvent evt) {
        try {
            selectedAnexos = (AnexosDTO) (evt.getComponent().getAttributes()
                                             .get("selectedAnexos"));

            Long codigoAnexo = new Long(selectedAnexos.getCodigoAnexo());
            entity = businessDelegatorView.getAnexos(codigoAnexo);
            action_delete();
        } catch (Exception e) {
            FacesUtils.addErrorMessage(e.getMessage());
        }

        return "";
    }

    public String action_delete_master() {
        try {
            Long codigoAnexo = FacesUtils.checkLong(txtCodigoAnexo);
            entity = businessDelegatorView.getAnexos(codigoAnexo);
            action_delete();
        } catch (Exception e) {
            FacesUtils.addErrorMessage(e.getMessage());
        }

        return "";
    }

    public void action_delete() throws Exception {
        try {
            businessDelegatorView.deleteAnexos(entity);
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
            selectedAnexos = (AnexosDTO) (evt.getComponent().getAttributes()
                                             .get("selectedAnexos"));

            Long codigoAnexo = new Long(selectedAnexos.getCodigoAnexo());
            entity = businessDelegatorView.getAnexos(codigoAnexo);
            businessDelegatorView.deleteAnexos(entity);
            data.remove(selectedAnexos);
            FacesUtils.addInfoMessage(ZMessManager.ENTITY_SUCCESFULLYDELETED);
            action_clear();
        } catch (Exception e) {
            FacesUtils.addErrorMessage(e.getMessage());
        }

        return "";
    }

    public String action_modifyWitDTO(Long codigoAnexo, String formato,
        String nombre, String url, Long codigoArti_Articulos)
        throws Exception {
        try {
            entity.setFormato(FacesUtils.checkString(formato));
            entity.setNombre(FacesUtils.checkString(nombre));
            entity.setUrl(FacesUtils.checkString(url));
            businessDelegatorView.updateAnexos(entity);
            FacesUtils.addInfoMessage(ZMessManager.ENTITY_SUCCESFULLYMODIFIED);
        } catch (Exception e) {
            //renderManager.getOnDemandRenderer("AnexosView").requestRender();
            FacesUtils.addErrorMessage(e.getMessage());
            throw e;
        }

        return "";
    }

    public InputText getTxtFormato() {
        return txtFormato;
    }

    public void setTxtFormato(InputText txtFormato) {
        this.txtFormato = txtFormato;
    }

    public InputText getTxtNombre() {
        return txtNombre;
    }

    public void setTxtNombre(InputText txtNombre) {
        this.txtNombre = txtNombre;
    }

    public InputText getTxtUrl() {
        return txtUrl;
    }

    public void setTxtUrl(InputText txtUrl) {
        this.txtUrl = txtUrl;
    }

    public InputText getTxtCodigoArti_Articulos() {
        return txtCodigoArti_Articulos;
    }

    public void setTxtCodigoArti_Articulos(InputText txtCodigoArti_Articulos) {
        this.txtCodigoArti_Articulos = txtCodigoArti_Articulos;
    }

    public InputText getTxtCodigoAnexo() {
        return txtCodigoAnexo;
    }

    public void setTxtCodigoAnexo(InputText txtCodigoAnexo) {
        this.txtCodigoAnexo = txtCodigoAnexo;
    }

    public List<AnexosDTO> getData() {
        try {
            if (data == null) {
                data = businessDelegatorView.getDataAnexos();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    public void setData(List<AnexosDTO> anexosDTO) {
        this.data = anexosDTO;
    }

    public AnexosDTO getSelectedAnexos() {
        return selectedAnexos;
    }

    public void setSelectedAnexos(AnexosDTO anexos) {
        this.selectedAnexos = anexos;
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
