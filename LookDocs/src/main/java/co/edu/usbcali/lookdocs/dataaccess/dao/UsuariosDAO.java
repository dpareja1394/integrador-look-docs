package co.edu.usbcali.lookdocs.dataaccess.dao;

import co.edu.usbcali.lookdocs.dataaccess.api.HibernateDaoImpl;
import co.edu.usbcali.lookdocs.model.Usuarios;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;
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
 * Usuarios entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 *
 * @see lidis.Usuarios
 */
@Scope("singleton")
@Repository("UsuariosDAO")
public class UsuariosDAO extends HibernateDaoImpl<Usuarios, Long> implements
		IUsuariosDAO {
	private static final Logger log = LoggerFactory
			.getLogger(UsuariosDAO.class);
	@Resource
	private SessionFactory sessionFactory;

	public static IUsuariosDAO getFromApplicationContext(ApplicationContext ctx) {
		return (IUsuariosDAO) ctx.getBean("UsuariosDAO");
	}

	@Override
	@Transactional(readOnly = true)
	public Usuarios obtenerPorMail(String email) {

		Usuarios usuarios = new Usuarios();
		List<Usuarios> elUsuario = sessionFactory.getCurrentSession()
				.createCriteria(Usuarios.class)
				.add(Restrictions.eq("email", email)).list();
		if (elUsuario.size() > 0) {
			return elUsuario.get(0);
		} else {
			return null;
		}
	}

	@Override
	public void registrarUsuarioLector(Usuarios usuarios) {
		sessionFactory.getCurrentSession().save(usuarios);

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
	public Usuarios consultarPorId(Long id) {
		
		return (Usuarios) sessionFactory.getCurrentSession().get(Usuarios.class, id);
	}

}
