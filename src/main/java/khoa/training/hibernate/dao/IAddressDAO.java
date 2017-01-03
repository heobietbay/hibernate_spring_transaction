package khoa.training.hibernate.dao;

import khoa.training.hibernate.model.Addressv1;
import khoa.training.hibernate.model.Studentv1;

import java.util.List;

/**
 * Created by khoa on 4/13/2016.
 */
public interface IAddressDAO extends IBaseDAO {
    void insertAddress(Addressv1 addressv1);
    Addressv1 find(String addr);
    Studentv1 findStudentLiveIn(String addr);
    List<Addressv1> findAll();
}
