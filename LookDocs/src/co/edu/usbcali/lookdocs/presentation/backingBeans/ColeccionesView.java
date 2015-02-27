package co.edu.usbcali.lookdocs.presentation.backingBeans;

import co.edu.usbcali.lookdocs.exceptions.*;
import co.edu.usbcali.lookdocs.model.*;
import co.edu.usbcali.lookdocs.model.dto.ColeccionesDTO;
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
public class ColeccionesView implements Serializable {
    private static final long serialVersionUID = 1L;
    private InputText txtNombre;
    private InputText txtCodigoUsua_Usuarios;
    private InputText txtCodigoCole;
    private CommandButton btnSave;
    private CommandButton btnModify;
    private CommandButton btnDelete;
    private CommandButton btnClear;
    private List<ColeccionesDTO> data;
    private ColeccionesDTO selectedColecciones;
    private Colecciones entity;
    private boolean showDialog;
    @ManagedProperty(value = "#{BusinessDelegatorView}")
    private IBusinessDelegatorView businessDelegatorView;

    public ColeccionesView() {
        super();
    }

    public void rowEventListener(RowEditEvent e) {
        try {
            ColeccionesDTO coleccionesDTO = (ColeccionesDTO) e.getObject();

            if (txtNombre == null) {
                txtNombre = new InputText();
            }

            txtNombre.setValue(coleccionesDTO.getNombre());

            if (txtCodigoUsua_Usuarios == null) {
                txtCodigoUsua_Usuarios = new InputText();
            }

            txtCodigoUsua_Usuarios.setValue(coleccionesDTO.getCodigoUsua_Usuarios());

            if (txtCodigoCole == null) {
                txtCodigoCole = new InputText();
            }

            txtCodigoCole.setValue(coleccionesDTO.getCodigoCole());

            Long codigoCole = FacesUtils.checkLong(txtCodigoCole);
            entity = businessDelegatorView.getColecciones(codigoCole);

            action_modify();
        } catch (Exception ex) {
        }
    }

    public String action_new() {
        action_clear();
        selectedColecciones = null;
        setShowDialog(true);

        return "";
    }

    public String action_clear() {
        entity = null;
        selectedColecciones = null;

        if (txtNombre != null) {
            txtNombre.setValue(null);
            txtNombre.setDisabled(true);
        }

        if (txtCodigoUsua_Usuarios != null) {
            txtCodigoUsua_Usuarios.setValue(null);
            txtCodigoUsua_Usuarios.setDisabled(true);
        }

        if (txtCodigoCole != null) {
            txtCodigoCole.setValue(null);
            txtCodigoCole.setDisabled(false);
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
            Long codigoCole = FacesUtils.checkLong(txtCodigoCole);
            entity = (codigoCole != null)
                ? businessDelegatorView.getColecciones(codigoCole) : null;
        } catch (Exception e) {
            entity = null;
        }

        if (entity == null) {
            txtNombre.setDisabled(false);
            txtCodigoUsua_Usuarios.setDisabled(false);
            txtCodigoCole.setDisabled(false);
            btnSave.setDisabled(false);
        } else {
            txtNombre.setValue(entity.getNombre());
            txtNombre.setDisabled(false);
            txtCodigoUsua_Usuarios.setValue(entity.getUsuarios().getCodigoUsua());
            txtCodigoUsua_Usuarios.setDisabled(false);
            txtCodigoCole.setValue(entity.getCodigoCole());
            txtCodigoCole.setDisabled(true);
            btnSave.setDisabled(false);

            if (btnDelete != null) {
                btnDelete.setDisabled(false);
            }
        }
    }

    public String action_edit(ActionEvent evt) {
        selectedColecciones = (ColeccionesDTO) (evt.getComponent()
                                                   .getAttributes()
                                                   .get("selectedColecciones"));
        txtNombre.setValue(selectedColecciones.getNombre());
        txtNombre.setDisabled(false);
        txtCodigoUsua_Usuarios.setValue(selectedColecciones.getCodigoUsua_Usuarios());
        txtCodigoUsua_Usuarios.setDisabled(false);
        txtCodigoCole.setValue(selectedColecciones.getCodigoCole());
        txtCodigoCole.setDisabled(true);
        btnSave.setDisabled(false);
        setShowDialog(true);

        return "";
    }

    public String action_save() {
        try {
            if ((selectedColecciones == null) && (entity == null)) {
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
            entity = new Colecciones();

            Long codigoCole = FacesUtils.checkLong(txtCodigoCole);

            entity.setCodigoCole(codigoCole);
            entity.setNombre(FacesUtils.checkString(txtNombre));
            entity.setUsuarios((FacesUtils.checkLong(txtCodigoUsua_Usuarios) != null)
                ? businessDelegatorView.getUsuarios(FacesUtils.checkLong(
                        txtCodigoUsua_Usuarios)) : null);
            businessDelegatorView.saveColecciones(entity);
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
                Long codigoCole = new Long(selectedColecciones.getCodigoCole());
                entity = businessDelegatorView.getColecciones(codigoCole);
            }

            entity.setNombre(FacesUtils.checkString(txtNombre));
            entity.setUsuarios((FacesUtils.checkLong(txtCodigoUsua_Usuarios) != null)
                ? businessDelegatorView.getUsuarios(FacesUtils.checkLong(
                        txtCodigoUsua_Usuarios)) : null);
            businessDelegatorView.updateColecciones(entity);
            FacesUtils.addInfoMessage(ZMessManager.ENTITY_SUCCESFULLYMODIFIED);
        } catch (Exception e) {
            data = null;
            FacesUtils.addErrorMessage(e.getMessage());
        }

        return "";
    }

    public String action_delete_datatable(ActionEvent evt) {
        try {
            selectedColecciones = (ColeccionesDTO) (evt.getComponent()
                                                       .getAttributes()
                                                       .get("selectedColecciones"));

            Long codigoCole = new Long(selectedColecciones.getCodigoCole());
            entity = businessDelegatorView.getColecciones(codigoCole);
            action_delete();
        } catch (Exception e) {
            FacesUtils.addErrorMessage(e.getMessage());
        }

        return "";
    }

    public String action_delete_master() {
        try {
            Long codigoCole = FacesUtils.checkLong(txtCodigoCole);
            entity = businessDelegatorView.getColecciones(codigoCole);
            action_delete();
        } catch (Exception e) {
            FacesUtils.addErrorMessage(e.getMessage());
        }

        return "";
    }

    public void action_delete() throws Exception {
        try {
            businessDelegatorView.deleteColecciones(entity);
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
            selectedColecciones = (ColeccionesDTO) (evt.getComponent()
                                                       .getAttributes()
                                                       .get("selectedColecciones"));

            Long codigoCole = new Long(selectedColecciones.getCodigoCole());
            entity = businessDelegatorView.getColecciones(codigoCole);
            businessDelegatorView.deleteColecciones(entity);
            data.remove(selectedColecciones);
            FacesUtils.addInfoMessage(ZMessManager.ENTITY_SUCCESFULLYDELETED);
            action_clear();
        } catch (Exception e) {
            FacesUtils.addErrorMessage(e.getMessage());
        }

        return "";
    }

    public String action_modifyWitDTO(Long codigoCole, String nombre,
        Long codigoUsua_Usuarios) throws Exception {
        try {
            entity.setNombre(FacesUtils.checkString(nombre));
            businessDelegatorView.updateColecciones(entity);
            FacesUtils.addInfoMessage(ZMessManager.ENTITY_SUCCESFULLYMODIFIED);
        } catch (Exception e) {
            //renderManager.getOnDemandRenderer("ColeccionesView").requestRender();
            FacesUtils.addErrorMessage(e.getMessage());
            throw e;
        }

        return "";
    }

    public InputText getTxtNombre() {
        return txtNombre;
    }

    public void setTxtNombre(InputText txtNombre) {
        this.txtNombre = txtNombre;
    }

    public InputText getTxtCodigoUsua_Usuarios() {
        return txtCodigoUsua_Usuarios;
    }

    public void setTxtCodigoUsua_Usuarios(InputText txtCodigoUsua_Usuarios) {
        this.txtCodigoUsua_Usuarios = txtCodigoUsua_Usuarios;
    }

    public InputText getTxtCodigoCole() {
        return txtCodigoCole;
    }

    public void setTxtCodigoCole(InputText txtCodigoCole) {
        this.txtCodigoCole = txtCodigoCole;
    }

    public List<ColeccionesDTO> getData() {
        try {
            if (data == null) {
                data = businessDelegatorView.getDataColecciones();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    public void setData(List<ColeccionesDTO> coleccionesDTO) {
        this.data = coleccionesDTO;
    }

    public ColeccionesDTO getSelectedColecciones() {
        return selectedColecciones;
    }

    public void setSelectedColecciones(ColeccionesDTO colecciones) {
        this.selectedColecciones = colecciones;
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
