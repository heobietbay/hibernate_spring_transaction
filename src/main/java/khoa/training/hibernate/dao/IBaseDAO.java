package khoa.training.hibernate.dao;

import java.io.Serializable;

/**
 * Created by Administrator on 4/15/2016.
 */
public interface IBaseDAO {
    Object merge(Object object);
    Object findById(Class T, Serializable id);
    Object lockById(Serializable id);
    void saveOrUpdate(Object object);
    void delete(Object object);
}
