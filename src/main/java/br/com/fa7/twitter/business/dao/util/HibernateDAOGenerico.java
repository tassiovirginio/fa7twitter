package br.com.fa7.twitter.business.dao.util;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class HibernateDAOGenerico<T, ID extends Serializable> extends HibernateDaoSupport{

	private static Log LOG = LogFactory.getLog(HibernateDAOGenerico.class);

	@SuppressWarnings("unchecked")
	public HibernateDAOGenerico() {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	private Class<T> persistentClass;

	public Class<T> getPersistentClass() {
		return this.persistentClass;
	}

	public void delete(T entity) {
		try {
			this.getHibernateTemplate().delete(entity);
		} catch (final HibernateException ex) {
			HibernateDAOGenerico.LOG.error(ex);
			throw convertHibernateAccessException(ex);
		}
	}

	@SuppressWarnings("unchecked")
	public T findById(ID id) {
		try {
			return (T) this.getHibernateTemplate().get(getPersistentClass(), id);
		} catch (final HibernateException ex) {
			HibernateDAOGenerico.LOG.error(ex);
			throw convertHibernateAccessException(ex);
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> listAll() {
		try {
			return this.getHibernateTemplate().loadAll(getPersistentClass());
		} catch (final HibernateException ex) {
			HibernateDAOGenerico.LOG.error(ex);
			throw convertHibernateAccessException(ex);
		}
	}

	public T save(T entity) {
		try {
			this.getHibernateTemplate().saveOrUpdate(entity);
			return entity;
		} catch (final HibernateException ex) {
			HibernateDAOGenerico.LOG.error(ex);
			throw convertHibernateAccessException(ex);
		}
	}

	public List<T> findByCriteria(Criterion... criterion) {
		return findByCriteria(null, criterion);
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findByCriteria(Order order,Criterion... criterion) {
		try {
			Criteria crit = this.getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createCriteria(getPersistentClass());
			for (Criterion c : criterion) {
				crit.add(c);
			}
			if(order != null){
				crit.addOrder(order);
			}
			return crit.list();
		} catch (final HibernateException ex) {
			HibernateDAOGenerico.LOG.error(ex);
			throw convertHibernateAccessException(ex);
		}
	}
}
