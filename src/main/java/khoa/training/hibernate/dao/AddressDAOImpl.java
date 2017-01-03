package khoa.training.hibernate.dao;

import khoa.training.hibernate.model.Addressv1;
import khoa.training.hibernate.model.Studentv1;
import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by khoa on 4/13/2016.
 */
public class AddressDAOImpl extends BaseDAOImpl implements IAddressDAO {

    @Transactional
    public void insertAddress(Addressv1 addressv1) {
        this.getSession().saveOrUpdate(addressv1);
    }

    @Transactional
    public List<Addressv1> findAll() {
        Query query = getSession().createQuery("from Addressv1 as addr join fetch addr.student ");
        return query.list();
    }

    @Transactional
    public Addressv1 find(String addr) {

        Query query = this.getSession()
                          .createQuery(" from Addressv1 as addr join fetch addr.student  where addr.location like :addr")
                          .setParameter("addr","%" + addr + "%")

                          .setMaxResults(1) ;

        List<Addressv1> list = query.list();
        return list == null || list.isEmpty() ? null : list.get(0);
    }

    @Transactional
    public Studentv1 findStudentLiveIn(String addr)
    {
        Query query = this.getSession()
                .createQuery(" from Addressv1 as addr join fetch addr.student  where addr.location like :addr ")
                .setParameter("addr","%" + addr + "%")
                .setMaxResults(1) ;
        List<Addressv1> list = query.list();
        return list == null || list.isEmpty() ? null : list.get(0).getStudent();
    }

    String getUpdateHql() {
        return "update Addressv1 a set a.location = a.location where a.id = :id ";
    }

    @Override
    protected Class getToLockClass() {
        return Addressv1.class;
    }
}
