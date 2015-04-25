package co.edu.usbcali.lookdocs.presentation.backingBeans;

import co.edu.usbcali.lookdocs.exceptions.*;
import co.edu.usbcali.lookdocs.model.*;
import co.edu.usbcali.lookdocs.model.dto.CategoriasArticulosDTO;
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
public class CategoriasArticulosView implements Serializable {
    private static final long serialVersionUID = 1L;
    private InputText txtEstadoRegistro;
    private InputText txtUsuCrea;
    private InputText txtUsuModifica;
    private InputText txtCodigoArti_Articulos;
    private InputText txtCodigoCate_Categorias;
    private InputText txtCodigoCateArti;
    private Calendar txtFechaCreacion;
    private Calendar txtFechaModifcacion;
    private CommandButton btnSave;
    private CommandButton btnModify;
    private CommandButton btnDelete;
    private CommandButton btnClear;
    private List<CategoriasArticulosDTO> data;
    private CategoriasArticulosDTO selectedCategoriasArticulos;
    private CategoriasArticulos entity;
    private boolean showDialog;
    @ManagedProperty(value = "#{BusinessDelegatorView}")
    private IBusinessDelegatorView businessDelegatorView;

    public CategoriasArticulosView() {
        super();
    }

    public void rowEventListener(RowEditEvent e) {
        try {
            CategoriasArticulosDTO categoriasArticulosDTO = (CategoriasArticulosDTO) e.getObject();

            if (txtEstadoRegistro == null) {
                txtEstadoRegistro = new InputText();
            }

            txtEstadoRegistro.setValue(categoriasArticulosDTO.getEstadoRegistro());

            if (txtUsuCrea == null) {
                txtUsuCrea = new InputText();
            }

            txtUsuCrea.setValue(categoriasArticulosDTO.getUsuCrea());

            if (txtUsuModifica == null) {
                txtUsuModifica = new InputText();
            }

            txtUsuModifica.setValue(categoriasArticulosDTO.getUsuModifica());

            if (txtCodigoArti_Articulos == null) {
                txtCodigoArti_Articulos = new InputText();
            }

            txtCodigoArti_Articulos.setValue(categoriasArticulosDTO.getCodigoArti_Articulos());

            if (txtCodigoCate_Categorias == null) {
                txtCodigoCate_Categorias = new InputText();
            }

            txtCodigoCate_Categorias.setValue(categoriasArticulosDTO.getCodigoCate_Categorias());

            if (txtCodigoCateArti == null) {
                txtCodigoCateArti = new InputText();
            }

            txtCodigoCateArti.setValue(categoriasArticulosDTO.getCodigoCateArti());

            if (txtFechaCreacion == null) {
                txtFechaCreacion = new Calendar();
            }

            txtFechaCreacion.setValue(categoriasArticulosDTO.getFechaCreacion());

            if (txtFechaModifcacion == null) {
                txtFechaModifcacion = new Calendar();
            }

            txtFechaModifcacion.setValue(categoriasArticulosDTO.getFechaModifcacion());

            Long codigoCateArti = FacesUtils.checkLong(txtCodigoCateArti);
            entity = businessDelegatorView.getCategoriasArticulos(codigoCateArti);

            action_modify();
        } catch (Exception ex) {
        }
    }

    public String action_new() {
        action_clear();
        selectedCategoriasArticulos = null;
        setShowDialog(true);

        return "";
    }

    public String action_clear() {
        entity = null;
        selectedCategoriasArticulos = null;

        if (txtEstadoRegistro != null) {
            txtEstadoRegistro.setValue(null);
            txtEstadoRegistro.setDisabled(true);
        }

        if (txtUsuCrea != null) {
            txtUsuCrea.setValue(null);
            txtUsuCrea.setDisabled(true);
        }

        if (txtUsuModifica != null) {
            txtUsuModifica.setValue(null);
            txtUsuModifica.setDisabled(true);
        }

        if (txtCodigoArti_Articulos != null) {
            txtCodigoArti_Articulos.setValue(null);
            txtCodigoArti_Articulos.setDisabled(true);
        }

        if (txtCodigoCate_Categorias != null) {
            txtCodigoCate_Categorias.setValue(null);
            txtCodigoCate_Categorias.setDisabled(true);
        }

        if (txtFechaCreacion != null) {
            txtFechaCreacion.setValue(null);
            txtFechaCreacion.setDisabled(true);
        }

        if (txtFechaModifcacion != null) {
            txtFechaModifcacion.setValue(null);
            txtFechaModifcacion.setDisabled(true);
        }

        if (txtCodigoCateArti != null) {
            txtCodigoCateArti.setValue(null);
            txtCodigoCateArti.setDisabled(false);
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
            Long codigoCateArti = FacesUtils.checkLong(txtCodigoCateArti);
            entity = (codigoCateArti != null)
                ? businessDelegatorView.getCategoriasArticulos(codigoCateArti)
                : null;
        } catch (Exception e) {
            entity = null;
        }

        if (entity == null) {
            txtEstadoRegistro.setDisabled(false);
            txtUsuCrea.setDisabled(false);
            txtUsuModifica.setDisabled(false);
            txtCodigoArti_Articulos.setDisabled(false);
            txtCodigoCate_Categorias.setDisabled(false);
            txtFechaCreacion.setDisabled(false);
            txtFechaModifcacion.setDisabled(false);
            txtCodigoCateArti.setDisabled(false);
            btnSave.setDisabled(false);
        } else {
            txtEstadoRegistro.setValue(entity.getEstadoRegistro());
            txtEstadoRegistro.setDisabled(false);
            txtFechaCreacion.setValue(entity.getFechaCreacion());
            txtFechaCreacion.setDisabled(false);
            txtFechaModifcacion.setValue(entity.getFechaModifcacion());
            txtFechaModifcacion.setDisabled(false);
            txtUsuCrea.setValue(entity.getUsuCrea());
            txtUsuCrea.setDisabled(false);
            txtUsuModifica.setValue(entity.getUsuModifica());
            txtUsuModifica.setDisabled(false);
            txtCodigoArti_Articulos.setValue(entity.getArticulos()
                                                   .getCodigoArti());
            txtCodigoArti_Articulos.setDisabled(false);
            txtCodigoCate_Categorias.setValue(entity.getCategorias()
                                                    .getCodigoCate());
            txtCodigoCate_Categorias.setDisabled(false);
            txtCodigoCateArti.setValue(entity.getCodigoCateArti());
            txtCodigoCateArti.setDisabled(true);
            btnSave.setDisabled(false);

            if (btnDelete != null) {
                btnDelete.setDisabled(false);
            }
        }
    }

    public String action_edit(ActionEvent evt) {
        selectedCategoriasArticulos = (CategoriasArticulosDTO) (evt.getComponent()
                                                                   .getAttributes()
                                                                   .get("selectedCategoriasArticulos"));
        txtEstadoRegistro.setValue(selectedCategoriasArticulos.getEstadoRegistro());
        txtEstadoRegistro.setDisabled(false);
        txtFechaCreacion.setValue(selectedCategoriasArticulos.getFechaCreacion());
        txtFechaCreacion.setDisabled(false);
        txtFechaModifcacion.setValue(selectedCategoriasArticulos.getFechaModifcacion());
        txtFechaModifcacion.setDisabled(false);
        txtUsuCrea.setValue(selectedCategoriasArticulos.getUsuCrea());
        txtUsuCrea.setDisabled(false);
        txtUsuModifica.setValue(selectedCategoriasArticulos.getUsuModifica());
        txtUsuModifica.setDisabled(false);
        txtCodigoArti_Articulos.setValue(selectedCategoriasArticulos.getCodigoArti_Articulos());
        txtCodigoArti_Articulos.setDisabled(false);
        txtCodigoCate_Categorias.setValue(selectedCategoriasArticulos.getCodigoCate_Categorias());
        txtCodigoCate_Categorias.setDisabled(false);
        txtCodigoCateArti.setValue(selectedCategoriasArticulos.getCodigoCateArti());
        txtCodigoCateArti.setDisabled(true);
        btnSave.setDisabled(false);
        setShowDialog(true);

        return "";
    }

    public String action_save() {
        try {
            if ((selectedCategoriasArticulos == null) && (entity == null)) {
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
            entity = new CategoriasArticulos();

            Long codigoCateArti = FacesUtils.checkLong(txtCodigoCateArti);

            entity.setCodigoCateArti(codigoCateArti);
            entity.setEstadoRegistro(FacesUtils.checkString(txtEstadoRegistro));
            entity.setFechaCreacion(FacesUtils.checkDate(txtFechaCreacion));
            entity.setFechaModifcacion(FacesUtils.checkDate(txtFechaModifcacion));
            entity.setUsuCrea(FacesUtils.checkString(txtUsuCrea));
            entity.setUsuModifica(FacesUtils.checkString(txtUsuModifica));
            entity.setArticulos((FacesUtils.checkLong(txtCodigoArti_Articulos) != null)
                ? businessDelegatorView.getArticulos(FacesUtils.checkLong(
                        txtCodigoArti_Articulos)) : null);
            entity.setCategorias((FacesUtils.checkLong(txtCodigoCate_Categorias) != null)
                ? businessDelegatorView.getCategorias(FacesUtils.checkLong(
                        txtCodigoCate_Categorias)) : null);
            businessDelegatorView.saveCategoriasArticulos(entity);
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
                Long codigoCateArti = new Long(selectedCategoriasArticulos.getCodigoCateArti());
                entity = businessDelegatorView.getCategoriasArticulos(codigoCateArti);
            }

            entity.setEstadoRegistro(FacesUtils.checkString(txtEstadoRegistro));
            entity.setFechaCreacion(FacesUtils.checkDate(txtFechaCreacion));
            entity.setFechaModifcacion(FacesUtils.checkDate(txtFechaModifcacion));
            entity.setUsuCrea(FacesUtils.checkString(txtUsuCrea));
            entity.setUsuModifica(FacesUtils.checkString(txtUsuModifica));
            entity.setArticulos((FacesUtils.checkLong(txtCodigoArti_Articulos) != null)
                ? businessDelegatorView.getArticulos(FacesUtils.checkLong(
                        txtCodigoArti_Articulos)) : null);
            entity.setCategorias((FacesUtils.checkLong(txtCodigoCate_Categorias) != null)
                ? businessDelegatorView.getCategorias(FacesUtils.checkLong(
                        txtCodigoCate_Categorias)) : null);
            businessDelegatorView.updateCategoriasArticulos(entity);
            FacesUtils.addInfoMessage(ZMessManager.ENTITY_SUCCESFULLYMODIFIED);
        } catch (Exception e) {
            data = null;
            FacesUtils.addErrorMessage(e.getMessage());
        }

        return "";
    }

    public String action_delete_datatable(ActionEvent evt) {
        try {
            selectedCategoriasArticulos = (CategoriasArticulosDTO) (evt.getComponent()
                                                                       .getAttributes()
                                                                       .get("selectedCategoriasArticulos"));

            Long codigoCateArti = new Long(selectedCategoriasArticulos.getCodigoCateArti());
            entity = businessDelegatorView.getCategoriasArticulos(codigoCateArti);
            action_delete();
        } catch (Exception e) {
            FacesUtils.addErrorMessage(e.getMessage());
        }

        return "";
    }

    public String action_delete_master() {
        try {
            Long codigoCateArti = FacesUtils.checkLong(txtCodigoCateArti);
            entity = businessDelegatorView.getCategoriasArticulos(codigoCateArti);
            action_delete();
        } catch (Exception e) {
            FacesUtils.addErrorMessage(e.getMessage());
        }

        return "";
    }

    public void action_delete() throws Exception {
        try {
            businessDelegatorView.deleteCategoriasArticulos(entity);
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
            selectedCategoriasArticulos = (CategoriasArticulosDTO) (evt.getComponent()
                                                                       .getAttributes()
                                                                       .get("selectedCategoriasArticulos"));

            Long codigoCateArti = new Long(selectedCategoriasArticulos.getCodigoCateArti());
            entity = businessDelegatorView.getCategoriasArticulos(codigoCateArti);
            businessDelegatorView.deleteCategoriasArticulos(entity);
            data.remove(selectedCategoriasArticulos);
            FacesUtils.addInfoMessage(ZMessManager.ENTITY_SUCCESFULLYDELETED);
            action_clear();
        } catch (Exception e) {
            FacesUtils.addErrorMessage(e.getMessage());
        }

        return "";
    }

    public String action_modifyWitDTO(Long codigoCateArti,
        String estadoRegistro, Date fechaCreacion, Date fechaModifcacion,
        String usuCrea, String usuModifica, Long codigoArti_Articulos,
        Long codigoCate_Categorias) throws Exception {
        try {
            entity.setEstadoRegistro(FacesUtils.checkString(estadoRegistro));
            entity.setFechaCreacion(FacesUtils.checkDate(fechaCreacion));
            entity.setFechaModifcacion(FacesUtils.checkDate(fechaModifcacion));
            entity.setUsuCrea(FacesUtils.checkString(usuCrea));
            entity.setUsuModifica(FacesUtils.checkString(usuModifica));
            businessDelegatorView.updateCategoriasArticulos(entity);
            FacesUtils.addInfoMessage(ZMessManager.ENTITY_SUCCESFULLYMODIFIED);
        } catch (Exception e) {
            //renderManager.getOnDemandRenderer("CategoriasArticulosView").requestRender();
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

    public InputText getTxtCodigoArti_Articulos() {
        return txtCodigoArti_Articulos;
    }

    public void setTxtCodigoArti_Articulos(InputText txtCodigoArti_Articulos) {
        this.txtCodigoArti_Articulos = txtCodigoArti_Articulos;
    }

    public InputText getTxtCodigoCate_Categorias() {
        return txtCodigoCate_Categorias;
    }

    public void setTxtCodigoCate_Categorias(InputText txtCodigoCate_Categorias) {
        this.txtCodigoCate_Categorias = txtCodigoCate_Categorias;
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

    public InputText getTxtCodigoCateArti() {
        return txtCodigoCateArti;
    }

    public void setTxtCodigoCateArti(InputText txtCodigoCateArti) {
        this.txtCodigoCateArti = txtCodigoCateArti;
    }

    public List<CategoriasArticulosDTO> getData() {
        try {
            if (data == null) {
                data = businessDelegatorView.getDataCategoriasArticulos();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    public void setData(List<CategoriasArticulosDTO> categoriasArticulosDTO) {
        this.data = categoriasArticulosDTO;
    }

    public CategoriasArticulosDTO getSelectedCategoriasArticulos() {
        return selectedCategoriasArticulos;
    }

    public void setSelectedCategoriasArticulos(
        CategoriasArticulosDTO categoriasArticulos) {
        this.selectedCategoriasArticulos = categoriasArticulos;
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
