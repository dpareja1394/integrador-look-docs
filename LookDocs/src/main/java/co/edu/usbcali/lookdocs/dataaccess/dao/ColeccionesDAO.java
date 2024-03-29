package co.edu.usbcali.lookdocs.dataaccess.dao;

import co.edu.usbcali.lookdocs.dataaccess.api.HibernateDaoImpl;
import co.edu.usbcali.lookdocs.model.Colecciones;
import co.edu.usbcali.lookdocs.model.Rss;
import co.edu.usbcali.lookdocs.model.Usuarios;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;


/**
 * A data access object (DAO) providing persistence and search support for
 * Colecciones entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 *
 * @see lidis.Colecciones
 */
@Scope("singleton")
@Repository("ColeccionesDAO")
public class ColeccionesDAO extends HibernateDaoImpl<Colecciones, Long>
    implements IColeccionesDAO {
    private static final Logger log = LoggerFactory.getLogger(ColeccionesDAO.class);
    @Resource
    private SessionFactory sessionFactory;

    public static IColeccionesDAO getFromApplicationContext(
        ApplicationContext ctx) {
        return (IColeccionesDAO) ctx.getBean("ColeccionesDAO");
    }
    
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public synchronized Long getConsecutivo(String sqlNombre) throws Exception {
        Long consecutivo = null;
        List qlist = null;
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session.getNamedQuery(sqlNombre);
            qlist = query.list();
            for (java.util.Iterator iter = qlist.iterator(); iter.hasNext();) {
                consecutivo = (Long) iter.next();
            }
        } catch (org.hibernate.HibernateException e) {
            consecutivo = new Long(0);
        }
        return consecutivo;
    } 
    
    @Override
    public List<Colecciones> consultarColeccionPorUsuario(Usuarios usuarios) throws Exception{
    	
    	String hql2 = "SELECT col FROM Colecciones col WHERE col.usuarios.codigoUsua = "+usuarios.getCodigoUsua();
    	Query query = sessionFactory.getCurrentSession().createQuery(hql2);
    	List<Colecciones> lasColecciones = query.list();
    	return lasColecciones;
    	
    }

	@Override
	public Colecciones consultarColeccionPorNombreYUsuario(Usuarios usuarios,
			String nombreColeccion) throws Exception {
		String hql2 = "SELECT col FROM Colecciones col WHERE col.usuarios.codigoUsua = "+usuarios.getCodigoUsua()+
				" and col.nombre = '"+nombreColeccion+"' ";
    	Query query = sessionFactory.getCurrentSession().createQuery(hql2);
    	Colecciones colecciones = (Colecciones) query.uniqueResult();
    	return colecciones;
		
	}
	
	@Override
	public Colecciones consultarNodoSeleccionado(String nodoSeleccionado) throws Exception{
		String hql = "SELECT col FROM Colecciones col WHERE col.nombre = '"+nodoSeleccionado+"' ";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		Colecciones colecciones = (Colecciones) query.uniqueResult();
		return colecciones;
	}
	
	@Override
	public List<Rss> consultarCodigoRss(String url){
		String hql = "SELECT rss FROM Rss rss WHERE rss.url = '"+url+"'" ;
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		List<Rss> elrss = query.list();
		return elrss;
	}
	
	/*select c.nombre
	from colecciones c, colecciones_rss cr, rss r 
	where r.codigo_rss = cr.rss_codigo_rss AND c.codigo_cole = cr.colecciones_codigo_cole*/ 
	
	@Override
	public String nombreColeccionPorCodigoRss(Rss rss){
		
//		select c.nombre
//		from colecciones c, colecciones_rss cr, rss r 
//		where r.codigo_rss = cr.rss_codigo_rss AND r.codigo_rss = 9 AND c.codigo_cole = cr.colecciones_codigo_cole 
		
		String hql = "SELECT col.nombre FROM Colecciones col, ColeccionesRss colrss, Rss rss WHERE col.codigoCole = colrss.colecciones.codigoCole AND "
				+ "rss.codigoRss = colrss.rss.codigoRss AND rss.codigoRss = "+rss.getCodigoRss();		
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		String elNombreColeccion = (String) query.uniqueResult();
		return elNombreColeccion;
	}
	
	@Override
	public String findColeccionPorId(Long idColeccion){
		String hql = "SELECT col.nombre FROM Colecciones col WHERE col.codigoCole = "+idColeccion;
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		String nombre = (String) query.uniqueResult();
		return nombre;
	}
}
