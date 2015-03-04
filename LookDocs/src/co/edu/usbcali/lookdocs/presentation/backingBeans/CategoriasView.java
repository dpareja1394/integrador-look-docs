package co.edu.usbcali.lookdocs.presentation.backingBeans;

import co.edu.usbcali.lookdocs.exceptions.*;
import co.edu.usbcali.lookdocs.model.*;
import co.edu.usbcali.lookdocs.model.dto.CategoriasDTO;
import co.edu.usbcali.lookdocs.presentation.businessDelegate.*;
import co.edu.usbcali.lookdocs.utilities.*;

import org.primefaces.component.calendar.*;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

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
import javax.swing.tree.DefaultTreeModel;


/**
 * @author Zathura Code Generator http://code.google.com/p/zathura
 * www.zathuracode.org
 *
 */
@ManagedBean
@ViewScoped
public class CategoriasView implements Serializable {
    private static final long serialVersionUID = 1L;
    private InputText txtEstadoRegistro;
    private InputText txtNombre;
    private InputText txtNombreModify;
    private InputText txtUsuCrea;
    private InputText txtUsuModifica;
    private InputText txtCodigoCate;
    private Calendar txtFechaCreacion;
    private Calendar txtFechaModifcacion;
    private CommandButton btnSave;
    private CommandButton btnModify;
    private CommandButton btnDelete;
    private CommandButton btnClear;
    private List<CategoriasDTO> data;
    private CategoriasDTO selectedCategorias;
    private Categorias entity;
    private boolean showDialog;
    @ManagedProperty(value = "#{BusinessDelegatorView}")
    private IBusinessDelegatorView businessDelegatorView;
    private TreeNode raiz;
    private TreeNode selectedNode;
    private String message = "";
    List<Categorias> categoriasRaices;
    private String estadoRegistro;
    private String estadoReg;
    

	

	public CategoriasView() {
        super();
    }
    
    public void consultarArbol(){
    	try {
    		categoriasRaices = getListaNodos();
			this.raiz = new DefaultTreeNode("Raiz",null);
			
			adicionarNodos(categoriasRaices, this.raiz);
			
		} catch (Exception e) {
			FacesUtils.addErrorMessage(e.getMessage());
		}
    }
    
    private void adicionarNodos(List<Categorias> categorias, TreeNode padre){
    	for (Categorias categoria : categorias) {
			TreeNode no = new DefaultTreeNode(categoria, padre);
		}
    }

    public void rowEventListener(RowEditEvent e) {
        try {
            CategoriasDTO categoriasDTO = (CategoriasDTO) e.getObject();

            if (txtEstadoRegistro == null) {
                txtEstadoRegistro = new InputText();
            }

            txtEstadoRegistro.setValue(categoriasDTO.getEstadoRegistro());

            if (txtNombre == null) {
                txtNombre = new InputText();
            }

            txtNombre.setValue(categoriasDTO.getNombre());

            if (txtUsuCrea == null) {
                txtUsuCrea = new InputText();
            }

            txtUsuCrea.setValue(categoriasDTO.getUsuCrea());

            if (txtUsuModifica == null) {
                txtUsuModifica = new InputText();
            }

            txtUsuModifica.setValue(categoriasDTO.getUsuModifica());

            if (txtCodigoCate == null) {
                txtCodigoCate = new InputText();
            }

            txtCodigoCate.setValue(categoriasDTO.getCodigoCate());

            if (txtFechaCreacion == null) {
                txtFechaCreacion = new Calendar();
            }

            txtFechaCreacion.setValue(categoriasDTO.getFechaCreacion());

            if (txtFechaModifcacion == null) {
                txtFechaModifcacion = new Calendar();
            }

            txtFechaModifcacion.setValue(categoriasDTO.getFechaModifcacion());

            Long codigoCate = FacesUtils.checkLong(txtCodigoCate);
            entity = businessDelegatorView.getCategorias(codigoCate);

            action_modify();
        } catch (Exception ex) {
        }
    }

    public String action_new() {
        action_clear();
        selectedCategorias = null;
        setShowDialog(true);

        return "";
    }

    public String action_clear() {
        entity = null;
        selectedCategorias = null;

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

        if (txtFechaCreacion != null) {
            txtFechaCreacion.setValue(null);
            txtFechaCreacion.setDisabled(true);
        }

        if (txtFechaModifcacion != null) {
            txtFechaModifcacion.setValue(null);
            txtFechaModifcacion.setDisabled(true);
        }

        if (txtCodigoCate != null) {
            txtCodigoCate.setValue(null);
            txtCodigoCate.setDisabled(false);
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
            Long codigoCate = FacesUtils.checkLong(txtCodigoCate);
            entity = (codigoCate != null)
                ? businessDelegatorView.getCategorias(codigoCate) : null;
        } catch (Exception e) {
            entity = null;
        }

        if (entity == null) {
            txtEstadoRegistro.setDisabled(false);
            txtNombre.setDisabled(false);
            txtUsuCrea.setDisabled(false);
            txtUsuModifica.setDisabled(false);
            txtFechaCreacion.setDisabled(false);
            txtFechaModifcacion.setDisabled(false);
            txtCodigoCate.setDisabled(false);
            btnSave.setDisabled(false);
        } else {
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
            txtCodigoCate.setValue(entity.getCodigoCate());
            txtCodigoCate.setDisabled(true);
            btnSave.setDisabled(false);

            if (btnDelete != null) {
                btnDelete.setDisabled(false);
            }
        }
    }

    public String action_edit(ActionEvent evt) {
        selectedCategorias = (CategoriasDTO) (evt.getComponent().getAttributes()
                                                 .get("selectedCategorias"));
        txtEstadoRegistro.setValue(selectedCategorias.getEstadoRegistro());
        txtEstadoRegistro.setDisabled(false);
        txtFechaCreacion.setValue(selectedCategorias.getFechaCreacion());
        txtFechaCreacion.setDisabled(false);
        txtFechaModifcacion.setValue(selectedCategorias.getFechaModifcacion());
        txtFechaModifcacion.setDisabled(false);
        txtNombre.setValue(selectedCategorias.getNombre());
        txtNombre.setDisabled(false);
        txtUsuCrea.setValue(selectedCategorias.getUsuCrea());
        txtUsuCrea.setDisabled(false);
        txtUsuModifica.setValue(selectedCategorias.getUsuModifica());
        txtUsuModifica.setDisabled(false);
        txtCodigoCate.setValue(selectedCategorias.getCodigoCate());
        txtCodigoCate.setDisabled(true);
        btnSave.setDisabled(false);
        setShowDialog(true);

        return "";
    }

    public String action_save() {
        try {
            if ((selectedCategorias == null) && (entity == null)) {
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
            entity = new Categorias();
            entity.setEstadoRegistro(
            		(estadoReg.equals("1")) ? "a" : 
            			(estadoReg.equals("2") ? "i" : null) );
            entity.setUsuCrea("Admin");
            entity.setNombre(FacesUtils.checkString(txtNombre));
            //entity.setUsuCrea(FacesUtils.getfromSession(name));
            businessDelegatorView.saveCategorias(entity);
            txtNombre.setValue("");
            categoriasRaices=null;
            consultarArbol();
            FacesUtils.addInfoMessage(ZMessManager.ENTITY_SUCCESFULLYSAVED);
            
            //action_clear();
        } catch (Exception e) {
            entity = null;
            FacesUtils.addErrorMessage(e.getMessage());
        }

        return "";
    }

    public String action_modify() {
        try {
        	Categorias entity = (Categorias) selectedNode.getData();
            entity.setEstadoRegistro(
            		(estadoRegistro.equals("1")) ? "a" : 
            			(estadoRegistro.equals("2") ? "i" : null) );
            entity.setUsuModifica("Admin");
            entity.setNombre(txtNombreModify.getValue().toString());
            //entity.setUsuCrea(FacesUtils.getfromSession(name));
            businessDelegatorView.updateCategorias(entity);
            txtNombreModify.setValue("");
            categoriasRaices=null;
            consultarArbol();
            FacesUtils.addInfoMessage(ZMessManager.ENTITY_SUCCESFULLYSAVED);
            
            //action_clear();
        } catch (Exception e) {
            entity = null;
            FacesUtils.addErrorMessage(e.getMessage());
        }

        return "";
    }

    public String action_delete_datatable(ActionEvent evt) {
        try {
            selectedCategorias = (CategoriasDTO) (evt.getComponent()
                                                     .getAttributes()
                                                     .get("selectedCategorias"));

            Long codigoCate = new Long(selectedCategorias.getCodigoCate());
            entity = businessDelegatorView.getCategorias(codigoCate);
            action_delete();
        } catch (Exception e) {
            FacesUtils.addErrorMessage(e.getMessage());
        }

        return "";
    }

    public String action_delete_master() {
        try {
            Long codigoCate = FacesUtils.checkLong(txtCodigoCate);
            entity = businessDelegatorView.getCategorias(codigoCate);
            action_delete();
        } catch (Exception e) {
            FacesUtils.addErrorMessage(e.getMessage());
        }

        return "";
    }
    
    public void deleteMessage(){
    	Categorias selected = (Categorias) selectedNode.getData();
    	setMessage("Esta seguro de eliminar la categoria: "+selected.getNombre());
    }

    public void action_delete() throws Exception {
        try {

        	
            businessDelegatorView.deleteCategoriasbyNode(selectedNode);
            FacesUtils.addInfoMessage(ZMessManager.ENTITY_SUCCESFULLYDELETED);
            categoriasRaices = null;
            consultarArbol();
            //action_clear();
            data = null;
        } catch (Exception e) {
        	FacesUtils.addErrorMessage(e.getMessage());
        }
    }

    public String action_closeDialog() {
        setShowDialog(false);
        action_clear();

        return "";
    }

    public String actionDeleteDataTableEditable(ActionEvent evt) {
        try {
            selectedCategorias = (CategoriasDTO) (evt.getComponent()
                                                     .getAttributes()
                                                     .get("selectedCategorias"));

            Long codigoCate = new Long(selectedCategorias.getCodigoCate());
            entity = businessDelegatorView.getCategorias(codigoCate);
            businessDelegatorView.deleteCategorias(entity);
            data.remove(selectedCategorias);
            FacesUtils.addInfoMessage(ZMessManager.ENTITY_SUCCESFULLYDELETED);
            action_clear();
        } catch (Exception e) {
            FacesUtils.addErrorMessage(e.getMessage());
        }

        return "";
    }

    public String action_modifyWitDTO(Long codigoCate, String estadoRegistro,
        Date fechaCreacion, Date fechaModifcacion, String nombre,
        String usuCrea, String usuModifica) throws Exception {
        try {
            entity.setEstadoRegistro(FacesUtils.checkString(estadoRegistro));
            entity.setFechaCreacion(FacesUtils.checkDate(fechaCreacion));
            entity.setFechaModifcacion(FacesUtils.checkDate(fechaModifcacion));
            entity.setNombre(FacesUtils.checkString(nombre));
            entity.setUsuCrea(FacesUtils.checkString(usuCrea));
            entity.setUsuModifica(FacesUtils.checkString(usuModifica));
            businessDelegatorView.updateCategorias(entity);
            FacesUtils.addInfoMessage(ZMessManager.ENTITY_SUCCESFULLYMODIFIED);
        } catch (Exception e) {
            //renderManager.getOnDemandRenderer("CategoriasView").requestRender();
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

    public InputText getTxtCodigoCate() {
        return txtCodigoCate;
    }

    public void setTxtCodigoCate(InputText txtCodigoCate) {
        this.txtCodigoCate = txtCodigoCate;
    }

    public List<CategoriasDTO> getData() {
        try {
            if (data == null) {
                data = businessDelegatorView.getDataCategorias();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    public void setData(List<CategoriasDTO> categoriasDTO) {
        this.data = categoriasDTO;
    }

    public CategoriasDTO getSelectedCategorias() {
        return selectedCategorias;
    }

    public void setSelectedCategorias(CategoriasDTO categorias) {
        this.selectedCategorias = categorias;
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
    
    public TreeNode getRaiz() {
		return raiz;
	}

	/**
	 * @return the selectedNode
	 */
	public TreeNode getSelectedNode() {
		return selectedNode;
	}
	
	public List<Categorias> getListaNodos(){
		
		if(categoriasRaices == null){
			try {
				
				return businessDelegatorView.getCategorias();
			} catch (Exception e) {
			}
		}
		
		return categoriasRaices;
	}

	/**
	 * @param selectedNode the selectedNode to set
	 */
	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the estadoRegistro
	 */
	public String getEstadoRegistro() {
		return estadoRegistro;
	}

	
	public void setEstadoRegistro(String estadoRegistro) {
		this.estadoRegistro = estadoRegistro;
	}

	
	public InputText getTxtNombreModify() {
		return txtNombreModify;
	}

	public void setTxtNombreModify(InputText txtNombreModify) {
		this.txtNombreModify = txtNombreModify;
	}

	/**
	 * @return the estadoReg
	 */
	public String getEstadoReg() {
		return estadoReg;
	}

	/**
	 * @param estadoReg the estadoReg to set
	 */
	public void setEstadoReg(String estadoReg) {
		this.estadoReg = estadoReg;
	}

	
}
