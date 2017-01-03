package khoa.training.hibernate.service;

import khoa.training.hibernate.model.Addressv1;
import khoa.training.hibernate.model.Studentv1;

import java.util.List;

/**
 * Created by trandangkhoa on 4/22/2015.
 */
public interface IStudentService {
    List<Studentv1> findAll();
    List<Addressv1> findAllAddress();
    Studentv1 findStudentLiveIn(String addr);
    Object merge(Object object);
    Studentv1 findById(Integer id);
    void saveOrUpdate(Studentv1 studentv1);
    void updateStudentInAddress(Addressv1 addressv1);
    void insertManyStudent(List<Studentv1> list);
    void findAndUpdateStudentThatLiveIn(String addr);
    void updateStudent(Studentv1 studentv1);
    void deleteList(List<Studentv1> list);
    void deleteAddressList(List<Addressv1> list);
}
