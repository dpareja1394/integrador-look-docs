package co.edu.usbcali.lookdocs.presentation.businessDelegate;

import co.edu.usbcali.lookdocs.model.Anexos;
import co.edu.usbcali.lookdocs.model.Articulos;
import co.edu.usbcali.lookdocs.model.Categorias;
import co.edu.usbcali.lookdocs.model.CategoriasArticulos;
import co.edu.usbcali.lookdocs.model.Colecciones;
import co.edu.usbcali.lookdocs.model.ColeccionesRss;
import co.edu.usbcali.lookdocs.model.Entradas;
import co.edu.usbcali.lookdocs.model.EventosArticulos;
import co.edu.usbcali.lookdocs.model.Roles;
import co.edu.usbcali.lookdocs.model.Rss;
import co.edu.usbcali.lookdocs.model.Usuarios;
import co.edu.usbcali.lookdocs.model.control.AnexosLogic;
import co.edu.usbcali.lookdocs.model.control.ArticulosLogic;
import co.edu.usbcali.lookdocs.model.control.CategoriasArticulosLogic;
import co.edu.usbcali.lookdocs.model.control.CategoriasLogic;
import co.edu.usbcali.lookdocs.model.control.ColeccionesLogic;
import co.edu.usbcali.lookdocs.model.control.ColeccionesRssLogic;
import co.edu.usbcali.lookdocs.model.control.EntradasLogic;
import co.edu.usbcali.lookdocs.model.control.EventosArticulosLogic;
import co.edu.usbcali.lookdocs.model.control.IAnexosLogic;
import co.edu.usbcali.lookdocs.model.control.IArticulosLogic;
import co.edu.usbcali.lookdocs.model.control.ICategoriasArticulosLogic;
import co.edu.usbcali.lookdocs.model.control.ICategoriasLogic;
import co.edu.usbcali.lookdocs.model.control.IColeccionesLogic;
import co.edu.usbcali.lookdocs.model.control.IColeccionesRssLogic;
import co.edu.usbcali.lookdocs.model.control.IEntradasLogic;
import co.edu.usbcali.lookdocs.model.control.IEventosArticulosLogic;
import co.edu.usbcali.lookdocs.model.control.IRolesLogic;
import co.edu.usbcali.lookdocs.model.control.IRssLogic;
import co.edu.usbcali.lookdocs.model.control.IUsuariosLogic;
import co.edu.usbcali.lookdocs.model.control.RolesLogic;
import co.edu.usbcali.lookdocs.model.control.RssLogic;
import co.edu.usbcali.lookdocs.model.control.UsuariosLogic;
import co.edu.usbcali.lookdocs.model.dto.AnexosDTO;
import co.edu.usbcali.lookdocs.model.dto.ArticulosDTO;
import co.edu.usbcali.lookdocs.model.dto.CategoriasArticulosDTO;
import co.edu.usbcali.lookdocs.model.dto.CategoriasDTO;
import co.edu.usbcali.lookdocs.model.dto.ColeccionesDTO;
import co.edu.usbcali.lookdocs.model.dto.ColeccionesRssDTO;
import co.edu.usbcali.lookdocs.model.dto.EntradasDTO;
import co.edu.usbcali.lookdocs.model.dto.EventosArticulosDTO;
import co.edu.usbcali.lookdocs.model.dto.RolesDTO;
import co.edu.usbcali.lookdocs.model.dto.RssDTO;
import co.edu.usbcali.lookdocs.model.dto.UsuariosDTO;

import org.primefaces.model.TreeNode;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;


/**
* @author Zathura Code Generator http://code.google.com/p/zathura
* www.zathuracode.org
*
*/
public interface IBusinessDelegatorView {
    public List<Anexos> getAnexos() throws Exception;

    public void saveAnexos(Anexos entity) throws Exception;

    public void deleteAnexos(Anexos entity) throws Exception;

    public void updateAnexos(Anexos entity) throws Exception;

    public Anexos getAnexos(Long codigoAnexo) throws Exception;

    public List<Anexos> findByCriteriaInAnexos(Object[] variables,
        Object[] variablesBetween, Object[] variablesBetweenDates)
        throws Exception;

    public List<Anexos> findPageAnexos(String sortColumnName,
        boolean sortAscending, int startRow, int maxResults)
        throws Exception;

    public Long findTotalNumberAnexos() throws Exception;

    public List<AnexosDTO> getDataAnexos() throws Exception;

    public List<Articulos> getArticulos() throws Exception;

    public void saveArticulos(Articulos entity, String categoriaSelected, String pdfUrl) throws Exception;

    public void deleteArticulos(Articulos entity) throws Exception;

    public void updateArticulos(Articulos entity) throws Exception;

    public Articulos getArticulos(Long codigoArti) throws Exception;

    public List<Articulos> findByCriteriaInArticulos(Object[] variables,
        Object[] variablesBetween, Object[] variablesBetweenDates)
        throws Exception;

    public List<Articulos> findPageArticulos(String sortColumnName,
        boolean sortAscending, int startRow, int maxResults)
        throws Exception;

    public Long findTotalNumberArticulos() throws Exception;

    public List<ArticulosDTO> getDataArticulos() throws Exception;

    public List<Categorias> getCategorias() throws Exception;

    public void saveCategorias(Categorias entity) throws Exception;

    public void deleteCategorias(Categorias entity) throws Exception;

    public void updateCategorias(Categorias entity) throws Exception;

    public Categorias getCategorias(Long codigoCate) throws Exception;

    public List<Categorias> findByCriteriaInCategorias(Object[] variables,
        Object[] variablesBetween, Object[] variablesBetweenDates)
        throws Exception;

    public List<Categorias> findPageCategorias(String sortColumnName,
        boolean sortAscending, int startRow, int maxResults)
        throws Exception;

    public Long findTotalNumberCategorias() throws Exception;

    public List<CategoriasDTO> getDataCategorias() throws Exception;

    public List<CategoriasArticulos> getCategoriasArticulos()
        throws Exception;
    
//    public void TransferFile(String pdfUrl, InputStream is, UploadedFile file)
//    		throws Exception;
    
    public void TransferFile(String server, String user, String pass,
    		UploadedFile file, String remotePath) throws Exception;
    
    public String downloadFileByFTP(String server, String user, String pass,
			String fileName, String remotePath) throws Exception;

    public void saveCategoriasArticulos(CategoriasArticulos entity)
        throws Exception;

    public void deleteCategoriasArticulos(CategoriasArticulos entity)
        throws Exception;

    public void updateCategoriasArticulos(CategoriasArticulos entity)
        throws Exception;

    public CategoriasArticulos getCategoriasArticulos(Long codigoCateArti)
        throws Exception;

    public List<CategoriasArticulos> findByCriteriaInCategoriasArticulos(
        Object[] variables, Object[] variablesBetween,
        Object[] variablesBetweenDates) throws Exception;

    public List<CategoriasArticulos> findPageCategoriasArticulos(
        String sortColumnName, boolean sortAscending, int startRow,
        int maxResults) throws Exception;

    public Long findTotalNumberCategoriasArticulos() throws Exception;

    public List<CategoriasArticulosDTO> getDataCategoriasArticulos()
        throws Exception;

    public List<Colecciones> getColecciones() throws Exception;

    public void saveColecciones(Colecciones entity) throws Exception;

    public void deleteColecciones(Colecciones entity) throws Exception;

    public void updateColecciones(Colecciones entity) throws Exception;

    public Colecciones getColecciones(Long codigoCole)
        throws Exception;

    public List<Colecciones> findByCriteriaInColecciones(Object[] variables,
        Object[] variablesBetween, Object[] variablesBetweenDates)
        throws Exception;

    public List<Colecciones> findPageColecciones(String sortColumnName,
        boolean sortAscending, int startRow, int maxResults)
        throws Exception;

    public Long findTotalNumberColecciones() throws Exception;

    public List<ColeccionesDTO> getDataColecciones() throws Exception;

    public List<ColeccionesRss> getColeccionesRss() throws Exception;

    public void saveColeccionesRss(ColeccionesRss entity)
        throws Exception;

    public void deleteColeccionesRss(ColeccionesRss entity)
        throws Exception;

    public void updateColeccionesRss(ColeccionesRss entity)
        throws Exception;

    public ColeccionesRss getColeccionesRss(Long codigoColRss)
        throws Exception;

    public List<ColeccionesRss> findByCriteriaInColeccionesRss(
        Object[] variables, Object[] variablesBetween,
        Object[] variablesBetweenDates) throws Exception;

    public List<ColeccionesRss> findPageColeccionesRss(String sortColumnName,
        boolean sortAscending, int startRow, int maxResults)
        throws Exception;

    public Long findTotalNumberColeccionesRss() throws Exception;

    public List<ColeccionesRssDTO> getDataColeccionesRss()
        throws Exception;

    public List<Entradas> getEntradas() throws Exception;

    public void saveEntradas(Entradas entity) throws Exception;

    public void deleteEntradas(Entradas entity) throws Exception;

    public void updateEntradas(Entradas entity) throws Exception;

    public Entradas getEntradas(Long codigoEntra) throws Exception;

    public List<Entradas> findByCriteriaInEntradas(Object[] variables,
        Object[] variablesBetween, Object[] variablesBetweenDates)
        throws Exception;

    public List<Entradas> findPageEntradas(String sortColumnName,
        boolean sortAscending, int startRow, int maxResults)
        throws Exception;

    public Long findTotalNumberEntradas() throws Exception;

    public List<EntradasDTO> getDataEntradas() throws Exception;

    public List<EventosArticulos> getEventosArticulos()
        throws Exception;

    public void saveEventosArticulos(EventosArticulos entity)
        throws Exception;

    public void deleteEventosArticulos(EventosArticulos entity)
        throws Exception;

    public void updateEventosArticulos(EventosArticulos entity)
        throws Exception;

    public EventosArticulos getEventosArticulos(Long codigoEveArt)
        throws Exception;

    public List<EventosArticulos> findByCriteriaInEventosArticulos(
        Object[] variables, Object[] variablesBetween,
        Object[] variablesBetweenDates) throws Exception;

    public List<EventosArticulos> findPageEventosArticulos(
        String sortColumnName, boolean sortAscending, int startRow,
        int maxResults) throws Exception;

    public Long findTotalNumberEventosArticulos() throws Exception;

    public List<EventosArticulosDTO> getDataEventosArticulos()
        throws Exception;

    public List<Roles> getRoles() throws Exception;

    public void saveRoles(Roles entity) throws Exception;

    public void deleteRoles(Roles entity) throws Exception;

    public void updateRoles(Roles entity) throws Exception;

    public Roles getRoles(Long codigoRol) throws Exception;

    public List<Roles> findByCriteriaInRoles(Object[] variables,
        Object[] variablesBetween, Object[] variablesBetweenDates)
        throws Exception;

    public List<Roles> findPageRoles(String sortColumnName,
        boolean sortAscending, int startRow, int maxResults)
        throws Exception;

    public Long findTotalNumberRoles() throws Exception;

    public List<RolesDTO> getDataRoles() throws Exception;

    public List<Rss> getRss() throws Exception;

    public void saveRss(Rss entity) throws Exception;

    public void deleteRss(Rss entity) throws Exception;

    public void updateRss(Rss entity) throws Exception;

    public Rss getRss(Long codigoRss) throws Exception;

    public List<Rss> findByCriteriaInRss(Object[] variables,
        Object[] variablesBetween, Object[] variablesBetweenDates)
        throws Exception;

    public List<Rss> findPageRss(String sortColumnName, boolean sortAscending,
        int startRow, int maxResults) throws Exception;

    public Long findTotalNumberRss() throws Exception;

    public List<RssDTO> getDataRss() throws Exception;

    public List<Usuarios> getUsuarios() throws Exception;

    public void saveUsuarios(Usuarios entity) throws Exception;

    public void deleteUsuarios(Usuarios entity) throws Exception;

    public void updateUsuarios(Usuarios entity) throws Exception;

    public Usuarios getUsuarios(Long codigoUsua) throws Exception;

    public List<Usuarios> findByCriteriaInUsuarios(Object[] variables,
        Object[] variablesBetween, Object[] variablesBetweenDates)
        throws Exception;

    public List<Usuarios> findPageUsuarios(String sortColumnName,
        boolean sortAscending, int startRow, int maxResults)
        throws Exception;

    public Long findTotalNumberUsuarios() throws Exception;

    public List<UsuariosDTO> getDataUsuarios() throws Exception;

    public boolean validateEmailAddress(String sEmail) throws Exception;

    public String iniciarSesionLector(String email, String password) throws Exception;
    
    public String iniciarSesionAdministrador(String email, String password) throws Exception;
    
    public void registrarUsuarioLector(Usuarios usuarios) throws Exception;

    public Usuarios obtenerPorMail(String email)throws Exception;
    
    public void deleteCategoriasbyNode(TreeNode selectedNode) throws Exception;
    

    public void modificarPasswordUsuarios(Usuarios usuarios, String claveActual, String nuevaClave, String confirmaClave) throws Exception;
   
    public void modificarNombreDeUsuario(Usuarios usuarios, String nombre) throws Exception;

    public void recuperarClave(String email) throws Exception;
    
    public List<Colecciones> consultarColeccionPorUsuario(Usuarios usuarios) throws Exception;
    
    public List<Articulos> consultarTodosArticulos() throws Exception;
    
    public Anexos getAnexosbyArtiuclo(Long codigoArticulo) throws Exception;
    
    public void guardarRSS(String urlRss, Long idColeccion) throws Exception;
    
    public List<Rss> getRssDadoIdColeccion(Long codigoCole) throws Exception;
    
    public List<RssDTO> getRssDTODadoIdColeccion(Long codigoCole) throws Exception;
    
    public List<Articulos> consultaArticulosPorCategoria(Long idCate) throws Exception;
    
    public Colecciones consultarNodoSeleccionado(String nodoSeleccionado) throws Exception;
    
    public Colecciones consultarColeccionPorNombreYUsuario(Usuarios usuarios, String nombreColeccion) throws Exception;
    
    public List<Rss> consultarCodigoRss(String url);
    
    public String nombreColeccionPorCodigoRss(Rss rss);
    
    public Entradas consultarEntradas(Rss rss);
    
    public ColeccionesRss consultarColeccionesRss(Rss rss, Colecciones coleccion);
    
    public Rss consultarRssPorUrlYColeccion(String url, Colecciones coleccion);
    
    public List<ArticulosDTO> getDataArticulosByCateg(Long codigoCate) throws Exception;
    
    public String findColeccionPorId(Long idColeccion);
    
    public List<Colecciones> consultarColeccionesPorURL(String url);
    
    public Rss consultarRssPorURl(String url);
    
    public ColeccionesRss consultarColeccionRssPorColeRss(Rss rss, Colecciones coleccion);
    
    public Rss consultarRssPorUrlCole(String rss, Colecciones coleccion);
    
    public Entradas consultarEntradaPorRss(Rss rss);
    
    public List<Entradas> consultarEntradasPorCole(Colecciones coleccion);
    
    //rssDTO por coleccion
    public List<RssDTO> getDataRssPorColeccion(Colecciones coleccion) throws Exception;
    
    public List<Colecciones> consultarColePorURL(String url);
    
    public List<Rss> consultarRssPorURlList(String url);
    
    public EventosArticulos consultareEventosPorArticulos(Long codigoArti, Long codigoUsua)
    		throws Exception;
    
    public List<ColeccionesDTO> obtenerColeccionesDadoMailDeUsuario(String email) throws Exception;
}
