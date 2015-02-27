package co.edu.usbcali.lookdocs.presentation.backingBeans;

import co.edu.usbcali.lookdocs.exceptions.*;
import co.edu.usbcali.lookdocs.model.*;
import co.edu.usbcali.lookdocs.model.dto.ArticulosDTO;
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
public class ArticulosView implements Serializable {
    private static final long serialVersionUID = 1L;
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
    private CommandButton btnSave;
    private CommandButton btnModify;
    private CommandButton btnDelete;
    private CommandButton btnClear;
    private List<ArticulosDTO> data;
    private ArticulosDTO selectedArticulos;
    private Articulos entity;
    private boolean showDialog;
    @ManagedProperty(value = "#{BusinessDelegatorView}")
    private IBusinessDelegatorView businessDelegatorView;

    public ArticulosView() {
        super();
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

            txtCodigoUsua_Usuarios.setValue(articulosDTO.getCodigoUsua_Usuarios());

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
        action_clear();
        selectedArticulos = null;
        setShowDialog(true);

        return "";
    }

    public String action_clear() {
        entity = null;
        selectedArticulos = null;

        if (txtAutor != null) {
            txtAutor.setValue(null);
            txtAutor.setDisabled(true);
        }

        if (txtDescripcion != null) {
            txtDescripcion.setValue(null);
            txtDescripcion.setDisabled(true);
        }

        if (txtEstadoRegistro != null) {
            txtEstadoRegistro.setValue(null);
            txtEstadoRegistro.setDisabled(true);
        }

        if (txtNombre != null) {
            txtNombre.setValue(null);
            txtNombre.setDisabled(true);
        }

        if (txtUsuCrea != null) {
            txtUsuCrea.setValue(null);
            txtUsuCrea.setDisabled(true);
        }

        if (txtUsuModifica != null) {
            txtUsuModifica.setValue(null);
            txtUsuModifica.setDisabled(true);
        }

        if (txtCodigoUsua_Usuarios != null) {
            txtCodigoUsua_Usuarios.setValue(null);
            txtCodigoUsua_Usuarios.setDisabled(true);
        }

        if (txtFechaCreacion != null) {
            txtFechaCreacion.setValue(null);
            txtFechaCreacion.setDisabled(true);
        }

        if (txtFechaModifcacion != null) {
            txtFechaModifcacion.setValue(null);
            txtFechaModifcacion.setDisabled(true);
        }

        if (txtCodigoArti != null) {
            txtCodigoArti.setValue(null);
            txtCodigoArti.setDisabled(false);
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
            Long codigoArti = FacesUtils.checkLong(txtCodigoArti);
            entity = (codigoArti != null)
                ? businessDelegatorView.getArticulos(codigoArti) : null;
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
            txtCodigoUsua_Usuarios.setValue(entity.getUsuarios().getCodigoUsua());
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
        txtCodigoUsua_Usuarios.setValue(selectedArticulos.getCodigoUsua_Usuarios());
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

    public String action_create() {
        try {
            entity = new Articulos();

            Long codigoArti = FacesUtils.checkLong(txtCodigoArti);

            entity.setAutor(FacesUtils.checkString(txtAutor));
            entity.setCodigoArti(codigoArti);
            entity.setDescripcion(FacesUtils.checkString(txtDescripcion));
            entity.setEstadoRegistro(FacesUtils.checkString(txtEstadoRegistro));
            entity.setFechaCreacion(FacesUtils.checkDate(txtFechaCreacion));
            entity.setFechaModifcacion(FacesUtils.checkDate(txtFechaModifcacion));
            entity.setNombre(FacesUtils.checkString(txtNombre));
            entity.setUsuCrea(FacesUtils.checkString(txtUsuCrea));
            entity.setUsuModifica(FacesUtils.checkString(txtUsuModifica));
            entity.setUsuarios((FacesUtils.checkLong(txtCodigoUsua_Usuarios) != null)
                ? businessDelegatorView.getUsuarios(FacesUtils.checkLong(
                        txtCodigoUsua_Usuarios)) : null);
            businessDelegatorView.saveArticulos(entity);
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
                Long codigoArti = new Long(selectedArticulos.getCodigoArti());
                entity = businessDelegatorView.getArticulos(codigoArti);
            }

            entity.setAutor(FacesUtils.checkString(txtAutor));
            entity.setDescripcion(FacesUtils.checkString(txtDescripcion));
            entity.setEstadoRegistro(FacesUtils.checkString(txtEstadoRegistro));
            entity.setFechaCreacion(FacesUtils.checkDate(txtFechaCreacion));
            entity.setFechaModifcacion(FacesUtils.checkDate(txtFechaModifcacion));
            entity.setNombre(FacesUtils.checkString(txtNombre));
            entity.setUsuCrea(FacesUtils.checkString(txtUsuCrea));
            entity.setUsuModifica(FacesUtils.checkString(txtUsuModifica));
            entity.setUsuarios((FacesUtils.checkLong(txtCodigoUsua_Usuarios) != null)
                ? businessDelegatorView.getUsuarios(FacesUtils.checkLong(
                        txtCodigoUsua_Usuarios)) : null);
            businessDelegatorView.updateArticulos(entity);
            FacesUtils.addInfoMessage(ZMessManager.ENTITY_SUCCESFULLYMODIFIED);
        } catch (Exception e) {
            data = null;
            FacesUtils.addErrorMessage(e.getMessage());
        }

        return "";
    }

    public String action_delete_datatable(ActionEvent evt) {
        try {
            selectedArticulos = (ArticulosDTO) (evt.getComponent()
                                                   .getAttributes()
                                                   .get("selectedArticulos"));

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

    public void action_delete() throws Exception {
        try {
            businessDelegatorView.deleteArticulos(entity);
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
            selectedArticulos = (ArticulosDTO) (evt.getComponent()
                                                   .getAttributes()
                                                   .get("selectedArticulos"));

            Long codigoArti = new Long(selectedArticulos.getCodigoArti());
            entity = businessDelegatorView.getArticulos(codigoArti);
            businessDelegatorView.deleteArticulos(entity);
            data.remove(selectedArticulos);
            FacesUtils.addInfoMessage(ZMessManager.ENTITY_SUCCESFULLYDELETED);
            action_clear();
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
            //renderManager.getOnDemandRenderer("ArticulosView").requestRender();
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
}
