package khoa.training.hibernate.dao;

import khoa.training.hibernate.model.Studentv1;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * Created by trandangkhoa on 4/22/2015.
 */
public class StudentDAOImpl extends BaseDAOImpl implements IStudentDAO{

    public void insertStudentWithOwnTransaction(Studentv1 studentv1) {

        Session session = this.getSession();

        Transaction transaction  = session.beginTransaction();

        session.save(studentv1);

        transaction.commit();

        session.close();
    }


    @Transactional
    public void insertStudent(Studentv1 studentv1) {
           this.getSession().saveOrUpdate(studentv1);
    }


    @Transactional
    public void update(Studentv1 studentv1) {
        super.saveOrUpdate(studentv1);
    }

    public List<Studentv1> findAll() {
        Query query = getSession().createQuery("from Studentv1 as st join fetch st.addressv1Set ");
        query.setFlushMode(FlushMode.MANUAL);
        List list = query.list();
        return list;
    }

    @Override
    @Transactional
    public Object findById(Class T, Serializable id) {
        Query query = getSession()
                .createQuery("from Studentv1 as st where st.id = :id")
                .setParameter("id",id)
                /*
               Entities and proxies that exist in the session before being returned by an HQL query or criteria are not affected.

               Uninitialized persistent collections returned by the query are not affected. Later, when the collection is initialized,
                entities loaded into the session will be read-only if Session.isDefaultReadOnly() returns true.

               Using Query.setReadOnly( true ) or Criteria.setReadOnly( true ) works well when a single HQL query or criteria loads all
                the entities and intializes all the proxies and collections that the application needs to be read-only.
                */

                // Furthermore, setReadOnly(true) will be called on the JDBC Connection, which is also a hint
               // to the underlying database. If your database supports it (most likely it does),
               // this has basically the same effect as FlushMode.NEVER, but it's stronger since you cannot even flush manually.
               // Be SMART with this choice, cause this can mess up your application, you will sometime run into
               // scenario where hibernate cannot commit transaction.
                .setReadOnly(true)
                .setFlushMode(FlushMode.MANUAL)
                ;

        return query.uniqueResult();
    }

    protected String getUpdateHql() {
        return "update Studentv1 a set a.dob = a.dob where a.id = :id ";
    }
    @Override
    protected Class getToLockClass() {
        return Studentv1.class;
    }
}
