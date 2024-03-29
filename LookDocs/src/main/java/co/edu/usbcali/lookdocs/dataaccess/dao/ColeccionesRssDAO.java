package co.edu.usbcali.lookdocs.dataaccess.dao;

import co.edu.usbcali.lookdocs.dataaccess.api.HibernateDaoImpl;
import co.edu.usbcali.lookdocs.model.Colecciones;
import co.edu.usbcali.lookdocs.model.ColeccionesRss;
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
 * ColeccionesRss entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 *
 * @see lidis.ColeccionesRss
 */
@Scope("singleton")
@Repository("ColeccionesRssDAO")
public class ColeccionesRssDAO extends HibernateDaoImpl<ColeccionesRss, Long>
    implements IColeccionesRssDAO {
    private static final Logger log = LoggerFactory.getLogger(ColeccionesRssDAO.class);
    @Resource
    private SessionFactory sessionFactory;

    public static IColeccionesRssDAO getFromApplicationContext(
        ApplicationContext ctx) {
        return (IColeccionesRssDAO) ctx.getBean("ColeccionesRssDAO");
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

	@Override
	public ColeccionesRss consultarSiExisteRssEnLaColeccion(Long idColeccion,
			String urlRss) {
		String hql2 = "SELECT colrss FROM ColeccionesRss colrss WHERE colrss.colecciones.codigoCole = "+idColeccion+
				" and colrss.rss.url = '"+urlRss+"' ";
    	Query query = sessionFactory.getCurrentSession().createQuery(hql2);
    	ColeccionesRss coleccionesRss = (ColeccionesRss) query.uniqueResult();
    	return coleccionesRss;
		
	}
	
	@Override
	public ColeccionesRss consultarColeccionesRss(Rss rss, Colecciones coleccion){
		String hql = "SELECT colrss FROM ColeccionesRss colrss, Rss WHERE colrss.rss.codigoRss = "+rss.getCodigoRss()+"AND colrss.colecciones.codigoCole ="+coleccion.getCodigoCole();
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		ColeccionesRss coleccionesRss = (ColeccionesRss) query.uniqueResult();
		return coleccionesRss;
	}
	
	/* 	select cr
		from colecciones c, rss r, colecciones_rss cr
		where c.codigo_cole = cr.colecciones_codigo_cole AND c.codigo_cole = 81 and r.codigo_rss = cr.rss_codigo_rss AND r.codigo_rss = 9 */
	
	@Override
	public ColeccionesRss consultarColeccionRssPorColeRss(Rss rss, Colecciones coleccion){
		String hql = "SELECT colrss FROM ColeccionesRss colrss, Rss rss, Colecciones col WHERE col.codigoCole = colrss.colecciones.codigoCole AND"
				+ " col.codigoCole = "+coleccion.getCodigoCole()+" AND rss.codigoRss = colrss.rss.codigoRss AND rss.codigoRss = "+rss.getCodigoRss();
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		ColeccionesRss coleccionRss = (ColeccionesRss) query.uniqueResult();
		return coleccionRss;
	}
}
