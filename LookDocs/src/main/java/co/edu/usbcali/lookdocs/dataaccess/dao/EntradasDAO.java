package co.edu.usbcali.lookdocs.dataaccess.dao;

import co.edu.usbcali.lookdocs.dataaccess.api.HibernateDaoImpl;
import co.edu.usbcali.lookdocs.model.Colecciones;
import co.edu.usbcali.lookdocs.model.Entradas;
import co.edu.usbcali.lookdocs.model.Rss;

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
 * Entradas entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 *
 * @see lidis.Entradas
 */
@Scope("singleton")
@Repository("EntradasDAO")
public class EntradasDAO extends HibernateDaoImpl<Entradas, Long>
    implements IEntradasDAO {
    private static final Logger log = LoggerFactory.getLogger(EntradasDAO.class);
    @Resource
    private SessionFactory sessionFactory;

    public static IEntradasDAO getFromApplicationContext(ApplicationContext ctx) {
        return (IEntradasDAO) ctx.getBean("EntradasDAO");
    }
    
    /* select e
from rss r, entradas e
where e.rss_codigo_rss = 1 */
    
    /*String hql = "SELECT col.nombre FROM Colecciones col, ColeccionesRss colrss, Rss rss WHERE col.codigoCole = colrss.colecciones.codigoCole AND 
     * colrss.rss.codigoRss = "+rss.getCodigoRss();		
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		String elNombreColeccion = (String) query.uniqueResult();
		return elNombreColeccion; */
    
    @Override
    public Entradas consultarEntradas(Rss rss){
    	String hql = "SELECT ent FROM Rss rss, Entradas ent WHERE ent.rss.codigoRss = "+rss.getCodigoRss();
    	Query query = sessionFactory.getCurrentSession().createQuery(hql);
    	Entradas entradas = (Entradas) query.uniqueResult();
    	return entradas;
    }
    
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public synchronized Long getConsecutivo(String sqlName) {

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
    
    /* 	SELECT e 
		from entradas e, colecciones_rss cr, rss r
		where cr.rss_codigo_rss = r.codigo_rss AND e.rss_codigo_rss = r.codigo_rss And r.codigo_rss = 17 */
    
    @Override
    public Entradas consultarEntradaPorRss(Rss rss){
    	String hql = "SELECT ent FROM Entradas ent, ColeccionesRss colrss, Rss rss WHERE "
    			+ " colrss.rss.codigoRss = rss.codigoRss AND ent.rss.codigoRss = rss.codigoRss AND rss.codigoRss = "+rss.getCodigoRss();
    	Query query = sessionFactory.getCurrentSession().createQuery(hql);
    	Entradas entrada = (Entradas) query.uniqueResult();
    	return entrada;
    	
    }
    
    /* 	select e
		from colecciones c, colecciones_rss cr, entradas e
		where c.codigo_cole = cr.colecciones_codigo_cole AND
		cr.rss_codigo_rss = e.rss_codigo_rss AND
		c.codigo_cole = 87 */
    
    @Override
    public List<Entradas> consultarEntradasPorCole(Colecciones coleccion){
    	String hql = "SELECT ent FROM Colecciones col, ColeccionesRss colrss, Entradas ent WHERE "
    			+ " col.codigoCole = colrss.colecciones.codigoCole AND colrss.rss.codigoRss = ent.rss.codigoRss AND "
    			+ " col.codigoCole = "+coleccion.getCodigoCole();
    	Query query = sessionFactory.getCurrentSession().createQuery(hql);
    	List<Entradas> lasEntradas = query.list();
    	return lasEntradas;
    }
}
