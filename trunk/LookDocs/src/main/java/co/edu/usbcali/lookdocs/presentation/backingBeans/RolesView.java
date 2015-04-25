package co.edu.usbcali.lookdocs.presentation.backingBeans;

import co.edu.usbcali.lookdocs.exceptions.*;
import co.edu.usbcali.lookdocs.model.*;
import co.edu.usbcali.lookdocs.model.dto.RolesDTO;
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
public class RolesView implements Serializable {
    private static final long serialVersionUID = 1L;
    private InputText txtEstadoRegistro;
    private InputText txtNombreRol;
    private InputText txtUsuCrea;
    private InputText txtUsuModifica;
    private InputText txtCodigoRol;
    private Calendar txtFechaCreacion;
    private Calendar txtFechaModifcacion;
    private CommandButton btnSave;
    private CommandButton btnModify;
    private CommandButton btnDelete;
    private CommandButton btnClear;
    private List<RolesDTO> data;
    private RolesDTO selectedRoles;
    private Roles entity;
    private boolean showDialog;
    @ManagedProperty(value = "#{BusinessDelegatorView}")
    private IBusinessDelegatorView businessDelegatorView;

    public RolesView() {
        super();
    }

    public void rowEventListener(RowEditEvent e) {
        try {
            RolesDTO rolesDTO = (RolesDTO) e.getObject();

            if (txtEstadoRegistro == null) {
                txtEstadoRegistro = new InputText();
            }

            txtEstadoRegistro.setValue(rolesDTO.getEstadoRegistro());

            if (txtNombreRol == null) {
                txtNombreRol = new InputText();
            }

            txtNombreRol.setValue(rolesDTO.getNombreRol());

            if (txtUsuCrea == null) {
                txtUsuCrea = new InputText();
            }

            txtUsuCrea.setValue(rolesDTO.getUsuCrea());

            if (txtUsuModifica == null) {
                txtUsuModifica = new InputText();
            }

            txtUsuModifica.setValue(rolesDTO.getUsuModifica());

            if (txtCodigoRol == null) {
                txtCodigoRol = new InputText();
            }

            txtCodigoRol.setValue(rolesDTO.getCodigoRol());

            if (txtFechaCreacion == null) {
                txtFechaCreacion = new Calendar();
            }

            txtFechaCreacion.setValue(rolesDTO.getFechaCreacion());

            if (txtFechaModifcacion == null) {
                txtFechaModifcacion = new Calendar();
            }

            txtFechaModifcacion.setValue(rolesDTO.getFechaModifcacion());

            Long codigoRol = FacesUtils.checkLong(txtCodigoRol);
            entity = businessDelegatorView.getRoles(codigoRol);

            action_modify();
        } catch (Exception ex) {
        }
    }

    public String action_new() {
        action_clear();
        selectedRoles = null;
        setShowDialog(true);

        return "";
    }

    public String action_clear() {
        entity = null;
        selectedRoles = null;

        if (txtEstadoRegistro != null) {
            txtEstadoRegistro.setValue(null);
            txtEstadoRegistro.setDisabled(true);
        }

        if (txtNombreRol != null) {
            txtNombreRol.setValue(null);
            txtNombreRol.setDisabled(true);
        }

        if (txtUsuCrea != null) {
            txtUsuCrea.setValue(null);
            txtUsuCrea.setDisabled(true);
        }

        if (txtUsuModifica != null) {
            txtUsuModifica.setValue(null);
            txtUsuModifica.setDisabled(true);
        }

        if (txtFechaCreacion != null) {
            txtFechaCreacion.setValue(null);
            txtFechaCreacion.setDisabled(true);
        }

        if (txtFechaModifcacion != null) {
            txtFechaModifcacion.setValue(null);
            txtFechaModifcacion.setDisabled(true);
        }

        if (txtCodigoRol != null) {
            txtCodigoRol.setValue(null);
            txtCodigoRol.setDisabled(false);
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
        FacesContext.getCurrentInstance()
                    .addMessage("",
            new FacesMessage("Selected Date " + dateFormat.format(inputDate)));
    }

    public void listener_txtFechaModifcacion() {
        Date inputDate = (Date) txtFechaModifcacion.getValue();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        FacesContext.getCurrentInstance()
                    .addMessage("",
            new FacesMessage("Selected Date " + dateFormat.format(inputDate)));
    }

    public void listener_txtId() {
        try {
            Long codigoRol = FacesUtils.checkLong(txtCodigoRol);
            entity = (codigoRol != null)
                ? businessDelegatorView.getRoles(codigoRol) : null;
        } catch (Exception e) {
            entity = null;
        }

        if (entity == null) {
            txtEstadoRegistro.setDisabled(false);
            txtNombreRol.setDisabled(false);
            txtUsuCrea.setDisabled(false);
            txtUsuModifica.setDisabled(false);
            txtFechaCreacion.setDisabled(false);
            txtFechaModifcacion.setDisabled(false);
            txtCodigoRol.setDisabled(false);
            btnSave.setDisabled(false);
        } else {
            txtEstadoRegistro.setValue(entity.getEstadoRegistro());
            txtEstadoRegistro.setDisabled(false);
            txtFechaCreacion.setValue(entity.getFechaCreacion());
            txtFechaCreacion.setDisabled(false);
            txtFechaModifcacion.setValue(entity.getFechaModifcacion());
            txtFechaModifcacion.setDisabled(false);
            txtNombreRol.setValue(entity.getNombreRol());
            txtNombreRol.setDisabled(false);
            txtUsuCrea.setValue(entity.getUsuCrea());
            txtUsuCrea.setDisabled(false);
            txtUsuModifica.setValue(entity.getUsuModifica());
            txtUsuModifica.setDisabled(false);
            txtCodigoRol.setValue(entity.getCodigoRol());
            txtCodigoRol.setDisabled(true);
            btnSave.setDisabled(false);

            if (btnDelete != null) {
                btnDelete.setDisabled(false);
            }
        }
    }

    public String action_edit(ActionEvent evt) {
        selectedRoles = (RolesDTO) (evt.getComponent().getAttributes()
                                       .get("selectedRoles"));
        txtEstadoRegistro.setValue(selectedRoles.getEstadoRegistro());
        txtEstadoRegistro.setDisabled(false);
        txtFechaCreacion.setValue(selectedRoles.getFechaCreacion());
        txtFechaCreacion.setDisabled(false);
        txtFechaModifcacion.setValue(selectedRoles.getFechaModifcacion());
        txtFechaModifcacion.setDisabled(false);
        txtNombreRol.setValue(selectedRoles.getNombreRol());
        txtNombreRol.setDisabled(false);
        txtUsuCrea.setValue(selectedRoles.getUsuCrea());
        txtUsuCrea.setDisabled(false);
        txtUsuModifica.setValue(selectedRoles.getUsuModifica());
        txtUsuModifica.setDisabled(false);
        txtCodigoRol.setValue(selectedRoles.getCodigoRol());
        txtCodigoRol.setDisabled(true);
        btnSave.setDisabled(false);
        setShowDialog(true);

        return "";
    }

    public String action_save() {
        try {
            if ((selectedRoles == null) && (entity == null)) {
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
            entity = new Roles();

            Long codigoRol = FacesUtils.checkLong(txtCodigoRol);

            entity.setCodigoRol(codigoRol);
            entity.setEstadoRegistro(FacesUtils.checkString(txtEstadoRegistro));
            entity.setFechaCreacion(FacesUtils.checkDate(txtFechaCreacion));
            entity.setFechaModifcacion(FacesUtils.checkDate(txtFechaModifcacion));
            entity.setNombreRol(FacesUtils.checkString(txtNombreRol));
            entity.setUsuCrea(FacesUtils.checkString(txtUsuCrea));
            entity.setUsuModifica(FacesUtils.checkString(txtUsuModifica));
            businessDelegatorView.saveRoles(entity);
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
                Long codigoRol = new Long(selectedRoles.getCodigoRol());
                entity = businessDelegatorView.getRoles(codigoRol);
            }

            entity.setEstadoRegistro(FacesUtils.checkString(txtEstadoRegistro));
            entity.setFechaCreacion(FacesUtils.checkDate(txtFechaCreacion));
            entity.setFechaModifcacion(FacesUtils.checkDate(txtFechaModifcacion));
            entity.setNombreRol(FacesUtils.checkString(txtNombreRol));
            entity.setUsuCrea(FacesUtils.checkString(txtUsuCrea));
            entity.setUsuModifica(FacesUtils.checkString(txtUsuModifica));
            businessDelegatorView.updateRoles(entity);
            FacesUtils.addInfoMessage(ZMessManager.ENTITY_SUCCESFULLYMODIFIED);
        } catch (Exception e) {
            data = null;
            FacesUtils.addErrorMessage(e.getMessage());
        }

        return "";
    }

    public String action_delete_datatable(ActionEvent evt) {
        try {
            selectedRoles = (RolesDTO) (evt.getComponent().getAttributes()
                                           .get("selectedRoles"));

            Long codigoRol = new Long(selectedRoles.getCodigoRol());
            entity = businessDelegatorView.getRoles(codigoRol);
            action_delete();
        } catch (Exception e) {
            FacesUtils.addErrorMessage(e.getMessage());
        }

        return "";
    }

    public String action_delete_master() {
        try {
            Long codigoRol = FacesUtils.checkLong(txtCodigoRol);
            entity = businessDelegatorView.getRoles(codigoRol);
            action_delete();
        } catch (Exception e) {
            FacesUtils.addErrorMessage(e.getMessage());
        }

        return "";
    }

    public void action_delete() throws Exception {
        try {
            businessDelegatorView.deleteRoles(entity);
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
            selectedRoles = (RolesDTO) (evt.getComponent().getAttributes()
                                           .get("selectedRoles"));

            Long codigoRol = new Long(selectedRoles.getCodigoRol());
            entity = businessDelegatorView.getRoles(codigoRol);
            businessDelegatorView.deleteRoles(entity);
            data.remove(selectedRoles);
            FacesUtils.addInfoMessage(ZMessManager.ENTITY_SUCCESFULLYDELETED);
            action_clear();
        } catch (Exception e) {
            FacesUtils.addErrorMessage(e.getMessage());
        }

        return "";
    }

    public String action_modifyWitDTO(Long codigoRol, String estadoRegistro,
        Date fechaCreacion, Date fechaModifcacion, String nombreRol,
        String usuCrea, String usuModifica) throws Exception {
        try {
            entity.setEstadoRegistro(FacesUtils.checkString(estadoRegistro));
            entity.setFechaCreacion(FacesUtils.checkDate(fechaCreacion));
            entity.setFechaModifcacion(FacesUtils.checkDate(fechaModifcacion));
            entity.setNombreRol(FacesUtils.checkString(nombreRol));
            entity.setUsuCrea(FacesUtils.checkString(usuCrea));
            entity.setUsuModifica(FacesUtils.checkString(usuModifica));
            businessDelegatorView.updateRoles(entity);
            FacesUtils.addInfoMessage(ZMessManager.ENTITY_SUCCESFULLYMODIFIED);
        } catch (Exception e) {
            //renderManager.getOnDemandRenderer("RolesView").requestRender();
            FacesUtils.addErrorMessage(e.getMessage());
            throw e;
        }

        return "";
    }

    public InputText getTxtEstadoRegistro() {
        return txtEstadoRegistro;
    }

    public void setTxtEstadoRegistro(InputText txtEstadoRegistro) {
        this.txtEstadoRegistro = txtEstadoRegistro;
    }

    public InputText getTxtNombreRol() {
        return txtNombreRol;
    }

    public void setTxtNombreRol(InputText txtNombreRol) {
        this.txtNombreRol = txtNombreRol;
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

    public InputText getTxtCodigoRol() {
        return txtCodigoRol;
    }

    public void setTxtCodigoRol(InputText txtCodigoRol) {
        this.txtCodigoRol = txtCodigoRol;
    }

    public List<RolesDTO> getData() {
        try {
            if (data == null) {
                data = businessDelegatorView.getDataRoles();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    public void setData(List<RolesDTO> rolesDTO) {
        this.data = rolesDTO;
    }

    public RolesDTO getSelectedRoles() {
        return selectedRoles;
    }

    public void setSelectedRoles(RolesDTO roles) {
        this.selectedRoles = roles;
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
