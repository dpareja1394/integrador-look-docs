package co.edu.usbcali.lookdocs.dataaccess.dao;

import co.edu.usbcali.lookdocs.dataaccess.api.HibernateDaoImpl;
import co.edu.usbcali.lookdocs.model.CategoriasArticulos;

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
 * CategoriasArticulos entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 *
 * @see lidis.CategoriasArticulos
 */
@Scope("singleton")
@Repository("CategoriasArticulosDAO")
public class CategoriasArticulosDAO extends HibernateDaoImpl<CategoriasArticulos, Long>
    implements ICategoriasArticulosDAO {
    private static final Logger log = LoggerFactory.getLogger(CategoriasArticulosDAO.class);
    @Resource
    private SessionFactory sessionFactory;

    public static ICategoriasArticulosDAO getFromApplicationContext(
        ApplicationContext ctx) {
        return (ICategoriasArticulosDAO) ctx.getBean("CategoriasArticulosDAO");
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
}
