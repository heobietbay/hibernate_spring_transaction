package khoa.training.hibernate.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;


/**
 * Created by trandangkhoa on 4/22/2015.
 */
public abstract class BaseDAOImpl implements IBaseDAO{

    protected SessionFactory sessionFactory;

    protected Session getSession() {
        return this.getSession(false);
    }

    protected Session getSession(boolean allowNewSession) {
        if (!allowNewSession) {
            return sessionFactory.getCurrentSession();
        } else {
            return sessionFactory.openSession();
        }
    }

    @Transactional
    public void clear() {
        getSession().clear();
    }

    @Transactional
    public void saveOrUpdate(Object object)
    {
        // Workaround readonly scenario: first evict that out of the session
        getSession().evict(object);
        // actual update operation
        getSession().saveOrUpdate(object);
    }

    @Transactional
    public void delete(Object object) {
        getSession().delete(object);
    }

    @Transactional
    public Object merge(Object object)
    {
        return getSession().merge(object);
    }

    @Transactional
    public Object findById(Class T, Serializable id) {
        Object res = getSession().get(T,id);
        return res;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public Object lockById(Serializable id) {
        String hqlUpdate = getUpdateHql();
        getSession().createQuery(hqlUpdate).setParameter("id", id).executeUpdate();
        return getSession().get(getToLockClass(),id);
    }

    protected abstract Class getToLockClass();

    /**
     * Must containt where clase by id: .... where T.id = :id
     * @return
     */
    protected abstract String getUpdateHql() ;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
