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
 * Rss entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 *
 * @see lidis.Rss
 */
@Scope("singleton")
@Repository("RssDAO")
public class RssDAO extends HibernateDaoImpl<Rss, Long> implements IRssDAO {
    private static final Logger log = LoggerFactory.getLogger(RssDAO.class);
    @Resource
    private SessionFactory sessionFactory;

    public static IRssDAO getFromApplicationContext(ApplicationContext ctx) {
        return (IRssDAO) ctx.getBean("RssDAO");
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
	public List<Rss> getRssDadoIdColeccion(Long codigoCole) {
		/*select r
 			from rss rss, colecciones col, colecciones_rss colrss
 			where col.codigo_cole = colrss.colecciones_codigo_cole and
 			cr.rss_codigo_rss = r.codigo_rss and c.codigo_cole = 13*/
		
		String hql = "SELECT rss FROM Rss rss, Colecciones col, ColeccionesRss colrss"
				+ " WHERE colrss.colecciones.codigoCole= col.codigoCole and"
				+ " colrss.rss.codigoRss = rss.codigoRss and"
				+ " col.codigoCole = "+codigoCole;
    	Query query = sessionFactory.getCurrentSession().createQuery(hql);
    	List<Rss> losRss = query.list();
    	return losRss;
	}
	
	


}
