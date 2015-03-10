package co.edu.usbcali.lookdocs.dataaccess.dao;

import co.edu.usbcali.lookdocs.dataaccess.api.HibernateDaoImpl;
import co.edu.usbcali.lookdocs.model.Articulos;

import org.hibernate.Query;
import org.hibernate.SessionFactory;

import org.hibernate.criterion.Example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;


/**
 * A data access object (DAO) providing persistence and search support for
 * Articulos entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 *
 * @see lidis.Articulos
 */
@Scope("singleton")
@Repository("ArticulosDAO")
public class ArticulosDAO extends HibernateDaoImpl<Articulos, Long>
    implements IArticulosDAO {
    private static final Logger log = LoggerFactory.getLogger(ArticulosDAO.class);
    @Resource
    private SessionFactory sessionFactory;

    public static IArticulosDAO getFromApplicationContext(
        ApplicationContext ctx) {
        return (IArticulosDAO) ctx.getBean("ArticulosDAO");
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
}
