package co.edu.usbcali.lookdocs.presentation.backingBeans;

import co.edu.usbcali.lookdocs.exceptions.*;
import co.edu.usbcali.lookdocs.model.*;
import co.edu.usbcali.lookdocs.model.dto.ColeccionesDTO;
import co.edu.usbcali.lookdocs.presentation.businessDelegate.*;
import co.edu.usbcali.lookdocs.utilities.*;

import org.primefaces.component.calendar.*;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.inputtext.InputText;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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
    private InputText txtModificarNombre;
    private CommandButton btnSave;
    private CommandButton btnModify;
    private CommandButton btnDelete;
    private CommandButton btnClear;
    private List<ColeccionesDTO> data;
    private ColeccionesDTO selectedColecciones;
    private Colecciones entity;
    private boolean showDialog;
    private TreeNode raizArbol;
    private TreeNode seleccionarNodo;
    List<Colecciones> coleccionRaices;
    List<Rss> coleccionHijos;
    private Usuarios usuarioSecurity;
    private String nameToSet;
    private Usuarios usuarioAdmin;
	
	private SelectOneMenu somColeccionesLector;
	private List<SelectItem> lasColeccionesItems;
	private List<SelectItem> lasColeccionesItemsAdmin;
    
    @ManagedProperty(value = "#{BusinessDelegatorView}")
    private IBusinessDelegatorView businessDelegatorView;

    public ColeccionesView() {
        super();
    }
    
    public void defaultModificar() {
		try {
			Colecciones modificar = (Colecciones) seleccionarNodo.getData();
			nameToSet = modificar.getNombre();
		} catch (Exception e) {
			nameToSet = "";
		}
		txtModificarNombre.setValue(nameToSet);

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
    
    public void consultarArbolColecciones(){
    	try {
    		retornarUsuario();
    		coleccionRaices =  getListaNodos();
    		raizArbol = new DefaultTreeNode("Raiz", null);
    		lasColeccionesItems = null;
    		agregarNodos(coleccionRaices, raizArbol);
		} catch (Exception e) {
			FacesUtils.addErrorMessage(e.getMessage());
		}
    }
    
    private void agregarNodos(List<Colecciones> colecciones, TreeNode padre){
//    	for (Colecciones coleccion : colecciones) {
//    		TreeNode no = new DefaultTreeNode(coleccion, padre);
//		}
    	
    	try {
			for(Colecciones coleccion : colecciones){
				TreeNode padres = new DefaultTreeNode(coleccion.getNombre(), padre);
				coleccionHijos = businessDelegatorView.getRssDadoIdColeccion(coleccion.getCodigoCole());
				for (Rss rss : coleccionHijos) {
					TreeNode hijos = new DefaultTreeNode(rss.getUrl(), padres);
				}	
			}
		} catch (Exception e) {
			FacesUtils.addErrorMessage(e.getMessage());
		}
    	
    }
    @PostConstruct
    public void retornarUsuario(){
//    	HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
//        usuario = (Usuarios) httpSession.getAttribute("usuarioLector");
    	ServletRequestAttributes servletRequestAttributes=(ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
    	HttpSession session = servletRequestAttributes.getRequest().getSession();
    	usuarioSecurity = (Usuarios) session.getAttribute("usuarioLector");
    }
    
    public List<Colecciones> getListaNodos(){   	
    	
        
    	if(coleccionRaices == null){
    		try {
    			return coleccionRaices = businessDelegatorView.consultarColeccionPorUsuario(usuarioSecurity);
			} catch (Exception e) {
				FacesUtils.addErrorMessage(e.getMessage());
			}
    	}
    	return coleccionRaices;
    	
    }
    
    public void crearColeccionAdmin() {
		try {
			Colecciones entity = new Colecciones();

			HttpSession httpSession = (HttpSession) FacesContext
					.getCurrentInstance().getExternalContext().getSession(true);
			Usuarios usuarios = (Usuarios) httpSession
					.getAttribute("usuarioAdministrador");

			entity.setNombre(txtNombre.getValue().toString());
			entity.setUsuarios(usuarios);

			businessDelegatorView.saveColecciones(entity);
			coleccionRaices = null;
			entity = null;
			consultarArbolColeccionesAdmin();
			FacesUtils.addInfoMessage(ZMessManager.ENTITY_SUCCESFULLYSAVED);

			txtNombre.setValue(null);
			txtNombre.setValue("");
		} catch (Exception e) {
			entity = null;
			FacesUtils.addErrorMessage(e.getMessage());
		}

	}
    
    public List<Rss> losRssPorColeccion(Long codigoColeccion){
		try {
			return businessDelegatorView.getRssDadoIdColeccion(codigoColeccion);
		} catch (Exception e) {
			FacesUtils.addErrorMessage(e.getMessage());
		}
		return null;
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
    
    public void eliminarColeccionAdmin() throws Exception {
		try {

			Colecciones coleccionSeleccionada = (Colecciones) seleccionarNodo
					.getData();
			businessDelegatorView.deleteColecciones(coleccionSeleccionada);
			FacesUtils.addInfoMessage(ZMessManager.ENTITY_SUCCESFULLYDELETED);
			// action_clear();
			coleccionRaices = null;
			consultarArbolColeccionesAdmin();
			data = null;
		} catch (Exception e) {
			throw e;
		}
	}
    
    public void modificarColeccionAdmin() {
		try {
			Colecciones coleccionSeleccionada = (Colecciones) seleccionarNodo
					.getData();			

			if (entity == null) {
				Long codigoCole = coleccionSeleccionada.getCodigoCole();				
				entity = businessDelegatorView.getColecciones(codigoCole);
			}

			
			coleccionSeleccionada.setNombre(txtModificarNombre.getValue()
					.toString());
			
			businessDelegatorView.updateColecciones(coleccionSeleccionada);
			coleccionRaices = null;
			entity = null;
			consultarArbolColeccionesAdmin();
			FacesUtils.addInfoMessage(ZMessManager.ENTITY_SUCCESFULLYMODIFIED);
			txtModificarNombre.setValue(null);
			txtModificarNombre.setValue("");
		} catch (Exception e) {
			data = null;
			FacesUtils.addErrorMessage(e.getMessage());
		}
		
	}

    public String action_create() {
        try {
            Colecciones entity = new Colecciones();

            //Long codigoCole = FacesUtils.checkLong(txtCodigoCole);
            //entity.setCodigoCole(codigoCole);
            
            HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            Usuarios usuarios = (Usuarios) httpSession.getAttribute("usuarioLector");
            
                    
            
            //entity.setNombre(FacesUtils.checkString(txtNombre));
            entity.setNombre(txtNombre.getValue().toString());
            entity.setUsuarios(usuarios);
            /*entity.setUsuarios((FacesUtils.checkLong(txtCodigoUsua_Usuarios) != null)
                ? businessDelegatorView.getUsuarios(FacesUtils.checkLong(
                        txtCodigoUsua_Usuarios)) : null);*/
            
            businessDelegatorView.saveColecciones(entity);
            coleccionRaices = null;
            entity = null;
            consultarArbolColecciones();
            FacesUtils.addInfoMessage(ZMessManager.ENTITY_SUCCESFULLYSAVED);
            //action_clear();
            txtNombre.setValue(null);
            txtNombre.setValue("");
        } catch (Exception e) {
            entity = null;
            FacesUtils.addErrorMessage(e.getMessage());
        }

        return "";
    }
    
    public void consultarArbolColeccionesAdmin() {
		try {
			retornarUsuarioAdmin();
			coleccionRaices = getListaNodosAdmin();
			raizArbol = new DefaultTreeNode("Raiz", null);
			lasColeccionesItems = null;
			agregarNodosAdmin(coleccionRaices, raizArbol);
		} catch (Exception e) {
			FacesUtils.addErrorMessage(e.getMessage());
		}
	}
    
    private void agregarNodosAdmin(List<Colecciones> colecciones, TreeNode padre) {
		for (Colecciones coleccion : colecciones) {
			TreeNode no = new DefaultTreeNode(coleccion, padre);
		}
	}

    public String action_modify() {
        try {
        	Colecciones coleccionSeleccionada = (Colecciones) seleccionarNodo.getData();
        	//HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            //Usuarios usuarios = (Usuarios) httpSession.getAttribute("usuarioLector");
        	
            if (entity == null) {
            	Long codigoCole = coleccionSeleccionada.getCodigoCole();
                //Long codigoCole = new Long(selectedColecciones.getCodigoCole());
                entity = businessDelegatorView.getColecciones(codigoCole);
            }
            
            
            //txtModificarNombre.setValue(coleccionSeleccionada.getNombre());
            /*Usuarios usuarios = new Usuarios();
            usuarios.setCodigoUsua(3L);
            usuarios.setNombre("Benito Camelas");
            usuarios.setClave("benito");
            usuarios.setEmail("jaime@gmail.com");
            usuarios.setFechaCreacion(new Date());
            usuarios.setUsuCrea("Jaimito");
            usuarios.setEstadoRegistro("A");*/

            //entity.setNombre(txtModificarNombre.getValue().toString());
            coleccionSeleccionada.setNombre(txtModificarNombre.getValue().toString());
            //entity.setUsuarios(usuarios);
            //entity.setNombre(FacesUtils.checkString(txtNombre));
            /*entity.setUsuarios((FacesUtils.checkLong(txtCodigoUsua_Usuarios) != null)
                ? businessDelegatorView.getUsuarios(FacesUtils.checkLong(
                        txtCodigoUsua_Usuarios)) : null);*/
            businessDelegatorView.updateColecciones(coleccionSeleccionada);
            coleccionRaices = null;
            entity = null;
            consultarArbolColecciones();
            FacesUtils.addInfoMessage(ZMessManager.ENTITY_SUCCESFULLYMODIFIED);
            txtModificarNombre.setValue(null);
            txtModificarNombre.setValue("");
        } catch (Exception e) {
            data = null;
            FacesUtils.addErrorMessage(e.getMessage());
        }

        return "";
    }
    
    @PostConstruct
	public void retornarUsuarioAdmin() {
		HttpSession httpSession = (HttpSession)
		FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		usuarioAdmin = (Usuarios) httpSession.getAttribute("usuarioAdministrador");
		/*ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder
				.currentRequestAttributes();
		HttpSession session = servletRequestAttributes.getRequest()
				.getSession();
		usuarioSecurity = (Usuarios) session.getAttribute("usuarioAdministrador");*/
	}
    
    public List<Colecciones> getListaNodosAdmin() {

		if (coleccionRaices == null) {
			try {
				return businessDelegatorView
						.consultarColeccionPorUsuario(usuarioAdmin);
			} catch (Exception e) {
				FacesUtils.addErrorMessage(e.getMessage());
			}
		}
		return coleccionRaices;

	}
    
    public void editarNombre(){
    	Colecciones coleccionSeleccionada = (Colecciones) seleccionarNodo.getData();
    	txtModificarNombre.setValue(coleccionSeleccionada.getNombre());
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
        	
        	Colecciones coleccionSeleccionada = (Colecciones) seleccionarNodo.getData();
            businessDelegatorView.deleteColecciones(coleccionSeleccionada);
            FacesUtils.addInfoMessage(ZMessManager.ENTITY_SUCCESFULLYDELETED);
            //action_clear();
            coleccionRaices=null;
            consultarArbolColecciones();
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

	public TreeNode getRaizArbol() {
		return raizArbol;
	}

	public void setRaizArbol(TreeNode raizArbol) {
		this.raizArbol = raizArbol;
	}

	public TreeNode getSeleccionarNodo() {
		return seleccionarNodo;
	}

	public void setSeleccionarNodo(TreeNode seleccionarNodo) {
		this.seleccionarNodo = seleccionarNodo;
	}

	public List<Colecciones> getColeccionRaices() {
		return coleccionRaices;
	}

	public void setColeccionRaices(List<Colecciones> coleccionRaices) {
		this.coleccionRaices = coleccionRaices;
	}

	public InputText getTxtModificarNombre() {
		return txtModificarNombre;
	}

	public void setTxtModificarNombre(InputText txtModificarNombre) {
		this.txtModificarNombre = txtModificarNombre;
	}
	
	public void setLasColeccionesItems(List<SelectItem> lasColeccionesItems) {
		this.lasColeccionesItems = lasColeccionesItems;
	}
	
	public void setLasColeccionesItemsAdmin(
			List<SelectItem> lasColeccionesItemsAdmin) {
		this.lasColeccionesItemsAdmin = lasColeccionesItemsAdmin;
	}

	public List<SelectItem> getLasColeccionesItemsAdmin() {
		try {
			if (lasColeccionesItemsAdmin == null) {
				lasColeccionesItemsAdmin = new ArrayList<SelectItem>();
				List<Colecciones> lasColecciones = businessDelegatorView
						.consultarColeccionPorUsuario(usuarioAdmin);
				for (Colecciones colecciones : lasColecciones) {
					SelectItem selectItem = new SelectItem(
							colecciones.getCodigoCole(),
							colecciones.getNombre());
					lasColeccionesItemsAdmin.add(selectItem);
				}
			}
		} catch (Exception e) {
		}
		return lasColeccionesItemsAdmin;
	}

	
	public SelectOneMenu getSomColeccionesLector() {
		return somColeccionesLector;
	}

	public void setSomColeccionesLector(SelectOneMenu somColeccionesLector) {
		this.somColeccionesLector = somColeccionesLector;
	}

	public List<SelectItem> getLasColeccionesItems() {
		try {
			if (lasColeccionesItems == null) {
				lasColeccionesItems = new ArrayList<SelectItem>();
				List<Colecciones> lasColecciones = businessDelegatorView
						.consultarColeccionPorUsuario(usuarioSecurity);
				for (Colecciones colecciones : lasColecciones) {
					SelectItem selectItem = new SelectItem(
							colecciones.getCodigoCole(),
							colecciones.getNombre());
					lasColeccionesItems.add(selectItem);
				}
			}
		} catch (Exception e) {
		}
		return lasColeccionesItems;
	}

	/**
	 * @return the nameToSet
	 */
	public String getNameToSet() {
		return nameToSet;
	}

	/**
	 * @param nameToSet the nameToSet to set
	 */
	public void setNameToSet(String nameToSet) {
		this.nameToSet = nameToSet;
	}
	
	
    
    
}
