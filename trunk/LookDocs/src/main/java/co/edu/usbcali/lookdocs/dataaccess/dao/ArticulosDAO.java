package co.edu.usbcali.lookdocs.dataaccess.dao;

import co.edu.usbcali.lookdocs.dataaccess.api.HibernateDaoImpl;
import co.edu.usbcali.lookdocs.model.Articulos;
import co.edu.usbcali.lookdocs.model.Colecciones;
import co.edu.usbcali.lookdocs.model.Usuarios;
import co.edu.usbcali.lookdocs.model.dto.ArticulosDTO;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.annotation.Resource;

/**
 * A data access object (DAO) providing persistence and search support for
 * Articulos entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 *
 * @see lidis.Articulos
 */
@Scope("singleton")
@Repository("ArticulosDAO")
public class ArticulosDAO extends HibernateDaoImpl<Articulos, Long> implements
		IArticulosDAO {
	private static final Logger log = LoggerFactory
			.getLogger(ArticulosDAO.class);
	@Resource
	private SessionFactory sessionFactory;

	public static IArticulosDAO getFromApplicationContext(ApplicationContext ctx) {
		return (IArticulosDAO) ctx.getBean("ArticulosDAO");
	}

	// public void TransferFile(String pdfUrl, InputStream is, UploadedFile
	// file) throws Exception {
	//
	// try {
	// OutputStream os = new FileOutputStream(new File(pdfUrl));
	// int reader = 0;
	// byte[] bytes = new byte[(int) file.getSize()];
	// while ((reader = is.read(bytes)) != -1) {
	// os.write(bytes, 0, reader);
	// }
	// is.close();
	// os.flush();
	// os.close();
	// } catch (IOException e) {
	// throw new Exception("Error trabajando con el archivo");
	// }
	//
	// }

	public void TransferFile(String server, String user, String pass,
			UploadedFile file, String remotePath) throws Exception {

		try {

			// Trae la carpeta temporal del sistema
			String localPath = System.getProperty("java.io.tmpdir")
					+ file.getFileName();

			// Flujo de Entrada de Datos
			InputStream is = file.getInputstream();

			// Guarda el archivo en la carpeta temporal
			OutputStream outputStream = new FileOutputStream(
					new File(localPath));
			int reader = 0;
			byte[] bytes = new byte[(int) file.getSize()];
			while ((reader = is.read(bytes)) != -1) {
				outputStream.write(bytes, 0, reader);
			}
			is.close();
			outputStream.flush();
			outputStream.close();

			// Trae el archivo para eliminarlo despues de subirlo al hosting
			File pendingOnDelete = new File(localPath);

			URL url = new URL("ftp://" + user + ":" + pass + "@" + server
					+ remotePath);

			URLConnection urlc = url.openConnection();
			OutputStream os = urlc.getOutputStream();
			File fichero = new File(localPath);
			FileInputStream inp = new FileInputStream(fichero);
			bytes = new byte[(int) file.getSize()];
			int readCount = 0;

			while ((readCount = inp.read(bytes)) > 0) {
				os.write(bytes, 0, readCount);
			}
			os.flush();
			os.close();
			inp.close();

			pendingOnDelete.delete();

		} catch (Exception ex) {
			throw new Exception("Error subiendo el archivo");

		}

	}

	public String downloadFileByFTP(String server, String user, String pass,
			String fileName, String remotePath) throws Exception {

		// trae el directorio de descargas por defecto
		String defaultDownloadPath = System.getProperty("user.home")
				+ File.separator + "Downloads";
		// arma el directorio final de la descarga
		String localPath = defaultDownloadPath + File.separator
				+ fileName;
		try {
			URL url = new URL("ftp://" + user + ":" + pass + "@" + server
					+ remotePath);
			URLConnection urlc = url.openConnection();
			BufferedInputStream in = new BufferedInputStream(
					urlc.getInputStream());
			FileOutputStream out = new FileOutputStream(localPath);

			int i = 0;
			byte[] bytesIn = new byte[1024];
			while ((i = in.read(bytesIn)) >= 0) {
				out.write(bytesIn, 0, i);
			}
			out.close();
			in.close();
			
			return "file:///"+localPath;

		} catch (Exception ex) {
			
			throw new Exception("Error descargando el archivo");
		}

	}
	
	 @Override
	    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	    public synchronized Long getConsecutivo(String sqlName) throws Exception {

	        Long consecutivo = null;
	        List qlist = null;
	        try {
	            Session session = sessionFactory.getCurrentSession();
	            Query query = session.getNamedQuery(sqlName);
	            qlist = query.list();
	            for (java.util.Iterator iter = qlist.iterator(); iter.hasNext();) {
	                consecutivo = (Long) iter.next();
	            }
	        } catch (org.hibernate.HibernateException e) {
	            consecutivo = new Long(0);
	        }

	        return consecutivo;
	    }
	 
	 /*
	    @Override
	    public List<Articulos> consultarTodosArticulos() throws Exception{
	    	String hql = "SELECT a.nombre, a.descripcion, c.nombre, a.autor, a.estadoRegistro "
	    			+ "FROM Articulos a, Categorias c, CategoriasArticulos ca "
	    			+ "WHERE a.codigoArti=ca.articulos.codigoArti AND c.codigoCate=ca.categorias.codigoCate";
	    	Query query = sessionFactory.getCurrentSession().createQuery(hql);
	    	List<Articulos> losArticulos = query.list();
	    	return losArticulos;
	    	
	    }*/
	 
	 @Override
	    public List<Articulos> consultarTodosArticulos() throws Exception {
	    	return sessionFactory.getCurrentSession().createCriteria(Articulos.class).list(); 
	    }
	 
	 @Override
	    public List<Object> consultaArticulosCategoria() throws Exception{
	    	
	    	String hql2 = "SELECT a.codigoArti, a.nombre, a.descripcion, "
	    			+ " a.autor, a.fechaCreacion, a.fechaModifcacion, a.usuCrea, a.usuModifica,"
	    			+ " a.estadoRegistro, a.usuarios.codigoUsua, c.nombre FROM Articulos a, CategoriasArticulos ca,"
	    			+ " Categorias c WHERE a.codigoArti=ca.articulos.codigoArti and ca.categorias.codigoCate=c.codigoCate";
	    	Query query = sessionFactory.getCurrentSession().createQuery(hql2);
	    	List<Object> losArticulos = query.list();
	    	return losArticulos;
	    	
	    }
}
