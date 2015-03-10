package co.edu.usbcali.lookdocs.dataaccess.dao;

import java.io.InputStream;

import org.primefaces.model.UploadedFile;
import java.util.List;
import co.edu.usbcali.lookdocs.dataaccess.api.Dao;
import co.edu.usbcali.lookdocs.model.Articulos;


/**
* Interface for   ArticulosDAO.
*
*/
public interface IArticulosDAO extends Dao<Articulos, Long> {
	
	//public void TransferFile(String pdfUrl, InputStream is, UploadedFile file) throws Exception;
	
	//public List<Articulos> consultarTodosArticulos() throws Exception;
	public List<Articulos> consultarTodosArticulos() throws Exception;
	
	public void TransferFile(String server, String user, String pass,
			UploadedFile file, String remotePath) throws Exception;
	
	public String downloadFileByFTP(String server, String user, String pass,
			String fileName, String remotePath) throws Exception;
	
	public Long getConsecutivo(String sqlName) throws Exception;
}
