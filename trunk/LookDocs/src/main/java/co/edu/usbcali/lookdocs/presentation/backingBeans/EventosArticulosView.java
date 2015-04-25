package co.edu.usbcali.lookdocs.presentation.backingBeans;

import co.edu.usbcali.lookdocs.exceptions.*;
import co.edu.usbcali.lookdocs.model.*;
import co.edu.usbcali.lookdocs.model.dto.EventosArticulosDTO;
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
public class EventosArticulosView implements Serializable {
    private static final long serialVersionUID = 1L;
    private InputText txtFavorito;
    private InputText txtMeGusta;
    private InputText txtCodigoArti_Articulos;
    private InputText txtCodigoUsua_Usuarios;
    private InputText txtCodigoEveArt;
    private Calendar txtFechaFavorito;
    private Calendar txtFechaMeGusta;
    private CommandButton btnSave;
    private CommandButton btnModify;
    private CommandButton btnDelete;
    private CommandButton btnClear;
    private List<EventosArticulosDTO> data;
    private EventosArticulosDTO selectedEventosArticulos;
    private EventosArticulos entity;
    private boolean showDialog;
    @ManagedProperty(value = "#{BusinessDelegatorView}")
    private IBusinessDelegatorView businessDelegatorView;

    public EventosArticulosView() {
        super();
    }

    public void rowEventListener(RowEditEvent e) {
        try {
            EventosArticulosDTO eventosArticulosDTO = (EventosArticulosDTO) e.getObject();

            if (txtFavorito == null) {
                txtFavorito = new InputText();
            }

            txtFavorito.setValue(eventosArticulosDTO.getFavorito());

            if (txtMeGusta == null) {
                txtMeGusta = new InputText();
            }

            txtMeGusta.setValue(eventosArticulosDTO.getMeGusta());

            if (txtCodigoArti_Articulos == null) {
                txtCodigoArti_Articulos = new InputText();
            }

            txtCodigoArti_Articulos.setValue(eventosArticulosDTO.getCodigoArti_Articulos());

            if (txtCodigoUsua_Usuarios == null) {
                txtCodigoUsua_Usuarios = new InputText();
            }

            txtCodigoUsua_Usuarios.setValue(eventosArticulosDTO.getCodigoUsua_Usuarios());

            if (txtCodigoEveArt == null) {
                txtCodigoEveArt = new InputText();
            }

            txtCodigoEveArt.setValue(eventosArticulosDTO.getCodigoEveArt());

            if (txtFechaFavorito == null) {
                txtFechaFavorito = new Calendar();
            }

            txtFechaFavorito.setValue(eventosArticulosDTO.getFechaFavorito());

            if (txtFechaMeGusta == null) {
                txtFechaMeGusta = new Calendar();
            }

            txtFechaMeGusta.setValue(eventosArticulosDTO.getFechaMeGusta());

            Long codigoEveArt = FacesUtils.checkLong(txtCodigoEveArt);
            entity = businessDelegatorView.getEventosArticulos(codigoEveArt);

            action_modify();
        } catch (Exception ex) {
        }
    }

    public String action_new() {
        action_clear();
        selectedEventosArticulos = null;
        setShowDialog(true);

        return "";
    }

    public String action_clear() {
        entity = null;
        selectedEventosArticulos = null;

        if (txtFavorito != null) {
            txtFavorito.setValue(null);
            txtFavorito.setDisabled(true);
        }

        if (txtMeGusta != null) {
            txtMeGusta.setValue(null);
            txtMeGusta.setDisabled(true);
        }

        if (txtCodigoArti_Articulos != null) {
            txtCodigoArti_Articulos.setValue(null);
            txtCodigoArti_Articulos.setDisabled(true);
        }

        if (txtCodigoUsua_Usuarios != null) {
            txtCodigoUsua_Usuarios.setValue(null);
            txtCodigoUsua_Usuarios.setDisabled(true);
        }

        if (txtFechaFavorito != null) {
            txtFechaFavorito.setValue(null);
            txtFechaFavorito.setDisabled(true);
        }

        if (txtFechaMeGusta != null) {
            txtFechaMeGusta.setValue(null);
            txtFechaMeGusta.setDisabled(true);
        }

        if (txtCodigoEveArt != null) {
            txtCodigoEveArt.setValue(null);
            txtCodigoEveArt.setDisabled(false);
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

    public void listener_txtFechaMeGusta() {
        Date inputDate = (Date) txtFechaMeGusta.getValue();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        FacesContext.getCurrentInstance()
                    .addMessage("",
            new FacesMessage("Selected Date " + dateFormat.format(inputDate)));
    }

    public void listener_txtId() {
        try {
            Long codigoEveArt = FacesUtils.checkLong(txtCodigoEveArt);
            entity = (codigoEveArt != null)
                ? businessDelegatorView.getEventosArticulos(codigoEveArt) : null;
        } catch (Exception e) {
            entity = null;
        }

        if (entity == null) {
            txtFavorito.setDisabled(false);
            txtMeGusta.setDisabled(false);
            txtCodigoArti_Articulos.setDisabled(false);
            txtCodigoUsua_Usuarios.setDisabled(false);
            txtFechaFavorito.setDisabled(false);
            txtFechaMeGusta.setDisabled(false);
            txtCodigoEveArt.setDisabled(false);
            btnSave.setDisabled(false);
        } else {
            txtFavorito.setValue(entity.getFavorito());
            txtFavorito.setDisabled(false);
            txtFechaFavorito.setValue(entity.getFechaFavorito());
            txtFechaFavorito.setDisabled(false);
            txtFechaMeGusta.setValue(entity.getFechaMeGusta());
            txtFechaMeGusta.setDisabled(false);
            txtMeGusta.setValue(entity.getMeGusta());
            txtMeGusta.setDisabled(false);
            txtCodigoArti_Articulos.setValue(entity.getArticulos()
                                                   .getCodigoArti());
            txtCodigoArti_Articulos.setDisabled(false);
            txtCodigoUsua_Usuarios.setValue(entity.getUsuarios().getCodigoUsua());
            txtCodigoUsua_Usuarios.setDisabled(false);
            txtCodigoEveArt.setValue(entity.getCodigoEveArt());
            txtCodigoEveArt.setDisabled(true);
            btnSave.setDisabled(false);

            if (btnDelete != null) {
                btnDelete.setDisabled(false);
            }
        }
    }

    public String action_edit(ActionEvent evt) {
        selectedEventosArticulos = (EventosArticulosDTO) (evt.getComponent()
                                                             .getAttributes()
                                                             .get("selectedEventosArticulos"));
        txtFavorito.setValue(selectedEventosArticulos.getFavorito());
        txtFavorito.setDisabled(false);
        txtFechaFavorito.setValue(selectedEventosArticulos.getFechaFavorito());
        txtFechaFavorito.setDisabled(false);
        txtFechaMeGusta.setValue(selectedEventosArticulos.getFechaMeGusta());
        txtFechaMeGusta.setDisabled(false);
        txtMeGusta.setValue(selectedEventosArticulos.getMeGusta());
        txtMeGusta.setDisabled(false);
        txtCodigoArti_Articulos.setValue(selectedEventosArticulos.getCodigoArti_Articulos());
        txtCodigoArti_Articulos.setDisabled(false);
        txtCodigoUsua_Usuarios.setValue(selectedEventosArticulos.getCodigoUsua_Usuarios());
        txtCodigoUsua_Usuarios.setDisabled(false);
        txtCodigoEveArt.setValue(selectedEventosArticulos.getCodigoEveArt());
        txtCodigoEveArt.setDisabled(true);
        btnSave.setDisabled(false);
        setShowDialog(true);

        return "";
    }

    public String action_save() {
        try {
            if ((selectedEventosArticulos == null) && (entity == null)) {
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
            entity = new EventosArticulos();

            Long codigoEveArt = FacesUtils.checkLong(txtCodigoEveArt);

            entity.setCodigoEveArt(codigoEveArt);
            entity.setFavorito(FacesUtils.checkString(txtFavorito));
            entity.setFechaFavorito(FacesUtils.checkDate(txtFechaFavorito));
            entity.setFechaMeGusta(FacesUtils.checkDate(txtFechaMeGusta));
            entity.setMeGusta(FacesUtils.checkString(txtMeGusta));
            entity.setArticulos((FacesUtils.checkLong(txtCodigoArti_Articulos) != null)
                ? businessDelegatorView.getArticulos(FacesUtils.checkLong(
                        txtCodigoArti_Articulos)) : null);
            entity.setUsuarios((FacesUtils.checkLong(txtCodigoUsua_Usuarios) != null)
                ? businessDelegatorView.getUsuarios(FacesUtils.checkLong(
                        txtCodigoUsua_Usuarios)) : null);
            businessDelegatorView.saveEventosArticulos(entity);
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
                Long codigoEveArt = new Long(selectedEventosArticulos.getCodigoEveArt());
                entity = businessDelegatorView.getEventosArticulos(codigoEveArt);
            }

            entity.setFavorito(FacesUtils.checkString(txtFavorito));
            entity.setFechaFavorito(FacesUtils.checkDate(txtFechaFavorito));
            entity.setFechaMeGusta(FacesUtils.checkDate(txtFechaMeGusta));
            entity.setMeGusta(FacesUtils.checkString(txtMeGusta));
            entity.setArticulos((FacesUtils.checkLong(txtCodigoArti_Articulos) != null)
                ? businessDelegatorView.getArticulos(FacesUtils.checkLong(
                        txtCodigoArti_Articulos)) : null);
            entity.setUsuarios((FacesUtils.checkLong(txtCodigoUsua_Usuarios) != null)
                ? businessDelegatorView.getUsuarios(FacesUtils.checkLong(
                        txtCodigoUsua_Usuarios)) : null);
            businessDelegatorView.updateEventosArticulos(entity);
            FacesUtils.addInfoMessage(ZMessManager.ENTITY_SUCCESFULLYMODIFIED);
        } catch (Exception e) {
            data = null;
            FacesUtils.addErrorMessage(e.getMessage());
        }

        return "";
    }

    public String action_delete_datatable(ActionEvent evt) {
        try {
            selectedEventosArticulos = (EventosArticulosDTO) (evt.getComponent()
                                                                 .getAttributes()
                                                                 .get("selectedEventosArticulos"));

            Long codigoEveArt = new Long(selectedEventosArticulos.getCodigoEveArt());
            entity = businessDelegatorView.getEventosArticulos(codigoEveArt);
            action_delete();
        } catch (Exception e) {
            FacesUtils.addErrorMessage(e.getMessage());
        }

        return "";
    }

    public String action_delete_master() {
        try {
            Long codigoEveArt = FacesUtils.checkLong(txtCodigoEveArt);
            entity = businessDelegatorView.getEventosArticulos(codigoEveArt);
            action_delete();
        } catch (Exception e) {
            FacesUtils.addErrorMessage(e.getMessage());
        }

        return "";
    }

    public void action_delete() throws Exception {
        try {
            businessDelegatorView.deleteEventosArticulos(entity);
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
            selectedEventosArticulos = (EventosArticulosDTO) (evt.getComponent()
                                                                 .getAttributes()
                                                                 .get("selectedEventosArticulos"));

            Long codigoEveArt = new Long(selectedEventosArticulos.getCodigoEveArt());
            entity = businessDelegatorView.getEventosArticulos(codigoEveArt);
            businessDelegatorView.deleteEventosArticulos(entity);
            data.remove(selectedEventosArticulos);
            FacesUtils.addInfoMessage(ZMessManager.ENTITY_SUCCESFULLYDELETED);
            action_clear();
        } catch (Exception e) {
            FacesUtils.addErrorMessage(e.getMessage());
        }

        return "";
    }

    public String action_modifyWitDTO(Long codigoEveArt, String favorito,
        Date fechaFavorito, Date fechaMeGusta, String meGusta,
        Long codigoArti_Articulos, Long codigoUsua_Usuarios)
        throws Exception {
        try {
            entity.setFavorito(FacesUtils.checkString(favorito));
            entity.setFechaFavorito(FacesUtils.checkDate(fechaFavorito));
            entity.setFechaMeGusta(FacesUtils.checkDate(fechaMeGusta));
            entity.setMeGusta(FacesUtils.checkString(meGusta));
            businessDelegatorView.updateEventosArticulos(entity);
            FacesUtils.addInfoMessage(ZMessManager.ENTITY_SUCCESFULLYMODIFIED);
        } catch (Exception e) {
            //renderManager.getOnDemandRenderer("EventosArticulosView").requestRender();
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

    public InputText getTxtMeGusta() {
        return txtMeGusta;
    }

    public void setTxtMeGusta(InputText txtMeGusta) {
        this.txtMeGusta = txtMeGusta;
    }

    public InputText getTxtCodigoArti_Articulos() {
        return txtCodigoArti_Articulos;
    }

    public void setTxtCodigoArti_Articulos(InputText txtCodigoArti_Articulos) {
        this.txtCodigoArti_Articulos = txtCodigoArti_Articulos;
    }

    public InputText getTxtCodigoUsua_Usuarios() {
        return txtCodigoUsua_Usuarios;
    }

    public void setTxtCodigoUsua_Usuarios(InputText txtCodigoUsua_Usuarios) {
        this.txtCodigoUsua_Usuarios = txtCodigoUsua_Usuarios;
    }

    public Calendar getTxtFechaFavorito() {
        return txtFechaFavorito;
    }

    public void setTxtFechaFavorito(Calendar txtFechaFavorito) {
        this.txtFechaFavorito = txtFechaFavorito;
    }

    public Calendar getTxtFechaMeGusta() {
        return txtFechaMeGusta;
    }

    public void setTxtFechaMeGusta(Calendar txtFechaMeGusta) {
        this.txtFechaMeGusta = txtFechaMeGusta;
    }

    public InputText getTxtCodigoEveArt() {
        return txtCodigoEveArt;
    }

    public void setTxtCodigoEveArt(InputText txtCodigoEveArt) {
        this.txtCodigoEveArt = txtCodigoEveArt;
    }

    public List<EventosArticulosDTO> getData() {
        try {
            if (data == null) {
                data = businessDelegatorView.getDataEventosArticulos();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    public void setData(List<EventosArticulosDTO> eventosArticulosDTO) {
        this.data = eventosArticulosDTO;
    }

    public EventosArticulosDTO getSelectedEventosArticulos() {
        return selectedEventosArticulos;
    }

    public void setSelectedEventosArticulos(
        EventosArticulosDTO eventosArticulos) {
        this.selectedEventosArticulos = eventosArticulos;
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
